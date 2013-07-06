package towers;

import java.awt.Point;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.ShapeRenderer;

import projectiles.Projectile;
import enemies.Enemy;

public abstract class Tower {
	public double fireRate;
	
	public boolean canFire;
	public int cooldown;
	
	public double[] center = new double[2];
		
	public int cost;
	
	public static Point[] range;
	public Shape rangeInd;
	
	public static Point[] shape;
	public Shape realShape;
	
	public Shape[] realShapeTest;
	
	public Enemy target;
	
	public Image sprite;
	
	public static String name;
	
	public void render(Graphics g) {
		// TODO Auto-generated method stub
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
	
	public abstract Projectile fireBullet();
	
}
