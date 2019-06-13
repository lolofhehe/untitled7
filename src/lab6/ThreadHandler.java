package lab6;

import com.google.gson.JsonSyntaxException;
import lab3.Malishka;
import lab3.MalishkaHelper;

import java.io.*;
import java.net.Socket;
import java.nio.CharBuffer;
import java.util.Scanner;
import java.util.concurrent.ConcurrentSkipListSet;

public class ThreadHandler extends Thread {
    private Socket incoming;

    public ThreadHandler(Socket incomingSocket) {
        incoming = incomingSocket;
    }

    public void run() {
        try {
            InputStream in = incoming.getInputStream();
            OutputStream out = incoming.getOutputStream();
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(out, "UTF-8"), true);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            CharBuffer byb = CharBuffer.allocate(512);
                try {
                    MalishkaHelper malHelp = new MalishkaHelper(new File("csvs.csv"));
                    malHelp.readFromFile();
                    writer.println("Введите команду:");
                    while (br.read(byb) > 0) {
                        byb.flip();
                        Scanner inputScanner = new Scanner(byb);
                    String[] command = inputScanner.nextLine().trim().split("\\s+", 2);
                    try {
                        switch (command[0]) {
                            case "add":
                                writer.println(malHelp.add(command[1]));
                                break;
                            case "show":
                                for(String s: malHelp.show()){
                                    if(s!=null){
                                    writer.println(s);}
                                   }
                                break;
                            case "remove_lower":
                                writer.println(malHelp.remove_lower(command[1]));
                                break;
                            case "remove":
                                writer.println(malHelp.remove(command[1]));
                                break;
                            case "add_if_max":
                                writer.println(malHelp.add_if_max(command[1]));
                                break;
                            case "load":
                                writer.println(malHelp.load(command[1]));
                                break;
                            case "save":
                                writer.println(malHelp.save(command[1]));
                                break;
                            case "exit":
                                inputScanner.close();
                                break;
                            case "info":
                                writer.println(malHelp.info());
                                break;
                            case "help":
                                writer.println("show - Выводит в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                                        "add {element}- Добавляет новый элемент в коллекцию\n" +
                                        "load - Перечитывает коллекцию из файла\n" +
                                        "remove {element} - Удаляет элемент из коллекции по его значению\n" +
                                        "add_if_max {element} - Добавляет новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции\n" +
                                        "remove_lower {element} - Удаляет из коллекции все элементы, меньшие, чем заданный\n" +
                                        "info - Выводит в стандартный поток вывода информацию о коллекции\n" + "exit - Выход из программы");
                                break;
                            case "import":
                                byb.clear();
                                int i = 0;
                                br.read(byb);
                                byb.flip();
                                char[] chars = new char[byb.limit()];
                                byb.get(chars);
                                String str = new String(chars);
                                byb.clear();
                               ConcurrentSkipListSet<Malishka> malar = new ConcurrentSkipListSet<>();
                                while (i<Integer.parseInt(str)){
                                    ObjectInputStream oi = new ObjectInputStream(in);
                                    try{Malishka mal = (Malishka) oi.readObject();
                                        malar.add(mal);
                                        malHelp.addMalDirect(malar);
                                    i++;}
                                    catch (ClassNotFoundException ex){
                                        System.out.println("Малышка не дошла или дошла по частям.");
                                        writer.println("Малышка не дошла или дошла по частям.");
                                    }
                                }
                                System.out.println("Успешный импорт.");
                                writer.println("Успешный импорт.");
                                break;
                            default:
                                System.out.println("Не введена ни одна из команд.");
                                writer.println("Не введена ни одна из команд.");
                                break;

                        }
                    } catch (JsonSyntaxException j) {
                        System.out.println("Неверно введен элемент JSON.");
                        writer.println("Неверно введен элемент JSON.");
                    } catch (ArrayIndexOutOfBoundsException a) {
                        System.out.println("За гранью.");
                        writer.println("За гранью.");
                    } catch(IOException ex){
                        System.out.println("Ошибка при работе с файлом.");
                        writer.println("Ошибка при работе с файлом.");
                    }
                    byb.flip();
                    writer.println("Введите команду:");
                    byb.clear();}
                } catch (IOException ex) {
                    System.out.println("Сеанс окончен.");
                    writer.println("Сеанс окончен.");
                }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
