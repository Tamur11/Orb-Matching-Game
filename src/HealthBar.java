import java.awt.*;

public class HealthBar {
	private int healthMax = 10;
	private int health;
	
	public HealthBar(int health) {
		this.health = health;
	}
	
	void draw(Graphics g) {
		g.setColor(Color.PINK);
		double boardHeight = ((((double)healthMax - health)/(double)healthMax))*390;
		g.fillRect(1, 406, (int)boardHeight, 30);
		g.setColor(Color.BLACK);
		g.drawRect(1, 406, 390, 30);
	}
	
	public int getHealthMax() {
		return healthMax;
	}
	public void setHealthMax(int healthMax) {
		this.healthMax = healthMax;
	}
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	
}
