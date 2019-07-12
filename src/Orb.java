import java.awt.Color;
import java.awt.Graphics;

public class Orb {
	
	public static final int DIAMETER_OF_ORB = 50;
	public static final int RADIUS_OF_ORB = DIAMETER_OF_ORB/2;
	public static final int ORB_OFFSET = BoardView.CHECKER_BOARD_WIDTH/2 - RADIUS_OF_ORB;
	
	// coordinates in the board array
	private int x;
	private int y;
	
	// coordinates in the checker board view
	private int xMouse;
	private int yMouse;
	
	// color of the orb
	private Color color;
	
	public Orb(int x, int y, Color color) {
		this.x = x;
		this.y = y;
		this.color = color;
		
		// calculate checker board view coordinate location
		xMouse = ORB_OFFSET + BoardView.CHECKER_BOARD_WIDTH * y;
		yMouse = ORB_OFFSET + BoardView.CHECKER_BOARD_WIDTH * x;
	}

	public void draw(Graphics g) {
		g.setColor(getColor());
		g.fillOval(getxMouse(), getyMouse(), DIAMETER_OF_ORB, DIAMETER_OF_ORB);
	}
	
	public Color randColor() {
		int randomNum = (int)(Math.random()*6);
		switch(randomNum) {
		case 0:
			return Color.RED;
		case 1:
			return Color.BLUE;
		case 2:
			return Color.GREEN;
		case 3:
			return Color.YELLOW;
		case 4:
			return new Color(128, 0, 128);
		default:
			return Color.PINK;
		}
	}
	
//	public boolean containsPoint(int x, int y) {
	
//		boolean inOrb = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)) <= RADIUS_OF_ORB;
//		return inOrb;
//	}
	
	public int getxMouse() {
		return xMouse;
	}
	
	public void setxMouse(int xMouse) {
		this.xMouse = xMouse;
	}
	
	public int getyMouse() {
		return yMouse;
	}
	
	public void setyMouse(int yMouse) {
		this.yMouse = yMouse;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
}
