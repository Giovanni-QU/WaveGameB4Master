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
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.print.DocFlavor.STRING;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import org.json.JSONException;

import mainGame.Game.STATE;

/**
 * The main menu
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public class Menu {

	private Game game;
	private Handler handler;
	private HUD hud;
	private BufferedImage img;
	private int timer;
	private Random r;
	private ArrayList<Color> colorPick = new ArrayList<Color>();
	private int colorIndex;
	private Spawn1to10 spawner;
	
	//images
	private Image enemy1Img;
	private Image enemy2Img;
	private Image enemy3Img;
	private Image enemy4Img;
	private Image enemy5Img;
	
	private Image boss1Img;
	private Image boss2Img;

	public Menu(Game game, Handler handler, HUD hud, Spawn1to10 spawner) {
		this.game = game;
		this.handler = handler;
		this.hud = hud;
		this.spawner = spawner;
		timer = 10;
		r = new Random();
		addColors();

		img = null;
		try {
			img = ImageIO.read(new File("images/4.gif"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		handler.addObject(new MenuFireworks((r.nextInt(Game.WIDTH) - 25), 500, 50, 50, 0, -2,
				colorPick.get(r.nextInt(6)), ID.Firework, this.handler));
		
		//images
		enemy1Img = getImage("images/gameImgEnemy1.PNG");
		enemy2Img = getImage("images/gameImgEnemy2.PNG");
		enemy3Img = getImage("images/gameImgEnemy3.PNG");
		enemy4Img = getImage("images/gameImgEnemy4.PNG");
		enemy5Img = getImage("images/gameImgEnemy5.PNG");
		
		boss1Img = getImage("images/EnemyBoss.png");
		boss2Img = getImage("images/bosseye.png");
	}

	public void addColors() {
		colorPick.add(Color.blue);
		colorPick.add(Color.white);
		colorPick.add(Color.green);
		colorPick.add(Color.red);
		colorPick.add(Color.cyan);
		colorPick.add(Color.magenta);
		colorPick.add(Color.yellow);
	}

	public void tick() throws JSONException {
		timer--;
		if (timer <= 0) {
			handler.object.clear();
			colorIndex = r.nextInt(6);
			handler.addObject(new MenuFireworks((r.nextInt(Game.WIDTH) - 25), 1080, 100, 100, 0, -4,
					colorPick.get(colorIndex), ID.Firework, this.handler));
			timer = 300;
		}
		handler.tick();
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

	public void render(Graphics g) {
		if (game.gameState == STATE.Menu) {
			g.drawImage(img, 0, 0, Game.WIDTH, Game.HEIGHT, null);
			handler.render(g);
			Font font = new Font("Amoebic", 1, 100);
			Font font2 = new Font("Amoebic", 1, 60);

			g.setFont(font);
			g.setColor(Color.white);
			g.drawString("Game Modes", 1140, 200);

			g.setFont(font);
			g.setColor(Color.white);
			g.drawString("Wave Game: B4&Aftr.io", 75, 100);

			g.setColor(Color.white);
			g.drawRect(1050, 300, 350, 400);
			g.setFont(font2);
			g.setColor(Color.white);
			g.drawString("Waves",1130, 510);
			
			g.setColor(Color.white);
			g.drawRect(1450, 300, 350, 400);
			g.setFont(font2);
			g.setColor(Color.white);
			g.drawString("Easy Mode",1470, 510);
			
			g.setColor(Color.white);
			g.drawRect(1050, 735, 750, 250);
			g.setFont(font2);
			g.setColor(Color.white);
			g.drawString("Leaderboard",1250, 900);
			

			/*g.setColor(Color.white);
			g.drawRect(1440, 135, 400, 400);
			g.setFont(font2);
			g.setColor(Color.white);
			g.drawString("Bosses", 1550, 215);*/

			/*g.setColor(Color.white);
			g.drawRect(990, 585, 400, 400);
			g.setFont(font2);
			g.setColor(Color.white);
			g.drawString("Attack", 1095, 665);*/

			/*g.setColor(Color.white);
			g.drawRect(1440, 585, 400, 400);
			g.setFont(font2);
			g.setColor(Color.white);
			g.drawString("Hunger", 1550, 665);*/


			g.setColor(Color.white);
			g.drawRect(80, 135, 850, 250);
			g.setFont(font);
			g.setColor(Color.white);
			g.drawString("Help", 400, 280);

			g.setColor(Color.white);
			g.drawRect(80, 435, 850, 250);
			g.setFont(font);
			g.setColor(Color.white);
			g.drawString("Credits", 340, 600);

			g.setColor(Color.white);
			g.drawRect(80, 735, 850, 250);
			g.setFont(font);
			g.setColor(Color.white);
			g.drawString("Quit", 400, 900);
		} else if (game.gameState == STATE.Help) { // if the user clicks on "help"
			Font font = new Font("impact", 1, 50);
			Font font2 = new Font("impact", 1, 30);

			g.setFont(font);
			g.setColor(Color.white);
			g.drawString("Help", 900, 70);

			g.setFont(font2);
			g.drawString("Waves: Simply use Arrow keys or WASD to move and avoid enemies.", 40, 200);
			g.drawString("One you avoid them long enough, a new batch will spawn in! Defeat each boss to win!", 40, 240);
			
			g.drawString("Press P to pause and un-pause", 40, 300);
			g.drawString("Press Enter to use abilities when they have been equipped", 40, 340);
			
			g.drawString("Click Next to see Enemy and Boss Summeries", 40, 800);

			g.setFont(font2);
			g.setColor(Color.white);
			
			g.drawRect(1600, 870, 200, 65);
			g.drawString("Next", 1650, 910);
			
			g.drawRect(850, 870, 200, 64);
			g.drawString("Main", 920, 910);
		} else if (game.gameState == STATE.Help2){ //second help page
			
			Font font = new Font("impact", 1, 50);
			Font font2 = new Font("impact", 1, 30);
			
			g.setFont(font);
			g.setColor(Color.white);
			g.drawString("Different Enemies", 800, 70);
			
			
			g.setFont(font2);
			g.drawString("1. Green. These will", 40, 200);
			g.drawString("follow you where ever", 40, 240);
			g.drawString("you are on screen.", 40, 280);
			
			g.drawString("2. Red. These bounce", 400, 200);
			g.drawString("of the walls at a", 400, 240);
			g.drawString("45 degree angle", 400, 280);
			
			g.drawString("3. Cyan. These also", 750, 200);
			g.drawString("bounce of walls but at", 750, 240);
			g.drawString("a shallow angle", 750, 280);
			
			g.drawString("4. Yellow. These squares", 1100, 200);
			g.drawString("shoot little bullets at", 1100, 240);
			g.drawString("you to dodge", 1100, 280);
			
			
			g.drawString("5. Burst. Warning flashes", 1500, 200);
			g.drawString("will appear from the side", 1500, 240);
			g.drawString("they will jump out from", 1500, 280);
			
			g.setFont(font2);
			g.setColor(Color.white);
			g.drawRect(100, 870, 200, 64);
			g.drawString("Back", 150, 910);
			
			g.drawRect(850, 870, 200, 64);
			g.drawString("Main", 920, 910);
			
			g.drawRect(1600, 870, 200, 65);
			g.drawString("Next", 1650, 910);
			
			//images
			g.drawImage(enemy1Img, 100, 340, 250, 250, null);
			g.drawImage(enemy2Img, 400, 340, 250, 250, null);
			g.drawImage(enemy3Img, 750, 340, 250, 250, null);
			g.drawImage(enemy4Img, 1100, 340, 250, 250, null);
			g.drawImage(enemy5Img, 1500, 340, 300, 250, null);
			
			
			
		} else if (game.gameState == STATE.Help3){
			
			Font font = new Font("impact", 1, 50);
			Font font2 = new Font("impact", 1, 30);
			
			g.setFont(font);
			g.setColor(Color.white);
			g.drawString("The Bosses", 830, 70);
			
			
			g.setFont(font2);
			g.drawString("The Red Boss. Dodge the", 40, 200);
			g.drawString("explosive bullets that he", 40, 240);
			g.drawString("throws and stay below the line.", 40, 280);
			
			g.drawImage(boss1Img, 100, 340, 250, 250, null);
			
			g.drawString("The Green Eye Boss. Each", 600, 200);
			g.drawString("moves differently so keep", 600, 240);
			g.drawString("moving and stay alert!", 600, 280);
			
			g.drawImage(boss2Img, 600, 340, 250, 250, null);
			
			g.setFont(font2);
			g.setColor(Color.white);
			g.drawRect(100, 870, 200, 64);
			g.drawString("Back", 150, 910);
			
			g.drawRect(850, 870, 200, 64);
			g.drawString("Main", 920, 910);

		}
		/*else if(game.gameState == STATE.PauseShop) {
			game.upgradeScreen.render(g);
		}*/
	}
}
