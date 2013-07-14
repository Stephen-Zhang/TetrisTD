package player;

import java.awt.Point;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;

import towers.TestTower;
import towers.Tower;
import towers.TowerType;

public class Player {
	public int gold = 0;
	public int lives;
	public TowerType holding = TowerType.NULL;
	public HashMap<TowerType, Point[]> towerShapes = new HashMap<TowerType, Point[]>();
	public HashMap<TowerType, Point[]> towerRange = new HashMap<TowerType, Point[]>();
	public ArrayList<TowerType> availTowers = new ArrayList<TowerType>();
	
	public int[] currMouseLoc = {0, 0};
	
	public Player(int life) {
		lives = life;

	}
	
	public Player(int life, int gold) {
		this(life);
		this.gold = gold;
	}
	
	//Additional Stuff for players such as place-able towers, achievements, powerups, etc.
	public void addTower(TowerType t) {
		availTowers.add(t);
		switch(t) {
		case TEST_TOWER: 
			towerShapes.put(t, TestTower.shape.clone());
			towerRange.put(t, TestTower.range.clone());
			break;
		}
	}
	
	public void removeTower(TowerType t) {
		availTowers.remove(t);
		towerShapes.remove(t);
		towerRange.remove(t);
	}
	
	public void removeAllTowers() {
		availTowers.clear();
		towerShapes.clear();
		towerRange.clear();
	}
	
	public void addBatchTowers(TowerType[] tTypes) {
		for (TowerType t: tTypes) {
			addTower(t);
		}
	}

	public Point[] getTowerShape() {
		return (towerShapes.get(holding));
	}

	public Point[] getTowerRange() {
		return (towerRange.get(holding));		
	}
	
	public Tower makeNewTower() {
		switch(holding) {
		case TEST_TOWER: 
			double[] cent = {currMouseLoc[0], currMouseLoc[1]};
			return new TestTower(cent);
		case NULL: 
			return null;
		}
		return null;
	}

	public int getCostOfTower() {
		// TODO Auto-generated method stub
		switch(holding) {
		case TEST_TOWER:
			return TestTower.cost;
		}
		return 0;
	}
}
