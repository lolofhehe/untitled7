package lab6;

import lab7.*;
import com.google.gson.JsonSyntaxException;
import lab3.MalishkaHelper;

import java.io.*;
import java.net.Socket;
import java.nio.CharBuffer;
import java.util.Scanner;

public class ThreadHandler extends Thread {
    private Socket incoming;

    public ThreadHandler(Socket incomingSocket) {
        incoming = incomingSocket;
    }

    public void run() {
        try {
            InputStream in = incoming.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            CharBuffer byb = CharBuffer.allocate(512);
            Integer id = Authorizer.registerInstance().apply(incoming);
                try {
                    MalishkaHelper malHelp = new MalishkaHelper(new File("csvs.csv"));
                    ServerWriter.write(incoming, "Введите команду:");
                    while (br.read(byb) > 0) {
                        byb.flip();
                        Scanner inputScanner = new Scanner(byb);
                    String[] command = inputScanner.nextLine().trim().split("\\s+", 2);
                    try {
                        switch (command[0]) {
                            case "add":
                                ServerWriter.write(incoming, malHelp.add(command[1], id));
                                break;
                            case "show":
                                malHelp.show(incoming, id);
                                break;
                            case "remove_lower":
                                ServerWriter.write(incoming, malHelp.remove_lower(command[1], id));
                                break;
                            case "remove":
                                ServerWriter.write(incoming, malHelp.remove(command[1], id));
                                break;
                            case "add_if_max":
                                ServerWriter.write(incoming, malHelp.add_if_max(command[1], id));
                                break;
                            case "exit":
                                inputScanner.close();
                                break;
                            case "help":
                                ServerWriter.write(incoming, malHelp.help());
                                break;

                            default:
                                System.out.println("Не введена ни одна из команд.");
                                ServerWriter.write(incoming, "Не введена ни одна из команд.");
                                break;

                        }
                    } catch (JsonSyntaxException j) {
                        System.out.println("Неверно введен элемент JSON.");
                        ServerWriter.write(incoming, "Неверно введен элемент JSON.");
                    } catch (ArrayIndexOutOfBoundsException a) {
                        System.out.println("За гранью.");
                        ServerWriter.write(incoming, "За гранью.");
                    }
                    byb.flip();
                    ServerWriter.write(incoming, "Введите команду:");
                    byb.clear();}
                } catch (IOException ex) {
                    System.out.println("Сеанс окончен.");
                    ServerWriter.write(incoming, "Сеанс окончен.");
                }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
