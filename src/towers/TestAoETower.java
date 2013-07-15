package towers;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Shape;

import projectiles.Projectile;
import projectiles.testBullet;
import enemies.Enemy;

public class TestAoETower extends Tower {
	
	private static final int maxTargets = 5;
	
	public static final Point[] shape = new Point[]{
		new Point(0*32,0*32),
		//TODO FILL IN SHAPE
	};
	
	public static final Point[] range = new Point[]{
		//TODO FILL IN RANGE
		new Point(-1*32,-1*32),
		new Point(-1*32,0*32),
		new Point(-1*32,1*32),
		new Point(0*32,-1*32),
		new Point(0*32,1*32),
		new Point(1*32,-1*32),
		new Point(1*32,0*32),
		new Point(1*32,1*32)

	};
	
	public static final String name = "testAOE";
	
	public static final String SpritePath = "resources/towers/testTower.png";
	
	public static final String IconPath = "resources/towers/testTower.png";
	
	public static final int cost = 100;

	//TODO Maybe adjust this dynamically? Maybe needs to b changed. dunno yet.
	public static int[] iconLoc = {940, 600};
	
	public static int key = Input.KEY_Y;

	public TestAoETower(double[] cent) {
		fireRate = .1;
		
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
	public void acquireTargets(ArrayList<Enemy> enemies,
			HashMap<Enemy, ArrayList<Tower>> addTtoE,
			HashMap<Enemy, ArrayList<Tower>> remTfromE,
			HashMap<Tower, ArrayList<Enemy>> addEtoT,
			HashMap<Tower, ArrayList<Enemy>> remEfromT) {
		// TODO Auto-generated method stub
		int targetsFound = this.target.size();
		for (Enemy e: enemies) {
			if (e.getHitbox().intersects(this.getRange()) && !this.target.contains(e)) {
				targetsFound++;
				if (targetsFound <= maxTargets) {
					//Add monster to tower, add tower to monster
					ArrayList<Enemy> tempTargetAdd = (addEtoT.containsKey(this)) ? addEtoT.get(this) : new ArrayList<Enemy>();
					tempTargetAdd.add(e);
					addEtoT.put(this, tempTargetAdd);
					
					ArrayList<Tower> tempTaddE = (addTtoE.containsKey(e)) ? addTtoE.get(e) : new ArrayList<Tower>();
					tempTaddE.add(this);
					addTtoE.put(e, tempTaddE);
				}
				//Need a clever fix for this. right now its removing 1 tower and adding in a lot of towers in 1 step, causing it to exceed 5 max targets.
				//Commenting out till further notice. TODO
				/*
				else {
					Enemy furthestAway = this.target.get(0);
					//Compare distance to furthest away
					for (Enemy e2: this.target) {
						furthestAway = (furthestAway.distToGoal() < e2.distToGoal()) ? e2 : furthestAway;
					}
					if (e.distToGoal() < furthestAway.distToGoal()) {
						//Remove enemy from tower
						ArrayList<Enemy> targetRem = (remEfromT.containsKey(this)) ? remEfromT.get(this) : new ArrayList<Enemy>();
						targetRem.add(furthestAway);
						remEfromT.put(this, targetRem);

						//Remove tower from enemy
						ArrayList<Tower> towerRem = (remTfromE.containsKey(this)) ? remTfromE.get(this) : new ArrayList<Tower>();
						towerRem.add(this);
						remTfromE.put(furthestAway, towerRem);
						
						//Add new enemy to tower
						ArrayList<Enemy> targetAdd = (addEtoT.containsKey(this)) ? addEtoT.get(this) : new ArrayList<Enemy>();
						targetAdd.add(e);
						addEtoT.put(this, targetAdd);
						
						//Add tower to enemy
						ArrayList<Tower> towerAdd = (addTtoE.containsKey(this)) ? addTtoE.get(this) : new ArrayList<Tower>();
						towerAdd.add(this);
						addTtoE.put(e, towerAdd);
						
						break;
					}
				}
				*/
			}
		}
	}

	@Override
	public void fire(ArrayList<Projectile> addB) {
		if (target.size() > 0) {
			if (canFire) {
				for (Enemy e: this.target) {
					addB.add(new testBullet(target.get(target.indexOf(e)), center));
				}

				canFire = false;
			}
		}
	}

}
