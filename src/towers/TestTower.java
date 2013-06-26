package towers;

import java.awt.Point;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import projectiles.Projectile;
import projectiles.testBullet;
import enemies.Enemy;

public class TestTower extends Tower {
	
	public TestTower(double[] cent) {
		fireRate = .25;
		
		canFire = true;
		cooldown = 0;
		
		cost = 100;
		center = cent;

		shape = new Point[5];
		shape[0] = new Point(0*32,0*32);
		shape[1] = new Point(1*32,0*32);
		shape[2] = new Point(2*32,0*32);
		shape[3] = new Point(0*32,-1*32);
		shape[4] = new Point(2*32,-1*32);
		
		realShape = formShape(shape);
		
		range = new Point[15];
		range[0] = new Point(-1*32, -2*32);
		range[1] = new Point(0*32, -2*32);
		range[2] = new Point(1*32, -2*32);
		range[3] = new Point(1*32, -1*32);
		range[4] = new Point(2*32, -2*32);
		range[5] = new Point(3*32, -2*32);
		range[6] = new Point(3*32, -1*32);
		range[7] = new Point(3*32, 0*32);
		range[8] = new Point(3*32, 1*32);
		range[9] = new Point(2*32, 1*32);
		range[10] = new Point(1*32, 1*32);
		range[11] = new Point(0*32, 1*32);
		range[12] = new Point(-1*32, 1*32);
		range[13] = new Point(-1*32, 0*32);
		range[14] = new Point(-1*32, -1*32);
		
		rangeInd = formShape(range);
		
		try {
			sprite = new Image("resources/towers/testTower.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	
	}

	private Shape formShape( Point[] pts ) {
		Point firstP = pts[0];
		Shape retVal = new Rectangle(Float.parseFloat(Double.toString(center[0])) + firstP.x, Float.parseFloat(Double.toString(center[1])) + firstP.y, 32, 32);
		for (int i = 1; i < pts.length; i++ ) {
			retVal = retVal.union(new Rectangle(Float.parseFloat(Double.toString(center[0])) + pts[i].x, Float.parseFloat(Double.toString(center[1])) + pts[i].y, 32, 32))[0];
		}
		
		return retVal;
	}

	public Shape getRealShape() {
		return realShape;
	}

	@Override
	public Projectile fireBullet() {
		// TODO Auto-generated method stub
		return new testBullet(target, center);
	}
}
