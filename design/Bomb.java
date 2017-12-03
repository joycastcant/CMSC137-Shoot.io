import java.io.Serializable;
import java.util.TimerTask;
import java.util.Timer;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Bomb implements Serializable {
  private int xPos;
  private int yPos;
  private Tile img;
  private boolean dead;
  private transient Timer timer;
  private int explosion; // current explosion range
  private int range;    // range of explosion
  private boolean exploding;
  private transient BufferedImage sprite;

  public Bomb(int xPos, int yPos) {
    this.xPos = xPos;
    this.yPos = yPos;
    this.dead = false;
    this.range = 2;
    this.explosion = 0;
    this.img = new Tile("/images/bombb.png");
  }

  public void explode() {
    this.exploding = true;
    int time;
    this.timer = new Timer();
    this.timer.schedule(new BombTimer(this), 200, 200);
  }

  public boolean isDead() {
    return this.dead;
  }

  public boolean isExploding() {
    return this.exploding;
  }

  public void die() {
    this.dead = true;
  }

  public void setTile(Tile t) {
    this.img = t;
  }

  /*=========
    GETTERS
  ==========*/

  public int getX() {
    return this.xPos;
  }

  public int getY() {
    return this.yPos;
  }

  public Tile getImg() {
    return this.img;
  }

  public int getExplosion() {
    return this.explosion;
  }

  public BufferedImage getSprite(String path) {
		try{
			this.sprite = ImageIO.read(this.getClass().getResourceAsStream(path));
		}catch(IOException e){
			e.getMessage();
		}
		return this.sprite;
	}

  /*================
    BOMB TIMERTASK
  =================*/

  private class BombTimer extends TimerTask {
    private Bomb bomb;
    private int time;

    public BombTimer(Bomb bomb) {
      this.bomb = bomb;
      this.time = 0;
    }

    @Override
    public void run() {
      this.time++;
      if(this.time >= this.bomb.range) {
        this.bomb.die();
        this.bomb.timer.cancel();
        this.bomb.timer.purge();
      }
      System.out.println("time: "+time);
      this.bomb.explosion++;
    }
  }

}
