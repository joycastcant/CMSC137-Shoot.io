import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.Runnable;

public class Player implements Runnable, Serializable { //implements KeyListener {
	//can be accessed in other classes as Player.<VAR_NAME>
	//tested by pressing keys to simulate actions
	//sample use in other classes (when a player1 kills player2):
		//player1.setPoints(Player.KILL);
		//player2.setPoints(Player.DIE);
	//(when player1 damages other players)
		//player1.setPoints(Player.DAMAGE);
	final static int KILL = 1000;
	final static int DIE = -500;
	final static int DAMAGE = 20;

	private String name;
	private int hp;
	private int points;
	private int kills;
	//private Weapon gun;
	private boolean isDead;
	private int posX;
	private int posY;
	private int prevX;
	private int prevY;
	private int direction;
	private transient BufferedImage sprite;	//marked as 'transient' bc BufferedImage does not implement Serializable
	private String id;

	public Player() {

	}
	
	public Player(String name, int[][] field, String id){
		this.name = name;
		this.points = 0;
		this.kills = 0;
		if (!this.name.equals(""))
			this.spawn(field);
		this.id = id;
	}

	public byte[] serialize() {	//called by a player
        try {
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
			objectStream.writeObject(this);
            return byteStream.toByteArray();
        } catch(Exception e) {
			System.out.println("Error in serialization");
		}
		return new byte[0];
	}
	
	public static Object deserialize(byte[] data) {	//called as a static fxn
        try{
			ByteArrayInputStream byteStream = new ByteArrayInputStream(data);
            ObjectInputStream objectStream = new ObjectInputStream(byteStream);
			return objectStream.readObject();
        } catch(Exception e) {
			System.out.println("Error in deserialization");
		}
		return new Player();
    }

	public void setPoints(int action) {
		this.points = this.points + action;
	}
		
	public void moveUp(int[][] field){
		if(!this.isDead){
			if(this.posY > 0){
				if(field[this.posX][this.posY - 1] == 0){
					this.prevX = this.posX;
					this.prevY = this.posY;

					field[this.posX][this.posY] = 0;
					this.posY = this.posY - 1;
					field[this.posX][this.posY] = 4;
					try{
						Thread.sleep(10);
					} catch (Exception e) {
						System.out.println(e);
					}
				}
			}
		}
	}
	public void moveDown(int[][] field){
		if(!this.isDead){
			if(this.posY < 42){
				if(field[this.posX][this.posY + 1] == 0){
					this.prevX = this.posX;
					this.prevY = this.posY;

					field[this.posX][this.posY] = 0;
					this.posY = this.posY + 1;
					field[this.posX][this.posY] = 3;
					try{
						Thread.sleep(10);
					} catch (Exception e) {
						System.out.println(e);
					}
				}
			}
		}
	}
	public void moveLeft(int[][] field){
		if(!this.isDead){
			if(this.posX > 0){
				if(field[this.posX - 1][this.posY] == 0){
					this.prevX = this.posX;
					this.prevY = this.posY;

					field[this.posX][this.posY] = 0;
					this.posX = this.posX - 1;
					field[this.posX][this.posY] = 3;
					try{
						Thread.sleep(10);
					} catch (Exception e) {
						System.out.println(e);
					}
				}
			}
		}
	}
	public void moveRight(int[][] field){
		if(!this.isDead){
			if(this.posX < 24){
				if(field[this.posX + 1][this.posY] == 0){
					this.prevX = this.posX;
					this.prevY = this.posY;

					field[this.posX][this.posY] = 0;
					this.posX = this.posX + 1;
					field[this.posX][this.posY] = 4;
					try{
						Thread.sleep(10);
					} catch (Exception e) {
						System.out.println(e);
					}
				}
			}
		}
	}
	
	public void run(){

	}

	public void shoot(){
	}
	public void invulnerable(){
	}
	public void jump(){
	}
	
	public void spawn(int[][] field){
		this.hp = 150;
		this.isDead = false;
		Random rand = new Random();
		int x,y;
		do{
			x = rand.nextInt(field.length);
			y = rand.nextInt(field[0].length);
		}while(field[x][y] == 1 || field[x][y] == 2);
		this.posX = x;
		this.posY = y;
		field[x][y] = 3;
	}
	public void die(int[][] field){
		this.isDead = true;
		field[this.posX][this.posY] = 0;
		//timer task
		this.spawn(field);
	}
	
	public void takeDamage(int[][] field){
		this.hp = this.hp - 15;
		if(this.hp <= 0){
			this.die(field);
		}
	}
	
	public int getPosX(){
		return this.posX;
	}

	public int getPosY(){
		return this.posY;
	}

	public int getPrevX(){
		return this.prevX;
	}

	public int getPrevY(){
		return this.prevY;
	}

	public BufferedImage getSprite(String path) {
		try{
			this.sprite = ImageIO.read(this.getClass().getResourceAsStream(path));
		}catch(IOException e){
			e.getMessage();
		}
		return this.sprite;
	}
	public int getWidth() {
		return this.sprite.getWidth();
	}
	public int getHeight() {
		return this.sprite.getHeight();
	}
	public int getHP(){
		return this.hp;
	}
	public int getPoints(){
		return this.points;
	}
	public int getKills(){
		return this.kills;
	}
	public boolean getStatus(){
		return this.isDead;
	}
	public int getDirection() {
		return this.direction;
	}
	public String getId() {
		return this.id;
	}
	public void setDirection(int dir) {
		this.direction = dir;
	}
}
