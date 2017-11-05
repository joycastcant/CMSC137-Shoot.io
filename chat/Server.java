import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Server extends Thread {
    private ServerSocket serverSocket;

    public Server(int port) throws IOException {
        try {
            System.out.println("Port: " + port + "\nConnecting...");
            serverSocket = new ServerSocket(port);
            System.out.println("Server at: " + port);
        } catch(IOException e) {
            System.out.println("IO Exception: " + e.getMessage()); 
        }
    }

    public void run() {
        boolean connected = true;    
        System.out.println("Server is open");
        ArrayList<DataOutputStream> arr = new ArrayList<DataOutputStream>();
        while (connected) {
            try {
                Socket client = serverSocket.accept();
                DataInputStream in = new DataInputStream(new BufferedInputStream(client.getInputStream()));
                DataOutputStream out = new DataOutputStream(client.getOutputStream());

                out.writeUTF("WELCOME!");

                arr.add(out);
                SendToServerThread cThread = new SendToServerThread(in, arr, client);
                cThread.start();
                System.out.println("Client accepted: " + client.getRemoteSocketAddress());
                
            } catch(IOException ee) {
                System.out.println("IO Exception: " + ee.getMessage());
            }
        }
   }
   
   public static void main(String args[]) {
        try {
            int port = Integer.parseInt(args[0]);
            Thread t = new Server(port);
            t.start();
        } catch(IOException e) {
            System.out.println("Usage: java Server <port no.>\n"+
                    "Make sure to use valid ports (greater than 1023)");
        } catch(ArrayIndexOutOfBoundsException ee) {
            System.out.println("Usage: java Server <port no.>\n"+
                    "Insufficient arguments given.");
        }
    }
}