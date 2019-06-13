package lab6;

import lab3.Malishka;
import lab3.MalishkaHelper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
    private static void connect() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Введите IP-адрес и порт:\n");
        String[] addr = new String[0];
        boolean addressCorrect = false;
        while (!addressCorrect) {
            if (!scan.hasNextLine())
                System.exit(0);
            addr = scan.nextLine().trim().split(":");

            addressCorrect = addr.length == 2 &&
                    addr[1].matches("^\\d+$") &&
                    Integer.parseInt(addr[1]) > 2000 &&
                    Integer.parseInt(addr[1]) < 65536;

            if (!addressCorrect) {
                System.out.print("Адрес некорректен. Введите еще раз:\n");
            }
        }

        String ip = addr[0];
        int port = Integer.parseInt(addr[1]);
        ClientHandler cli = new ClientHandler(ip, port);
        Thread clientThread = cli.readThread();
        String command;
        while (!cli.threadStopped(clientThread)) {
            if (!scan.hasNextLine() || (command = scan.nextLine()).trim().equals("exit")) {
                cli.sendCommand("exit");
                break;
            }
            cli.sendCommand(command);
            if (command.trim().startsWith("import")) {
                File f = new File(command.trim().split("\\s+")[1]);
                try {
                    MalishkaHelper malhelp = new MalishkaHelper(f);
                    ArrayList<Malishka> l = new ArrayList<>();
                    Files.lines(f.toPath()).skip(1).forEach((m) -> l.add(malhelp.fromCSV(m)));
                    cli.sendLines(Files.lines(f.toPath()).skip(1).count());
                    for (Malishka m : l) {
                        cli.sendBytes(m);
                    }
                } catch (IOException e) {
                    System.out.println("Ошибка при работе с файлом.");
                }
            }
        }
            if (cli.threadStopped(clientThread)) {
                System.out.println("Потеряна связь с сервером. ¿Quires подключиться заново?");
                if (scan.hasNextLine() && scan.nextLine().trim().equals("y")) {
                    cli.close();
                    connect();
                }
            } else {
                cli.stopThread(clientThread);
            }
        }

    public static void main(String args[]) {
        connect();
    }
}
