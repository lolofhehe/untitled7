package lab6;

import java.io.*;
import java.net.Socket;
import java.nio.CharBuffer;

public class ServerWriter {

    public static void write(Socket socket, String message){
        try{
            OutputStream out = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(out, "UTF-8"), true);
        writer.println(message);
        } catch (IOException e){
            System.out.println("Неполадки при передаче сообщения.");
        }
    }

    public static String read(Socket socket){
        try{
            InputStream in = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            CharBuffer byb = CharBuffer.allocate(512);
            br.read(byb);
            byb.flip();
            char[] chars = new char[byb.limit()];
            byb.get(chars);
            String str = new String(chars);
            byb.clear();
            return str;
        } catch (IOException e){
            System.out.println("Неполадки при получении сообщения.");
            return "oop";
        }
    }
}
