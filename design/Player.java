import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Player { //implements KeyListener {
	private String name;
	private int hp;
	private int points;
	private int kills;
	//private Weapon gun;
	private boolean isDead;
	private int posX;
	private int posY;
	private int direction;
	private BufferedImage sprite;
	private String id;
	
	public Player(String name, int[][] field, String id){
		this.name = name;
		this.points = 0;
		this.kills = 0;
		if (!this.name.equals(""))
			this.spawn(field);
		this.id = id;
	}

	/* public Player(String name, int[][] field, String id, int hp, int points, int kills){
		this.name = name;
		this.points = 0;
		this.kills = 0;
		this.spawn(field);
		this.id = id;
	} */
		
	public void moveUp(int[][] field){
		if(!this.isDead){
			if(this.posY > 0){
				if(field[this.posX][this.posY - 1] == 0){
					field[this.posX][this.posY] = 0;
					this.posY = this.posY - 1;
					field[this.posX][this.posY] = 4;
				}
			}
		}
	}
	public void moveDown(int[][] field){
		if(!this.isDead){
			if(this.posY < 42){
				if(field[this.posX][this.posY + 1] == 0){
					field[this.posX][this.posY] = 0;
					this.posY = this.posY + 1;
					field[this.posX][this.posY] = 3;
				}
			}
		}
	}
	public void moveLeft(int[][] field){
		if(!this.isDead){
			if(this.posX > 0){
				if(field[this.posX - 1][this.posY] == 0){
					field[this.posX][this.posY] = 0;
					this.posX = this.posX - 1;
					field[this.posX][this.posY] = 3;
				}
			}
		}
	}
	public void moveRight(int[][] field){
		if(!this.isDead){
			if(this.posX < 24){
				if(field[this.posX + 1][this.posY] == 0){
					field[this.posX][this.posY] = 0;
					this.posX = this.posX + 1;
					field[this.posX][this.posY] = 4;
				}
			}
		}
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
