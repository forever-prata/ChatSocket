package designpattern;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServidorSocket {
    private static ServidorSocket instance;
    private ServerSocket serverSocket;
    private List<ClientHandler> clients = new ArrayList<>();

    private ServidorSocket() {
        try {
            serverSocket = new ServerSocket(7000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized ServidorSocket getInstance() {
        if (instance == null) {
            instance = new ServidorSocket();
        }
        return instance;
    }

    public void start() {
        System.out.println("RODANDO SERVIDOR...");
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clients.add(clientHandler);
                Thread clientThread = new Thread(clientHandler);
                clientThread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void broadcastMessage(String message, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }

    public static void main(String[] args) {
        ServidorSocket server = ServidorSocket.getInstance();
        server.start();
    }
}
