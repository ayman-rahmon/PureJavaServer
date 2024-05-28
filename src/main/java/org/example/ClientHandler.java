package org.example;

import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread{
    private Socket socket ;

    public ClientHandler(Socket socket){
        this.socket  = socket ;
    }

    public void run() {

        try (InputStream input = socket.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(input));
             OutputStream output = socket.getOutputStream();
             PrintWriter writer = new PrintWriter(output, true))
        {
            String text ;

            while((text = reader.readLine()) != null){
                System.out.println("Recieved from client : " + text);
                writer.println("Echo: " + text);

                if("bye".equals(text)){
                    System.out.println("Client Disconnected...");
                    break ;
                }
            }
            socket.close();
        }catch (IOException ex){
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

    }




}
