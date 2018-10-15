package mainGame;

import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import mainGame.Game.STATE;

/**
 * This class closely resembles Spawn1to10. Please refer to that class for
 * documentation
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public class Spawn10to20 {

	private Handler handler;
	private HUD hud;
	private Game game;
	private int scoreKeep = 0;
	private Random r = new Random();
	private int randx, randy;
	private int timer;
	private int levelTimer;
	private String[] side = { "left", "right", "top", "bottom" };
	ArrayList<Integer> levels = new ArrayList<Integer>();
	private int index;
	private int randomMax;
	private int levelNumber = 0;
	private int levelCounter = 1;
	private int tempCounter = 0;
	public static int LEVEL_SET_2_RESET = 0;
	private int levelsRemaining = 10;
	public static boolean spawning = true;

	public Spawn10to20(Handler handler, HUD hud, Spawn1to10 spawner, Game game) {
		restart();
		this.handler = handler;
		this.hud = hud;
		this.game = game;
		hud.restoreHealth();
		timer = 10;
		levelTimer = 150;
		randomMax = 10;
		hud.setLevel(1);
		tempCounter = 0;
		levelCounter = 1;
		addLevels();
		index = r.nextInt(randomMax);
		levelNumber = 0;
	}

	public void addLevels() {
		for (int i = 1; i <= 10; i++) {
			levels.add(i);
		}
	}

	public void tick() {
		
		handler.addPickupCoin(new PickupCoin(100, 100, ID.PickupCoin, "images/PickupCoin.PNG", handler, game ));
		
		if (game.getPlayerXInt() > (Game.WIDTH - Game.WIDTH/(6 + (2/3)) - 5)) {
			randx = r.nextInt((Game.WIDTH - (Game.WIDTH - game.getPlayerXInt())) - Game.WIDTH/(6 + (2/3)));
		} else if (game.getPlayerXInt() < Game.WIDTH/(6 + (2/3)) + 5) {
			randx = r.nextInt(Game.WIDTH - game.getPlayerXInt() - Game.WIDTH/(6 + (2/3))) + game.getPlayerXInt() + Game.WIDTH/(6 + (2/3));
		} else {
			if (r.nextInt(2) == 0) {
				randx = r.nextInt((Game.WIDTH - (Game.WIDTH - game.getPlayerXInt())) - Game.WIDTH/(6 + (2/3)));
			} else {
				randx = r.nextInt(Game.WIDTH - game.getPlayerXInt() - Game.WIDTH/(6 + (2/3))) + game.getPlayerXInt() + Game.WIDTH/(6 + (2/3));
			}
		}
		
		if (game.getPlayerYInt() > (Game.HEIGHT - Game.HEIGHT/(6 + (2/3))) - 5) {
			randy = r.nextInt((Game.HEIGHT - (Game.HEIGHT - game.getPlayerYInt())) - Game.HEIGHT/(6 + (2/3)));
		} else if (game.getPlayerYInt() < Game.HEIGHT/(6 + (2/3)) + 5) {
			randy = r.nextInt(Game.HEIGHT - game.getPlayerYInt() - Game.HEIGHT/(6 + (2/3))) + game.getPlayerYInt() + Game.HEIGHT/(6 + (2/3));
		} else {
			if (r.nextInt(2) == 0) {
				randy = r.nextInt(Game.HEIGHT - game.getPlayerYInt() - Game.HEIGHT/(6 + (2/3))) + game.getPlayerYInt() + Game.HEIGHT/(6 + (2/3));
			} else {
				randy = r.nextInt((Game.HEIGHT - (Game.HEIGHT - game.getPlayerYInt())) - Game.HEIGHT/(6 + (2/3)));
			}
		}
		
		// if(LEVEL_SET_2_RESET < 1){
		// restart();
		// LEVEL_SET_2_RESET ++;
		// }
		if (levelNumber <= 0) {
			levelTimer--;
			if (tempCounter < 1) {
				handler.addObject(new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, "Same levels...",
						ID.Levels1to10Text,handler));
				handler.addObject(new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2, "...but a little harder now",
						ID.Levels1to10Text,handler));
				tempCounter++;
			}
			if (levelTimer <= 0) {
				handler.clearEnemies();
				levelCounter = 1;
				tempCounter = 0;
				levelNumber = levels.get(index);
			}

		}

		else if (levelNumber == 1) {
			if (spawning) {
				timer--;
			}
			levelTimer--;
			if (tempCounter < 1) {
				handler.addObject(new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Challenge: Hotshots"),
						ID.Levels1to10Text,handler));
				levelTimer = 1500;
				tempCounter++;
			}
			if (timer == 0) {
				handler.addObject(
						new EnemyBasic(randx, randy, 13, 13, ID.EnemyBasic, handler));
				timer = 80;
			}
			if (levelTimer == 1320) {
				handler.clearLevelText();
			}
			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				levelCounter++;
				timer = 40;
				tempCounter = 0;
				if (randomMax == 1) {
					levelNumber = 101;
				} else {
					levels.remove(index);
					randomMax--;
					index = r.nextInt(randomMax);
					levelNumber = levels.get(index);
				}
			}
		} else if (levelNumber == 2) {
			if (spawning) {
				timer--;
			}
			levelTimer--;
			if (tempCounter < 1) {
				handler.addObject(new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Challenge: Sweep"),
						ID.Levels1to10Text,handler));
				levelTimer = 1500;
				tempCounter++;
			}
			if (timer == 30) {
				handler.addObject(
						new EnemySweep(randx, randy, 20, 2, ID.EnemySweep, handler));
			} else if (timer == 20) {
				handler.addObject(
						new EnemySweep(randx, randy, 20, -2, ID.EnemySweep, handler));
			} else if (timer == 10) {
				handler.addObject(
						new EnemySweep(randx, randy, 20, 4, ID.EnemySweep, handler));
			} else if (timer == 0) {
				handler.addObject(
						new EnemySweep(randx, randy, 20, -4, ID.EnemySweep, handler));
				timer = 45;
			}
			if (levelTimer == 1320) {
				handler.clearLevelText();
			}
			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				levelCounter++;
				tempCounter = 0;
				if (randomMax == 1) {
					levelNumber = 101;
				} else {
					levels.remove(index);
					randomMax--;
					index = r.nextInt(randomMax);
					levelNumber = levels.get(index);
				}
			}
		} else if (levelNumber == 3) {
			if (spawning) {
				timer--;
			}
			levelTimer--;
			if (tempCounter < 1) {
				handler.addObject(new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Challenge: Tail Chase"),
						ID.Levels1to10Text,handler));
				levelTimer = 1500;
				tempCounter++;
			}
			if (timer == 0) {
				handler.addObject(
						new EnemySmart(randx, randy, -7, ID.EnemySmart, handler));
				timer = 60;
			}
			if (levelTimer == 1320) {
				handler.clearLevelText();
			}
			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				levelCounter++;
				timer = 10;
				tempCounter = 0;
				if (randomMax == 1) {
					levelNumber = 101;
				} else {
					levels.remove(index);
					randomMax--;
					index = r.nextInt(randomMax);
					levelNumber = levels.get(index);
				}
			}
		} else if (levelNumber == 4) {
			levelTimer--;
			if (tempCounter < 1) {
				handler.addObject(new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Challenge: Target"),
						ID.Levels1to10Text,handler));
				handler.addObject(new EnemyShooter(randx - 35, randy - 75, 100, 100,
						-30, ID.EnemyShooter, this.handler));
				levelTimer = 1300;
				tempCounter++;
			}
			if (levelTimer == 1120) {
				handler.clearLevelText();
			}
			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				levelCounter++;
				timer = 10;
				tempCounter = 0;
				if (randomMax == 1) {
					levelNumber = 101;
				} else {
					levels.remove(index);
					randomMax--;
					index = r.nextInt(randomMax);
					levelNumber = levels.get(index);
				}
			}
		} else if (levelNumber == 5) {
			if (spawning) {
				timer--;
			}
			levelTimer--;
			if (tempCounter < 1) {
				handler.addObject(new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Challenge: Burst"),
						ID.Levels1to10Text,handler));
				levelTimer = 1400;
				tempCounter++;
			}
			if (timer <= 0) {
				handler.addObject(new EnemyBurst(-250, 250, 75, 75, 250, side[r.nextInt(4)], ID.EnemyBurst, handler));
				timer = 120;
			}
			if (levelTimer == 1220) {
				handler.clearLevelText();
			}
			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				levelCounter++;
				timer = 10;
				tempCounter = 0;
				if (randomMax == 1) {
					levelNumber = 101;
				} else {
					levels.remove(index);
					randomMax--;
					index = r.nextInt(randomMax);
					levelNumber = levels.get(index);
				}
			}
		} else if (levelNumber == 6) {
			if (spawning) {
				timer--;
			}
			levelTimer--;
			if (tempCounter < 1) {
				handler.addObject(new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Challenge: Seeing Red"),
						ID.Levels1to10Text,handler));
				levelTimer = 1500;
				tempCounter++;
			}
			if (timer == 0) {
				handler.addObject(
						new EnemyBasic(randx, randy, 15, 15, ID.EnemyBasic, handler));
				timer = 50;
			}
			if (levelTimer == 1320) {
				handler.clearLevelText();
			}
			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				levelCounter++;
				timer = 40;
				tempCounter = 0;
				if (randomMax == 1) {
					levelNumber = 101;
				} else {
					levels.remove(index);
					randomMax--;
					index = r.nextInt(randomMax);
					levelNumber = levels.get(index);
				}
			}
		} else if (levelNumber == 7) {
			if (spawning) {
				timer--;
			}
			levelTimer--;
			if (tempCounter < 1) {
				handler.addObject(new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Challenge: Big Sweep"),
						ID.Levels1to10Text,handler));
				levelTimer = 1500;
				tempCounter++;
			}
			if (timer == 35) {
				handler.addObject(
						new EnemySweep(randx, randy, 30, 2, ID.EnemySweep, handler));
			} else if (timer == 25) {
				handler.addObject(
						new EnemySweep(randx, randy, 30, -2, ID.EnemySweep, handler));
			} else if (timer == 15) {
				handler.addObject(
						new EnemySweep(randx, randy, 30, 4, ID.EnemySweep, handler));
			} else if (timer == 0) {
				handler.addObject(
						new EnemySweep(randx, randy, 30, -4, ID.EnemySweep, handler));
				timer = 30;
			}
			if (levelTimer == 1320) {
				handler.clearLevelText();
			}
			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				levelCounter++;
				tempCounter = 0;
				if (randomMax == 1) {
					levelNumber = 101;
				} else {
					levels.remove(index);
					randomMax--;
					index = r.nextInt(randomMax);
					levelNumber = levels.get(index);
				}
			}
		} else if (levelNumber == 8) {
			if (spawning) {
				timer--;
			}
			levelTimer--;
			if (tempCounter < 1) {
				handler.addObject(new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Challenge: Run"),
						ID.Levels1to10Text,handler));
				levelTimer = 1000;
				tempCounter++;
			}
			if (timer == 0) {
				handler.addObject(
						new EnemySmart(randx, randy, -9, ID.EnemySmart, handler));
				timer = 50;
			}
			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				levelCounter++;
				timer = 10;
				tempCounter = 0;
				if (randomMax == 1) {
					levelNumber = 101;
				} else {
					levels.remove(index);
					randomMax--;
					index = r.nextInt(randomMax);
					levelNumber = levels.get(index);
				}
			}
		} else if (levelNumber == 9) {
			levelTimer--;
			if (tempCounter < 1) {
				handler.addObject(new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Challenge: Point Blank"),
						ID.Levels1to10Text,handler));
				handler.addObject(new EnemyShooter(randx - 35, randy - 75, 200, 200,
						-40, ID.EnemyShooter, this.handler));
				levelTimer = 2500;
				tempCounter++;
			}

			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				levelCounter++;
				timer = 10;
				tempCounter = 0;
				if (randomMax == 1) {
					levelNumber = 101;
				} else {
					levels.remove(index);
					randomMax--;
					index = r.nextInt(randomMax);
					levelNumber = levels.get(index);
				}
			}
		} else if (levelNumber == 10) {
			if (spawning) {
				timer--;
			}
			levelTimer--;
			if (tempCounter < 1) {
				handler.addObject(new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Challenge: Crazy Burst"),
						ID.Levels1to10Text,handler));
				levelTimer = 1400;
				tempCounter++;
			}
			if (timer <= 0) {
				handler.addObject(new EnemyBurst(-300, 300, 60, 60, 300, side[r.nextInt(4)], ID.EnemyBurst, handler));
				timer = 60;
			}

			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				levelCounter++;
				timer = 10;
				tempCounter = 0;
				if (randomMax == 1) {
					levelNumber = 101;
				} else {
					levels.remove(index);
					randomMax--;
					index = r.nextInt(randomMax);
					levelNumber = levels.get(index);
				}
			}
		}

		else if (levelNumber == 101) {
			game.gameState = STATE.Boss;
			if (tempCounter < 1) {
				handler.addObject(new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, ("Can you win the gauntlet?"),
						ID.Levels1to10Text,handler));
				handler.addObject(new BossEye(game, Game.WIDTH / 2 - 150, Game.HEIGHT / 2 - 150, ID.BossEye, handler, 1));
				handler.addObject(new BossEye(game, Game.WIDTH / 2 - 50, Game.HEIGHT / 2 - 150, ID.BossEye, handler, 2));
				handler.addObject(new BossEye(game, Game.WIDTH / 2 + 50, Game.HEIGHT / 2 - 150, ID.BossEye, handler, 3));
				handler.addObject(new BossEye(game, Game.WIDTH / 2 - 150, Game.HEIGHT / 2 - 50, ID.BossEye, handler, 4));
				handler.addObject(new BossEye(game, Game.WIDTH / 2 - 50, Game.HEIGHT / 2 - 50, ID.BossEye, handler, 5));
				handler.addObject(new BossEye(game, Game.WIDTH / 2 + 50, Game.HEIGHT / 2 - 50, ID.BossEye, handler, 6));
				handler.addObject(new BossEye(game, Game.WIDTH / 2 - 150, Game.HEIGHT / 2 + 50, ID.BossEye, handler, 7));
				handler.addObject(new BossEye(game, Game.WIDTH / 2 - 50, Game.HEIGHT / 2 + 50, ID.BossEye, handler, 8));
				handler.addObject(new BossEye(game, Game.WIDTH / 2 + 50, Game.HEIGHT / 2 + 50, ID.BossEye, handler, 9));
				handler.clearLevelText();
				tempCounter++;
			}
			else if (tempCounter == 1) {
				handler.clearLevelText();
			}
		}
		// WINNER
		// else if(levelNumber){
		// levelTimer --;
		// if(tempCounter < 1){
		// handler.addObject(new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200,
		// "Same levels...", ID.Levels1to10Text));
		// handler.addObject(new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2,
		// "...but a little harder now", ID.Levels1to10Text));
		// tempCounter++;
		// }
		// if(levelTimer <= 0){
		// handler.clearEnemies();
		// tempCounter = 0;
		// levelNumber = levels.get(index);
		// }
		//
		// }

	}

	public void skipLevel() {
		if (randomMax == 1) {
			tempCounter = 0;
			levelNumber = 101;
		} else if (randomMax > 1) {
			levels.remove(index);
			randomMax--;
			tempCounter = 0;
			index = r.nextInt(randomMax);
			levelNumber = levels.get(index);
		}
	}
	
	public static void setSpawn(boolean x) {
		spawning = x;
	}

	public void restart() {
		levelNumber = -10;
		tempCounter = 0;
		levelTimer = 150;
		randomMax = 10;
		index = r.nextInt(randomMax);
	}
	
	public int getLevelNumber(){
		return levelNumber;
	}
	
	public void setLevelNumber(int l){
		levelNumber = l;
	}
	
	public int getLevelsRemaining(){
		return randomMax;
	}
	
	public void setLevelsRemaining(int levelRem){
		levelsRemaining = levelRem;
	}
	
	
	public void setRandomMax(int n){
		randomMax = n;
	}

	public void resetTempCounter() {
		tempCounter = 0;
		
	}

	
}
