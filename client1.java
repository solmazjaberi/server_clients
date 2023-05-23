package clients;
// steps:
// 1. create a client socket and connect to the server socket
// 2. read the specification file from the server
// 3. write the specification file to the client outputs directory

import java.io.*;
import java.net.Socket;

public class client1 {
    public static void main(String[] args) {
        int portNumber = "PORT_NUMBER";
        try {
            String hostName = "localhost";
            String outputDirectory = "client_outputs";
            //create a directory for the client outputs to store the received files
            File directory = new File(outputDirectory);
            if (!directory.exists()) {
                directory.mkdir();
            }
            while (true) {
                //connect to the server
                Socket socket = new Socket(hostName, portNumber);
                System.out.println("Connected to the server!");
                //read the specification file from the server
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String specificationFileContent = in.readLine();
                //write the specification file to the client outputs directory
                File specificationFile = new File(directory.getPath() + "/specification.txt");
                FileWriter fileWriter = new FileWriter(specificationFile);
                fileWriter.write(specificationFileContent);
                fileWriter.close();

                //close the connection
                in.close();
                socket.close();
                System.out.println("Connection closed!");
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port " + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }

    }

}
