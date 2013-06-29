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
		
		Enemy[] wOneEnemies = {
				new TestEnemy(startLoc.clone(), new ArrayList<Point>(waypts)),
				new TestEnemy(startLoc.clone(), new ArrayList<Point>(waypts)),
				new TestEnemy(startLoc.clone(), new ArrayList<Point>(waypts)),
				new TestEnemy(startLoc.clone(), new ArrayList<Point>(waypts)),
				new TestEnemy(startLoc.clone(), new ArrayList<Point>(waypts)),
				new TestEnemy(startLoc.clone(), new ArrayList<Point>(waypts)),
				new TestEnemy(startLoc.clone(), new ArrayList<Point>(waypts))
		};
		
		ArrayList<Integer> tOne = new ArrayList<Integer>();
		tOne.add(new Integer(0));
		tOne.add(new Integer(500));
		tOne.add(new Integer(1000));
		tOne.add(new Integer(1200));
		tOne.add(new Integer(1400));
		tOne.add(new Integer(1600));
		tOne.add(new Integer(2000));

		Enemy[] wTwoEnemies = {
				new TestEnemy(startLoc.clone(), new ArrayList<Point>(waypts)),
				new TestEnemy(startLoc.clone(), new ArrayList<Point>(waypts)),
				new TestEnemy(startLoc.clone(), new ArrayList<Point>(waypts)),
				new TestEnemy(startLoc.clone(), new ArrayList<Point>(waypts)),
				new TestEnemy(startLoc.clone(), new ArrayList<Point>(waypts)),
				new TestEnemy(startLoc.clone(), new ArrayList<Point>(waypts)),
				new TestEnemy(startLoc.clone(), new ArrayList<Point>(waypts))
		};
		
		ArrayList<Integer> tTwo = new ArrayList<Integer>();
		tTwo.add(new Integer(0));
		tTwo.add(new Integer(1000));
		tTwo.add(new Integer(2000));
		tTwo.add(new Integer(3000));
		tTwo.add(new Integer(4000));
		tTwo.add(new Integer(5000));
		tTwo.add(new Integer(6000));
		
		Wave wOne = new Wave(1, wOneEnemies, tOne, 0);
		
		Wave wTwo = new Wave(1, wTwoEnemies, tTwo, 4000);
		
		currWave = wOne;
		waves.add(wTwo);
	}
}
