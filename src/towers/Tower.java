package towers;

import java.awt.Point;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
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
	
	public Point[] range;
	public Shape rangeInd;
	
	public Point[] shape;
	public Shape realShape;
	
	public Enemy target;
	
	public Image sprite;
	
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		ShapeRenderer.texture(realShape, sprite);
	}
	
	public abstract Projectile fireBullet();
	
}
