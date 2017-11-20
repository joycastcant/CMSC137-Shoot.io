import java.io.*;
import java.net.*;
import java.util.Scanner;

public class MultiThreadChatClient implements Runnable {

  private static Socket clientSocket = null;
  private static DataOutputStream os = null;
  private static DataInputStream is = null;
  private String host;
  private int portNumber;
  private static String name;
  private Game game;

  // private static BufferedReader inputLine = null;
  private static boolean closed = false;

  public MultiThreadChatClient(String host, String portNumber, String name, Game game) {

    //Default Values
    // int portNumber = 2222;
    // String host = "127.0.0.1";
    // String name = "Anonymous";
    
    this.game = game;
    try {
        this.host = host;
        this.portNumber = Integer.parseInt(portNumber);
        this.name = name;
    } catch(ArrayIndexOutOfBoundsException e){
        System.out.println("Usage: java Client <server ip> <port no.> <your name>");
    }
    
    //Initialization
    try {
      clientSocket = new Socket(InetAddress.getByName(this.host), this.portNumber);
      // inputLine = new BufferedReader(new InputStreamReader(System.in));
      os = new DataOutputStream(clientSocket.getOutputStream());
      is = new DataInputStream(clientSocket.getInputStream());
    } catch (UnknownHostException e) {
      System.err.println("Don't know about host " + this.host);
    } catch (IOException e) {
      System.err.println("Couldn't get I/O for the connection to the host "
          + host);
    }
    new Thread(
      new Runnable() {
        public void run(){
          String responseLine;
          try {
            while ((responseLine = is.readUTF()) != null) {
              String msg = "     " + responseLine + "\n";
              game.appendMsg(msg);
            }
            closed = true;
          } catch (IOException e) {
            System.exit(0);
          }
          // if (clientSocket != null && os != null && is != null) {
          //   try {
          //     // new Thread(this).start();
          //     while (!closed) {
          //       os.writeUTF(name + ": " + inputLine.readLine());
          //     }
          //     os.close();
          //     is.close();
          //     clientSocket.close();
          //   } catch (IOException e) {
          //     System.exit(0);
          //   }
          // }
        }
      }
    ).start();
  }

  public void run() {
    if (clientSocket != null && os != null && is != null) {
      try {
        // new Thread(this).start();
        while(!closed) {
          String message = this.game.getMessage();
          while(message.equals("")) {
            message = this.game.getMessage();
            try{
              Thread.sleep(10);
            } catch (InterruptedException e) {
              System.out.println(e);
            }
          }
          this.game.setMessage("");
          os.writeUTF(name + ": " + message); 
        }
      } catch (IOException e) {
        try{
          os.close();
          is.close();
          clientSocket.close();
        } catch (IOException err) {}
        System.exit(0);
      }
    }
    // String responseLine;
    // try {
    //   while ((responseLine = is.readUTF()) != null) {
    //     System.out.println(responseLine);
    //   }
    //   closed = true;
    // } catch (IOException e) {
    //   System.exit(0);
    // }
  }
}


//Credits to: http://makemobiapps.blogspot.com/p/multiple-client-server-chat-programming.html