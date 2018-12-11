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
			g.drawString("Game Modes", (int) (Game.drawWidth*0.59375), (int) (Game.drawHeight*0.18518518518518518518518518518519));

			g.setFont(font);
			g.setColor(Color.white);
			g.drawString("Wave Game: B4&Aftr.io", (int) (Game.drawWidth*0.0390625), (int) (Game.drawHeight*0.09259259259259259259259259259259));

			g.setColor(Color.white);
			g.drawRect((int) (Game.drawWidth*0.546875), (int) (Game.drawHeight*0.27777777777777777777777777777778), (int) (Game.drawWidth*0.18229166666666666666666666666667), (int) (Game.drawHeight*0.37037037037037037037037037037037));
			g.setFont(font2);
			g.setColor(Color.white);
			g.drawString("Waves", (int) (Game.drawWidth*0.58854166666666666666666666666667), (int) (Game.drawHeight*0.47222222222222222222222222222222));
			
			g.setColor(Color.white);
			g.drawRect((int) (Game.drawWidth*0.75520833333333333333333333333333), (int) (Game.drawHeight*0.27777777777777777777777777777778), (int) (Game.drawWidth*0.18229166666666666666666666666667), (int) (Game.drawHeight*0.37037037037037037037037037037037));
			g.setFont(font2);
			g.setColor(Color.white);
			g.drawString("Easy Mode", (int) (Game.drawWidth*0.765625), (int) (Game.drawHeight*0.47222222222222222222222222222222));
			
			g.setColor(Color.white);
			g.drawRect((int) (Game.drawWidth*0.546875), (int) (Game.drawHeight*0.68055555555555555555555555555556), (int) (Game.drawWidth*0.390625), (int) (Game.drawHeight*0.23148148148148148148148148148148));
			g.setFont(font2);
			g.setColor(Color.white);
			g.drawString("Leaderboard", (int) (Game.drawWidth*0.65104166666666666666666666666667), (int) (Game.drawHeight*0.83333333333333333333333333333333));
			

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
			g.drawRect((int) (Game.drawWidth*0.04166666666666666666666666666667), (int) (Game.drawHeight*0.125), (int) (Game.drawWidth*0.44270833333333333333333333333333), (int) (Game.drawHeight*0.23148148148148148148148148148148));
			g.setFont(font);
			g.setColor(Color.white);
			g.drawString("Help", (int) (Game.drawWidth*0.20833333333333333333333333333333), (int) (Game.drawHeight*0.25925925925925925925925925925926));

			g.setColor(Color.white);
			g.drawRect((int) (Game.drawWidth*0.04166666666666666666666666666667), (int) (Game.drawHeight*0.40277777777777777777777777777778), (int) (Game.drawWidth*0.44270833333333333333333333333333), (int) (Game.drawHeight*0.23148148148148148148148148148148));
			g.setFont(font);
			g.setColor(Color.white);
			g.drawString("Credits", (int) (Game.drawWidth*0.17708333333333333333333333333333), (int) (Game.drawHeight*0.55555555555555555555555555555556));

			g.setColor(Color.white);
			g.drawRect((int) (Game.drawWidth*0.04166666666666666666666666666667), (int) (Game.drawHeight*0.68055555555555555555555555555556), (int) (Game.drawWidth*0.44270833333333333333333333333333), (int) (Game.drawHeight*0.23148148148148148148148148148148));
			g.setFont(font);
			g.setColor(Color.white);
			g.drawString("Quit", (int) (Game.drawWidth*0.20833333333333333333333333333333), (int) (Game.drawHeight*0.83333333333333333333333333333333));
		} else if (game.gameState == STATE.Help) { // if the user clicks on "help"
			Font font = new Font("impact", 1, 50);
			Font font2 = new Font("impact", 1, 30);

			g.setFont(font);
			g.setColor(Color.white);
			g.drawString("Help", (int) (Game.drawWidth*0.46875), (int) (Game.drawHeight*0.06481481481481481481481481481481));

			g.setFont(font2);
			g.drawString("Waves: Simply use Arrow keys or WASD to move and avoid enemies.", (int) (Game.drawWidth*0.02083333333333333333333333333333), (int) (Game.drawHeight*0.10416666666666666666666666666667));
			g.drawString("One you avoid them long enough, a new batch will spawn in! Defeat each boss to win!", (int) (Game.drawWidth*0.02083333333333333333333333333333), (int) (Game.drawHeight*0.125));
			
			g.drawString("Press P to pause and un-pause", (int) (Game.drawWidth*0.02083333333333333333333333333333), 300);
			g.drawString("Press Enter to use abilities when they have been equipped", (int) (Game.drawWidth*0.02083333333333333333333333333333), (int) (Game.drawHeight*0.17708333333333333333333333333333));
			
			g.drawString("Click Next to see Enemy and Boss Summeries", (int) (Game.drawWidth*0.02083333333333333333333333333333), (int) (Game.drawHeight*0.41666666666666666666666666666667));

			g.setFont(font2);
			g.setColor(Color.white);
			
			g.drawRect((int) (Game.drawWidth*0.83333333333333333333333333333333), (int) (Game.drawHeight*0.80555555555555555555555555555556), (int) (Game.drawWidth*0.10416666666666666666666666666667), (int) (Game.drawHeight*0.06018518518518518518518518518519));
			g.drawString("Next", (int) (Game.drawWidth*0.859375), (int) (Game.drawHeight*0.84259259259259259259259259259259));
			
			g.drawRect((int) (Game.drawWidth*0.44270833333333333333333333333333), (int) (Game.drawHeight*0.80555555555555555555555555555556), (int) (Game.drawWidth*0.10416666666666666666666666666667), (int) (Game.drawHeight*0.05925925925925925925925925925926));
			g.drawString("Main", (int) (Game.drawWidth*0.47916666666666666666666666666667), (int) (Game.drawHeight*0.84259259259259259259259259259259));
		} else if (game.gameState == STATE.Help2){ //second help page
			
			Font font = new Font("impact", 1, 50);
			Font font2 = new Font("impact", 1, 30);
			
			//(int) (Game.drawWidth*)
			//(int) (Game.drawHeight*)
			
			g.setFont(font);
			g.setColor(Color.white);
			g.drawString("Different Enemies", (int) (Game.drawWidth*0.41666666666666666666666666666667), (int) (Game.drawHeight*0.03645833333333333333333333333333));
			
			
			g.setFont(font2);
			g.drawString("1. Green. These will", (int) (Game.drawWidth*0.02083333333333333333333333333333), (int) (Game.drawHeight*0.10416666666666666666666666666667));
			g.drawString("follow you where ever", (int) (Game.drawWidth*0.02083333333333333333333333333333), (int) (Game.drawHeight*0.125));
			g.drawString("you are on screen.", (int) (Game.drawWidth*0.02083333333333333333333333333333), (int) (Game.drawHeight*0.14583333333333333333333333333333));
			
			g.drawString("2. Red. These bounce", (int) (Game.drawWidth*0.20833333333333333333333333333333), (int) (Game.drawHeight*0.10416666666666666666666666666667));
			g.drawString("of the walls at a", (int) (Game.drawWidth*0.20833333333333333333333333333333), (int) (Game.drawHeight*0.125));
			g.drawString("45 degree angle", (int) (Game.drawWidth*0.20833333333333333333333333333333), (int) (Game.drawHeight*0.14583333333333333333333333333333));
			
			g.drawString("3. Cyan. These also", (int) (Game.drawWidth*0.390625), (int) (Game.drawHeight*0.10416666666666666666666666666667));
			g.drawString("bounce of walls but at", (int) (Game.drawWidth*0.390625), (int) (Game.drawHeight*0.125));
			g.drawString("a shallow angle", (int) (Game.drawWidth*0.390625), (int) (Game.drawHeight*0.14583333333333333333333333333333));
			
			g.drawString("4. Yellow. These squares", (int) (Game.drawWidth*0.57291666666666666666666666666667), (int) (Game.drawHeight*0.10416666666666666666666666666667));
			g.drawString("shoot little bullets at", (int) (Game.drawWidth*0.57291666666666666666666666666667), (int) (Game.drawHeight*0.125));
			g.drawString("you to dodge", (int) (Game.drawWidth*0.57291666666666666666666666666667), (int) (Game.drawHeight*0.14583333333333333333333333333333));
			
			
			g.drawString("5. Burst. Warning flashes", (int) (Game.drawWidth*0.78125), (int) (Game.drawHeight*0.10416666666666666666666666666667));
			g.drawString("will appear from the side", (int) (Game.drawWidth*0.78125), (int) (Game.drawHeight*0.125));
			g.drawString("they will jump out from", (int) (Game.drawWidth*0.78125), (int) (Game.drawHeight*0.14583333333333333333333333333333));
			
			g.setFont(font2);
			g.setColor(Color.white);
			g.drawRect((int) (Game.drawWidth*0.05208333333333333333333333333333), (int) (Game.drawHeight*0.80555555555555555555555555555556), (int) (Game.drawWidth*0.10416666666666666666666666666667), (int) (Game.drawHeight*0.05925925925925925925925925925926));
			g.drawString("Back", (int) (Game.drawWidth*0.078125), (int) (Game.drawHeight*0.84259259259259259259259259259259));
			
			//(int) (Game.drawWidth*)
			//(int) (Game.drawHeight*)
			
			g.drawRect((int) (Game.drawWidth*0.44270833333333333333333333333333), (int) (Game.drawHeight*0.80555555555555555555555555555556), (int) (Game.drawWidth*0.10416666666666666666666666666667), (int) (Game.drawHeight*0.05925925925925925925925925925926));
			g.drawString("Main", (int) (Game.drawWidth*0.47916666666666666666666666666667), (int) (Game.drawHeight*0.84259259259259259259259259259259));
			
			g.drawRect((int) (Game.drawWidth*0.83333333333333333333333333333333), (int) (Game.drawHeight*0.80555555555555555555555555555556), (int) (Game.drawWidth*0.10416666666666666666666666666667), (int) (Game.drawHeight*0.06018518518518518518518518518519));
			g.drawString("Next", (int) (Game.drawWidth*0.859375), (int) (Game.drawHeight*0.84259259259259259259259259259259));
			
			//images
			g.drawImage(enemy1Img, (int) (Game.drawWidth*0.05208333333333333333333333333333), (int) (Game.drawHeight*0.17708333333333333333333333333333), (int) (Game.drawWidth*0.13020833333333333333333333333333), (int) (Game.drawHeight*0.13020833333333333333333333333333), null);
			g.drawImage(enemy2Img, (int) (Game.drawWidth*0.20833333333333333333333333333333), (int) (Game.drawHeight*0.17708333333333333333333333333333), (int) (Game.drawWidth*0.13020833333333333333333333333333), (int) (Game.drawHeight*0.13020833333333333333333333333333), null);
			g.drawImage(enemy3Img, (int) (Game.drawWidth*0.390625), (int) (Game.drawHeight*0.17708333333333333333333333333333), (int) (Game.drawWidth*0.13020833333333333333333333333333), (int) (Game.drawHeight*0.13020833333333333333333333333333), null);
			g.drawImage(enemy4Img, (int) (Game.drawWidth*0.57291666666666666666666666666667), (int) (Game.drawHeight*0.17708333333333333333333333333333), (int) (Game.drawWidth*0.13020833333333333333333333333333), (int) (Game.drawHeight*0.13020833333333333333333333333333), null);
			g.drawImage(enemy5Img, (int) (Game.drawWidth*0.78125), (int) (Game.drawHeight*0.17708333333333333333333333333333), (int) (Game.drawWidth*0.15625), (int) (Game.drawHeight*0.13020833333333333333333333333333), null);
			
			
			
		} else if (game.gameState == STATE.Help3){
			
			Font font = new Font("impact", 1, 50);
			Font font2 = new Font("impact", 1, 30);
			
			g.setFont(font);
			g.setColor(Color.white);
			g.drawString("The Bosses", (int) (Game.drawWidth*0.43229166666666666666666666666667), (int) (Game.drawHeight*0.03645833333333333333333333333333));
			
			
			g.setFont(font2);
			g.drawString("The Red Boss. Dodge the", (int) (Game.drawWidth*0.02083333333333333333333333333333), (int) (Game.drawHeight*0.10416666666666666666666666666667));
			g.drawString("explosive bullets that he", (int) (Game.drawWidth*0.02083333333333333333333333333333), (int) (Game.drawHeight*0.125));
			g.drawString("throws and stay below the line.", (int) (Game.drawWidth*0.02083333333333333333333333333333), (int) (Game.drawHeight*0.14583333333333333333333333333333));
			
			g.drawImage(boss1Img, (int) (Game.drawWidth*0.05208333333333333333333333333333), (int) (Game.drawHeight*0.17708333333333333333333333333333), (int) (Game.drawWidth*0.13020833333333333333333333333333), (int) (Game.drawHeight*0.13020833333333333333333333333333), null);
			
			g.drawString("The Green Eye Boss. Each", (int) (Game.drawWidth*0.3125), (int) (Game.drawHeight*0.10416666666666666666666666666667));
			g.drawString("moves differently so keep", (int) (Game.drawWidth*0.3125), (int) (Game.drawHeight*0.125));
			g.drawString("moving and stay alert!", (int) (Game.drawWidth*0.3125), (int) (Game.drawHeight*0.14583333333333333333333333333333));
			
			g.drawImage(boss2Img, (int) (Game.drawWidth*0.3125), (int) (Game.drawHeight*0.17708333333333333333333333333333), (int) (Game.drawWidth*0.13020833333333333333333333333333), (int) (Game.drawHeight*0.13020833333333333333333333333333), null);

			//(int) (Game.drawWidth*)
			//(int) (Game.drawHeight*)
			
			g.setFont(font2);
			g.setColor(Color.white);
			g.drawRect((int) (Game.drawWidth*0.05208333333333333333333333333333), (int) (Game.drawHeight*0.80555555555555555555555555555556), (int) (Game.drawWidth*0.10416666666666666666666666666667), (int) (Game.drawHeight*0.05925925925925925925925925925926));
			g.drawString("Back", (int) (Game.drawWidth*0.078125), (int) (Game.drawHeight*0.84259259259259259259259259259259));
			
			g.drawRect((int) (Game.drawWidth*0.44270833333333333333333333333333), (int) (Game.drawHeight*0.80555555555555555555555555555556), (int) (Game.drawWidth*0.10416666666666666666666666666667), (int) (Game.drawHeight*0.05925925925925925925925925925926));
			g.drawString("Main", (int) (Game.drawWidth*0.47916666666666666666666666666667), (int) (Game.drawHeight*0.84259259259259259259259259259259));

		}
	}
}
