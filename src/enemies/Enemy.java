package enemies;

import java.awt.Point;
import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public abstract class Enemy {
	public double maxHealth;
	public double currHealth;
	public double[] pos = new double[2];
	public ArrayList<Point> waypoints;
	public Point destination;
	public double walkSpeed = 1;
//	public Animation currAnimation;
	public Image sprite;
	
	public boolean alive = true;
	public int bounty;
	
	public double[] dir = new double[2];
	
	public void getNextDest() {
		if (waypoints.size() != 0) {
			destination = waypoints.remove(0);
			System.out.println(destination);
		} else {
			//Take a life away. once its implemented. Means it hit the end of its map
			alive = false;
			bounty = 0;
			//TODO take away lives from player
		}
	}
	
	public void setWaypoints(ArrayList<Point> waypts) {
		waypoints = waypts;
	}
		
	public void getDirection() {
		double distance = destination.distance(pos[0], pos[1]);
		dir[0] = (destination.x - pos[0])/distance;
		dir[1] = (destination.y - pos[1])/distance;
		System.out.println("xDir: "+dir[0]);
		System.out.println("yDir: "+dir[1]);
	}

	public void render(Graphics g) {
		// TODO Auto-generated method stub
		sprite.draw((int)pos[0],(int)pos[1]);
	}

}
