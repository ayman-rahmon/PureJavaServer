package org.example;


import java.io.* ;
import java.net.*;


public class Main {

    public static void main(String[] args) {

        int port = 3000 ;

        try(ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Server is listening on port " + port );

            while(true){

                Socket socket = serverSocket.accept();
                System.out.println("New client connected...");


                new ClientHandler(socket).start();
            }

        } catch (IOException e) {
            System.out.println("Server exception : " + e.getMessage() );
            throw new RuntimeException(e);
        }


    }






}