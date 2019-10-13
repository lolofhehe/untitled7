package lab6;

import lab3.Malishka;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.net.*;


public class ClientHandler {
    private SocketChannel socket;
    private InetSocketAddress inet;
    private ByteBuffer wb;
    private ByteBuffer rb;

    public ClientHandler(String ip, int port) {
        try {
            rb = ByteBuffer.allocate(512);
            wb = ByteBuffer.allocate(512);
            inet = new InetSocketAddress(ip, port);
            socket = SocketChannel.open();
            if (socket.connect(inet)) {
                System.out.println("Подключился.");
            }
        } catch (IOException | UnresolvedAddressException e) {
            System.out.println("Ошибка с подключением к серверу.");
            System.exit(0);
        }
    }

    private class ClientThread extends Thread {

        private boolean isStopped = false;

        private void setStopped() {
            isStopped = true;
        }

        public void run() {
            try {
                while (socket.read(rb) > 0) {
                    rb.flip();
                    byte[] bytes = new byte[rb.limit()];
                    rb.get(bytes);
                    System.out.print(new String(bytes));
                    rb.clear();
                }
            } catch (IOException ex) {
                System.out.println("Чтение окончено.");
            } finally {
                setStopped();
            }
        }
    }

    protected void close() {
        try {
            socket.close();
        } catch (IOException ex) {
            System.out.println("Не удалось закрыть");
        }
    }

    protected Thread readThread() {
        ClientThread cli = new ClientThread();
        cli.start();
        return cli;
    }

    protected void sendCommand(String command) {
        try {
            wb.clear();
            wb.put(command.getBytes());
            wb.flip();
            socket.write(wb);
        } catch (IOException ex) {
            System.out.println("Не удалось отправить");
        }
    }

    protected void stopThread(Thread cli) {
        ((ClientThread) cli).setStopped();
        close();
    }

    public boolean threadStopped(Thread cli) {
        return ((ClientThread) cli).isStopped;
    }

    protected void sendBytes (Malishka mal) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream(wb.capacity());
            ObjectOutput ob = new ObjectOutputStream(bo);
            ob.writeObject(mal);
            byte[] bytes = bo.toByteArray();
            wb.clear();
            wb.put(bytes);
            wb.flip();
            socket.write(wb);
        } catch (IOException ex) {
            System.out.println("Ошибка при отправке малышек.");
            ex.printStackTrace();
        }
    }

    protected void sendLines (long lines){
       try {
           String s = String.valueOf(lines);
           wb.clear();
           wb.put(s.getBytes());
           wb.flip();
           socket.write(wb);
       }catch (IOException e){
           System.out.println("Это невозможно.");
       }
    }
}

