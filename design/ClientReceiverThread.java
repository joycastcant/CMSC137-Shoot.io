import java.io.*;
import java.net.*;
import java.util.*;

public class ClientReceiverThread extends Thread {
    private DatagramSocket sock;
    private byte buf[];
    private Object received;
    private Game game;

    ClientReceiverThread(DatagramSocket s, Game g) {
        sock = s;
        buf = new byte[1024];
        game = g;
    }
    public void run() {
        while (true) {
            try {               
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                sock.receive(packet);
                received = GenSerial.deserialize(packet.getData());    //deserialize received object
                
                ArrayList<Object> re = (ArrayList<Object>) received;
                
                if(re.size() == 1) {
                    HashMap<Integer, Bomb> bms = (HashMap<Integer, Bomb>) re.get(0);
                    System.out.println("BOMBS RECEIVED");;
                    game.setBombs(bms, true);
                } else {
                    HashMap<Integer, Bomb> bmbs = (HashMap<Integer, Bomb>) re.get(0);
                    game.setBombs(bmbs, false);
                    Player pl = (Player) re.get(1);  //convert to player object
                    int[][] f = game.getField();
                    
                    f[pl.getPrevX()][pl.getPrevY()] = 0;
                    if (pl.getDirection() == Game.DOWN || pl.getDirection() == Game.LEFT)
                        f[pl.getPosX()][pl.getPosY()] = 5;
                    else
                        f[pl.getPosX()][pl.getPosY()] = 6;
                }
            } catch(Exception e) {
                // System.err.println(e);
            }
        }
    }
}