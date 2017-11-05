import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {
    public static void main(String args[]) {
        try{
            String server = args[0];
            int port = Integer.parseInt(args[1]);
            String name = args[2];

            Socket serverSocket = new Socket(server, port);
            System.out.println("Just connected to " + serverSocket.getRemoteSocketAddress());

            DataOutputStream oStream = new DataOutputStream(serverSocket.getOutputStream());

            InputStream inFromServer = serverSocket.getInputStream();
            DataInputStream iStream = new DataInputStream(inFromServer);

            Scanner scanner = new Scanner(System.in);
            String message = "";
            
            while (!message.equals("end")) {
                try {
                    System.out.println(iStream.readUTF());
                } catch(IOException ee) {
                    System.out.println("IO Exception: " + ee.getMessage());
                }
                
                try {
                    System.out.print("You: ");
                    message = scanner.nextLine();
                    oStream.writeUTF(name + ": " + message);
                    oStream.flush();
                } catch(IOException e) {
                    System.out.println("IO Exception: " + e.getMessage());
                }
            }

            System.out.println("You have disconnected from the server.");
            scanner.close();

            serverSocket.close();
        }catch(IOException e){
            e.printStackTrace();
            System.out.println("Cannot find (or disconnected from) Server");
        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Usage: java Client <server ip> <port no.> <your name>");
        }
    }
}

