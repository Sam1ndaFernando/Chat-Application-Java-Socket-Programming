package com.chatRoom.server;

import com.mysql.cj.xdevapi.ClientFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {


    private final ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void startServer() {

        try{
            System.out.println("[SERVER] server is waiting for clients");
            while(!serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
                System.out.println("SERVER : server is accept a new client");

                Thread thread = new Thread(new ClientHandler(socket));
                thread.start();
            }
        }catch (IOException e){
            closeServer();
            e.printStackTrace();
        }

    }

    private void closeServer() {

        try {
            if (serverSocket != null) {
                System.out.println("SERVER : server is closed");
                serverSocket.close();
            }
            System.out.println("SERVER : server is closed");

        }catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
