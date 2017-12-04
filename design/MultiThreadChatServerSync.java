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
    int serverPort = 2221;
    
    if (args.length < 1) {
      System.out.println("Usage: java MultiThreadChatServerSync <portNumber> <serverPort>\n"
          + "Now using port number=" + portNumber + "and server port = " + serverPort);
    } else {
      portNumber = Integer.parseInt(args[0]) + 1;
      serverPort = Integer.parseInt(args[0]);
      System.out.println("Server Running...\n"
          + "Now using port number=" + portNumber + "and server port = " + serverPort);
      try {
        Server s = new Server(Integer.parseInt(args[0]));
        s.start();
      } catch (Exception e) {
        System.out.println(e);
      }
    }

    try {
      serverSocket = new ServerSocket(portNumber);
    } catch (IOException e) {
      System.out.println(e);
    }

    while (true) {
      try {
        // Thread.sleep(50);
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