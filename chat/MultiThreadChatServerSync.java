import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList; 
import java.net.ServerSocket;

public class MultiThreadChatServerSync {

  private static ServerSocket serverSocket = null;
  private static Socket clientSocket = null;
  private static final ArrayList<clientThread> threads = new ArrayList<clientThread>();
  public static void main(String args[]) {

    int portNumber = 2222;
    if (args.length < 1) {
      System.out.println("Usage: java MultiThreadChatServerSync <portNumber>\n"
          + "Now using port number=" + portNumber);
    } else {
      portNumber = Integer.parseInt(args[0]);
      System.out.println("Server Running...\n"
          + "Now using port number=" + portNumber);
    }

    try {
      serverSocket = new ServerSocket(portNumber);
    } catch (IOException e) {
      System.out.println(e);
    }

    while (true) {
      try {
        clientSocket = serverSocket.accept();
        threads.add(new clientThread(clientSocket, threads));
        threads.get(threads.size()-1).start();
      } catch (IOException e) {
        System.out.println(e);
      }
    }
  }
}




//Credits to: http://makemobiapps.blogspot.com/p/multiple-client-server-chat-programming.html