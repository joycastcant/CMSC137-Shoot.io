import java.io.*;
import java.net.*;
import java.util.Scanner;

public class MultiThreadChatClient implements Runnable {

  private static Socket clientSocket = null;
  private static DataOutputStream os = null;
  private static DataInputStream is = null;

  private static BufferedReader inputLine = null;
  private static boolean closed = false;
  
  public static void main(String[] args) {

    int portNumber = 2222;
    String host = "127.0.0.1";
    String name = "Anonymous";

    try {
        host = args[0];
        portNumber = Integer.parseInt(args[1]);
        name = args[2];
    } catch(ArrayIndexOutOfBoundsException e){
        System.out.println("Usage: java Client <server ip> <port no.> <your name>");
    }

    try {
      clientSocket = new Socket(host, portNumber);
      inputLine = new BufferedReader(new InputStreamReader(System.in));
      os = new DataOutputStream(clientSocket.getOutputStream());
      is = new DataInputStream(clientSocket.getInputStream());
    } catch (UnknownHostException e) {
      System.err.println("Don't know about host " + host);
    } catch (IOException e) {
      System.err.println("Couldn't get I/O for the connection to the host "
          + host);
    }

    if (clientSocket != null && os != null && is != null) {
      try {

        new Thread(new MultiThreadChatClient()).start();
        while (!closed) {
          os.writeUTF(name + ": " + inputLine.readLine().trim());
        }
        os.close();
        is.close();
        clientSocket.close();
      } catch (IOException e) {
        System.exit(0);
      }
    }
  }

  public void run() {
    String responseLine;
    try {
      while ((responseLine = is.readUTF()) != null) {
        System.out.println(responseLine);
      }
      closed = true;
    } catch (IOException e) {
      System.exit(0);
    }
  }
}


//Credits to: http://makemobiapps.blogspot.com/p/multiple-client-server-chat-programming.html