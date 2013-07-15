package player;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import towers.TestAoETower;
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
		case TEST_AOE_TOWER:
			towerShapes.put(t, TestAoETower.shape.clone());
			towerRange.put(t, TestAoETower.range.clone());
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
		double[] cent = {currMouseLoc[0], currMouseLoc[1]};
		switch(holding) {
		case TEST_TOWER: 
			return new TestTower(cent);
		case TEST_AOE_TOWER:
			return new TestAoETower(cent);
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
		case TEST_AOE_TOWER:
			return TestAoETower.cost;
		}
		return 0;
	}
}
