import java.awt.*;
import javax.swing.*;

public class GameMain extends JPanel {
	public static void main(String[] args) {
		JFrame window = new JFrame("Orb Monster");
		window.setSize(399,800);
		window.setLocation(650,150);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		BoardView board = new BoardView();
		new OrbListener(board);
		window.setContentPane(board);
		
        window.setResizable(false);  
		window.setVisible(true);
    }
}
