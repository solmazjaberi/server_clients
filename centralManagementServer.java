package server;
//Goal: A server as the central management can send specification files to the clients
//and the clients can receive the specification files and write them to their outputs directory
//steps:
//1. create a server socket as the central management server:
//      -listens to incoming connections
//      -accepts incoming connections
//      -send specification files to the clients
//2. create a client socket and connect to the server socket
//3. When a client connects, accept the connection and spawn a new thread to handle the client
//4. The thread reads input from the client socket and responds accordingly


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class CentralManagementServer{
    public static void main(String[] args) {
        int port = "PORT_NUMBER";
        try {

            String specificationFile= "specification.txt";
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server is listening on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: "+clientSocket.getInetAddress().getHostAddress());
                //Open th specification file and read its content to a string
                File file = new File(specificationFile);
                BufferedReader br = new BufferedReader(new FileReader(file));
                StringBuilder sb = new StringBuilder();
                String line;
                String ls = System.getProperty("line.separator");
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                    sb.append(ls);
                }
                //delete the last new line separator
                sb.deleteCharAt(sb.length() - 1);
                br.close();
                String specificationFileContent = sb.toString();
                //send the specification file to the client
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                out.println(specificationFileContent);
                //close the connection
                out.close();
                clientSocket.close();
                System.out.println("specification file sent to client!");
                //ClientHandler clientHandler = new ClientHandler(clientSocket);
                //clientHandler.start();
            }

        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port 5151 or listening for a connection");
            System.out.println(e.getMessage());
        }
    }

}
