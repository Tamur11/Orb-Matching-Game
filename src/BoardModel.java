import java.awt.*;
import java.util.*;

public class BoardModel {
	
	private final int ORB_MATCHING_SIZE = 3;

	private int score = 0;
	private int counter = 1;
	private int healthMax;
	private int health;
	private int maxScore;
	private int height;
	private int width;
	private int monsterAttack = 3;
	private Orb[][] board;

	public BoardModel() {
		maxScore = 50;
		height = 5;
		width = 6;
		healthMax = 10;
		board = new Orb[height][width];
		setUp();
	}

	public BoardModel(int height, int width, int maxScore, int healthMax) {
		this.maxScore = maxScore;
		this.height = height;
		this.width = width;
		this.healthMax = healthMax;
		board = new Orb[height][width];
		setUp();
	}

	public void setUp() {
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[0].length; col++) {
				board[row][col] = makeOrb(row, col);
			}
		}
	}

	public Orb makeOrb(int row, int col) {
		int randomNum = (int)(Math.random()*6);
		switch(randomNum) {
		case 0:
			return new Orb(row, col, Color.RED);
		case 1:
			return new Orb(row, col, Color.BLUE);
		case 2:
			return new Orb(row, col, Color.GREEN);
		case 3:
			return new Orb(row, col, Color.YELLOW);
		case 4:
			return new Orb(row, col, Color.MAGENTA);
		default:
			return new Orb(row, col, Color.PINK);
		}
	}

	public void removeOrb(int row, int col) {
		if(!checkOrbBounds(row, col)) {
			throw new IndexOutOfBoundsException("The indices (height=" + height + " , width=" + width + ") for the Orb provided are out of bounds.");
		}
		board[row][col] = null;
	}

	public void addOrb(int row, int col){
		if(!checkOrbBounds(row, col)) {
			throw new IndexOutOfBoundsException("The indices (height=" + height + " , width=" + width + ") for the Orb provided are out of bounds.");
		}
		board[row][col] = makeOrb(row, col);
	}

	private boolean checkOrbBounds(int row, int col) {
		if(row < 0 || col < 0 || row >= height || col >=width) {
			return false;
		}
		return true;	
	}

	public void popMatchingOrbs(int row, int col) {

		Orb centerOrb = board[row][col];
		Queue<Orb> matchingOrbs = new LinkedList<Orb>();
		ArrayList<Orb> removeOrbsRow = new ArrayList<Orb>();
		ArrayList<Orb> removeOrbsCol = new ArrayList<Orb>();

		matchingOrbs.add(centerOrb);
		removeOrbsRow.add(centerOrb);

		// check orbs in the row of the centerOrb
		while(!matchingOrbs.isEmpty()) {
			Orb rowOrb = matchingOrbs.remove();
			
			// check rows
			int i = rowOrb.getX();
			int j = rowOrb.getY();
			if(checkOrbBounds(i, j+1) || checkOrbBounds(i, j-1)) {				
				if(checkOrbBounds(i, j+1) && rowOrb.getColor() == board[i][j+1].getColor() && !removeOrbsRow.contains(board[i][j+1])) {
					matchingOrbs.add(board[i][j+1]);
					removeOrbsRow.add(board[i][j+1]);
				}
				if(checkOrbBounds(i, j-1) && rowOrb.getColor() == board[i][j-1].getColor() && !removeOrbsRow.contains(board[i][j-1])) {
					matchingOrbs.add(board[i][j-1]);
					removeOrbsRow.add(board[i][j-1]);
				}
			}
		}
		
		matchingOrbs.add(centerOrb);
		removeOrbsCol.add(centerOrb);
		
		// check orbs in the row of the centerOrb
		while(!matchingOrbs.isEmpty()) {
			Orb colOrb = matchingOrbs.remove();
			
			// check rows
			int i = colOrb.getX();
			int j = colOrb.getY();
			if(checkOrbBounds(i+1, j) || checkOrbBounds(i-1, j)) {				
				if(checkOrbBounds(i+1, j) && colOrb.getColor() == board[i+1][j].getColor() && !removeOrbsRow.contains(board[i+1][j])) {
					matchingOrbs.add(board[i+1][j]);
					removeOrbsRow.add(board[i+1][j]);
				}
				if(checkOrbBounds(i-1, j) && colOrb.getColor() == board[i-1][j].getColor() && !removeOrbsRow.contains(board[i-1][j])) {
					matchingOrbs.add(board[i-1][j]);
					removeOrbsRow.add(board[i-1][j]);
				}
			}
		}
		
		boolean healthOrb = false;
		int numOrbsPopped = 0;
		// remove all row orbs from board
		if(removeOrbsRow.size() >= ORB_MATCHING_SIZE) {
			// check if the orbs popped are health orbs
			if(removeOrbsRow.get(0).getColor() == Color.PINK) {
				healthOrb = true;
			}
			for(Orb orb: removeOrbsRow) {
				removeOrb(orb.getX(), orb.getY());
				addOrb(orb.getX(), orb.getY());
				numOrbsPopped++;
			}
		}
		
		// remove all col orbs from board
		if(removeOrbsCol.size() >= ORB_MATCHING_SIZE) {
			for(Orb orb: removeOrbsCol) {
				// don't remove center orb twice
				if(!removeOrbsRow.contains(orb)) {
					removeOrb(orb.getX(), orb.getY());
					addOrb(orb.getX(), orb.getY());
					numOrbsPopped++;
				}
			}
		}
		
		// either update score or health (what if health and score?)
		if(!healthOrb) {
			updateScore(numOrbsPopped);	
		} else {
			giveHealth(numOrbsPopped);
		}
		if(removeOrbsCol.size() >= ORB_MATCHING_SIZE || removeOrbsRow.size() >= ORB_MATCHING_SIZE) {
			updateHealth();
			updateMonsterAttack();
		}
	}
	
	private void updateScore(int numOrbsPopped) {
		score += Math.pow(numOrbsPopped, 2);
	}
	
	private void updateMonsterAttack() {
		if (monsterAttack == 1) {
			monsterAttack = 3;
		} else {
			monsterAttack -= 1;
		}
	}
	
	private void updateHealth() {
		if(health >= healthMax-1) {
			System.out.println("You lose!");
			System.exit(0);
		} else if (counter%3 == 0) {
			health+=2;
		}
		counter++;
	}
	
	private void giveHealth(int numOrbsPopped) {
		if(health > 0) {
			health-=(numOrbsPopped-1);
		}
	}
	
	public int getMonsterAttack() {
		return monsterAttack;
	}
	
	public void setMonsterAttack(int monsterAttack) {
		this.monsterAttack = monsterAttack;
	}
	
	public int getCounter() {
		return counter;
	}
	
	public int getHealth() {
		return health;
	}
	
	public int getMaxScore() {
		return maxScore;
	}
	
	public int getScore() {
		return score;
	}
	
	public int setScore(int score) {
		return this.score = score;
	}

	public int getHeight() {
		return height;
	}

	public int 	getWidth() {
		return width;
	}
	
	public Orb[][] getBoard() {
		return board;
	}
	
	public int getHealthMax() {
		return healthMax;
	}
	public void setHealthMax(int healthMax) {
		this.healthMax = healthMax;
	}
}
