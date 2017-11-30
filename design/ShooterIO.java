import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/* import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
// import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException; */

public class ShooterIO implements ActionListener {
  // Data
  private String host;
  private String port;
  private String name;

  // Containers
  JFrame mainFrame      = new JFrame("Shooter.io");
  JFrame mapFrame;
  JPanel mainComponents = new JPanel(new CardLayout());
  JPanel mainButtons    = new JPanel();

  // Main frame components
  JPanel home;
  JPanel instructions;
  JPanel highScores;
  JPanel game;

  // Home buttons
  JButton playButton         = getButton("Play");
  JButton instructionsButton = getButton("Instructions");
  JButton highScoresButton   = getButton("High Scores");
  JButton exitButton         = getButton("Exit");

  // Back buttons
  JButton backButton1 = getButton("Back");
  JButton backButton2 = getButton("Back");
  // JButton backButton3 = getButton("Back");

  Font font;
  Game g;

  // For card layout reference
  final static String HOMEPANEL         = "Home";
	final static String GAMEPANEL         = "Game";
	final static String INSTRUCTIONSPANEL = "Instructions";
  final static String HIGHSCORESPANEL   = "High Scores";

  final static int WIDTH  = 800;
  final static int HEIGHT = 600;

  /* Constructor */
  public ShooterIO() {
    this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.mainFrame.setResizable(false);
    this.mainFrame.setPreferredSize(new Dimension(ShooterIO.WIDTH, ShooterIO.HEIGHT));
    this.setBackgrounds();
    this.registerFont();
    this.setComponents();
    this.mainFrame.repaint();
    this.mainFrame.revalidate();
    this.mainFrame.pack();
    this.mainFrame.setLocationRelativeTo(null);
    this.mainFrame.setVisible(true);
  }

  public void setComponents() {
    // Home components
    this.mainButtons.setLayout(new BoxLayout(mainButtons,BoxLayout.PAGE_AXIS));
    this.mainButtons.setOpaque(false);

    ImageIcon title = new ImageIcon("images/title.png");
    JLabel titleLabel = new JLabel();
    titleLabel.setIcon(title);
    titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    this.mainButtons.add(titleLabel);
    this.mainButtons.add(this.playButton);
    this.mainButtons.add(this.instructionsButton);
    this.mainButtons.add(this.highScoresButton);
    this.mainButtons.add(this.exitButton);
    
    this.home.setLayout(new GridBagLayout());
    this.home.add(this.mainButtons);
    
    // Instructions/highScores components
    title = new ImageIcon("images/hs.png");
    JLabel hsLabel = new JLabel();
    hsLabel.setIcon(title);
    hsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    
    this.highScores.add(this.backButton2);
    this.highScores.add(hsLabel);
    
    Container container = new Container();
    HighScore hScores = new HighScore("../data/hscores.txt", container);
    this.highScores.add(hScores);
    
    this.instructions.add(this.backButton1);
    
    //Data collection
    this.collectData(container);
    
    // Game component
    g = new Game(ShooterIO.WIDTH, ShooterIO.HEIGHT, 0, 0, this.host, this.port, this.name);
    this.game = g;
    this.game.setLayout(new GridLayout(3,3));

    // Main frame components
    this.mainComponents.setLayout(new CardLayout());
    this.mainComponents.add(this.home, ShooterIO.HOMEPANEL);
    this.mainComponents.add(this.game, ShooterIO.GAMEPANEL);
    this.mainComponents.add(this.instructions, ShooterIO.INSTRUCTIONSPANEL);
    this.mainComponents.add(this.highScores, ShooterIO.HIGHSCORESPANEL);
    this.mainFrame.add(this.mainComponents);

    JPanel[][] panels = new JPanel[3][3];
    for(int i = 0; i<3; i++) {
        for(int j = 0; j < 3; j++) {
            panels[i][j] = new JPanel();
            panels[i][j].setOpaque(false);
            // if (i == 0 && j == 0) {
            //   Container c = new Container();
            //   Map m = new Map(g, c, 267, 200, 0, 0);
            //   panels[i][j].add(m);
            // }
            if (i == 2 && j == 0) {
              Container c = new Container();
              Chat chat = new Chat(c, g, this.host, this.port, this.name);
              g.setChat(chat);
              panels[i][j].add(chat);
            }
            this.game.add(panels[i][j]);
        }
    }
  }

  public void setBackgrounds() {
    this.home = new JPanel(){
      // @Override
      protected void paintComponent(Graphics g) {
        g.drawImage((new Tile("images/background.png")).getTile(), 0, 0, null);
      }
    };
    this.highScores = new JPanel(){
      // @Override
      protected void paintComponent(Graphics g) {
        g.drawImage((new Tile("images/background.png")).getTile(), 0, 0, null);
      }
    };
    this.instructions = new JPanel(){
      // @Override
      protected void paintComponent(Graphics g) {
        g.drawImage((new Tile("images/inst.png")).getTile(), 0, 0, null);
      }
    };
  }

  public void registerFont() {
    GraphicsEnvironment ge = null;
    try{
      ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
      ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, game.getClass().getResourceAsStream("resources/joystix.monospace.ttf")));
    } catch(FontFormatException e){} catch (IOException e){} catch(NullPointerException e){}
  }

  /* Cusomized button */
  public JButton getButton(String text) {
    JButton b = new JButton(text);
    b.setFont(new Font("Joystix Monospace", Font.BOLD, 24));
    b.setBorderPainted(false);
    b.setContentAreaFilled(false);
    b.setFocusPainted(false);
    b.setOpaque(false);
    b.setMargin(new Insets(5,5,10,10));
    b.setAlignmentX(Component.CENTER_ALIGNMENT);
    b.addActionListener(this);
    b.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e){ // hover effect
        ((JButton)e.getSource()).setForeground(Color.white);
      }
			public void mouseExited(MouseEvent e) {
        ((JButton)e.getSource()).setForeground(Color.black);
      }
		});
    return b;
  }

  public void collectData(Container container) {
    JPanel all = new JPanel(new GridLayout(3, 1));

    FlowLayout leftFlow = new FlowLayout(FlowLayout.LEFT);

    JPanel namePanel = new JPanel();
    namePanel.setLayout(leftFlow);
    JLabel nameLabel = new JLabel("Input name: ");
    JTextField nameField = new JTextField(15);
    namePanel.add(nameLabel);
    namePanel.add(nameField);

    JPanel hostPanel = new JPanel();
    hostPanel.setLayout(leftFlow);
    JLabel hostLabel = new JLabel("Input host: ");
    JTextField hostField = new JTextField(15);
    hostPanel.add(hostLabel);
    hostPanel.add(hostField);

    JPanel portPanel = new JPanel();
    portPanel.setLayout(leftFlow);
    JLabel portLabel = new JLabel("Input port: ");
    JTextField portField = new JTextField(15);
    portPanel.add(portLabel);
    portPanel.add(portField);

    all.add(namePanel);
    all.add(hostPanel);
    all.add(portPanel);

    JOptionPane pane = new JOptionPane(all);

    JDialog dialog = pane.createDialog(container, "ShooterIO: Welcome!");

    dialog.setVisible(true);

    if(((Integer)pane.getValue()).intValue() == JOptionPane.OK_OPTION) {
        this.name = nameField.getText();
        this.host = hostField.getText();
        this.port = portField.getText();
    } else System.exit(0);
  }

  /* ActionListener */
  @Override
  public void actionPerformed(ActionEvent e) {
    if( e.getSource() == this.playButton ){
      ((CardLayout)this.mainComponents.getLayout()).show(this.mainComponents, ShooterIO.GAMEPANEL);
      this.mapFrame = new JFrame();
      this.mapFrame.setPreferredSize(new Dimension(285,500));
      Container cc = new Container();
      Map map = new Map(this.g, cc, 267, 200, 0, 0);
      this.mapFrame.add(map);
      this.mapFrame.repaint();
      this.mapFrame.revalidate();
      this.mapFrame.pack();
      this.mapFrame.setVisible(true);
    }
    if( e.getSource() == this.instructionsButton )
      ((CardLayout)this.mainComponents.getLayout()).show(this.mainComponents, ShooterIO.INSTRUCTIONSPANEL);
    if( e.getSource() == this.highScoresButton )
      ((CardLayout)this.mainComponents.getLayout()).show(this.mainComponents, ShooterIO.HIGHSCORESPANEL);
    if( e.getSource() == this.backButton1 || e.getSource() == this.backButton2)// || e.getSource() == this.backButton3)
      ((CardLayout)this.mainComponents.getLayout()).show(this.mainComponents, ShooterIO.HOMEPANEL);
    if( e.getSource() == this.exitButton )
      this.mainFrame.dispose();
  }

  public static void main(String[] args) {
    new ShooterIO();
  }
}
