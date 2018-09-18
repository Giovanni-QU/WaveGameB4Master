package mainGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

/**
 * The bullets that the first boss shoots
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public class EnemyBossBullet extends GameObject {

	private Handler handler;
	Random r = new Random();
	private int max = 45;
	private int min = -45;
	
	private int sizeX = 16;
	private int sizeY = 16;
	
	private int ticksToExplode;
	private int explosionSize;
	
	private boolean isGrowing = true;
	
	

	public EnemyBossBullet(double x, double y, ID id, Handler handler, int _ticksToExplode, int _explosionSize) {
		super(x, y, id);
		this.handler = handler;
		velX = (r.nextInt((max - min) + 1) + min);// OFFICIAL WAY TO GET A RANGE FOR randInt()
		velY = 30;
		
		ticksToExplode = _ticksToExplode;
		explosionSize = _explosionSize;
		
	}

	public void tick() {
		this.x += velX;
		this.y += velY;
		
		ticksToExplode --;
		
		if (ticksToExplode <= 0){
			explode();
		}

		// if (this.y <= 0 || this.y >= Game.HEIGHT - 40) velY *= -1;
		// if (this.x <= 0 || this.x >= Game.WIDTH - 16) velX *= -1;

		if (this.y >= Game.HEIGHT)
			handler.removeObject(this);

	}

	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect((int) x, (int) y, this.sizeX, this.sizeY);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) this.x, (int) this.y, this.sizeX, this.sizeY);
	}
	
	public void explode(){
		this.velX = 0;
		this.velY = 0;
		
		if (isGrowing){
			sizeX += (int)(explosionSize/100);
			x -= (int)(explosionSize/200);
			sizeY += (int)(explosionSize/100);
			y -= (int)(explosionSize/200);
		} else {
			sizeX -= (int)(explosionSize/100);
			x += (int)(explosionSize/200);
			sizeY -= (int)(explosionSize/100);
			y += (int)(explosionSize/200);
		}
		
		if (this.sizeX >= explosionSize){
			isGrowing = false;
		}
		
		if (this.sizeX <= 0){
			handler.removeObject(this);
		}
	}

}
