package levels;

import java.awt.Point;
import java.util.ArrayList;

import enemies.Enemy;
import enemies.TestEnemy;

public class LevelOne extends Level {
	
	public LevelOne() {
		startLoc[0] = 0;
		startLoc[1] = 11*32;
		
		waypts.add(new Point(5*32, 11*32));
		waypts.add(new Point(5*32, 15*32));
		waypts.add(new Point(21*32, 15*32));
		waypts.add(new Point(21*32, 6*32));
		waypts.add(new Point(26*32, 6*32));
		
		Enemy e1 = new TestEnemy(startLoc, new ArrayList<Point>(waypts));
		Enemy e2 = new TestEnemy(startLoc, new ArrayList<Point>(waypts));
		
		Enemy[] wOneEnemies = {
				e1, e2
		};
		
		ArrayList<Integer> timings = new ArrayList<Integer>();
		timings.add(new Integer(0));
		timings.add(new Integer(2000));
		/*
		timings.add(new Integer(1000));
		timings.add(new Integer(1200));
		timings.add(new Integer(1400));
		timings.add(new Integer(1600));
		timings.add(new Integer(2000));
		*/
		Wave wOne = new Wave(1, wOneEnemies, timings, 0);
		
		currWave = wOne;
	}
}
