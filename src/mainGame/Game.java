package mainGame;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.Thread.State;
import java.util.Scanner;
import java.net.MalformedURLException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.IOAcknowledge;
import io.socket.IOCallback;
import io.socket.SocketIO;
import io.socket.SocketIOException;

/**
 * Main game class. This class is the driver class and it follows the Holder
 * pattern. It houses references to ALL of the components of the game
 * 
 * @author Brandon Loehle 5/30/16
 */

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 1920, HEIGHT = 1080;
	public static final int drawWidth = 1280, drawHeight = 720;
	public static double scaleFactor;
	private Thread thread;
	private boolean running = false;
	private Handler handler;
	private HUD hud;
	private SpawnEasy spawnerE;
	private Spawn1to10 spawner;
	private Spawn10to20 spawner2;
	private Menu menu;
	private GameOver gameOver;
	private GameWon gameWon;
	private UpgradeScreen upgradeScreen;
	private MouseListener mouseListener;
	private Upgrades upgrades;
	private Player player;
	private Pause pause;
	private Leaderboard leaderboard;
	public STATE gameState = STATE.Menu;
	public static int TEMP_COUNTER;
	private Midi menuMIDIPlayer;
	private String menuMIDIMusic = "m64file_synth.mid";
	private Midi gameMIDIPlayer;
	private String gameMIDIMusic = "Danza_Kuduro_synth.mid";
	private Midi bossMIDIPlayer;
	private String bossMIDIMusic = "sm1castl_synth.mid";
	float originalTempoGAME;
	private Game game;
	
	private File _inFile;
	private Scanner _fileInput;
	
	private Boolean isGameSaved;
	private GameSave savedGame;	
	public SocketIO socket;
	private Midi upgradeMidiPlayer;
	private String upgradeMIDIMusic = "Bandit_Radio_synth.midi";
	private SimpleMidi winMIDIPlayer;
	private String winMIDIMusic = "Super_Mario_Bros._-_Flag_synth.mid";
	/**
	 * Used to switch between each of the screens shown to the user
	 */
	public enum STATE {
		Menu, Help, Help2, Help3, Game, GameOver, GameWon, Upgrade, Boss, Pause, PauseH1, PauseH2, PauseH3, PauseShop, Leaderboard, GameWonEasy, GameEasy
	};

	/**
	 * Initialize the core mechanics of the game
	 * @throws MalformedURLException 
	 */

	public Game() throws MalformedURLException {

		scaleFactor = (double) drawWidth / (double) WIDTH;
		
		this.readFromSavedGameFile("gameSavesFile.txt");

		handler = new Handler();
		hud = new HUD();
		spawnerE = new SpawnEasy(this.handler, this.hud, this.spawner, this);
		spawner = new Spawn1to10(this.handler, this.hud, this);
		spawner2 = new Spawn10to20(this.handler, this.hud, this.spawner, this);
		menu = new Menu(this, this.handler, this.hud, this.spawner);
		upgradeScreen = new UpgradeScreen(this, this.handler, this.hud);
		player = new Player(WIDTH / 2 - 32, HEIGHT / 2 - 32, ID.Player, handler, this.hud, this);
		upgrades = new Upgrades(this, this.handler, this.hud, this.upgradeScreen, this.player, this.spawnerE, this.spawner,
				this.spawner2);
		gameOver = new GameOver(this, this.handler, this.hud);
		gameWon = new GameWon(this, this.handler, this.hud);
		pause = new Pause(this.hud, this, this.handler, false, this.spawner, this.spawner2, upgrades); // , drawWidth, drawHeight
		leaderboard = new Leaderboard(this,this.handler,this.hud);
		mouseListener = new MouseListener(this, this.handler, this.hud, this.spawnerE, this.spawner, this.spawner2, this.upgradeScreen,
				this.player, this.upgrades, pause);
		this.addKeyListener(new KeyInput(this.pause, this.handler, this, this.hud, this.player, this.spawner, this.upgrades));
		this.addMouseListener(mouseListener);
		gameMIDIPlayer = new Midi();
		menuMIDIPlayer = new Midi();
		bossMIDIPlayer = new Midi();
		upgradeMidiPlayer = new Midi();
		new Window((int) drawWidth, (int) drawHeight, "Wave Game", this);
		
		
		socket = new SocketIO("http://tubbschat.com:3000/");
		socket.connect(new IOCallback() {
			@Override
			public void onMessage(JSONObject json, IOAcknowledge ack) {
				try {
					System.out.println("Server said:" + json.toString(2));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onMessage(String data, IOAcknowledge ack) {
				hud.setHighScore(data);
			}

			@Override
			public void onError(SocketIOException socketIOException) {
				System.out.println("an Error occured");
				socketIOException.printStackTrace();
			}

			@Override
			public void onDisconnect() {
				System.out.println("Connection terminated.");
			}

			@Override
			public void onConnect() {
				socket.emit("getScore");
				System.out.println("Connection established");
			}

			@Override
			public void on(String event, IOAcknowledge ack, Object... args) {
				System.out.println("Server triggered event '" + event + "'");
			}
			
			
		});
		socket.emit("getBoard");
	}

	/**
	 * The thread is simply a programs path of execution. This method ensures
	 * that this thread starts properly.
	 */
	
	//This sets the game stats when the user goes into the wave game mode, takes stuff from the saved game
	public void setGameStats(){
		
		hud.setLevel(savedGame.getLevel());
		hud.setHealth(savedGame.getHealth());
		hud.setScore(savedGame.getScore());
		
		
		if(savedGame.getLevel() <= 10){
			spawner.setLevelsRemaining(savedGame.getLevelsRem());
			spawner.setLevelNumber(savedGame.getEnemy());
			spawner.resetTempCounter();
		} else {
			Spawn1to10.LEVEL_SET = 2;
			spawner2.setLevelNumber(savedGame.getEnemy());
			spawner2.setRandomMax(savedGame.getLevelsRem());
			spawner2.resetTempCounter();
			upgrades.setAbility(savedGame.getAbility());
			hud.setAbilityUses(savedGame.getAbilityUses());

		}
	}
	
	
	public void setIsGameSaved(Boolean b){
		isGameSaved = b;
	}
	
	
	public Boolean getIsGameSaved(){	
		return isGameSaved;
	}
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	//reading from the saved game file to see if there are saved games or not.
	public void readFromSavedGameFile(String inputFile){
		try {
			_inFile =  new File(inputFile); 
			_fileInput = new Scanner(_inFile);
			
			//checks if there 
			
			if(_fileInput.nextInt() == 0){
				isGameSaved = false;
			} else { //takes all the elements from the file
				isGameSaved = true;
				do{
					_fileInput.nextLine();
					String name = _fileInput.next();
					int score = _fileInput.nextInt();
					double health = _fileInput.nextDouble();
					int level = _fileInput.nextInt();
					int enemy = _fileInput.nextInt();
					int lvlRem = _fileInput.nextInt();
					String ability = _fileInput.next();
					int abilityUses = _fileInput.nextInt();
					
					savedGame = new GameSave(name, score, health, level, enemy, lvlRem, ability, abilityUses);
					
					
				
				} while( _fileInput.hasNextLine());
				
				}
				
			} catch (FileNotFoundException e){
			System.out.println(e);
			System.exit(1);		// IO error; exit program
		}
		

	}
	
	
	
	
	
	
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Best Java game loop out there (used by Notch!)
	 */
	@Override
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;

		// Get monitor refresh rate:

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] gs = ge.getScreenDevices();

		double maxRefreshRate = 0;

		// if there are multiple monitors, we want to use the refresh rate of
		// the highest one
		for (int i = 0; i < gs.length; i++) {
			DisplayMode dm = gs[i].getDisplayMode();
			int refreshRate = dm.getRefreshRate();

			// if we can't ascertain the refresh rate, we want to default to
			// 60FPS
			if (refreshRate == DisplayMode.REFRESH_RATE_UNKNOWN) {
				System.out.println("Unknown refresh rate");
				if (maxRefreshRate < 60) {
					maxRefreshRate = 60;
				}

				// if we can ascertain the refresh rate(s) we want to use the
				// highest one.
			} else {
				System.out.println("Refresh Rate for Screen " + i + " : " + refreshRate);
				if (refreshRate >= maxRefreshRate) {
					maxRefreshRate = refreshRate;
				}
			}
		}

		System.out.println("Using refresh rate: " + maxRefreshRate);

		// we now want to set out refreshRate to the max refresh rate.

		double nsScreen = 1000000000 / maxRefreshRate;
		double deltaScreen = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			deltaScreen += (now - lastTime) / nsScreen;
			lastTime = now;
			while (delta >= 1) {
				try {
					tick();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}// 60 times a second, objects are being updated
 catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				delta--;
			}
			while (deltaScreen >= 1) {
				render();
				frames++;
				deltaScreen--;

			}

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames);
				System.out.println(gameState);
				System.out.println(Spawn1to10.LEVEL_SET);
				frames = 0;
			}
		}
		stop();
	}

	 /* Constantly ticking (60 times per second, used for updating smoothly). Used
	 * for updating the instance variables (DATA) of each entity (location, health,
	 * appearance, etc).
	 * @throws IOException 
	 * @throws JSONException 
	 */
	private void tick() throws IOException, JSONException {
		if(gameState == STATE.Pause || gameState == STATE.PauseH1 || gameState == STATE.PauseH2 || gameState == STATE.PauseH3 || gameState == STATE.Leaderboard){
			//do nothing when paused
			
		} else {
		
		
		handler.tick();// ALWAYS TICK HANDLER, NO MATTER IF MENU OR GAME SCREEN
		if (gameState == STATE.Game || gameState == STATE.Boss && Spawn1to10.LEVEL_SET != 3) {// game is running
			hud.tick();
			if (Spawn1to10.LEVEL_SET == 1) {// user is on levels 1 thru 10, update them
				spawner.tick();
			} else if (Spawn1to10.LEVEL_SET == 2) {// user is on levels 10 thru 20, update them
				spawner2.tick();
			}
		} else if (gameState == STATE.GameEasy || gameState == STATE.Boss) {// user is on menu, update the menu items
			hud.tick();
			if (SpawnEasy.LEVEL_SET == 1) {
				spawnerE.tick();
			} else {
				spawner.tick();
			}
		} else if (gameState == STATE.Menu || gameState == STATE.Help || gameState == STATE.Help2 || gameState == STATE.Help3) {// user is on menu, update the menu items
			menu.tick();
		} else if (gameState == STATE.Upgrade) {// user is on upgrade screen, update the upgrade screen
			upgradeScreen.tick();
		} else if (gameState == STATE.GameOver) {// game is over, update the game over screen
			gameOver.tick();
		} else if (gameState == STATE.GameWon){
			gameWon.highscore = true;
			gameWon.tick();
		} else if (gameState == STATE.GameWonEasy){
			gameWon.highscore = false;
			gameWon.tick();
		}
		
		//working with midi
		if (gameState == STATE.Game) {
			bossMIDIPlayer.StopMidi();
			upgradeMidiPlayer.StopMidi();
			menuMIDIPlayer.StopMidi();
			try {
				gameMIDIPlayer.PlayMidi(gameMIDIMusic);
				originalTempoGAME = gameMIDIPlayer.getTempo();
			} catch (IOException | InvalidMidiDataException | MidiUnavailableException e) {
				e.printStackTrace();
			}
			if (Spawn1to10.LEVEL_SET == 2) {// user is on levels 10 thru 20, update them
				gameMIDIPlayer.setTempo(160);
			}
			else if (Spawn1to10.LEVEL_SET == 1) {// user is on levels 1 thru 10, update them
				gameMIDIPlayer.setTempo(originalTempoGAME);
			}
		} else if (gameState == STATE.Menu || gameState == STATE.Help || gameState == STATE.Help2 || gameState == STATE.Help3) {
			gameMIDIPlayer.StopMidi();
			bossMIDIPlayer.StopMidi();
			upgradeMidiPlayer.StopMidi();
			try {
				menuMIDIPlayer.PlayMidi(menuMIDIMusic);
			} catch (IOException | InvalidMidiDataException | MidiUnavailableException e) {
				e.printStackTrace();
			}
		} else if (gameState == STATE.Boss) {
			gameMIDIPlayer.StopMidi();
			menuMIDIPlayer.StopMidi();
			try {
				bossMIDIPlayer.PlayMidi(bossMIDIMusic);
			} catch (IOException | InvalidMidiDataException | MidiUnavailableException e) {
				e.printStackTrace();
			}
		}  else if (gameState == STATE.Upgrade) {
			bossMIDIPlayer.StopMidi();
			try {
				upgradeMidiPlayer.PlayMidi(upgradeMIDIMusic);
			} catch (IOException | InvalidMidiDataException | MidiUnavailableException e) {
				e.printStackTrace();
			}
		} else {
			gameMIDIPlayer.StopMidi();
			menuMIDIPlayer.StopMidi();
			bossMIDIPlayer.StopMidi();
			upgradeMidiPlayer.StopMidi();
		}
		}
	}

	/**
	 * Constantly drawing to the many buffer screens of each entity requiring
	 * the Graphics objects (entities, screens, HUD's, etc).
	 */
	private void render() {
		/*
		 * BufferStrategies are used to prevent screen tearing. In other words,
		 * this allows for all objects to be redrawn at the same time, and not
		 * individually
		 */
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();

		///////// Draw things bellow this/////////////
		g.scale(scaleFactor, scaleFactor);

		g.setColor(Color.black);
		g.fillRect(0, 0, (int) WIDTH, (int) HEIGHT);

		handler.render(g); // ALWAYS RENDER HANDLER, NO MATTER IF MENU OR GAME
							// SCREEN

		if (gameState == STATE.Pause || gameState == STATE.PauseH1 || gameState == STATE.PauseH2
				|| gameState == STATE.PauseH3 || gameState == STATE.PauseShop) {
			hud.render(g);
			pause.render(g);
		} else {
			if (gameState == STATE.Game || gameState == STATE.Boss || gameState == STATE.GameEasy) {// user is
																		// playing
																		// game,
																		// draw
																		// game
																		// objects
				hud.render(g);
			} else if (gameState == STATE.Menu || gameState == STATE.Help || gameState == STATE.Help2
					|| gameState == STATE.Help3) {// user is in help or the
													// menu, draw the menu and
													// help objects
				menu.render(g);
			} else if (gameState == STATE.Upgrade) {// user is on the upgrade
													// screen, draw the upgrade
													// screen
				upgradeScreen.render(g);
			} else if (gameState == STATE.GameOver) {// game is over, draw the
														// game over screen
				gameOver.render(g);
			} else if (gameState == STATE.GameWon || gameState == STATE.GameWonEasy) {
				gameWon.render(g);
			} else if (gameState == STATE.Leaderboard){
				leaderboard.render(g);
			}
		}
		///////// Draw things above this//////////////
		g.dispose();
		bs.show();
	}

	/**
	 * 
	 * Constantly checks bounds, makes sure players, enemies, and info doesn't
	 * leave screen
	 * 
	 * @param var
	 *            x or y location of entity
	 * @param min
	 *            minimum value still on the screen
	 * @param max
	 *            maximum value still on the screen
	 * @return value of the new position (x or y)
	 */
	public static double clamp(double var, double min, double max) {
		if (var >= max)
			return var = max;
		else if (var <= min)
			return var = min;
		else
			return var;
	}

	public int getPlayerXInt() {
		return (int) player.getX();
	}

	public int getPlayerYInt() {
		return (int) player.getY();
	}

	public static void fuckItUpBrah() {
		Spawn1to10.LEVEL_SET = 3;
	}
	
	public GameOver getGameOver(){
		return gameOver;
	}
	
	public static void main(String[] args) throws MalformedURLException {
		new Game();
	}
}
