import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class OrbListener implements MouseListener, MouseMotionListener {

	private BoardView board;
	private Orb currentlyDraggedOrb = null;
	
	// data type to store location of orb in board array
	private class OrbLocation {
		int row;
		int col; 
		public OrbLocation(int row, int col) {
			this.row = row;
			this.col = col;
		}
	}

	public OrbListener(BoardView panel) {
		board = panel;
		board.addMouseListener(this);
		board.addMouseMotionListener(this);
	}

	@Override
	public void mouseDragged(MouseEvent e) {

		// redraw the orb in it's dragged location
		if(currentlyDraggedOrb != null) {
			currentlyDraggedOrb.setxMouse(e.getX());
			currentlyDraggedOrb.setyMouse(e.getY());
			board.repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		OrbLocation loc = getDraggedOrbLocation(currentlyDraggedOrb);
		Orb neighbor = board.getBoardModel().getBoard()[loc.row][loc.col];

		// location is neighboring orb
		if(neighboringOrb(loc)) {
			//Orb neighbor = board.getBoardModel().getBoard()[loc.row][loc.col];
			swapOrbColor(currentlyDraggedOrb, neighbor);
		}
		
		resetOrbMousePosition(currentlyDraggedOrb);
		board.getBoardModel().popMatchingOrbs(neighbor.getX(), neighbor.getY());
		board.getBoardModel().popMatchingOrbs(currentlyDraggedOrb.getX(), currentlyDraggedOrb.getY());
		board.repaint();
	}
	
	private void resetOrbMousePosition(Orb orb) {
		int xMouse = Orb.ORB_OFFSET + BoardView.CHECKER_BOARD_WIDTH * orb.getY();
		int yMouse = Orb.ORB_OFFSET + BoardView.CHECKER_BOARD_WIDTH * orb.getX();
		orb.setxMouse(xMouse);
		orb.setyMouse(yMouse);
	}
	
	private void swapOrbColor(Orb original, Orb neighbor) {
		Color tempColor = original.getColor();
		original.setColor(neighbor.getColor());
		neighbor.setColor(tempColor);
	}
	
	private boolean neighboringOrb(OrbLocation loc) {
		
		// location is off the board
		if(loc == null) {
			return false;
		}
		
		int centerX = currentlyDraggedOrb.getX();
		int centerY = currentlyDraggedOrb.getY();

		// top, bottom, left, right
		if((loc.row == centerX - 1 && loc.col == centerY) || (loc.row == centerX + 1 && loc.col == centerY) ||
				(loc.row == centerX && loc.col == centerY - 1) || (loc.row == centerX && loc.col == centerY + 1)) {
			return true;
		}
		return false;
	}
	
	private OrbLocation getDraggedOrbLocation(Orb orb) {
		int row = orb.getyMouse() / BoardView.CHECKER_BOARD_WIDTH;
		int col = orb.getxMouse() / BoardView.CHECKER_BOARD_WIDTH;
		
		// check if click was out of bounds
		int maxRow = board.getBoardModel().getHeight();
		int maxCol = board.getBoardModel().getWidth();		
		if(maxRow <= row || maxCol <= col) {
			return null;
		}
		
		return new OrbLocation(row, col);
	}
	
	
	private Orb getNearestOrb(int xMouse, int yMouse) {		
		int row = yMouse / BoardView.CHECKER_BOARD_WIDTH;
		int col = xMouse / BoardView.CHECKER_BOARD_WIDTH;
		
		// check if click was out of bounds
		int maxRow = board.getBoardModel().getHeight();
		int maxCol = board.getBoardModel().getWidth();		
		if(maxRow <= row || maxCol <= col) {
			return null;
		}
		
		Orb currentlyDraggedOrb = board.getBoardModel().getBoard()[row][col];
		return currentlyDraggedOrb;
	}

	@Override
	public void mousePressed(MouseEvent e) {		
		currentlyDraggedOrb = getNearestOrb(e.getX(), e.getY());
	}
	

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}

}
