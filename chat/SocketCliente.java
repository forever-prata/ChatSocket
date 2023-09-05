package designpattern;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class SocketCliente {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 7000);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            Thread receiveThread = new Thread(() -> {
                try {
                    String message;
                    while ((message = in.readLine()) != null) {
                        System.out.println("Recebido: " + message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            receiveThread.start();

            Scanner scanner = new Scanner(System.in);
            String message = "";
            while (!message.equalsIgnoreCase("sair")) {
                message = scanner.nextLine();
                out.println(message);
            }
            socket.close();
            receiveThread.interrupt();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
