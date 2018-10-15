package mainGame;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.net.URL;

public class PickupCoin extends Pickup {
	private Handler handler;
	private Image coin = getImage("images/PickupCoin.PNG");
	private GameObject player;
	private int timeCounter = 0;
	private Game game;

	public PickupCoin(double x, double y, ID id, String path, Handler handler, Game game ) {
		
		super(x, y, id, path);
		this.handler = handler;
		this.img = getImage("images/PickupCoin.png");
		this.handler = handler;
		this.game = game;
		
	}


	public void tick() {
		
		
	}

	

	public void render(Graphics g) {
		
		g.drawImage(this.img, (int)super.getX(), (int)super.getY(), 50, 50, null);
		
	}


	public Rectangle getBounds() {
		
		return new Rectangle((int)super.getX(), (int)super.getY(), 50,  50);
		
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
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}

}


