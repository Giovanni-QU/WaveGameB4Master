package mainGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.swing.JOptionPane;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.IOAcknowledge;
import io.socket.IOCallback;
import io.socket.SocketIO;
import io.socket.SocketIOException;

/**
 * Modeled after he game over screen
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public class GameWon {

	private Game game;
	private Handler handler;
	private HUD hud;
	private int timer;
	private Color retryColor;
	private String text;
	private int ticks = 0;
	public boolean highscore = true;
	
	private SimpleMidi winMIDIPlayer;
	private String winMIDIMusic = "Super_Mario_Bros._-_Flag_synth.mid";

	public GameWon(Game game, Handler handler, HUD hud) {
		this.game = game;
		this.handler = handler;
		this.hud = hud;
		timer = 90;
		this.retryColor = Color.white;
		winMIDIPlayer = new SimpleMidi();
	}

	public void tick() throws MalformedURLException, JSONException {
		handler.clearPlayer();
		flash();

		if (ticks == 0 && highscore == true){
			// This line is cached until the connection is establisched.
			
			String username = JOptionPane.showInputDialog("Enter a username");
			
			JSONObject jsonString = new JSONObject()
					.put("username", username)
					.put("score", hud.getScore());
			
			game.socket.emit("setScore", jsonString);
		}
		else if (ticks == 1){
			try {
				winMIDIPlayer.PlayMidi(winMIDIMusic);
			} catch (IOException | InvalidMidiDataException | MidiUnavailableException e) {
				e.printStackTrace();
			}
		}
		ticks++;
	}

	public void render(Graphics g) {
		Font font = new Font("Amoebic", 1, 100);
		Font font2 = new Font("Amoebic", 1, 60);
		g.setFont(font);
		text = "CONGRATS you WON the Game!!!";
		g.drawString(text, Game.WIDTH / 2 - getTextWidth(font, text) / 2, Game.HEIGHT / 2 - 150);
		g.setFont(font2);
		text = "Level: " + hud.getLevel();
		g.drawString(text, Game.WIDTH / 2 - getTextWidth(font2, text) / 2, Game.HEIGHT / 2 - 50);
		text = "Score: " + hud.getScore();
		g.drawString(text, Game.WIDTH / 2 - getTextWidth(font2, text) / 2, Game.HEIGHT / 2 + 50);
		g.setColor(this.retryColor);
		g.setFont(font2);
		text = "Click anywhere to play again";
		g.drawString(text, Game.WIDTH / 2 - getTextWidth(font2, text) / 2, Game.HEIGHT / 2 + 150);
	}

	public void flash() {
		timer--;
		if (timer == 45) {
			this.retryColor = Color.black;
		} else if (timer == 0) {
			this.retryColor = Color.white;
			timer = 90;
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
