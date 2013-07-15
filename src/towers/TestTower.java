package towers;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Shape;

import projectiles.Projectile;
import projectiles.testBullet;
import enemies.Enemy;

public class TestTower extends Tower {
	
	public static final Point[] shape = new Point[]{
		new Point(0*32,0*32),
		new Point(1*32,0*32),
		new Point(2*32,0*32),
		new Point(0*32,-1*32),
		new Point(2*32,-1*32)
	};
	
	public static final Point[] range = new Point[]{
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
	
	public static final String name = "test";
	
	public static final String SpritePath = "resources/towers/testTower.png";
	
	public static final String IconPath = "resources/towers/testTower.png";
	
	public static final int cost = 100;

	//TODO Maybe adjust this dynamically? Maybe needs to b changed. dunno yet.
	public static int[] iconLoc = {900, 600};
	
	public static int key = Input.KEY_T;
		
	public TestTower(double[] cent) {
		fireRate = .01;
		
		canFire = true;
		cooldown = 0;
		
		center = cent;
				
		realShape = formShape(shape);
				
		rangeInd = formShape(range);		
	
	}

	public Shape getRealShape() {
		return realShape;
	}

	@Override
	public String getSpritePath() {
		return SpritePath;
	}

	@Override
	public String getIconPath() {
		return IconPath;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getCost() {
		return cost;
	}

	@Override
	public Shape getShape() {
		return realShape;
	}

	@Override
	public Shape getRange() {
		return rangeInd;
	}

	@Override
	public void acquireTargets(ArrayList<Enemy> enemies, HashMap<Enemy, ArrayList<Tower>> addTtoE, HashMap<Enemy, ArrayList<Tower>> remTfromE, HashMap<Tower, ArrayList<Enemy>> addEtoT, HashMap<Tower, ArrayList<Enemy>> remEfromT) {
		// TODO Auto-generated method stub
		for (Enemy e : enemies) {
			if (this.getRange().intersects(e.getHitbox()) ) {
				//In Range, determine if has target
				if (this.target.size() == 0) {

					//if target exists, add target to addEtoT
					ArrayList<Enemy> tempTargetAdd = (addEtoT.containsKey(this)) ? addEtoT.get(this) : new ArrayList<Enemy>();
					tempTargetAdd.add(e);
					addEtoT.put(this, tempTargetAdd);
					
					ArrayList<Tower> tempTaddE = (addTtoE.containsKey(e)) ? addTtoE.get(e) : new ArrayList<Tower>();
					tempTaddE.add(this);
					addTtoE.put(e, tempTaddE);
				}
				else {
					//If has target, determine if curr Target is ahead of other possible targets
					if (e.distToGoal() < this.target.get(0).distToGoal()) {
						//new target acquired
						
						//if t's target is leaving multiple targets...
						ArrayList<Tower> tempTremE = (remTfromE.containsKey(this)) ? remTfromE.get(this) : new ArrayList<Tower>();
						tempTremE.add(this);
						remTfromE.put(this.target.get(0), tempTremE);

						//Remove enemy from Tower
						ArrayList<Enemy> tempEremT = (remEfromT.containsKey(this)) ? remEfromT.get(this) : new ArrayList<Enemy>();
						tempEremT.add(this.target.get(0));
						remEfromT.put(this, tempEremT);
						
						ArrayList<Enemy> tempTargetAdd = (addEtoT.containsKey(this)) ? addEtoT.get(this) : new ArrayList<Enemy>();
						tempTargetAdd.add(e);
						addEtoT.put(this, tempTargetAdd);
						
						//if addTtoE has more than 1 target already...
						ArrayList<Tower> tempTaddE = (addTtoE.containsKey(this)) ? addTtoE.get(this) : new ArrayList<Tower>();
						tempTaddE.add(this);
						addTtoE.put(this.target.get(0), tempTaddE);					
						
					}
				}
			}
		}
	}

	@Override
	public void fire(ArrayList<Projectile> addB) {
		// TODO Auto-generated method stub
		//If target is acquired, attack it
		if (target.size() > 0) {
			//Spawn bullet that flies at shortest path to target
			if (canFire) {
				addB.add(new testBullet(target.get(0), center));
				canFire = false;
			}
		}
	}
}
