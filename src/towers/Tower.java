package towers;

import java.awt.Point;
import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.ShapeRenderer;

public abstract class Tower {
	public double fireRate;
	public double damage;
	
	public double[] center = new double[2];
	
	
	public int cost;
	public Circle rangeInd;
	public Point[] shape;
	public Shape realShape;
	
	public Image sprite;
	
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		ShapeRenderer.texture(realShape, sprite);
	}
	
	
}
