package towers;

import java.awt.Point;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
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
	
	public static int cost = 200;

	public TestTower(double[] cent) {
		fireRate = .01;
		
		canFire = true;
		cooldown = 0;
		
		cost = 100;
		center = cent;
				
		realShape = formShape(shape);
				
		rangeInd = formShape(range);
		
		try {
			sprite = new Image("resources/towers/testTower.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	
	}

	public Shape getRealShape() {
		return realShape;
	}

	@Override
	public Projectile fireBullet() {
		// TODO Auto-generated method stub
		return new testBullet(target, center);
	}
	
	public static void drawIcon(int x, int y) throws SlickException {
		new Image("resources/towers/testTower.png").draw(x, y);
	}
}
