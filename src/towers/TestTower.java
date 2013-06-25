package towers;

import java.awt.Point;
import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class TestTower extends Tower {
	
	public TestTower(double[] cent) {
		fireRate = 2;
		damage = 5;
		cost = 100;
		center = cent;
		shape = new Point[4];
		shape[0] = new Point(0*32,0*32);
		shape[1] = new Point(1*32,0*32);
		shape[2] = new Point(2*32,0*32);
		shape[3] = new Point(0*32,-1*32);

		formRealShape();
		
		try {
			sprite = new Image("resources/towers/testTower.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	
	}

	private void formRealShape() {
		// TODO Auto-generated method stub
		Point firstP = shape[0];
		realShape = new Rectangle(Float.parseFloat(Double.toString(center[0])) + firstP.x, Float.parseFloat(Double.toString(center[1])) + firstP.y, 32, 32);
		for (int i = 1; i < shape.length; i++ ) {
			realShape = realShape.union(new Rectangle(Float.parseFloat(Double.toString(center[0])) + shape[i].x, Float.parseFloat(Double.toString(center[1])) + shape[i].y, 32, 32))[0];
		}
	}

	public Shape getRealShape() {
		return realShape;
	}
}
