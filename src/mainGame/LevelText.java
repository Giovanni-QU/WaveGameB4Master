package mainGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.net.URL;
import java.util.Random;
import java.awt.AlphaComposite;


/**
 * This is the text you see before each set of 10 levels
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public class LevelText extends GameObject {

	private String text;
	private int timer;
	private Handler handler;
	private Color[] color = { Color.WHITE, Color.RED, Color.BLUE, Color.CYAN, Color.GREEN, Color.MAGENTA, Color.ORANGE,
			Color.PINK, Color.YELLOW };
	private Random r = new Random();
	private int index;
	private float alpha = 1;
	private double life;

	public LevelText(double x, double y, String text, ID id, Handler _handler) {
		super(x, y, id);
		this.text = text;
		this.handler = _handler;
		AffineTransform at = new AffineTransform();
		timer = 180;
	}

	@Override
	public void tick() {
	}

	@Override
	public void render(Graphics g) {
		timer--;

		
		if (text == "Controls") {
			Font font = new Font("Amoebic", 1, 25);
			g.setFont(font);
			g.setColor(Color.CYAN);// set the new random color
			g.fillRect(1586, 10, 300, 300);
			g.setColor(Color.BLACK);
			g.drawString(this.text, 1675, (int) 29);
			g.drawImage(this.getImage("/images/arrowkeys.png"), 1680, 100, 100, 100, null);
			g.drawString("Left", 1600, 155);
			g.drawString("right", 1810, 155);
			g.drawString("up", 1710, 90);
			g.drawString("Down", 1695, 225);
			Font font2 = new Font("Amoebic", 1, 18);
			g.setFont(font2);
			g.drawString("Pause Menu: PRESS P", 1588, 265);
			g.drawString("Activate Power-up: PRESS ENTER", 1588, 285);
		}
		else {
			Font font = new Font("Amoebic", 1, 125);
		g.setFont(font);
		g.setColor(color[index]);// set the new random color
		g.drawString(this.text, Game.WIDTH / 2 - getTextWidth(font, this.text) / 2, (int) this.y);
		if (timer == 0) {
			index = r.nextInt(9);// get a new random color
			timer = 15;
		     } 
		}
	}

	public int getTextWidth(Font font, String text) {
		AffineTransform affinetransform = new AffineTransform();
		FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
		int textWidth = (int) (font.getStringBounds(text, frc).getWidth());
		return textWidth;
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

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}
}
