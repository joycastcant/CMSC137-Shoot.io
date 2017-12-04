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
		Bullet b = new Bullet(0, 0, 0, 0);
		if(this.direction == Game.RIGHT) {
			b = new Bullet(this.type, this.playerX + Game.TILE_SIZE, this.playerY + Game.TILE_SIZE / 2, this.direction);
		} else if(this.direction == Game.LEFT) {
			b = new Bullet(this.type, this.playerX, this.playerY + Game.TILE_SIZE / 2, this.direction);
		} else if(this.direction == Game.UP) {
			b = new Bullet(this.type, this.playerX + Game.TILE_SIZE / 2, this.playerY, this.direction);
		} else if(this.direction == Game.DOWN) {
			b = new Bullet(this.type, this.playerX + Game.TILE_SIZE / 2, this.playerY + Game.TILE_SIZE, this.direction);
		}

		b.isMoving = true;
		this.bullets.add(b);
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

		for(int i = 0; i < this.bullets.size(); i++) {
			Bullet bullet = this.bullets.get(i);
			if(!bullet.isMoving) {
				if(this.direction == Game.RIGHT)
					bullet.setX(x + Game.TILE_SIZE);
				else if(this.direction == Game.LEFT)
					bullet.setX(x);
				else if(this.direction == Game.UP || this.direction == Game.DOWN)
					bullet.setX(x + Game.TILE_SIZE / 2);
	
				System.out.println("\n X: " + this.getX());
			}

		}
	}

	public void setY(int y) {
		this.playerY = y;

		for(int i = 0; i < this.bullets.size(); i++) {
			Bullet bullet = this.bullets.get(i);
			if(!bullet.isMoving) {
				if(this.direction == Game.RIGHT || this.direction == Game.LEFT)
					bullet.setY(y + Game.TILE_SIZE / 2);
				else if(this.direction == Game.UP)
					bullet.setY(y);
				else if(this.direction == Game.UP)
					bullet.setY(y + Game.TILE_SIZE);
	
				System.out.println("\n Y: " + this.getY());
			}
		}
	}

	public int getX()  {
		return this.playerX;
	}

	public int getY()  {
		return this.playerY;
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
