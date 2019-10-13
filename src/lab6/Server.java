package lab6;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Server {

    public static String dbLogin;
    public static String dbPassword;
    public static String mailLogin;
    public static String mailPassword;
    public static boolean debug = true;

    public static void main(String args[]) {
        try (ServerSocket s = new ServerSocket(11111)) {
            System.out.println("\nПорт: " + s.getLocalPort());
            Scanner scan = new Scanner(System.in);
            System.out.println("Введите логин для БД:");
            dbLogin = scan.nextLine();
            System.out.println("Введите пароль для БД:");
            dbPassword = scan.nextLine();
            System.out.println("Введите адрес электронной почты:");
            mailLogin = scan.nextLine();
            System.out.println("Введите пароль электронной почты:");
            mailPassword = scan.nextLine();

            while (true) {
                Socket incoming = s.accept();
                ThreadHandler th = new ThreadHandler(incoming);
                th.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

