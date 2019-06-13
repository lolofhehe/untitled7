package lab3;

import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;

import com.google.gson.*;

public class MalishkaHelper {
    private final String headers = "name, distributedHarpType, isPlaying";
    private File malFile;
    private File defaultFile = new File("csvs.csv");

    private Scanner malScanner;
    private Gson gson = new Gson();
    private ConcurrentSkipListSet<Malishka> skipl;
    private final Date initDate = new Date();

    private void setScanner(File file) throws IOException{
        try{malScanner = new Scanner(file);}
        catch (IOException ex){
            throw new IOException();
        }
    }

    public File getMalFile() {
        return malFile;
    }

    private void setMalFile(File file) {
        this.malFile = file;
    }

    public void updateCollection(){
        try {
            FileWriter fileWriter = new FileWriter(malFile,    false);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            writer.write(headers + "\n");
            for (Malishka mal : skipl) {
                writer.write(mal.toCSV() + "\n");
            }
            writer.close();
        }
        catch (IOException exc) {
            System.out.println("Нет доступа к записи в файл.");
            exc.printStackTrace();
        }

        System.out.printf("Записано %d малышек.\n", skipl.size());
    }

    public void readFromFile(){
        skipl = new ConcurrentSkipListSet<>();
        try{malScanner.nextLine();}
        catch (NoSuchElementException e){
            System.out.println("Малышек нет.");
        }
        int malcounter = 0;
        while (malScanner.hasNext()){
            Malishka mal = fromCSV(malScanner.nextLine());

            skipl.add(mal);
            malcounter++;
        }
        try {setMalFile(defaultFile);
            setScanner(defaultFile);
        } catch (IOException e) {
            System.out.println("Файл недоступен.");
            e.printStackTrace();
        }
        System.out.printf("Создано %d малышек.\n", malcounter);
    }
    public MalishkaHelper(File file) throws IOException {
        if (!file.exists()){
            file.createNewFile();
        }
        this.malFile = file;
        skipl = new ConcurrentSkipListSet<>();
        setScanner(file);
    }

    public Malishka convertCommandtoMalishka(String jsonCom){
        jsonCom.replace("\"","\\\"");
        //add {"name": "Alfa", "distributedHarpType": "LARGE", "isPlaying": "true"}
        Malishka mal = gson.fromJson(jsonCom, Malishka.class);
        mal.setDistributedHarp(new Harp());
        mal.distributedHarp.setType(HarpType.matchByName(mal.getDistributedHarpType()));
        return mal;
    }
    /** Выводит в стандартный поток вывода все элементы коллекции в строковом представлении
     */
    public ArrayList<String> show() throws IOException{
        ArrayList<String> mals = new ArrayList<>(skipl.size());
        Files.lines(getMalFile().toPath()).skip(1).forEach(mals::add);
        if (mals.size() == 0){
            mals.add("Малышек нет.");
        }
        return mals;
    }
    /** Добавляет новый элемент в коллекцию
    @param command  Введенная пользователем команда
     */
    public String add(String command) {
        if (skipl.add(convertCommandtoMalishka(command))) {
            updateCollection();
            System.out.println("Малышка успешно добавлена.");
            return "Малышка успешно добавлена.";
        } else {
            System.out.println("Малышка не добавлена.");
            return "Малышка не добавлена.";
        }
    }

    /**Перечитывает коллекцию из файла*/
    public String load(String filename) throws IOException{
        File f = new File(filename);
        setMalFile(f);
        setScanner(f);
        readFromFile();
        updateCollection();
        return "Перечитывание успешно.";
    }

    /**  Удаляет элемент из коллекции по его значению
    @param command  Введенная пользователем команда
     */
    public String remove(String command){
        if (skipl.remove(convertCommandtoMalishka(command))) {
            updateCollection();
            System.out.println("Малышка успешно удалена.");
            return "Малышка успешно удалена.";
        } else {
            System.out.println("Малышка не удалена.");
            return "Малышка не удалена.";
        }
    }

    /**
    Добавляет новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции
        @param command  Введенная пользователем команда
     */
    public String add_if_max(String command) {
        Malishka search = convertCommandtoMalishka(command);
        if (skipl.equals(skipl.stream().filter((s) -> s.compareTo(search) < 0).collect(Collectors.toSet()))) {
            skipl.add(search);
            updateCollection();
            System.out.println("Наибольшая малышка успешно добавлена.");
            return "Наибольшая малышка успешно добавлена.";
        }else{
            System.out.println("Не добавлено.");
        }
            return "Малышка не наибольшая.";
        }


    /** Удаляет из коллекции все элементы, меньшие, чем заданный
    @param command  Введенная пользователем команда
     */
    public String remove_lower (String command) throws ArrayIndexOutOfBoundsException{
        Malishka search = convertCommandtoMalishka(command);
        long removeCounter = skipl.stream().filter((s)->s.compareTo(search)<0).count();
        skipl = new ConcurrentSkipListSet<>(skipl.stream().filter((s)->!(s.compareTo(search)<0)).collect(Collectors.toSet()));
        updateCollection();
        System.out.printf("Удалено %d малышек.\n", removeCounter);
        return String.format("Удалено %d малышек.", removeCounter);
    }

    /**
    Выводит в стандартный поток вывода информацию о коллекции
     */
    public String info(){
        String s = "Число элементов коллекции: " + skipl.size() + "\nВремя инициализации: " + initDate + "\nТип коллекции: " + skipl.getClass();
        System.out.println(s);
        return s;
    }
    public String save(String filename) throws IOException{
        setMalFile(new File(filename));
        updateCollection();
        System.out.println("Файл успешно сохранен.");
        setMalFile(defaultFile);
        return "Файл успешно сохранен.";
    }

    public  void help(){
        System.out.println("show - Выводит в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                "add {element}- Добавляет новый элемент в коллекцию\n" +
                "load [filename] - Перечитывает коллекцию из файла\n" +
                "remove {element} - Удаляет элемент из коллекции по его значению\n" +
                "add_if_max {element} - Добавляет новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции\n" +
                "remove_lower {element} - Удаляет из коллекции все элементы, меньшие, чем заданный\n" +
                "info - Выводит в стандартный поток вывода информацию о коллекции\n" + "exit - Выход из программы\n" +
                "save - Сохраняет содержимое коллекции в указанном файле\n"+ "import [filename] - Переносит содержимое файла в коллекцию на сервера");
    }
    public Malishka fromCSV(String s){
        Malishka mal = new Malishka();
        mal.setDistributedHarp(new Harp());
        String malString[] = s.split(",");
        mal.setName(malString[0]);
        mal.setDistributedHarpType(malString[1]);
        mal.setDistributedHarp(new Harp());
        mal.distributedHarp.setType(HarpType.matchByName(malString[1]));
        mal.setPlayingByString(malString[2]);
        return mal;
    }

    public void addMalDirect(ConcurrentSkipListSet mal){
        skipl = mal;
        updateCollection();
    }

}
