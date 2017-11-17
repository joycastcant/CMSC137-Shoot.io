import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Game extends JPanel implements KeyListener {
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
  private Tile bomb = new Tile("images/bombb.png");
  private Tile a = new Tile("images/a.png");
  private int direction;
  int she=0;

  private ArrayList<Bomb> bombs = new ArrayList<Bomb>();

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
    this.setBackground(Color.black);
    this.generateBombs(10);
    this.addKeyListener(this);
    System.out.println("lengh0 - " + this.field[0].length);
    System.out.println("lengh - " + this.field.length);
  }

  public void generateBombs(int num) {
    Random rand = new Random();
    int x,y;
    for(int i=0;i<num;i++) { // randomized position
      do{
        x = rand.nextInt(this.field.length);
        y = rand.nextInt(this.field[0].length);
      } while(this.field[x][y] == 1);
      this.bombs.add(new Bomb(x,y)); // instantiate bombs
      this.field[x][y] = 2;
    }
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    int x,y;
    // sudoX and sudoY represents the x and y of visible map to the camera
    //i and j represents the x and y of the window
    for(int i=0, sudoX=this.camX; i<=this.width && sudoX<=this.width; i+=this.tileSize, sudoX+=this.tileSize) {
      for(int j=0, sudoY=this.camY; j<=this.height && sudoY<=this.height; j+=this.tileSize, sudoY+=this.tileSize) {
        x = sudoX/this.tileSize;
        y = sudoY/this.tileSize;

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
          } else if(field[x][y] == 1) // IF FIELD CONTAINS BLOCK
            g.drawImage(this.block.getTile(), i, j, this);
          else { // IF FIELD CONTAINS -NO- BLOCK
            g.drawImage(this.floor.getTile(), i, j, this);
            if(field[x][y] == 2) { // IF FIELD CONTAINS BOMB

              // Check each bomb position
              for(int k=0;k<this.bombs.size();k++) {
                if(this.bombs.get(k).isDead()) { // remove bomb if exploded
                  this.field[this.bombs.get(0).getX()][this.bombs.get(0).getY()] = 0;
                  this.bombs.remove(0);
                  she++;
                } else if(this.bombs.get(k).getX() == x && this.bombs.get(k).getY() == y) {
                  g.drawImage(this.bombs.get(k).getImg().getTile(), i, j, this);

                  //------ Check explosion of bomb

                  //---- SOUTH
                  for(int l=1;l<this.bombs.get(k).getExplosion()+1;l++) {
                      if ( y+l < this.field[0].length && this.field[x][(y+l)] != 1 ) {
                        g.drawImage(this.bombs.get(k).getImg().getTile(), i, (j+(l*this.tileSize)), this);
                      } else break;
                  }

                  //---- NORTH
                  for(int l=1;l<this.bombs.get(k).getExplosion()+1;l++) {
                      if ( y-l > -1 && this.field[x][(y-l)] != 1 ) {
                        g.drawImage(this.bombs.get(k).getImg().getTile(), i, (j-(l*this.tileSize)), this);
                      } else break;
                  }

                  //---- EAST
                  for(int l=1;l<this.bombs.get(k).getExplosion()+1;l++) {
                      if ( x+l < this.field.length && this.field[(x+l)][y] != 1 ) {
                        g.drawImage(this.bombs.get(k).getImg().getTile(), (i+(l*this.tileSize)), j, this);
                      } else break;
                  }

                  //---- WEST
                  for(int l=1;l<this.bombs.get(k).getExplosion()+1;l++) {
                      if ( x-l > -1 && this.field[(x-l)][y] != 1 ) {
                        g.drawImage(this.bombs.get(k).getImg().getTile(), (i-(l*this.tileSize)), j, this);
                      } else break;
                  }
                }
              }
            }
          }

        } catch(ArrayIndexOutOfBoundsException e) { }
      }
    }

    setFocusable(true);
    requestFocus();
    this.repaint();
    this.revalidate();
  }

  /*==============
    KEYLISTENERS
  ===============*/

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
      }
    }
    if( e.getKeyCode() == KeyEvent.VK_W) {
      nextX = (this.camX/this.tileSize) + ROW_ADJUST;
      nextY = (this.camY - this.offSet)/this.tileSize + COL_ADJUST;
      this.direction = UP;
      if(field[nextX][nextY] != 1) {
        this.camY -= this.offSet;
      }
    }
    if( e.getKeyCode() == KeyEvent.VK_A) {
      nextX = (this.camX - this.offSet)/this.tileSize + ROW_ADJUST;
      nextY = (this.camY/this.tileSize) + COL_ADJUST;
      this.direction = LEFT;
      if(field[nextX][nextY] != 1) {
        this.camX -= this.offSet;
      }
    }
    if( e.getKeyCode() == KeyEvent.VK_D) {
      nextX = (this.camX + this.offSet)/this.tileSize + ROW_ADJUST;
      nextY = (this.camY/this.tileSize) + COL_ADJUST;
      this.direction = RIGHT;
      if(field[nextX][nextY] != 1) {
        this.camX += this.offSet;
      }
    }
    // bomb
    if( e.getKeyCode() == KeyEvent.VK_UP) {
      if(this.bombs.size()!=0) {
        this.bombs.get(0).explode();
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

}
