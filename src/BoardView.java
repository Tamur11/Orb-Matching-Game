import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class BoardView extends JPanel {

	public static final int CHECKER_BOARD_WIDTH = 65;
	public static final int CHECKER_BOARD_OFFSET_X = 1;
	public static final int CHECKER_BOARD_OFFSET_Y = 437;

	private static final int SCORE_OFFSET_HEIGHT = 310;
	
	private int numKilled = 0;

	private HealthBar health;
	private BoardModel model;

	// default colors
	private Color color1 = new Color(102, 34, 0); // dark brown
	private Color color2 = new Color(160,82,45); // light brown

	// default dimensions of board
	private int rows;
	private int cols;

	//image stuff
	private BufferedImage monster = randomMonster();
	private float alpha = 1f;

	public BoardView() {
		model = new BoardModel();
		rows = model.getHeight();
		cols = model.getWidth();
	}

	public BufferedImage randomMonster() {
		int randomNum = (int)(Math.random()*4);
		switch(randomNum) {
		case 0:
			try {
				return ImageIO.read(new File("ghost.png"));
			}
			catch(IOException ex) {

			}
		case 1:
			try {
				return ImageIO.read(new File("goblin.png"));
			}
			catch(IOException ex) {

			}
		case 2:
			try {
				return ImageIO.read(new File("skeleton.png"));
			}
			catch(IOException ex) {

			}
		default:
			try {
				return ImageIO.read(new File("slime.png"));
			}
			catch(IOException ex) {

			}
		}
		return null;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// settings for graphics in Jpanel
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// paint board and orbs
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				if ( row % 2 == col % 2 ) {
					g.setColor(color1); //dark brown
				} else {
					g.setColor(color2); //light brown
				}
				g.fillRect(col*CHECKER_BOARD_WIDTH, row*CHECKER_BOARD_WIDTH, CHECKER_BOARD_WIDTH, CHECKER_BOARD_WIDTH);
			}
		}

		// draw the orbs on the board
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				Orb[][] board = model.getBoard();				
				board[row][col].draw(g);
			}
		}

		// add health bar
		health = new HealthBar(model.getHealth());
		health.draw(g);

		// display the monsters killed
		int scoreXpos = model.getHeight()*CHECKER_BOARD_WIDTH - SCORE_OFFSET_HEIGHT;
		int scoreYpos = model.getWidth()*CHECKER_BOARD_WIDTH;
		Font font = new Font("Verdana", Font.BOLD, 20);
		g.setFont(font);
		g.setColor(Color.BLACK);
		g.drawString("Number Killed: " + Integer.toString(numKilled), scoreXpos, scoreYpos);

		//monster attack
		g.setColor(Color.RED);
		g.drawString("Time Until Attack: "+ Integer.toString(model.getMonsterAttack()), scoreXpos, scoreYpos+75);
		
		Image newImage = monster.getScaledInstance(250, 300, Image.SCALE_DEFAULT);
		try {
			alpha = (float)((double)(model.getMaxScore() - model.getScore())/(double)model.getMaxScore());
			if(alpha <= 0) {
				alpha = 1f;
				numKilled ++;
				model.setMonsterAttack(3);
				model.setScore(0);
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
				g2.drawImage(newImage, 100, 450, null);
			}
			else {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
			g2.drawImage(newImage, 100, 450, null);
			}
		} finally {

		}
	}

	public BoardModel getBoardModel() {
		return model;
	}
}
