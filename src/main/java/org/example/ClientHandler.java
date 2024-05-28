package org.example;

import org.junit.Test;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ClientHandler extends Thread{
    private Socket socket ;

    public ClientHandler(Socket socket){
        this.socket  = socket ;
    }

    public void run() {

        try{
        socket.setSoTimeout(5000); //3000000 =  5 mins...
        InputStream input = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        OutputStream output = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(output, true);
        try{
            // set timeOut for the connection...

// TRY 1 ===============> simple echo back message server...
            String text ;

            while((text = reader.readLine()) != null){
                System.out.println("Recieved from client : " + text);
                writer.println("Echo: " + text);

                if("bye".equals(text)){
                    System.out.println("Client Disconnected...");
                    break ;
                }
            }


// TRY 2 ===============> actual GET and routing action (simple)...

//            String requestLine = reader.readLine() ;
//            if(requestLine == null) return;
//            String[] requestParts = requestLine.split(" ");
//            String method = requestParts[0];
//            String path = requestParts[1];
//            // Read the rest of the headers (for now , we'll ignore them)...
//            while(reader.readLine().length() != 0) {
//                //Ignore Headers...
//            }
//
//            // handle different GET PATHS
//            if(method.equals("GET")) {
//                switch(path){
//                    case "/" :
//                        writer.println("HTTP/1.1 200 OK");
//                        writer.println("Content-Type: text/html");
//                        writer.println();
//                        writer.println("<h1>Welcome to the Home Page </h1>");
//                    break ;
//                    case "/about":
//                        writer.println("HTTP/1.1 200 OK");
//                        writer.println("Content-Type: text/html");
//                        writer.println();
//                        writer.println("<h1> About US</h1>");
//                    break;
//                    default:
//                        writer.println("HTTP/1.1 404 Not Found");
//                        writer.println();
//                        writer.println("<h1>404 Not Found</h1>");
//                        break;
//                }
//            }

            socket.close();
        }catch (SocketTimeoutException ex){
//            socket.close();
//            Main.removeOneConnection();
            // Handle timeout without throwing an error
            System.out.println("Connection timed out. Closing connection.");
            writer.println("HTTP/1.1 408 Request Timeout");
            writer.println(); // Send an HTTP response for timeout if necessary
//            System.out.println( "Timeout Exception : " + ex.getMessage());
//            ex.printStackTrace();
        }finally{
            closeSocket();
            synchronized (Main.class){
                Main.removeOneConnection();
            }
        }

        }catch(IOException ex) {
            System.out.println( "IO Exception  : "  + ex.getMessage());
            ex.printStackTrace();


        }


    }


    private void closeSocket() {
        try{
            if(socket != null){
                socket.close();
            }
        }catch (IOException ex){
            System.out.println("Error Closing Socket: " + ex.getMessage());
        }


    }



    @Test
    private void testRequests(BufferedReader reader ) throws IOException {
        String line;
        StringBuilder requestBuilder = new StringBuilder();

        while (!(line = reader.readLine()).isEmpty()) {
            requestBuilder.append(line + "\n");
        }

        System.out.println("Received Request: \n" + requestBuilder.toString());


    }



}
