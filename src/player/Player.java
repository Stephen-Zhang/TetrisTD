package player;

import java.awt.Point;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;

import towers.TestTower;
import towers.Tower;

public class Player {
	public int gold = 0;
	public int lives;
	public String holding = "none";
	public HashMap<String, Point[]> towerShapes = new HashMap<String, Point[]>();
	public HashMap<String, Point[]> towerRange = new HashMap<String, Point[]>();
	
	public int[] currMouseLoc = {0, 0};
	
	public Player(int life) {
		lives = life;

		towerShapes.put(TestTower.name, TestTower.shape.clone());
		towerRange.put(TestTower.name, TestTower.range.clone());
	}
	
	public Player(int life, int gold) {
		this(life);
		this.gold = gold;
	}
	
	//Additional Stuff for players such as place-able towers, achievements, powerups, etc.
	public void addTowerShape(Tower t) {
		towerShapes.put(t.name, t.shape.clone());
	}

	public void addTowerRange(Tower t) {
		towerRange.put(t.name, t.range.clone());
	}

	public Point[] getTowerShape() {
		return (towerShapes.get(holding));
	}

	public Point[] getTowerRange() {
		return (towerRange.get(holding));		
	}
	
	public Tower makeNewTower() {
		
		if (holding == "test") {
			double[] cent = {currMouseLoc[0], currMouseLoc[1]};
			return new TestTower(cent);
		}
		return null;
	}

	public int getCostOfTower() {
		// TODO Auto-generated method stub

		if (holding == "test") {
			return TestTower.cost;
		}
		
		return 0;
	}
}
