package mainGame;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

/**
 * Loads the window of the game, and sets the proper dimensions
 * @author Brandon Loehle
 * 5/30/16
 */

public class Window extends Canvas{

	private static final long serialVersionUID = 1L;
	
	public Window(int width, int height, String title, Game game){
		JFrame frame = new JFrame(title);
		frame.setPreferredSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.add(game);
		frame.setVisible(true);
		game.start();
		
	}


}
