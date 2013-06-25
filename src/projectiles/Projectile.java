package projectiles;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Circle;

import enemies.Enemy;

public abstract class Projectile {
	public Enemy target;
	public double speed;
	public double[] pos = new double[2];
	public double[] dir = new double[2];
	public int damage;
	public Circle point;
	public Image sprite;
	//TODO possible other effects
	
	
	public void getDirection() {
		//Distance to sprite center. assumed to be 32 atm.
		double distance = Math.sqrt( Math.pow((target.pos[0]+16-pos[0]),2) + Math.pow((target.pos[1]+16-pos[1]), 2) );
		dir[0] = (target.pos[0] - pos[0])/distance;
		dir[1] = (target.pos[1] - pos[1])/distance;
	}


	public void render(Graphics g) {
		// TODO Auto-generated method stub
		sprite.draw(Float.parseFloat(Double.toString(pos[0])), Float.parseFloat(Double.toString(pos[1])));
	}

	
}
