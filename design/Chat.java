import javax.swing.*;
import javax.imageio.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class Chat extends JPanel {
    private ArrayList<JLabel> msgs;
    protected Container container;
    
	public Chat(Container container) {
        super(new BorderLayout(), true);

        this.setPreferredSize(new Dimension(300, 200));
        this.requestFocusInWindow();
        
        this.container = container;
        Font def = new Font("Default", Font.BOLD, 20);
        
        JPanel mainWindow = new JPanel();

        // mainWindow.setLayout(new BoxLayout(con, BoxLayout.PAGE_AXIS));

        mainWindow.setBackground(new Color(250, 250, 250, 70));
        this.setBackground(new Color(250, 250, 250, 70));
        // mainWindow.setOpacity()

        JPanel inputField = new JPanel();
        JTextField inputMsg = new JTextField(23);
        inputMsg.setEditable(true);
        inputMsg.setFocusable(true);
        inputField.add(inputMsg);

        this.add(inputField, BorderLayout.SOUTH);
        this.add(mainWindow, BorderLayout.CENTER);
        
	}
}