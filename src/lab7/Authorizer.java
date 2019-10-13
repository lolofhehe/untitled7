package lab7;

import lab6.ServerWriter;

import java.io.*;
import java.net.Socket;
import java.sql.ResultSet;
import java.util.function.Function;


public class Authorizer {

    public static Function<Socket, Integer> registerInstance() {
        return socket -> {
            try (DBManager manager = new DBManager()) {
                ServerWriter.write(socket, "Регистрация\nВведите адрес электронной почты:");
                String email = readEmail(socket);

                ResultSet set = manager.getQuery("SELECT * FROM users WHERE email LIKE ?;", email);
                if (set.next()) {
                    ServerWriter.write(socket, "Данный пользователь уже зарегистрирован. Забыли пароль? y/n");
                    if (ServerWriter.read(socket).equals("y")) {
                        forgotPasswordInstance().apply(socket);
                    }
                    return authorizeInstance().apply(socket);
                } else {
                    Password password = new Password();

                    MailManager sender = new MailManager();
                    sender.sendMessage(email, "Пароль от БД", "Пароль:\n" + password.get());


                    manager.update("INSERT INTO users(email,password) VALUES (?,?);",
                            email, password.getHashCode());
                    ServerWriter.write(socket, "Регистрация успешно выполнена.");
                    return authorizeInstance().apply(socket);
                }
            } catch (NullPointerException exc) {
                return null;
            } catch (Exception exc) {
                System.out.println("Не удалось зарегистрировать пользователя.");
                ServerWriter.write(socket, "Вы не зарегистрированы.");
                exc.printStackTrace();
                return null;
            }
        };
    }

    public static Function<Socket, Integer> authorizeInstance() {
        return socket -> {
            try (DBManager manager = new DBManager()) {
                ServerWriter.write(socket, "Авторизация\nВведите адрес электронной почты:");
                String email = readEmail(socket);

                ResultSet set = manager.getQuery("SELECT * FROM users WHERE email LIKE ?;", email);
                if (!set.next()) {
                    ServerWriter.write(socket, "Такого пользователя не существует.");
                    return null;
                }

                ServerWriter.write(socket, "Пароль:");
                Password password = new Password(ServerWriter.read(socket));

                String correctHash = set.getString("password");
                while ( !password.getHashCode().equals( correctHash ) ) {
                    ServerWriter.write(socket, "Неверно введен пароль. Забыли пароль? y/n");
                    if (ServerWriter.read(socket).equals("y"))
                        correctHash = forgotPasswordInstance().apply(socket);

                    ServerWriter.write(socket, "Пароль:");
                    password = new Password(ServerWriter.read(socket));
                }

                ServerWriter.write(socket, "Авторизация выполнена.");
                return set.getInt("id");
            } catch (NullPointerException exc) {
                return null;
            }  catch (Exception exc) {
                System.out.println("Невозможно авторизовать пользователя.");
                ServerWriter.write(socket, "Вы не авторизованы.");
                exc.printStackTrace();
                return null;
            }
        };
    }

    private static Function<Socket, String> forgotPasswordInstance() {
        return socket -> {
            try (DBManager manager = new DBManager()) {
                ServerWriter.write(socket, "Восстановление пароля\nВведите адрес электронной почты:");
                String email = readEmail(socket);

                Password password = new Password();
                ResultSet set = manager.getQuery("SELECT * FROM users WHERE email LIKE ?;", email);
                if (!set.next()) {
                    ServerWriter.write(socket, "Данного пользователя не существует.");
                    return null;
                }

                manager.update("UPDATE users SET password=? WHERE email LIKE ?;", password.getHashCode(), email);

                MailManager sender = new MailManager();
                sender.sendMessage(email, "Восстановление пароля",
                                "Ваш новый пароль:\n" + password.get());

                ServerWriter.write(socket, "Доступ восстановлен, новый пароль выслан вам на почту.");
                return password.getHashCode();
            } catch (NullPointerException exc) {
                return null;
            }  catch (Exception exc) {
                System.out.println("Невозможно восстановить пароль.");
                ServerWriter.write(socket, "Пароль не восстановлен.");
                exc.printStackTrace();
                return null;
            }
        };
    }

    private static String readEmail(Socket socket) throws IOException {
        String email = ServerWriter.read(socket);
        while (!email.matches("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)$")) {
            ServerWriter.write(socket, "Формат неверен. Введите e-mail:");
            email = ServerWriter.read(socket);
        }
        return email;
    }
}
