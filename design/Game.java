import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Game extends JPanel implements KeyListener {
  // MultiThreadChatClient client;
  Client client;
  //adjustments between game and the field 2D array
  final static int ROW_ADJUST = 8;
  final static int COL_ADJUST = 6;

  //directions
  final static int NONE = 0;
  final static int UP = 1;
  final static int RIGHT = 2;
  final static int DOWN = 3;
  final static int LEFT = 4;
  
  private int camX;
  private int camY;
  private int height;
  private int width;
  private int offSet = 50;
  private int tileSize = 50;
  private Tile block = new Tile("images/prison_platform.png");
  private Tile floor = new Tile("images/tile.png");
  private int direction;
  private Chat chat;
  private boolean isInGame = true;
  private String message = "";
  private Thread clThread;
  private int flag = 1;

  //original map
  /* private int [][]field = {     {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},   //initial map with no destroyable blocks
                        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                        {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                        {1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1},
                        {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                        {1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1},
                        {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                        {1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1},
                        {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                        {1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1},
                        {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
                    }; */
    //extended map
    private int [][]field = {     
                        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                        {1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1},
                        {1, 1, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                        {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                        {1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1},
                        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                        {1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1},
                        {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                        {1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1},
                        {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                        {1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1},
                        {1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1},
                        {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                        {1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1},
                        {1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                        {1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                        {1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1},
                        {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                        {1, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1},
                        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                        {1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1},
                        {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1},
                        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
                    };

  public Game(int width, int height, int camX, int camY) {
    this.width = field.length * this.tileSize;//width;
    this.height = field[0].length * this.tileSize;//height;
    this.camX = camX;
    this.camY = camY;
    this.direction = NONE;
    this.addKeyListener(this);
    /* client = new MultiThreadChatClient("127.0.0.1", "2222", "PlayerName HEHE", this);
    clThread = new Thread(client); */
    try {
      this.client = new Client("192.168.122.1", "8080");
      Thread receiver = new Thread(this.client.getReceiver());
      Thread sender = new Thread(this.client.getSender());
      receiver.start();
      sender.start();
    } catch(Exception e) {
      System.err.println(e);
    }
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    // sudoX and sudoY represents the x and y of visible map to the camera
    //i and j represents the x and y of the window
    for(int i=0, sudoX=this.camX; i<=this.width && sudoX<=this.width; i+=this.tileSize, sudoX+=this.tileSize) {
      for(int j=0, sudoY=this.camY; j<=this.height && sudoY<=this.height; j+=this.tileSize, sudoY+=this.tileSize) {
        // check if block or floor
        try {
          if(i == (ROW_ADJUST*this.tileSize) && j == (COL_ADJUST*this.tileSize)) {
            /* switch (this.direction) {
              case UP:
                g.setColor(Color.BLUE);
                break;
              case RIGHT:
                g.setColor(Color.PINK);
                break;
              case DOWN:
                g.setColor(Color.GREEN);
                break;
              case LEFT:
                g.setColor(Color.ORANGE);
                break;
              default:
                g.setColor(Color.BLACK);
                break;
            } */
            g.setColor(Color.BLACK);
            g.fillRect(i, j, this.tileSize, this.tileSize);
          } else if(field[sudoX/this.tileSize][sudoY/this.tileSize] == 1)
            g.drawImage(this.block.getTile(), i, j, null);
          else
            g.drawImage(this.floor.getTile(), i, j, null);

        } catch(ArrayIndexOutOfBoundsException e) { }
      }
    }

    setFocusable(true);
    if (this.isInGame) {
      requestFocus();
    }
    this.repaint();
    this.revalidate();
  }

  @Override
  public void keyPressed( KeyEvent e ){
    // scroll through map
    int nextX = 0;
    int nextY = 0;
    
    if( e.getKeyCode() == KeyEvent.VK_S) {
      nextX = (this.camX/this.tileSize) + ROW_ADJUST;
      nextY = (this.camY + this.offSet)/this.tileSize + COL_ADJUST;
      this.direction = DOWN;
      if(field[nextX][nextY] != 1) {
        this.camY += this.offSet;
        this.client.getSender().setData("x: " + nextX + "\n y: " + nextY + "\n");
      }
    }
    if( e.getKeyCode() == KeyEvent.VK_W) {
      nextX = (this.camX/this.tileSize) + ROW_ADJUST;
      nextY = (this.camY - this.offSet)/this.tileSize + COL_ADJUST;
      this.direction = UP;
      if(field[nextX][nextY] != 1) {
        this.camY -= this.offSet;
        this.client.getSender().setData("x: " + nextX + "\n y: " + nextY + "\n");
      }
    }
    if( e.getKeyCode() == KeyEvent.VK_A) {
      nextX = (this.camX - this.offSet)/this.tileSize + ROW_ADJUST;
      nextY = (this.camY/this.tileSize) + COL_ADJUST;
      this.direction = LEFT;
      if(field[nextX][nextY] != 1) {
        this.camX -= this.offSet;
        this.client.getSender().setData("x: " + nextX + "\n y: " + nextY + "\n");
      }
    }
    if( e.getKeyCode() == KeyEvent.VK_D) {
      nextX = (this.camX + this.offSet)/this.tileSize + ROW_ADJUST;
      nextY = (this.camY/this.tileSize) + COL_ADJUST;
      this.direction = RIGHT;
      if(field[nextX][nextY] != 1) {
        this.camX += this.offSet;
        this.client.getSender().setData("x: " + nextX + "\n y: " + nextY + "\n");
      }
    }
    if( e.getKeyCode() == KeyEvent.VK_ENTER) {
      if(isInGame) {
        System.out.println("Ehllooo");
        isInGame = false;
        if(flag == 1){
          this.chat.clThread.start();
          this.flag = 0;
        }
      }  else {
        System.out.println("Ehllooo2");
        isInGame = true;
      }
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    this.direction = NONE;
  }

  @Override
  public void keyTyped(KeyEvent arg0) {
  }

  public void setInGameStatus(boolean isInGame) {
    System.out.println("Here");
    this.isInGame = isInGame;
    requestFocus();
  }

  public void setChat(Chat chat) {
    this.chat = chat;
  }

  public boolean getIsFocused() {
    return this.isInGame;
  }

  public String getMessage () {
    return this.message;
  }

  public void setMessage(String m) {
    this.message = m;
  }

  /* public void setWaitThread(){
    this.pauseThread();
  }
  private void pauseThread(){
    try{
      if(this.clThread.isAlive()){
        this.clThread.wait();
      }
    } catch(Exception e) {
      System.out.println("Error has occured");
    }
  } */
}
