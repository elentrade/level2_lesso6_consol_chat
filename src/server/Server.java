package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server{
    private static final int PORT = 8189;

    public static void main(String[] args) {
        ServerSocket server = null;
        Socket socket = null;
        Scanner scanner = new Scanner(System.in);
        try {
            server = new ServerSocket(PORT);
            System.out.println("Сервер запущен, сокет для подключения выдлен");
            socket = server.accept();
            System.out.println("Клиент подключился");
            //входящие и исходящие данные
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
           new Thread(new Runnable() {
               @Override
               public void run() {
                   while (true){
                       //считывать сообщение из консоли и передать в исходящий поток
                       try {
                           out.writeUTF(scanner.nextLine());
                       } catch (IOException e) {
                           e.printStackTrace();
                       }
                   }
               }
           }).start();
           new Thread(new Runnable() {
               @Override
               public void run() {
                   try {
                   while (true) {
                       //считывать сообщение из входящего потока
                       String str = in.readUTF();
                       System.out.println("Клиент " + str);
                       if (str.equals("/end")) {
                           System.out.println("Клиент покинул чат");
                           break;
                       }
                   }
                       } catch (IOException e) {
                           e.printStackTrace();
                       }

                   }

           }).start();

            }

        catch (IOException e){
            e.printStackTrace();
        }
        finally {
//закрытие ресурсов сервер и сокет
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
