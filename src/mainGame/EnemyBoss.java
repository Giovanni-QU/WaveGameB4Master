package mainGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.net.URL;
import java.util.Random;

/**
 * The first boss in the game
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public class EnemyBoss extends GameObject {

	private Handler handler;
	private int timer = 80;
	private int timer2 = 50;
	private int timer3 = 20;
	Random r = new Random();
	private Image img;
	private int spawn;

	public EnemyBoss(ID id, Handler handler) {
		super(Game.WIDTH / 2 - 48, -120, id);
		this.handler = handler;
		velX = 0;
		velY = 2;
		img = getImage("images/EnemyBoss.png");
		this.health = 1000;//full health is 1000
	}

	public void tick() {
		this.x += velX;
		this.y += velY;

		if (timer <= 0)
			velY = 0;
		else
			timer--;
		drawFirstBullet();
		if (timer <= 0)
			this.health -= 1;
			timer2--;
		if (timer2 <= 0) {
			if (velX == 0)
				velX = 8;
			this.isMoving = true;
			spawn = r.nextInt(15);
			if (spawn == 0) {
				handler.addObject(
						new EnemyBossBullet((int) this.x + 48, (int) this.y + 96, ID.EnemyBossBullet, handler,5 + r.nextInt(25),100 + r.nextInt(500)));
				
			}
		}

		// if (this.y <= 0 || this.y >= Game.HEIGHT - 40) velY *= -1;
		if (this.x <= 0 || this.x >= Game.WIDTH - 96)
			velX *= -1;

		// handler.addObject(new Trail(x, y, ID.Trail, Color.red, 96, 96, 0.025,
		// this.handler));

	}

	public Image getImage(String path) {
		Image image = null;
		try {
			URL imageURL = Game.class.getResource(path);
			image = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return image;
	}

	public void render(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		g.drawLine(0, 138, Game.WIDTH, 138);
		g.drawImage(img, (int) this.x, (int) this.y, 96, 96, null);

		// HEALTH BAR
		g.setColor(Color.GRAY);
		g.fillRect(Game.WIDTH / 2 - 500, Game.HEIGHT - 150, 1000, 50);
		g.setColor(Color.RED);
		g.fillRect(Game.WIDTH / 2 - 500, Game.HEIGHT - 150, this.health, 50);
		g.setColor(Color.WHITE);
		g.drawRect(Game.WIDTH / 2 - 500, Game.HEIGHT - 150, 1000, 50);

	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) this.x, (int) this.y, 96, 96);
	}

	// allows for grey line to be drawn, as well as first bullet shot
	public void drawFirstBullet() {
		if (timer2 == 1)
			handler.addObject(new EnemyBossBullet((int) this.x + 48, (int) this.y + 96, ID.EnemyBossBullet, handler,10 + r.nextInt(25),100 + r.nextInt(500)));
	}

}
