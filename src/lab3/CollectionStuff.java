package lab3;

import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class CollectionStuff {
    public static void main(String args[]){
        System.out.println("Введите имя файла:");
        Scanner inputScanner = new Scanner(System.in);
        String file =  inputScanner.nextLine().trim();
        try {
            MalishkaHelper malHelp = new MalishkaHelper(new File(file));
            malHelp.readFromFile();
            System.out.println("Введите команду:");
            do {
                String[] command = inputScanner.nextLine().split("\\s+", 2);
                try{
                    switch (command[0]) {
                        case "add":
                                malHelp.add(command[1]);

                            break;
                        case "show":
                            malHelp.show();
                            break;
                        case "remove_lower":
                                malHelp.remove_lower(command[1]);
                            break;
                        case "remove":
                                malHelp.remove(command[1]);
                            break;
                        case "add_if_max":
                                malHelp.add_if_max(command[1]);
                            break;
                        case "load":
                            malHelp.load(command[1]);
                            break;
                        case "exit":
                            inputScanner.close();
                            System.exit(0);
                            break;
                        case "info":
                            malHelp.info();
                            break;
                        case "help":
                            malHelp.help();
                            break;
                        case "":
                            break;
                        default:
                            System.out.println("Не введена ни одна из команд.");
                            break;

                    }
                } catch (JsonSyntaxException j){
                    System.out.println("Неверно введен элемент JSON.");
                } catch (ArrayIndexOutOfBoundsException a){
                    System.out.println("Не введена ни одна из команд.");
                }
                System.out.println("Введите команду:");
            } while (inputScanner.hasNextLine());
        }
        catch (IOException ex){
            System.out.println("Ошибка при работе с файлом.");
            inputScanner.close();
        }
    }
}

