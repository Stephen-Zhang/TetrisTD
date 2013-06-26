package levels;

import java.awt.Point;
import java.util.ArrayList;

public abstract class Level {
	protected ArrayList<Wave> waves = new ArrayList<Wave>();
	public Wave currWave;
	public double[] startLoc = new double[2];
	public ArrayList<Point> waypts = new ArrayList<Point>();
	public int waveTimer = 0;
	
	public void getNextWave() {
		if (waves.size() > 0) {
			currWave = waves.remove(0);
		} else {
			System.out.println("FINISHED SPAWNING ALL UNITS IN THIS LEVEL!");
		}
	}
	
	public boolean checkSendWave() {
		if (waveTimer > currWave.startTime && waves.size() > 0) {
			return true;
		}
		return false;
	}
	
}
