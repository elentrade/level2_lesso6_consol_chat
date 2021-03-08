package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final int PORT = 8189;

    public static void main(String[] args) {
        Socket socket = null;
        Scanner scanner = new Scanner(System.in);
        try {
            socket = new Socket("localhost",PORT);
            System.out.println("Подключился к серверу");
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
                                String str = in.readUTF();
                                if (str.equals("/end")) {
                                    System.out.println("Сервер отключился");
                                    break;
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        finally {
                            System.out.println("Мы отключились от сервера");

                        }
                    }

           }).start();

        }

        catch (IOException e){
            e.printStackTrace();
        }
//      finally {
//            try {
//                socket.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        }

    }

