package mainGame;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.net.URL;
import java.util.Random;

import org.json.JSONException;

import mainGame.Game.STATE;

/**
 * The last boss in the game, shown in a 3x3 grid of 9 instances of BossEye
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public class BossEye extends GameObject {

	private Image img;
	private Random r = new Random();;
	private float alpha = 0;
	private double life = 0.005;
	private int tempCounter = 0;
	private int timer;
	private int spawnOrder = 1;// make them begin moving from left to right, top to bottom
	private int placement;// where they are in the 3x3 grid of eyes
	private double speed;
	private double[] speedTypes = { -5, -6, -7, -8, -9 };
	private GameObject player;
	private Handler handler;
	private int timeCounter = 0;
	private Game game;
	
	private int velX1 = 4;
	private int velY1 = 11;
	private int velX2 = 11;
	private int velY2 = 4;
	private int velY3 = 7;
	private int velX3 = 7;
	private int velX4 = (int)(Math.random() * 10);
	private int velY4 = (int)(Math.random() * 10);
	
	private int stopTime = 2360;
	
	
	
	
	public BossEye(Game game, double x, double y, ID id, Handler handler, int placement) {
		super(x, y, id);
		this.img = getImage("images/bosseye.png");
		this.velX = 0;
		this.velY = 0;
		this.speed = speedTypes[r.nextInt(4)];
		this.handler = handler;
		this.placement = placement;
		this.timer = 200;
		this.game = game;
	}

	public void tick() throws JSONException {
		
		timeCounter++; // for use in abilities and stop
		
		if(timeCounter >= stopTime){
			if(game.gameState == STATE.GameOver){
				
			} else {
				game.gameState = STATE.GameWon;
				if(timeCounter == 0){
					game.getGameOver().sendScore();
				}
				
				handler.clearPlayer();
				alpha *= 0;
			}
		}
		
		
		
		if (tempCounter == 0) {
			if (alpha < 0.995) {// this handles each eye fading in to the game
				alpha += life + 0.001;
			} else {
				tempCounter++;
				for (int i = 0; i < handler.object.size(); i++) {
					if (handler.object.get(i).getId() == ID.Player)
						this.player = handler.object.get(i);
				}
			}
		} else if (tempCounter == 1) {
			spawn();
			if (this.placement == 1 && this.spawnOrder >= 1) {
				attackPlayer();
			} else if (this.placement == 2 && this.spawnOrder >= 2) {
				bounceAtAngle();
			} else if (this.placement == 3 && this.spawnOrder >= 3) {
				bounceAtRandomAngle();
			} else if (this.placement == 4 && this.spawnOrder >= 4) {
				bounceAtAngle3();
			} else if (this.placement == 5 && this.spawnOrder >= 5) {
				bounceAtAngle();
			} else if (this.placement == 6 && this.spawnOrder >= 6) {
				bounceAtAngle2();
			} else if (this.placement == 7 && this.spawnOrder >= 7) {
				bounceAtRandomAngle();
			} else if (this.placement == 8 && this.spawnOrder >= 8) {
				bounceAtAngle3();
			} else if (this.placement == 9 && this.spawnOrder >= 9) {
				bounceAtRandomAngle();
			} else {
				this.health = 0;
			}
		}
	}

	public void spawn() {
		timer--;
		if (timer == 0) {
			this.spawnOrder++;
			timer = 200;
		}
	}

	//for the one eye that will follow the player, to keep them moving
	public void attackPlayer() {
		
		
		if (player != null) {
			double diffY = (this.y - player.getY());
			double diffX = (this.x - player.getX());
			double distance = Math.sqrt(((this.x - this.player.getX()) * (this.x - this.player.getX()))
					+ ((this.y - this.player.getY()) * (this.y - this.player.getY())));
			this.velX = (this.speed / distance) * diffX;
			this.velY = (this.speed / distance) * diffY;
			
			
			if(timeCounter >= stopTime){
				velX *= 0;
				velY *= 0;
			}
			
			this.x += this.velX;
			this.y += this.velY;
		}
	}
	
	//bounces at a shallow angle
	public void bounceAtAngle() {
		
		if(timeCounter >= stopTime){
			velX1 *= 0;
			velY1 *= 0;
		}
		
		this.x += velX1;
		this.y += velY1;

		if (this.y <= 0 || this.y >= Game.HEIGHT - 80) {
			velY1 *= -1;
		}
		if (this.x <= 0 || this.x >= Game.WIDTH - 80) {
			velX1 *= -1;
		}
	}
	
	//bounces at more vertical angle
	public void bounceAtAngle2 (){
		if(timeCounter >= stopTime){
			velX2 *= 0;
			velY2 *= 0;
		}
		
		
		
		this.x += velX2;
		this.y += velY2;

		if (this.y <= 0 || this.y >= Game.HEIGHT - 80) {
			velY2 *= -1;
		}
		if (this.x <= 0 || this.x >= Game.WIDTH - 80) {
			velX2 *= -1;
		}
	}
	
	//bounces at a neutral angle
	public void bounceAtAngle3(){
		
		if(timeCounter >= stopTime){
			velX3 *= 0;
			velY3 *= 0;
		}
		
		
		
		this.x += velX3;
		this.y += velY3;

		if (this.y <= 0 || this.y >= Game.HEIGHT - 80) {
			velY3 *= -1;
		}
		if (this.x <= 0 || this.x >= Game.WIDTH - 80) {
			velX3 *= -1;
		}
	}
	
	//changes directions every few seconds
	public void bounceAtRandomAngle(){
		
		if(timeCounter >= stopTime){
			velX4 *= 0;
			velY4 *= 0;
		}
		
		
		
		this.x += velX4;
		this.y += velY4;
		
		if (timeCounter % 60 == 0){
			
			double negator1 = Math.random();
			double negator2 = Math.random();
			
			velX4 = (int)(Math.random() * 10);
			if (negator1 < .5){
				velX4 *= -1;
			}
			
			
			velY4 = (int)(Math.random() * 10);
			if (negator2 < .5) {
				velY4 *= -1;
			}
			
		}
		

		if (this.y <= 0 || this.y >= Game.HEIGHT - 80) {
			
			velY4  *= -1;
			
		}
		
		
		if (this.x <= 0 || this.x >= Game.WIDTH - 80) {
			
			velX4  *= -1;
		}
	}
	
	

	public void render(Graphics g) {
		if (g.getColor() == Color.BLACK) {// prevent black text from showing "Game Over" if the player dies here, or
											// "Winner!" if the player survives
			g.setColor(Color.GREEN);
		}
		Graphics2D g2d = (Graphics2D) g;
		g2d.setComposite(makeTransparent(alpha));
		g.drawImage(img, (int) this.x, (int) this.y, null);
		g2d.setComposite(makeTransparent(1));
	}

	private AlphaComposite makeTransparent(float alpha) {
		int type = AlphaComposite.SRC_OVER;
		return (AlphaComposite.getInstance(type, alpha));
	}

	public Rectangle getBounds() {
		return new Rectangle((int) this.x, (int) this.y, (int) this.img.getWidth(null), (int) this.img.getHeight(null));
	}

	public Image getImage(String path) {
		Image image = null;
		try {
			URL imageURL = Game.class.getResource(path);
			image = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return image;
	}
}
