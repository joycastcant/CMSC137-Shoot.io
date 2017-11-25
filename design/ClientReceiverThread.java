import java.io.*;
import java.net.*;
import java.util.*;

public class ClientReceiverThread extends Thread {
    DatagramSocket sock;
    byte buf[];
    Object received;
    HashMap<String, Player> players = new HashMap<String, Player>();
    int[][] field;

    ClientReceiverThread(DatagramSocket s, int[][] f) {
        sock = s;
        buf = new byte[1024];
        field = f;
    }
    public void run() {
        while (true) {
            try {               
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                System.out.println("receive");
                sock.receive(packet);
                received = Player.deserialize(packet.getData());    //deserialize received object
                
                Player pl = (Player) received;  //convert to player object
                
                field[pl.getPrevX()][pl.getPrevY()] = 0;
                if (pl.getDirection() == Game.DOWN || pl.getDirection() == Game.LEFT)
                    field[pl.getPosX()][pl.getPosY()] = 5;
                else
                    field[pl.getPosX()][pl.getPosY()] = 6;
            } catch(Exception e) {
                // System.err.println(e);
            }
        }
    }
}