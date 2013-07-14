package enemies;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.Color;
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

	public boolean success = false;
	
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
			success = true;
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
		renderHealthBar(g);
		sprite.draw((int)pos[0],(int)pos[1]);
	}

	public int distToGoal() {
		int dist = 0;
		
		//Waypoints added
		for (int i = waypoints.size()-1; i > 0; i--) {
			dist += waypoints.get(i).distanceSq(waypoints.get(i-1));
		}
		
		//closest waypoint to currDestination
		if (waypoints.size() > 0) {
			dist += waypoints.get(0).distanceSq(destination);
		}
		
		//currDestination to currPos
		dist += destination.distanceSq(pos[0], pos[1]);
		
		return dist;
	}

	public void renderHealthBar(Graphics g) {
        int offset = -5;
        double x = pos[0];
        double  y = pos[1] + offset;
        int width = 32;
        int height = 5;
        double healthRemaining = width * currHealth / maxHealth;
        g.setColor(new Color(255, 0, 0));
        g.fillRect(Float.parseFloat(Double.toString(x)), Float.parseFloat(Double.toString(y)), (float) (width), height);
        g.setColor(new Color(0, 255, 0));
        g.fillRect(Float.parseFloat(Double.toString(x)), Float.parseFloat(Double.toString(y)), (float) healthRemaining, height);
	}

	public void updateMovement() {
		// TODO Auto-generated method stub
		getDirection();
		pos[0] += dir[0]*walkSpeed;
		pos[1] += dir[1]*walkSpeed;
	}

	public void loseTowers(HashMap<Enemy, ArrayList<Tower>> remTfromE) {
		// TODO Auto-generated method stub
		//Leaving range of towers
		for (Tower t : hittingT) {
			if (!t.getRange().intersects(getHitbox()) ) {
				//no longer in range, remove from hittingT, remove that tower's target on enemy
				t.target = null;
				
				//If dictionary has entries already...
				if (remTfromE.containsKey(this)) {
					//add tower to curr ArrayList
					ArrayList<Tower> temp = remTfromE.get(this);
					temp.add(t);
					remTfromE.put(this, temp);
				} else {
					//add entry to dictionary, add new arrayList to dictionary
					ArrayList<Tower> temp = new ArrayList<Tower>();
					temp.add(t);
					remTfromE.put(this, temp);
				}
			}
		}

	}
}
