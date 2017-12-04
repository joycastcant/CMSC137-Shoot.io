import javax.swing.*;
import javax.imageio.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class Chat extends JPanel implements KeyListener {
    MultiThreadChatClient client;
    private ArrayList<JLabel> msgs;
    protected Container container;
    private Game game;
    private boolean inChat = false;
    private JTextField inputMsg;
    private String line = "";
    protected Thread clThread;
    private int flag = 1;
    private JTextArea chatWindow;
    private int chatCounter = 0;
    
	public Chat(Container container, Game game, String host, String port, String name) {
        super(new BorderLayout(), true);

        this.setPreferredSize(new Dimension(300, 200));
        this.requestFocusInWindow();
        this.game = game;

        this.container = container;
        Font def = new Font("Default", Font.BOLD, 20);

        JPanel mainWindow = new JPanel();

        // mainWindow.setLayout(new BoxLayout(con, BoxLayout.PAGE_AXIS));
        mainWindow.setBackground(new Color(0, 0, 0, 0));
        this.setBackground(new Color(0, 0, 0, 0));
        // mainWindow.setOpacity()

        this.chatWindow = new JTextArea(11, 1);
        this.chatWindow.setBackground(new Color(0,0,0,80)); // output background color
        this.chatWindow.setForeground(Color.WHITE); // output font color

        JScrollPane s = new JScrollPane();
        s.setBackground(new Color(0,0,0,0));
        s.setViewportView(chatWindow);
        JPanel inputField = new JPanel();
        this.inputMsg = new JTextField(23);
        this.inputMsg.setBackground(new Color(0,0,0,40)); // input background color
        this.chatWindow.setForeground(Color.WHITE); // input font color
        this.inputMsg.setEditable(true);
        this.inputMsg.setEnabled(true);
        this.inputMsg.addKeyListener(this);
        if(!this.game.getIsFocused()) {
            this.inputMsg.requestFocus();
        }
        inputField.add(inputMsg);
        inputField.setBackground(new Color(0,0,0,0));


        this.add(s, BorderLayout.NORTH);
        this.add(inputField, BorderLayout.SOUTH);
        this.add(mainWindow, BorderLayout.CENTER);

        int po = Integer.parseInt(port) + 1;
        
        client = new MultiThreadChatClient(host, String.valueOf(po), name, this.game);
        clThread = new Thread(client);
    }
    @Override
    public void keyPressed( KeyEvent e ){
        if( e.getKeyCode() == KeyEvent.VK_ENTER) {
            line = this.inputMsg.getText();
            this.inputMsg.setText("");
            this.game.setMessage(line);
            this.game.setInGameStatus(true);
            this.inChat = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
     public void keyTyped(KeyEvent arg0) {
    }

    public void setInChat(boolean inChat) {
        this.inChat = inChat;
    }

    public void appendMessage(String msg) {
        if (chatCounter < 11) {
            this.chatWindow.append(msg);
            this.chatCounter = this.chatCounter + 1;
        }
        else {
            this.chatWindow.setText("");
            this.chatWindow.append(msg);
            this.chatCounter = 0;
        }
    }
}
