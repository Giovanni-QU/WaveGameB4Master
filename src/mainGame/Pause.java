package mainGame;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.print.DocFlavor.STRING;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import mainGame.Game.STATE;

public class Pause {

	//VARIABLES
	private Game game;
	private Handler handler;
	private Image buttonImg;
	private HUD hud;
	
	private Image enemy1Img;
	private Image enemy2Img;
	private Image enemy3Img;
	private Image enemy4Img;
	private Image enemy5Img;
	private Image speedBoostIcon;
	private Image healthRegenIcon;
	private Image healthIncreaseIcon;
	private Image shrinkIcon;
	private Image freezeTimeIcon;
	private Image clearScreenIcon;
	private Image extraLifeIcon;
	private Image damageResistanceIcon;
	private Image coin;



	private Image boss1Img;
	private Image boss2Img;
	
	private Boolean gameSaved;
	
	private Pause pause;
	private FileWriter savedGameFile;
	private Spawn1to10 spawner1;
	private Spawn10to20 spawner2;
	private Upgrades upgrade;
	
	
	//CONSTRUCTOR
	public Pause (HUD hud, Game game, Handler handler, Boolean gameSaved, Spawn1to10 sp1, Spawn10to20 sp2, Upgrades upgrade){
		this.hud = hud;
		this.game = game;
		this.handler = handler;
		this.spawner1 = sp1;
		this.spawner2 = sp2;
		this.upgrade = upgrade;
		
		buttonImg = getImage("images/background.jpg");
		
		enemy1Img = getImage("images/gameImgEnemy1.PNG");
		enemy2Img = getImage("images/gameImgEnemy2.PNG");
		enemy3Img = getImage("images/gameImgEnemy3.PNG");
		enemy4Img = getImage("images/gameImgEnemy4.PNG");
		enemy5Img = getImage("images/gameImgEnemy5.PNG");
		speedBoostIcon = getImage("images/SpeedBoostAbility.PNG");
		healthRegenIcon = getImage("images/Health Regen Ability.png");
		healthIncreaseIcon = getImage("images/Health Increase Ability.png");
		shrinkIcon = getImage("images/Shrink Ability.png");
		freezeTimeIcon = getImage("images/Freeze Time Ability.png");
		clearScreenIcon = getImage("images/Clear Screen Ability.png");
		extraLifeIcon = getImage("images/Extra Life Ability.png");
		clearScreenIcon = getImage("images/Clear Screen Ability.png");
		damageResistanceIcon = getImage("images/Damage Resistance Ability.png");
		coin = getImage("images/PickupCoin.png");


		boss1Img = getImage("images/EnemyBoss.png");
		boss2Img = getImage("images/bosseye.png");
		
		this.gameSaved = gameSaved;
		pause = this;
	}
	
	
	//METHODS
	public void writeToSavedGameFile(String n, int score, double hp, int level, int enemy, int lvlRem, String ability, int abilityUses){
		
		try{
		savedGameFile = new FileWriter("gameSavesFile.txt");
		PrintWriter printWriter = new PrintWriter(savedGameFile);
		
		printWriter.println("1");
		printWriter.print(n + " " + score + " "+ hp + " " + level + " " + enemy + " " + lvlRem + " " + ability + " " + abilityUses);
		
		printWriter.close();
		} catch (FileNotFoundException e){
			System.out.println(e);
			System.exit(1);		// IO error; exit program
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void clearSavedGameFile(){
		try{
		savedGameFile = new FileWriter("gameSavesFile.txt");
		PrintWriter printWriter = new PrintWriter(savedGameFile);
		
		printWriter.println(0);
		
		printWriter.close();
		} catch (FileNotFoundException e){
			System.out.println(e);
			System.exit(1);		// IO error; exit program
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//saves the game gets elements from different classes
	public void saveGame(){
		double health = hud.getHealth();
		int score = hud.getScore();
		int level = hud.getLevel();
		int enemy = 0;
		int levelsRem = spawner1.getLevelsRemaining();
		int abilityUses = 0;
		String ability = "none";
		if(level < 11) {
			enemy = spawner1.getLevelNumber();
			levelsRem = spawner1.getLevelsRemaining();
		} else {
			enemy = spawner2.getLevelNumber();
			ability = upgrade.getAbility();
			abilityUses = hud.getAbilityUses();
			levelsRem = spawner2.getLevelsRemaining();
		}
		
		
		writeToSavedGameFile("Alex", score, health, level, enemy, levelsRem, ability, abilityUses);
	}
	
	public void unSaveGame(){
		hud.resetHealth();
		hud.setLevel(1);
		hud.setScore(0);
		spawner1.setLevelNumber(0);
		spawner1.setLevelsRemaining(10);
		
		this.clearSavedGameFile();
		
	}
	
	
	public void tick(){
		//handler.tick();
	}
	
	public void setGameSaved(Boolean s){
		gameSaved = s;
	}
	
	public Boolean getGameSave(){
		return gameSaved;
	}
	
	public void reset(){
		this.pause = new Pause(this.hud, this.game, this.handler, gameSaved, this.spawner1, this.spawner2, this.upgrade);
	}
	
	public void render(Graphics g){
		Font font;
		if(game.gameState == STATE.Pause){
			font = new Font("Amoebic", 1, 100);
			
			
			g.drawImage(buttonImg, 550, 100, 900, 200, null);
			
			g.setColor(Color.WHITE);
			g.setFont(font);
			g.drawString("MAIN", 900, 225);
			
			g.drawImage(buttonImg, 550, 350, 900, 200, null);
			
			g.setColor(Color.WHITE);
			g.setFont(font);
			g.drawString("HELP", 900, 475);
			
			g.drawImage(buttonImg, 550, 600, 900, 200, null);
			
			g.setColor(Color.WHITE);
			g.setFont(font);
			
			if(!gameSaved){
				g.drawString("SAVE", 900, 725);
			} else {
				g.drawString("SAVED", 900, 725);
			}
			g.drawImage(buttonImg, 550, 850, 900, 200, null);
			g.setColor(Color.WHITE);
			g.setFont(font);
			g.drawString("SHOP", 900, 975);
			

		}
		if(game.gameState == STATE.PauseShop) {
			font = new Font("impact", 1, 50);
		

		} 


        Font font2;
        if (this.game.gameState == STATE.PauseShop) {
            font = new Font("impact", 1, 50);
            font2 = new Font("impact",1,30);
			g.setColor(Color.BLACK);
			g.fillRect(0,0,1920,1280);
			g.setFont(font);
			g.setColor(Color.white);

			g.drawString("Shop in progress", 900, 70);

			
			
			g.drawRect(850, 870, 200, 64);
			g.drawString("Main", 920, 910);
		}
			else if(game.gameState == STATE.PauseH1){
			
			 font = new Font("impact", 1, 50);
			font2 = new Font("impact", 1, 30);

			g.drawString("Shop", 900, 70);

			int rectW = 1895;
			int rectH = 1020;
			int rectY = rectH - 80;

			g.setColor(Color.white);
			g.drawRect(10, 80, (rectW / 2) - 15, (rectY / 2) - 15); // top left
			g.drawRect((rectW / 2) + 5, 80, (rectW / 2) - 15, (rectY / 2) - 15); // top right
			g.drawRect(10, 80 + (rectY / 2) + 5, (rectW / 2) - 15, (rectY / 2) - 15); // bottom left
			g.drawRect((rectW / 2) + 5, 80 + (rectY / 2) + 5, (rectW / 2) - 15, (rectY / 2) - 15); // bottom right


			g.setFont(font2);
			g.drawString("Passive Abilities", 360, 110);
			g.drawString("Active Abilities", 360, 585);
			g.drawString("Passive Loadout", 1260, 110);
			g.drawString("Description", 1290, 585);

			/*g.drawString("Shop under construction.", 900, 70);
			g.drawString("Passive Abilities:", 100, 100);
			g.drawString("Active Abilities: ", 100, 600);
			g.drawRect(50, 100, 900, 450);
			g.drawRect(1000, 100, 900, 450);
			g.drawRect(50, 600, 900, 400);
			g.drawRect(1000, 600, 900, 400);*/

			//Left side of the Shop

			//Health Regeneration
			g.drawImage(healthRegenIcon, 100, 125, 125, 125, null);//passive
			g.drawImage(coin,100,260,40,40,null);
			g.drawString("X500",125,300);
			//Increase Max Health
			g.drawImage(healthIncreaseIcon, 300, 125, 125, 125, null);//passive
			g.drawImage(coin,300,260,40,40,null);
			g.drawString("X500",325,300);
			//Shrink Player Size
			g.drawImage(shrinkIcon, 500, 125, 125, 125, null);//passive
			g.drawImage(coin,500,260,40,40,null);
			g.drawString("X500",525,300);
			//Extra Life
			g.drawImage(extraLifeIcon, 300, 325, 125, 125, null);//passive
			g.drawImage(coin,300,460,40,40,null);
			g.drawString("X500",325,500);
			//Freeze Time
			g.drawImage(freezeTimeIcon, 100, 650, 125, 125, null);//Active
			g.drawImage(coin,100,785,40,40,null);
			g.drawString("X500",125,825);
			//Speed Boost
			g.drawImage(speedBoostIcon, 100, 325, 125, 125, null);//passive
			g.drawImage(coin,100,460,40,40,null);
			g.drawString("X500",125,500);
			//Damage Resistance
			g.drawImage(damageResistanceIcon, 700, 125, 125, 125, null);//passive
			g.drawImage(coin,700,260,40,40,null);
			g.drawString("X500",725,300);
			//Clear Screen
			g.drawImage(clearScreenIcon, 500, 650, 125, 125, null);//Active
			g.drawImage(coin,500,785,40,40,null);
			g.drawString("X500",525,825);

			//Passive Loadout
			g.drawImage(healthRegenIcon, 1050, 125, 125, 125, null);
			g.drawImage(damageResistanceIcon, 1650, 125, 125, 125, null);
			g.drawImage(freezeTimeIcon, 1450, 325, 125, 125, null);
			g.drawImage(clearScreenIcon, 1650, 325, 125, 125, null);
			g.drawImage(speedBoostIcon, 1050, 325, 125, 125, null);
			g.drawImage(healthIncreaseIcon, 1250, 125, 125, 125, null);
			g.drawImage(shrinkIcon, 1450, 125, 125, 125, null);
			g.drawImage(extraLifeIcon, 1250, 325, 125, 125, null);

			//Description
			
        } else if (this.game.gameState == STATE.PauseH1) {
            font = new Font("impact", 1, 50);
            font2 = new Font("impact", 1, 30);
			g.setColor(Color.BLACK);
			g.fillRect(0,0,1920,1280);
			g.setFont(font);
			g.setColor(Color.white);
			g.drawString("Help", 900, 70);

			g.setFont(font2);
			g.drawString("Waves: Simply use Arrow keys or WASD to move and avoid enemies.", 40, 300);
			g.drawString("One you avoid them long enough, a new batch will spawn in! Defeat each boss to win!", 40, 340);
			
			g.drawString("Press P to pause and un-pause", 40, 400);
			g.drawString("Press Enter to use abilities when they have been equipped", 40, 440);
			
			g.drawString("Click Next to see Enemy and Boss Summaries", 40, 800);

			g.setFont(font2);
			g.setColor(Color.white);
			
			g.drawRect(1600, 870, 200, 65);
			g.drawString("Next", 1650, 910);
			
			g.drawRect(850, 870, 200, 64);
			g.drawString("Main", 920, 910);
		} else if (game.gameState == STATE.PauseH2){
			font = new Font("impact", 1, 50);
			font2 = new Font("impact", 1, 30);
			
			g.setColor(Color.BLACK);
			g.fillRect(0,0,1920,1280);
			g.setFont(font);
			g.setColor(Color.white);
			g.drawString("Different Enemies", 800, 70);
			
			
			g.setFont(font2);
			g.drawString("1. Green. These will", 40, 300);
			g.drawString("follow you where ever", 40, 340);
			g.drawString("you are on screen.", 40, 380);
			
			g.drawString("2. Red. These bounce", 400, 300);
			g.drawString("of the walls at a", 400, 340);
			g.drawString("45 degree angle", 400, 380);
			
			g.drawString("3. Cyan. These also", 750, 300);
			g.drawString("bounce of walls but at", 750, 340);
			g.drawString("a shallow angle", 750, 380);
			
			g.drawString("4. Yellow. These squares", 1100, 300);
			g.drawString("shoot little bullets at", 1100, 340);
			g.drawString("you to dodge", 1100, 380);
			
			
			g.drawString("5. Burst. Warning flashes", 1500, 300);
			g.drawString("will appear from the side", 1500, 340);
			g.drawString("they will jump out from", 1500, 380);
			
			g.setFont(font2);
			g.setColor(Color.white);
			g.drawRect(100, 870, 200, 64);
			g.drawString("Back", 150, 910);
			
			g.drawRect(850, 870, 200, 64);
			g.drawString("Main", 920, 910);
			
			g.drawRect(1600, 870, 200, 65);
			g.drawString("Next", 1650, 910);
			
			//images
			g.drawImage(enemy1Img, 100, 440, 250, 250, null);
			g.drawImage(enemy2Img, 400, 440, 250, 250, null);
			g.drawImage(enemy3Img, 750, 440, 250, 250, null);
			g.drawImage(enemy4Img, 1100, 440, 250, 250, null);
			g.drawImage(enemy5Img, 1500, 440, 300, 250, null);
		} else if (game.gameState == STATE.PauseH3){
			font = new Font("impact", 1, 50);
			font2 = new Font("impact", 1, 30);
			
			g.setColor(Color.BLACK);
			g.fillRect(0,0,1920,1280);
			g.setFont(font);
			g.setColor(Color.white);
			g.drawString("The Bosses", 830, 70);
			
			
			g.setFont(font2);
			g.drawString("The Red Boss. Dodge the", 40, 300);
			g.drawString("explosive bullets that he", 40, 340);
			g.drawString("throws and stay below the line.", 40, 380);
			
			g.drawImage(boss1Img, 100, 440, 250, 250, null);
			
			g.drawString("The Green Eye Boss. Each", 600, 300);
			g.drawString("moves differently so keep", 600, 340);
			g.drawString("moving and stay alert!", 600, 380);
			
			g.drawImage(boss2Img, 600, 440, 250, 250, null);
			
			g.setFont(font2);
			g.setColor(Color.white);
			g.drawRect(100, 870, 200, 64);
			g.drawString("Back", 150, 910);
			
			g.drawRect(850, 870, 200, 64);
			g.drawString("Main", 920, 910);
		}


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
