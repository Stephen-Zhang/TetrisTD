package enemies;

import java.awt.Point;
import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class TestEnemy extends Enemy {
	
	public TestEnemy(double[] startingLoc, ArrayList<Point> waypts) {
		this.maxHealth = 20;
		this.currHealth = 20;
		this.pos = startingLoc;
		this.bounty = 10;
		setWaypoints(waypts);
		getNextDest();
		getDirection();
		try {
			this.sprite = new Image("resources/enemySprites/testEnemy.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	
}
