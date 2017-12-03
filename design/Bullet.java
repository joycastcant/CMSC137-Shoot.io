import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;

public class Bullet implements Serializable {
    final static int OFFSET = 5;

    private int type;
    private int posX;
    private int posY;
    private int width;
	private int height;
    private int direction;
    private Color color;
	private boolean visibility;

	public Bullet(int type, int posX, int posY, int direction) {
        this.type = type;
        this.posX = posX;
        this.posY = posY;
        this.direction = direction;

        if(this.type == Weapon.SHOTGUN)
            this.color = Color.BLACK;
        else if(this.type == Weapon.PISTOL)
            this.color = Color.MAGENTA;
        
        if(this.direction == Game.UP || this.direction == Game.DOWN) {
            this.width = 2;
            this.height = 10;
        }else if(this.direction == Game.LEFT || this.direction == Game.RIGHT) {
            this.width = 10;
            this.height = 2;
        }
        
        this.visibility = true;
	}

	public void move() {
        switch (this.direction) {
			case Game.UP:
                this.posY -= OFFSET;
                break;
			case Game.DOWN:
                this.posY += OFFSET;
                break;
			case Game.LEFT:
                this.posX -= OFFSET;
                break;
			case Game.RIGHT:
                this.posX += OFFSET;
                break;
        }
        
        System.out.println("X: " + this.posX + "\nY: " + this.posY);
    }

    //getters
    public int getType() {
        return this.type;
    }

	public int getX() {
		return this.posX;
	}

	public int getY() {
		return this.posY;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public int getDirection() {
		return this.direction;
    }
    
    public Color getColor() {
		return this.color;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public boolean isVisible() {
		return this.visibility;
	}

	public void setVisible(Boolean visible) {
		this.visibility = visible;
	}
}