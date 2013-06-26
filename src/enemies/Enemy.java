package enemies;

import java.awt.Point;
import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import towers.Tower;

public abstract class Enemy {
	public double maxHealth;
	public double currHealth;
	public double[] pos = new double[2];
	public double[] dir = new double[2];

	public ArrayList<Point> waypoints;
	public Point destination;
	public double walkSpeed = 1;
//	public Animation currAnimation;
	public Image sprite;


	public ArrayList<Tower> hittingT = new ArrayList<Tower>();
	public boolean alive = true;
	public int bounty;
	
	
	public void getNextDest() {
		if (waypoints.size() != 0) {
			destination = waypoints.remove(0);
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
	}
	
	public Shape getHitbox() {
		return new Rectangle(Float.parseFloat(Double.toString(pos[0])), Float.parseFloat(Double.toString(pos[1])), 32f, 32f);
	}
	
	public Shape getBulletBox() {
		return new Rectangle(Float.parseFloat(Double.toString(pos[0]+14)), Float.parseFloat(Double.toString(pos[1]+14)), 4f, 4f);
	}

	public void render(Graphics g) {
		// TODO Auto-generated method stub
		sprite.draw((int)pos[0],(int)pos[1]);
	}

	public int distToGoal() {
		int dist = 0;
		
		//Waypoints added
		for (int i = waypoints.size()-1; i > 0; i--) {
			dist += waypoints.get(i).distanceSq(waypoints.get(i-1));
		}
		
		//closest waypoint to currDestination
		dist += waypoints.get(0).distanceSq(destination);
		
		//currDestination to currPos
		dist += destination.distanceSq(pos[0], pos[1]);
		
		return dist;
	}
}
