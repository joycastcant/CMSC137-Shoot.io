
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList; 
import java.net.ServerSocket;

public class clientThread extends Thread {

  private String clientName = null;
  private DataInputStream is = null;
  private DataOutputStream os = null;
  private Socket clientSocket = null;
  private final ArrayList<clientThread> threads;

  public clientThread(Socket clientSocket, ArrayList<clientThread> threads) {
    this.clientSocket = clientSocket;
    this.threads = threads;
  }

  public void run() {
    ArrayList<clientThread> threads = this.threads;
    try {
      is = new DataInputStream(clientSocket.getInputStream());
      os = new DataOutputStream(clientSocket.getOutputStream());

      os.writeUTF("Welcome!\nTo leave enter /quit in a new line.");
      synchronized (this) {
        for (int i = 0; i < threads.size(); i++) {
          if (threads.get(i) != null && threads.get(i) != this) {
            threads.get(i).os.writeUTF("A new user entered the chat room !!!");
          }
        }
      }
      while (true) {
        String line = is.readUTF();
        if (line.split(": ").length < 2){	
        	continue;
        } else if (line.split(": ")[1].equals("/quit")) {
          break;
        } else {
          synchronized (this) {
            for (int i = 0; i < threads.size(); i++) {
              if (threads.get(i) != null) {
                threads.get(i).os.writeUTF(line);
              }
            }
          }
        }
      }
      synchronized (this) {
        for (int i = 0; i < threads.size(); i++) {
          if (threads.get(i) != null && threads.get(i) != this) {
            threads.get(i).os.writeUTF("A user is leaving the chat room !");
          }
        }
      }
      
      os.writeUTF("You have been disconnected");

      synchronized (this) {
        for (int i = 0; i < threads.size(); i++) {
          if (threads.get(i) == this) {
            threads.set(i, null);
          }
        }
      }

      is.close();
      os.close();
      clientSocket.close();
    } catch (IOException e) {
      System.exit(0);
    }
  }
}


//Credits to: http://makemobiapps.blogspot.com/p/multiple-client-server-chat-programming.html