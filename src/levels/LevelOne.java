package levels;

import java.awt.Point;
import java.util.ArrayList;

import towers.TowerType;
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
		
		Wave testEmpty = new Wave(1, new Enemy[0], new ArrayList<Integer>(), 0, "Press Enter or Space to move onto the next text bubble. You may also " +
				"click on the bubble itself to close it.", new TowerType[]{});
		
		Wave wOne = new Wave(1, wOneEnemies, tOne, 0, "Welcome to Tetris TD Alpha Version .00! This game is a barely playable game but here is the first test level! " +
				"Please build the first tower available to you and destroy your enemies! You may build this tower by clicking on the icon or pressing t " +
				"on your keyboard. Good luck!", new TowerType[]{TowerType.TEST_TOWER});
		
		Wave wTwo = new Wave(2, wTwoEnemies, tTwo, 20000, "Phew. You beat the first wave! but how are you going to deal with this next massive swarm of enemies? " +
				"Hint: You should build some Area of Effect Towers that have now been enabled. I've disabled the original single shot tower. Good luck! ", new TowerType[]{TowerType.TEST_AOE_TOWER});
		
		waves.add(testEmpty);
		waves.add(wOne);
		waves.add(wTwo);
	}
}
