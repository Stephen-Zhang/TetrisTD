package scenes;

import java.awt.Point;
import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import enemies.Enemy;
import enemies.TestEnemy;

public class MapState extends BasicGameState {
	public static ArrayList<TiledMap> maps = new ArrayList<TiledMap>();

	int stateID = -1;
	private TiledMap currMap;
	public ArrayList<Enemy> monsters = new ArrayList<Enemy>();
	public ArrayList<Point> waypoints = new ArrayList<Point>();
	
	public MapState(int id) {
		stateID = id;
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
		currMap = maps.get(0);
		sendEnemy();
	}

	@Override
	public int getID() {
		return stateID;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		try {
			maps.add(new TiledMap("src/maps/levelOne/levelOne.tmx") );
			
			//Move to Maps later
			waypoints.add(new Point(5*32, 11*32));
			waypoints.add(new Point(5*32, 15*32));
			waypoints.add(new Point(21*32, 15*32));
			waypoints.add(new Point(21*32, 6*32));
			waypoints.add(new Point(26*32, 6*32));
			
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void leave(GameContainer container, StateBasedGame game)
			throws SlickException {

	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		currMap.render(0,0);
		
		for (Enemy e : monsters ) {
			e.render(g);
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		for (Enemy e : monsters ) {
			if ( ( (int) e.pos[0] == (int) e.destination.x ) && ( (int) e.pos[1] == (int) e.destination.y ) ) {
				//Reached waypoint, new waypoint
				e.getNextDest();
				e.getDirection();
			}
			e.pos[0] += e.dir[0]*e.walkSpeed;
			e.pos[1] += e.dir[1]*e.walkSpeed;
		}
	}
	
	public void sendEnemy() {
		double[] start = {0*32, 11*32};
		monsters.add(new TestEnemy(start, waypoints ) );
	}

}