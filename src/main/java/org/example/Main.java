package org.example;


import java.io.* ;
import java.net.*;


public class Main {
    private static int connectionCount = 0 ;

    public static void main(String[] args) {

        int port = 3000 ;

        try(ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Server is listening on port " + port );

            while(true){

                Socket socket = serverSocket.accept();
                System.out.println("New client connected...");

                synchronized(Main.class){
                    connectionCount++;
                    System.out.println("ActiveConnections: " + connectionCount );
                }

                new ClientHandler(socket).start();
            }

        } catch (IOException e) {
            System.out.println("Server exception : " + e.getMessage() );
            throw new RuntimeException(e);
        }


    }

public static void removeOneConnection() {
        if(connectionCount > 0){
            connectionCount--;
        }

        System.out.println("Connection closed => new Connection Counter : " + connectionCount);
}




}