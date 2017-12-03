import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.Runnable;

public class Weapon implements Serializable {
	final static int PISTOL = 1;
	final static int SHOTGUN = 2;

	private int type;
	private int damage;
	private int playerX;
	private int playerY;
	private int direction;

	private ArrayList<Bullet> bullets;
	/*private Player owner;
	private BufferedImage sprite;*/
	
	public Weapon(int type, int direction){
		/* if(type.equals("PISTOL")){
			this.type = PISTOL;
		} else {
			this.type = SHOTGUN;
		} */

		this.type = type;
		this.direction = direction;
		this.bullets = new ArrayList<Bullet>();
		this.setDamage();
	}
	
	public void setDamage(){
		if(this.type == PISTOL){
			this.damage = 20;
		} else if(this.type == SHOTGUN){
			this.damage = 10;
		}
	}
	
	public void changeType(){
		if(this.type == PISTOL){
			this.type = SHOTGUN;
		} else if(this.type == SHOTGUN){
			this.type = PISTOL;
		}
		this.setDamage();
	}
	
	public void fire() {
		if(this.direction == Game.RIGHT) {
			this.bullets.add(new Bullet(this.type, this.playerX + Game.TILE_SIZE, this.playerY + Game.TILE_SIZE / 2, this.direction));
		}else if(this.direction == Game.LEFT) {
			this.bullets.add(new Bullet(this.type, this.playerX, this.playerY + Game.TILE_SIZE / 2, this.direction));
		}else if(this.direction == Game.UP) {
			this.bullets.add(new Bullet(this.type, this.playerX + Game.TILE_SIZE / 2, this.playerY, this.direction));
		}else if(this.direction == Game.DOWN) {
			this.bullets.add(new Bullet(this.type, this.playerX + Game.TILE_SIZE / 2, this.playerY + Game.TILE_SIZE, this.direction));
		}
	}
	
	/*public BufferedImage getSprite(int col, int row, int width, int height) {
		BufferedImage img = this.sprite.getSubimage((col * 50) - 50, (row * 50) - 50, width, height);
		return img;
	}*/

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public void setX(int x) {
		this.playerX = x;
	}

	public void setY(int y) {
		this.playerY = y;
	}
	
	public int getType()  {
		return this.type;
	}

	public int getDamage() {
		return this.damage;
	}

	public ArrayList<Bullet> getBullets() {
		return this.bullets;
	}
}
