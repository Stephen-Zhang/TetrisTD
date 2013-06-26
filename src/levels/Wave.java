package levels;

import java.util.ArrayList;

import enemies.Enemy;

public class Wave {
	private int waveID;
	private int enemyCtr = 0;
	protected Enemy[] enemies;
	public ArrayList<Integer> timing;
	public int startTime;
	
	public Wave(int id, Enemy[] e, ArrayList<Integer> timing, int startTime) {
		this.waveID = id;
		this.enemies = e;
		this.timing = timing;
		this.startTime = startTime;
	}
	
	public Enemy getNextEnemy() {
		if (enemyCtr == enemies.length) { 
			return null;
		}
		Enemy e = enemies[enemyCtr];
		enemyCtr++;
		timing.remove(0);
		return e;
	}
	
	public boolean checkNextEnemy(int timer) {
		if (timing.size() == 0) {
			return false;
		}
		if (timer >= timing.get(0)) {
			return true;
		}
		return false;
	}
	
}
