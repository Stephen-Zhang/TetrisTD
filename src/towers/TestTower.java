package towers;

import java.awt.Point;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import projectiles.Projectile;
import projectiles.testBullet;

public class TestTower extends Tower {
	
	public static Point[] shape = new Point[]{
		new Point(0*32,0*32),
		new Point(1*32,0*32),
		new Point(2*32,0*32),
		new Point(0*32,-1*32),
		new Point(2*32,-1*32)
	};
	
	public static Point[] range = new Point[]{
		new Point(-1*32, -2*32),
		new Point(0*32, -2*32),
		new Point(1*32, -2*32),
		new Point(1*32, -1*32),
		new Point(2*32, -2*32),
		new Point(3*32, -2*32),
		new Point(3*32, -1*32),
		new Point(3*32, 0*32),
		new Point(3*32, 1*32),
		new Point(2*32, 1*32),
		new Point(1*32, 1*32),
		new Point(0*32, 1*32),
		new Point(-1*32, 1*32),
		new Point(-1*32, 0*32),
		new Point(-1*32, -1*32)
	};
	
	public static String name = "test";
	
	public static String SpritePath = "resources/towers/testTower.png";
	
	public static String IconPath = "resources/towers/testTower.png";
	public static int[] iconLoc = {900, 600};
	
	public static int cost = 200;

	public TestTower(double[] cent) {
		fireRate = .01;
		
		canFire = true;
		cooldown = 0;
		
		cost = 100;
		center = cent;
				
		realShape = formShape(shape);
				
		rangeInd = formShape(range);		
	
	}

	public Shape getRealShape() {
		return realShape;
	}

	@Override
	public Projectile fireBullet() {
		// TODO Auto-generated method stub
		return new testBullet(target, center);
	}

	public static Shape iconRect() {
		// TODO Auto-generated method stub
		return new Rectangle(iconLoc[0], iconLoc[1], 32, 32);
	}

}
