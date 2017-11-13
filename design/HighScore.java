import javax.swing.*;
import javax.imageio.*;
import java.awt.*;
import java.io.*;

public class HighScore extends JPanel {
    private JLabel nameHead, scoreHead;
    protected Container container;
    
	public HighScore(String file, Container container) {
        super(new BorderLayout(), true);

		this.setBackground(Color.BLACK);
        this.requestFocusInWindow();
        
        this.container = container;

		Color transparent = new Color(0, 0, 0, 0);
		Font def = new Font("Default", Font.BOLD, 20);

		this.nameHead = new JLabel("Name");
		this.nameHead.setForeground(Color.BLACK);
		this.nameHead.setFont(def);
		JPanel pp1 = new JPanel();
		pp1.setBackground(transparent);
		pp1.add(nameHead);

		this.scoreHead = new JLabel("Score");
		this.scoreHead.setForeground(Color.BLACK);
		this.scoreHead.setFont(def);
		JPanel pp2 = new JPanel();
		pp2.setBackground(transparent);
        pp2.add(scoreHead);
        
        JPanel dataPanel = new JPanel(new GridLayout(6, 2, 250, 30));
        dataPanel.add(pp1);
        dataPanel.add(pp2);

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
			for(int i = 0; i < 5; i++){
				String line = br.readLine();
				String[] tokens = line.split(",");
                
                JLabel n = new JLabel(" " + tokens[0]);
                n.setForeground(Color.BLACK);
                n.setFont(def);

                JLabel s = new JLabel(" " + tokens[1]);
                s.setForeground(Color.BLACK);
                s.setFont(def);
                dataPanel.add(n);
                dataPanel.add(s);
            }
            br.close();
        } catch (IOException e) {}

        dataPanel.setBackground(new Color(250, 250, 250, 90));
        
        this.add(dataPanel,BorderLayout.CENTER);
	}
}