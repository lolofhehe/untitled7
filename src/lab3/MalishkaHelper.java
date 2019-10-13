package lab3;

import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.Date;
import java.util.concurrent.ConcurrentSkipListSet;
import java.sql.*;

import com.google.gson.*;
import lab6.ServerWriter;
import lab7.DBManager;

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
        Malishka mal = gson.fromJson(jsonCom, Malishka.class);
        mal.setDistributedHarp(new Harp());
        mal.distributedHarp.setType(HarpType.matchByName(mal.getDistributedHarpType()));
        return mal;
    }
    /** Выводит в стандартный поток вывода все элементы коллекции в строковом представлении
     * @param in - сокет для отправки строк
     * @param id - идентификатор пользователя
     */
    public void show(Socket in, int id){
        try (DBManager manager = new DBManager()) {
            ResultSet res = manager.getQuery("SELECT * FROM malishki ORDER BY name;");
            ServerWriter.write(in,"name," + "\t"+ "height," + "\t" + "creation_time," + "\t" + "harp," + "\t" + "user_id" + "\n" );
            while (res.next()) {
                String name = res.getString("name");
                int height = res.getInt("height");
                String time = res.getString("creation_time");
                String harp = res.getString("harp_type");
                int us_id = res.getInt("user_id");
                ServerWriter.write(in,name + ",\t"+ height + ",\t" + time + ",\t" + harp + ",\t" + us_id );
            }
        }catch (SQLException e){
            ServerWriter.write(in,"Не покажу.");
        }
    }
    /** Добавляет новый элемент в коллекцию
    @param command  Введенная пользователем команда
     @param id - Идентификатор клиента
     */
    public String add(String command, int id) {
         Malishka mal = convertCommandtoMalishka(command);
         try (DBManager manager = new DBManager()){
             manager.update("INSERT INTO malishki ( name, height, creation_time, harp_type, isplaying, user_id) " + String.format( "VALUES ('%s',%d,'%s','%s','%s',%d);",
                     mal.getName(),  mal.getHeight(), mal.getBirthday(), mal.getDistributedHarpType(), mal.isPlaying, id)
             );
             return "Малышка успешно добавлена.";
         }
    }


    /**  Удаляет элемент из коллекции по его значению
    @param command  Введенная пользователем команда
     @param id - Идентификатор клиента
     */
    public String remove(String command, int id){
        Malishka mal = convertCommandtoMalishka(command);
        int deleted;
        try(DBManager manager = new DBManager()){
            deleted = manager.update("DELETE FROM malishki WHERE user_id = ? AND name = ?;", id, mal.getName());
        }
        if (deleted > 0){
            return "Малышка удалена";
        } else {
            return  "Малышка не удалена.";
        }
    }

    /**
    Добавляет новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции
        @param command  Введенная пользователем команда
        @param id - Идентификатор клиента
     */
    public String add_if_max(String command, int id) {
        Malishka mal = convertCommandtoMalishka(command);
        try (DBManager manager = new DBManager()){
            ResultSet res = manager.getQuery("SELECT * FROM malishki WHERE user_id = ? AND height - ? >= 0;", id, mal.getHeight());
            if (res.next() == false){
                add(command, id);
                return "Наибольшая малышка добавлена";
            } else {
                return "Малышка не добавлена.";
            }
        } catch (SQLException ex){
            return "Из-за ошибки наибольшая малышка не добавлена.";
        }
    }


    /** Удаляет из коллекции все элементы, меньшие, чем заданный
    @param command  Введенная пользователем команда
     @param id - Идентификатор клиента
     */
    public String remove_lower (String command, int id) throws ArrayIndexOutOfBoundsException{
        Malishka mal = convertCommandtoMalishka(command);
        int deleted;
        try(DBManager manager = new DBManager()){
            deleted = manager.update("DELETE FROM malishki where user_id = ? AND height < ?", id, mal.getHeight());
        }
        if(deleted > 0){
            return "Удалено " + deleted + " малышек.";
        } else {
            return "Малышки не удалены.";
        }
    }

    public String help(){
        return "show - Выводит в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                "add {element}- Добавляет новый элемент в коллекцию\n" +
                "remove {element} - Удаляет элемент из коллекции по его значению\n" +
                "add_if_max {element} - Добавляет новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции\n" +
                "remove_lower {element} - Удаляет из коллекции все элементы, меньшие, чем заданный\n" +
                "exit - Выход из программы\n" ;
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

}
