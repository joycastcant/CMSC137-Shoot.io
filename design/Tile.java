import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Tile {
  private BufferedImage img;

  public Tile(String path) {
    try {
      this.img = ImageIO.read(this.getClass().getResourceAsStream(path));
    } catch(IOException e) {
      System.out.println("dasda");
    }
  }

  public BufferedImage getTile() {
    return this.img;
  }

  public int getWidth() {
    return this.img.getWidth();
  }

  public int getHeight() {
    return this.img.getHeight();
  }
}
