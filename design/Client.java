import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
    private String host;
    private int port;
    private String id;
    private ClientSenderThread sender;
    private ClientReceiverThread receiver;

    public Client(String host, String port, Game game) throws Exception {
        this.host = host;
        this.port = Integer.parseInt(port);
        // this.id = host + "," + port;

        DatagramSocket socket = new DatagramSocket();
        this.id = socket.getLocalAddress().toString() + "," + socket.getLocalPort();
        this.receiver = new ClientReceiverThread(socket, game);
        this.sender = new ClientSenderThread(socket, this.host, this.port);
    }

    public String getHost() {
        return this.host;
    }

    public int getPort() {
        return this.port;
    }

    public String getId() {
        return this.id;
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