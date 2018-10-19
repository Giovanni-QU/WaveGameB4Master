package mainGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.Random;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import org.json.JSONException;

import mainGame.Game.STATE;

/**
 * The main player in the game
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public class Player extends GameObject {
	Random r = new Random();
	Handler handler;
	private HUD hud;
	private Game game;
	private int damage;
	private int playerWidth, playerHeight;
	private int tempInvincable = 0;
	public static int playerSpeed = 10;
	public static int diagonalPlayerSpeed = 8;
	private SimpleMidi hitsoundMIDIPlayer;
	private String hitsoundMIDIMusic = "HitsoundPart2.mid";
	private boolean wasHit;
	

	public Player(double x, double y, ID id, Handler handler, HUD hud, Game game) {
		super(x, y, id);
		this.handler = handler;
		this.hud = hud;
		this.game = game;
		this.damage = 2;
		playerWidth = 32;
		playerHeight = 32;
		hitsoundMIDIPlayer = new SimpleMidi();
	}

	@Override
	public void tick() throws JSONException {
		this.x += velX;
		this.y += velY;
		x = Game.clamp(x, 0, Game.WIDTH - 38);
		y = Game.clamp(y, 0, Game.HEIGHT - 60);
		// add the trail that follows it
		handler.addObject(new Trail(x, y, ID.Trail, Color.white, playerWidth, playerHeight, 0.05, this.handler));
		collision();
		if (tempInvincable > 0) {
			tempInvincable--;
		}
		if (wasHit == true) {
			try {
				hitsoundMIDIPlayer.PlayMidi(hitsoundMIDIMusic);
			}
			catch (IOException | InvalidMidiDataException | MidiUnavailableException e) {
				e.printStackTrace();
			}
			wasHit = false;
		}
		checkIfDead();
	}

	public void checkIfDead() throws JSONException {
		if (hud.health <= 0) {// player is dead, game over!
			if (hud.getExtraLives() == 0) {
				game.gameState = STATE.GameOver;
				game.getGameOver().sendScore();
			}
			else if (hud.getExtraLives() > 0) {// has an extra life, game continues
				hud.setExtraLives(hud.getExtraLives() - 1);
				hud.setHealth(100);
			}
		}
	}

	/**
	 * Checks for collisions with all of the enemies, and handles it accordingly
	 */
	public void collision() {
		hud.updateScoreColor(Color.white);
		if (!handler.pickups.isEmpty()) {
		for (int j = 0; j < handler.pickups.size(); j++) {
			 Pickup pickupObject = handler.pickups.get(j);
			 System.out.println(j);
			if (pickupObject.getId() == ID.PickupCoin) {
				
				if (this.getBounds().intersects(pickupObject.getBounds())) {
					hud.setScore(500);
					handler.removePickup(pickupObject);
				}
			}
		}
		}
		
		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			if (tempObject.getId() == ID.EnemyBasic || tempObject.getId() == ID.EnemyFast
					|| tempObject.getId() == ID.EnemySmart || tempObject.getId() == ID.EnemyBossBullet
					|| tempObject.getId() == ID.EnemySweep || tempObject.getId() == ID.EnemyShooterBullet
					|| tempObject.getId() == ID.EnemyBurst || tempObject.getId() == ID.EnemyShooter
					|| tempObject.getId() == ID.BossEye) {// tempObject is an enemy
				// collision code
				if (getBounds().intersects(tempObject.getBounds()) && tempInvincable == 0) {// player hit an enemy
					hud.health -= damage;
					hud.updateScoreColor(Color.red);
					wasHit = true;
					tempInvincable = 15;
				}
			}
			if (tempObject.getId() == ID.EnemyBoss) {
				// Allows player time to get out of upper area where they will get hurt once the
				// boss starts moving
				if (this.y <= 138 && tempObject.isMoving) {
					hud.health -= 2;
					hud.updateScoreColor(Color.red);
					wasHit = true;
				}
			}
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.white);
		g.fillRect((int) x, (int) y, playerWidth, playerHeight);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) this.x, (int) this.y, 32, 32);
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public void setPlayerSize(int size) {
		this.playerWidth = size;
		this.playerHeight = size;
	}
}
