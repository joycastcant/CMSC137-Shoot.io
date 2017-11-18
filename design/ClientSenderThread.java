import java.io.*;
import java.net.*;
import java.util.*;


public class ClientSenderThread extends Thread {
    private int port;
    private DatagramSocket socket;
    private String host;
    private String data;

    ClientSenderThread(DatagramSocket socket, String host, int port) {
        this.socket = socket;
        this.host = host;
        this.port = port;
    }
    
    public void run() {
        boolean connected = false;
        do {
            try {
                transmit("START");
                connected = true;
            } catch (Exception e) {
                
            }
        } while (!connected);
        // BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        //change sending;
        while (true) {
            try {
                //while (!in.ready()) {
                    Thread.sleep(1000);
                //}
                transmit(this.data);
            } catch(Exception e) {
                // System.err.println(e);
            }
        }
    }
    
    private void transmit(String message) throws Exception {        
        byte buf[] = message.getBytes();
        InetAddress address = InetAddress.getByName(this.host);
        System.out.println(address + ":" + this.port);
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, this.port);
        socket.send(packet);
    }

    public void setData(String data) {
        this.data = data;
    }
}