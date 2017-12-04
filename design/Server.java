import java.io.*;
import java.net.*;
import java.util.*;

public class Server extends Thread {    
    private DatagramSocket socket;
    private ArrayList<InetAddress> clientAddresses;
    private ArrayList<Integer> clientPorts;
    private HashMap<String, Integer> existingClients;
    private HashMap<Integer, Bomb> bombs;
    private int numP;
    private int flag = 0;

    public Server(int port, int num) throws IOException {
        this.socket = new DatagramSocket(port);
        this.clientAddresses = new ArrayList<InetAddress>();
        this.clientPorts = new ArrayList<Integer>();
        this.existingClients = new HashMap<String, Integer>();
        this.numP = num;
        this.generateBombs(10);
    }
    
    public void run() {
        System.out.println("Waiting for players...");
        byte[] buf = new byte[1024];
        
        while (true) {
            try {
                Arrays.fill(buf, (byte)0);
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                // String content = new String(packet.getData(), 0, packet.getLength());

                InetAddress clientAddress = packet.getAddress();
                int clientPort = packet.getPort();
                
                // while(existingClients.size() < numP) {
                    String id = clientAddress.toString() + "," + clientPort;
                    if (!existingClients.containsKey(id)) {
                        existingClients.put(id, 0);
                        clientPorts.add(clientPort);
                        clientAddresses.add(clientAddress);
                        System.out.println("Current number: " + existingClients.size());
                    }
                // }

                if(this.flag == 0 && existingClients.size() >= numP){
                    System.out.println("Expected number of players reached!");
                    this.flag = 1;
                }
                
                // byte[] data = content.getBytes();
                byte data[] = packet.getData();
                
                for (int i = 0; i < clientAddresses.size(); i++) {
                    InetAddress addr = clientAddresses.get(i);
                    int port = clientPorts.get(i);

                    String cl = addr.toString();
                    String iid = cl + "," + port;
                    if(existingClients.get(iid) != null && existingClients.get(iid) == 0) {
                        this.sendBombs(addr, port);
                        existingClients.put(iid, 1);
                    }

                    packet = new DatagramPacket(data, data.length, addr, port);
                    socket.send(packet);
                }

            } catch(Exception e) {
                System.err.println(e);
                System.out.println("Exception: " + e.getMessage());
            }
        }
    }

    public void generateBombs(int num) {
        this.bombs = new HashMap<Integer, Bomb>();
        Random rand = new Random();
        int x,y;
        for(int i=0;i<num;i++) { // randomized position
            do{
            x = rand.nextInt(Game.FIELD.length);
            y = rand.nextInt(Game.FIELD[0].length);
            } while(Game.FIELD[x][y] == 1);
            this.bombs.put(this.bombs.size(), new Bomb(x,y)); // instantiate bombs
            // this.field[x][y] = 2;
        }
    }

    public void sendBombs(InetAddress addr, int port) {
        System.out.println("send bombs");
        ArrayList<HashMap<Integer, Bomb>> daataa = new ArrayList<HashMap<Integer, Bomb>>();
        daataa.add(this.bombs);
        byte[] data = GenSerial.serialize(daataa);
        try{
            DatagramPacket packet = new DatagramPacket(data, data.length, addr, port);
            this.socket.send(packet);
        } catch(Exception e){}
    }
}