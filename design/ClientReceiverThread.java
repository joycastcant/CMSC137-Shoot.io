import java.io.*;
import java.net.*;
import java.util.*;

public class ClientReceiverThread extends Thread {
    DatagramSocket sock;
    byte buf[];
    ClientReceiverThread(DatagramSocket s) {
        sock = s;
        buf = new byte[1024];
    }
    public void run() {
        while (true) {
            try {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                System.out.println("receive");
                sock.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength());
                System.out.println(received);
            } catch(Exception e) {
                // System.err.println(e);
            }
        }
    }
}