import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Game extends JPanel implements KeyListener {
  private int camX;
  private int camY;
  private int height;
  private int width;
  private int offSet = 7;
  private int tileSize = 50;
  private Tile block = new Tile("images/prison_platform.png");
  private Tile floor = new Tile("images/tile.png");

  private int [][]field = {     {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},   //initial map with no destroyable blocks
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
                    };

  public Game(int width, int height, int camX, int camY) {
    this.width = width;
    this.height = height;
    this.camX = camX;
    this.camY = camY;
    this.addKeyListener(this);
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    // TO DO: fix algo
    // inadd lang yung "/offSet" para makita yung gumagana na scroll
    for(int i=camX; i<this.width/offSet; i+=this.tileSize) {
      for(int j=camY; j<this.height/offSet; j+=this.tileSize) {
        // check if block or floor
        if(field[(int)(i/this.tileSize)][(int)(j/this.tileSize)] == 1)
          g.drawImage(this.block.getTile(), i, j, null);
        else
          g.drawImage(this.floor.getTile(), i, j, null);
      }
    }

    setFocusable(true);
    requestFocus();
    this.repaint();
    this.revalidate();
  }

  @Override
  public void keyPressed( KeyEvent e ){
    // scroll through map
    if( e.getKeyCode() == KeyEvent.VK_UP) {
      this.camY += this.offSet;
    }
    if( e.getKeyCode() == KeyEvent.VK_DOWN) {
      this.camY -= this.offSet;
    }
    if( e.getKeyCode() == KeyEvent.VK_RIGHT) {
      this.camX -= this.offSet;
    }
    if( e.getKeyCode() == KeyEvent.VK_LEFT) {
      this.camX += this.offSet;
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
  }

  @Override
  public void keyTyped(KeyEvent arg0) {
  }
}
