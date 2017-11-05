import java.net.*;
import java.util.ArrayList;
import java.io.*;

public class SendToServerThread extends Thread {
    private Socket socket;
    private DataInputStream in;
    private ArrayList<DataOutputStream> outStreams;
    
    public SendToServerThread(DataInputStream sin, ArrayList<DataOutputStream> os, Socket sockket) {
        socket = sockket;
        in = sin;
        outStreams = os;
    }

    public void run() {
        boolean connected = true;
        while (connected) {
            try {
                boolean not_end = true;
                String message = "";
                
                while (not_end) {
                    try {
                        message = in.readUTF();
                        
                        for (int i = 0; i < outStreams.size(); i++) {
                            if (outStreams.get(i) != socket.getOutputStream()) {
                                System.out.println("sent to " + i + ": " + message);
                                outStreams.get(i).writeUTF(message);
                            }
                        }
                        
                        if (message.split(":")[1].equals(" end")) {
                            socket.close();
                            
                            System.out.println("Client "+ socket.getRemoteSocketAddress() + " has disconnected.");
                            
                            not_end = false;
                        } else
                            System.out.println(message);
        
                    } catch(IOException e) {
                        not_end = false;
                        break;
                    }
                }
        
                if (in != null)  in.close();
            } catch(IOException e) {
                System.out.println("IO Exception: " + e.getMessage());
            }
        }
    }

}
