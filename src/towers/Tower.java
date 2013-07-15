package towers;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.ShapeRenderer;

import projectiles.Projectile;
import enemies.Enemy;

public abstract class Tower {

	public double fireRate;
	public boolean canFire;
	public int cooldown;

	protected double[] center = new double[2];
	
	protected static Point[] range;
	protected Shape rangeInd;
	
	protected static Point[] shape;
	protected Shape realShape;
	
	public ArrayList<Enemy> target = new ArrayList<Enemy>();
	
	public abstract String getName();

	public abstract String getSpritePath();
	public abstract String getIconPath();
	
	public abstract int getCost();
	public abstract Shape getShape();
	public abstract Shape getRange();
	
	public void render(Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		Image sprite = new Image("resources/towers/testTower.png");
		ShapeRenderer.texture(realShape, sprite);
	}
	
	public Shape formShape( Point[] pts ) {
		Point firstP = pts[0];
		Shape retVal = new Rectangle(Float.parseFloat(Double.toString(center[0])) + firstP.x, Float.parseFloat(Double.toString(center[1])) + firstP.y, 32, 32);
		for (int i = 1; i < pts.length; i++ ) {
			retVal = retVal.union(new Rectangle(Float.parseFloat(Double.toString(center[0])) + pts[i].x, Float.parseFloat(Double.toString(center[1])) + pts[i].y, 32, 32))[0];
		}
		
		return retVal;
	}

	public static void drawIcon(int x, int y) throws SlickException {
		Image icon = new Image("resources/towers/testTower.png");
		icon.draw(x, y);
	}
	
	public static void drawTowerIcon(TowerType t) throws SlickException {
		switch(t) {
		case TEST_TOWER:
			Tower.drawIcon(TestTower.iconLoc[0], TestTower.iconLoc[1]);
			break;
		case TEST_AOE_TOWER:
			Tower.drawIcon(TestAoETower.iconLoc[0], TestAoETower.iconLoc[1]);
			break;
		}
	}
	
	public static Rectangle towerIcon(TowerType t) {
		switch(t) {
		case TEST_TOWER:
			return new Rectangle(TestTower.iconLoc[0], TestTower.iconLoc[1], 32, 32);
		case TEST_AOE_TOWER:
			return new Rectangle(TestAoETower.iconLoc[0], TestAoETower.iconLoc[1], 32, 32);
		}
		return null;
	}
	
	public static int towerKey(TowerType t) {
		switch(t) {
		case TEST_TOWER:
			return TestTower.key;
		case TEST_AOE_TOWER:
			return TestAoETower.key;
		}
		return -1;
	}
	
	public abstract void acquireTargets(ArrayList<Enemy> enemies, HashMap<Enemy, ArrayList<Tower>> addTtoE, HashMap<Enemy, ArrayList<Tower>> remTfromE, HashMap<Tower, ArrayList<Enemy>> addEtoT, HashMap<Tower, ArrayList<Enemy>> remEfromT);

	public abstract void fire(ArrayList<Projectile> addB);

	public void updateTime(int delta) {
		// TODO Auto-generated method stub
		if (canFire == false) {
			if (cooldown > fireRate*1000) {
				canFire = true;
				cooldown = 0;
			} else {
				cooldown += delta;
			}
		}
	}
	
}
