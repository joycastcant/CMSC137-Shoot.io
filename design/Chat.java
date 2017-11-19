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
    
	public Chat(Container container, Game game) {
        super(new BorderLayout(), true);

        this.setPreferredSize(new Dimension(300, 200));
        this.requestFocusInWindow();
        this.game = game;
        
        this.container = container;
        Font def = new Font("Default", Font.BOLD, 20);
        
        JPanel mainWindow = new JPanel();

        // mainWindow.setLayout(new BoxLayout(con, BoxLayout.PAGE_AXIS));

        mainWindow.setBackground(new Color(250, 250, 250, 70));
        this.setBackground(new Color(250, 250, 250, 70));
        // mainWindow.setOpacity()

        JPanel inputField = new JPanel();
        this.inputMsg = new JTextField(23);
        this.inputMsg.setEditable(true);
        this.inputMsg.setEnabled(true);
        this.inputMsg.setText("asdasd");
        this.inputMsg.addKeyListener(this);
        if(!this.game.getIsFocused()) {
            this.inputMsg.requestFocus();
        }
        inputField.add(inputMsg);

        this.add(inputField, BorderLayout.SOUTH);
        this.add(mainWindow, BorderLayout.CENTER);
        
        client = new MultiThreadChatClient("127.0.0.1", "2222", "PlayerName HEHE", this.game);
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
}