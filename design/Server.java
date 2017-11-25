import java.io.*;
import java.net.*;
import java.util.*;

public class Server extends Thread {    
    private DatagramSocket socket;
    private ArrayList<InetAddress> clientAddresses;
    private ArrayList<Integer> clientPorts;
    private HashMap<String, Integer> existingClients;

    public Server(int port) throws IOException {
        this.socket = new DatagramSocket(port);
        this.clientAddresses = new ArrayList<InetAddress>();
        this.clientPorts = new ArrayList<Integer>();
        this.existingClients = new HashMap<String, Integer>();
    }
    
    public void run() {
        byte[] buf = new byte[1024];
        while (true) {
            try {
                Arrays.fill(buf, (byte)0);
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                
                // String content = new String(packet.getData(), 0, packet.getLength());
                
                InetAddress clientAddress = packet.getAddress();
                int clientPort = packet.getPort();
                
                // System.out.println("CONTENT: " + content);
                String id = clientAddress.toString() + "," + clientPort;
                if (!existingClients.containsKey(id)) {
                    int k = existingClients.size();
                    existingClients.put(id, k);
                    clientPorts.add(clientPort);
                    clientAddresses.add(clientAddress);
                }
                
                // byte[] data = content.getBytes();
                byte data[] = packet.getData();
                
                for (int i = 0; i < clientAddresses.size(); i++) {
                    InetAddress addr = clientAddresses.get(i);
                    int port = clientPorts.get(i);
                    packet = new DatagramPacket(data, data.length, addr, port);
                    socket.send(packet);
                }

            } catch(Exception e) {
                System.err.println(e);
                System.out.println("Exception: " + e.getMessage());
            }
        }
    }
    
    public static void main(String args[]) throws Exception {
        Server s = new Server(Integer.parseInt(args[0]));
        s.start();
    }
}