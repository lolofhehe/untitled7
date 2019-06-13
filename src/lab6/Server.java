package lab6;

import java.io.*;
import java.net.*;

public class Server {

    public static void main(String args[]) {
        try (ServerSocket s = new ServerSocket(11111)) {
            System.out.println("\nПорт: " + s.getLocalPort());
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

