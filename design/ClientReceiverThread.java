import java.io.*;
import java.net.*;
import java.util.*;

public class ClientReceiverThread extends Thread {
    DatagramSocket sock;
    byte buf[];
    String received;
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
                received = new String(packet.getData(), 0, packet.getLength());
                
                String[] pos = received.split(",");
                String id = pos[0] + "," + pos[1];
                Player p = new Player("", field, id);
                if(players.containsKey(id)) {
                    p = players.get(id);
                } else {
                    System.out.println("NEW: "+id);
                    p = new Player("My Name", field, id);
                    players.put(id, p);
                }

                //way too many players
                // field[Integer.parseInt(pos[4])][Integer.parseInt(pos[5])] = 0;
                
                //blinking player
                for(int i=0; i<field.length; i++) {
                    for(int j=0; j<field[0].length; j++){
                        if(field[i][j] == 3 || field[i][j] == 4)
                            field[i][j] = 0;
                    }
                }

                if (Integer.parseInt(pos[6]) == Game.DOWN || Integer.parseInt(pos[6]) == Game.LEFT)
                    field[Integer.parseInt(pos[2])][Integer.parseInt(pos[3])] = 3;
                else
                    field[Integer.parseInt(pos[2])][Integer.parseInt(pos[3])] = 4;
                // System.out.println("AAA "+received);
            } catch(Exception e) {
                // System.err.println(e);
            }
        }
    }
}