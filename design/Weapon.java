import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
	/*private Player owner;
	private BufferedImage sprite;*/
	
	public Weapon(){
	}
	
	public Weapon(String type){
		if(type.equals("PISTOL")){
			this.type = PISTOL;
		} else {
			this.type = SHOTGUN;
		}
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
	
	public void fire(){
		
	}
	
	/*public BufferedImage getSprite(int col, int row, int width, int height) {
		BufferedImage img = this.sprite.getSubimage((col * 50) - 50, (row * 50) - 50, width, height);
		return img;
	}*/
	
	public int getType(){
		return this.type;
	}
	public int getDamage(){
		return this.damage;
	}
}



