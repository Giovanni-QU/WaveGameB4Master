package mainGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The main Heads Up Display of the game
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public class HUD {
	public double health = 100;
	private double healthMax = 100;
	private double greenValue = 255;
	private int score = 00000000000;
	private int level = 0;
	private boolean regen = false;
	private int timer = 10;
	private int healthBarWidth = 400;
	private int healthBarModifier = 2;
	private boolean doubleHealth = false;
	private String ability = "";
	private int abilityUses;
	private Color scoreColor = Color.white;
	private Color freezeColor = new Color(0, 255, 255, 25);
	private Color regenColor = new Color(120, 255, 120);
	private int extraLives = 0;

	private String highScoreString = "";
	
	private ArrayList<String> leaderboard;

	public void tick() {
		health = Game.clamp(health, 0, health);
		health = Game.clamp(health, 0, healthMax);
		greenValue = Game.clamp(greenValue, 0, 255);
		greenValue = health * healthBarModifier;
		score++;

		if (regen) {// regenerates health if that ability has been unlocked
			timer--;
			if (timer == 0) {
				health += 1;
				timer = 10;
			}
			health = Game.clamp(health, 0, healthMax);
		}
	}
	
	public void reset(){
		health = 100;
		greenValue = 255;
		healthBarModifier = 2;
	}

	public void render(Graphics g) {
		Font font = new Font("Amoebic", 1, 30);
		g.setColor(Color.GRAY);
		g.fillRect(15, 15, healthBarWidth, 64);
		g.setColor(new Color(75, (int) greenValue, 0));
		g.fillRect((int) 15, (int) 15, (int) health * 4, 64);
		if (regen && health < healthMax)
			g.setColor(regenColor);
		else
			g.setColor(scoreColor);
		g.drawRect(15, 15, healthBarWidth, 64);
		if (Handler.getFreeze()) {
			g.setColor(Color.GRAY);
			g.fillRect(1560, 20, 300, 30);
			g.setColor(Color.CYAN);
			g.fillRect(1560, 20, Handler.getTimer(), 30);
			g.setColor(scoreColor);
			g.drawRect(1560, 20, 300, 30);
			g.setColor(freezeColor);
			g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		}

		g.setFont(font);
		g.setColor(scoreColor);
		g.drawString("Score: " + score, 15, 115);
		g.drawString("Level: " + level, 15, 150);
		g.drawString("Extra Lives: " + extraLives, 15, 185);
		
		if (this.highScoreString != null){
			g.drawString("High Score:", 15, 950);
			g.drawString(this.highScoreString, 15, 1000);
		}

		if (ability.equals("freezeTime")) {
			g.drawString("Time Freezes: " + abilityUses, Game.WIDTH - 300, 64);
		} else if (ability.equals("clearScreen")) {
			g.drawString("Screen Clears: " + abilityUses, Game.WIDTH - 300, 64);
		} else if (ability.equals("levelSkip")) {
			g.drawString("Level Skips: " + abilityUses, Game.WIDTH - 300, 64);
		}
	}

	public void setAbility(String ability) {
		this.ability = ability;
	}
	
	public String getAbility(){
		return ability;
	}

	public int getAbilityUses() {
		return this.abilityUses;
	}

	public void setAbilityUses(int abilityUses) {
		this.abilityUses = abilityUses;
	}

	public void updateScoreColor(Color color) {
		this.scoreColor = color;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public double getHealth(){
		return health;
	}

	public int getScore() {
		return score;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setHealth(double health) {
		this.health = health;
	}

	public void setRegen() {
		regen = true;
	}

	public void resetRegen() {
		regen = false;
	}

	public void setExtraLives(int lives) {
		this.extraLives = lives;
	}

	public int getExtraLives() {
		return this.extraLives;
	}

	public void healthIncrease() {
		doubleHealth = true;
		healthMax = 200;
		this.health = healthMax;
		healthBarModifier = 1;
		healthBarWidth = 800;
	}

	public void resetHealth() {
		doubleHealth = false;
		healthMax = 100;
		this.health = healthMax;
		healthBarModifier = 2;
		healthBarWidth = 400;
	}

	public void restoreHealth() {
		this.health = healthMax;
	}

	public void setHighScore(String data) {
		
		leaderboard = new ArrayList<String>(Arrays.asList(data.split(",")));
		System.out.println(leaderboard.size());
		
		this.highScoreString = leaderboard.get(0);
		
	}
	
	public ArrayList<String> getLeaderboard(){
		return leaderboard;
	}
}