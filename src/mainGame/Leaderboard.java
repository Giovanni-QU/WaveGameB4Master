package mainGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.net.MalformedURLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.json.JSONException;
import org.json.JSONObject;

public class Leaderboard {
	
	private Game game;
	private Handler handler;
	private HUD hud;
	private int timer;
	private Color retryColor;
	private String text;

	public Leaderboard(Game game, Handler handler, HUD hud) throws MalformedURLException {
		this.game = game;
		this.handler = handler;
		this.hud = hud;
		this.retryColor = Color.white;
	}

	public void tick(){
		handler.clearPlayer();
	}

	public void render(Graphics g) {
		Font font = new Font("Amoebic", 1, 100);
		Font font2 = new Font("Amoebic", 1, 40);
		g.setFont(font);
		text = "Leaderboard";
		g.drawString(text, Game.WIDTH / 2 - getTextWidth(font, text) / 2, Game.HEIGHT / 2 - 150);
		g.setFont(font2);
		
		ArrayList<String> leaderboard = hud.getLeaderboard();
		
		for (int i = 0; i < leaderboard.size(); i++){
			text = leaderboard.get(i);
			g.drawString(text,Game.WIDTH / 2 - getTextWidth(font2,text)/2, Game.HEIGHT/2 + (50*i));
		}
	}

	/**
	 * Function for getting the pixel width of text
	 * 
	 * @param font
	 *            the Font of the test
	 * @param text
	 *            the String of text
	 * @return width in pixels of text
	 */
	public int getTextWidth(Font font, String text) {
		AffineTransform at = new AffineTransform();
		FontRenderContext frc = new FontRenderContext(at, true, true);
		int textWidth = (int) (font.getStringBounds(text, frc).getWidth());
		return textWidth;
	}

}
