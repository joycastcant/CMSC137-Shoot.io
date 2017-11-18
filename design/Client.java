import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
    private String host;
    private int port;
    private ClientSenderThread sender;
    private ClientReceiverThread receiver;

    public Client(String host, String port) throws Exception {
        this.host = host;
        this.port = Integer.parseInt(port);

        DatagramSocket socket = new DatagramSocket();
        this.receiver = new ClientReceiverThread(socket);
        this.sender = new ClientSenderThread(socket, this.host, this.port);
    }

    public String getHost() {
        return this.host;
    }

    public int getPort() {
        return this.port;
    }

    public ClientSenderThread getSender() {
        return this.sender;
    }

    public ClientReceiverThread getReceiver() {
        return this.receiver;
    }
    
    /* public static void main(String args[]) throws Exception {
        String host = "";
        String name = "";
        int port = 0;
        if (args.length != 3) {
            System.out.println("Usage: java Client <server> <port> <name>");
            System.exit(0);
        } else {
            host = args[0];
            port = Integer.parseInt(args[1]);
            name = args[2];
        }
        DatagramSocket socket = new DatagramSocket();
        ClientReceiverThread r = new ClientReceiverThread(socket);
        ClientSenderThread s = new ClientSenderThread(socket, host, port, name);
        Thread rt = new Thread(r);
        Thread st = new Thread(s);
        rt.start(); st.start();
    } */
}