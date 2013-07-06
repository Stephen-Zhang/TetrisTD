package scenes;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import levels.Level;
import levels.LevelOne;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import player.Player;
import projectiles.Projectile;
import towers.TestTower;
import towers.Tower;
import enemies.Enemy;

public class MapState extends BasicGameState {
	public static ArrayList<TiledMap> maps = new ArrayList<TiledMap>();
	
	int stateID = -1;
	private TiledMap currMap;
	public Level currLevel;

	public Player player;
	
	public ArrayList<Enemy> monsters = new ArrayList<Enemy>();
	public ArrayList<Point> waypoints = new ArrayList<Point>();
	public ArrayList<Tower> towers = new ArrayList<Tower>();
	public ArrayList<Projectile> bullets = new ArrayList<Projectile>();
	
	public ArrayList<Projectile> removeB = new ArrayList<Projectile>();
	public ArrayList<Enemy> removeE = new ArrayList<Enemy>();
	public HashMap<Enemy, ArrayList<Tower>> remTfromE = new HashMap<Enemy, ArrayList<Tower>>();

	public ArrayList<Projectile> addB = new ArrayList<Projectile>();
	public ArrayList<Enemy> addE = new ArrayList<Enemy>();
	public ArrayList<Tower> addT = new ArrayList<Tower>();
	public HashMap<Enemy, ArrayList<Tower>> addTtoE = new HashMap<Enemy, ArrayList<Tower>>();

	public MapState(int id) {
		stateID = id;
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
		currMap = maps.get(0);

		player = new Player(20, 100);
		
		currLevel = new LevelOne();
		
		double[] tCent = {6*32, 14*32};
		towers.add(new TestTower(tCent));
		
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
		
		g.setColor(new Color(255,255,255));
		g.drawString("Gold: "+player.gold, 900, 200);
		g.drawString("Lives: "+player.lives, 900, 225);
		
		//TODO: For every tower type, draw one of its icons on the right hand side for selecting
		TestTower.drawIcon(600,  700);
		
		for (Enemy e : monsters ) {
			e.render(g);
		}
		
		for (Tower t : towers ) {
			t.render(g);
			g.setColor(new Color (0, 255, 0) );
			g.draw(t.realShape);
		}
		
		for (Projectile p : bullets ) {
			p.render(g);
		}
		
		if (player.holding != "none") {
			//Holding Tower. Time to draw placement squares
			Point[] drawThisShape = player.getTowerShape();
			Point[] drawThisRange = player.getTowerRange();
			
			if (canPutDown(drawThisShape)) {
				for (Point p: drawThisShape) {
					g.setColor(new Color(0, 200, 0, 100));
					g.fill(new Rectangle(p.x+player.currMouseLoc[0], p.y+player.currMouseLoc[1], 32, 32));
				}
				
				g.setColor(new Color(0, 0, 200, 100));
				for (Point p: drawThisRange) {
					g.fill(new Rectangle(p.x+player.currMouseLoc[0], p.y+player.currMouseLoc[1], 32, 32));
				}
			}
			else {
				g.setColor(new Color(200, 0, 0, 100));
				for (Point p: drawThisShape) {
					g.fill(new Rectangle(p.x+player.currMouseLoc[0], p.y+player.currMouseLoc[1], 32, 32));
				}
			}
		}
		
		g.resetFont();
	}
	
	private boolean canPutDown(Point[] shape) {
		// TODO Needs cleaning up. must be an easier way to check tile properties =\ Hard Coding might be necessary =(		
		int tileX;
		int tileY;
		int tileID;
		for (Point p : shape ) {
			tileX = (p.x+player.currMouseLoc[0])/32;
			tileY = (p.y+player.currMouseLoc[1])/32;
			
			//For every point, check if the square it forms -1 on each side fits inside any tower's shape. If so, that means they intersect.
			//Not using intersects because edges count and towers should be buildable next to one another.
			if (player.gold < player.getCostOfTower()) {
				return false;
			}
			
			for (Tower t : towers ) {
				if (t.realShape.contains(new Rectangle(tileX*32+1, tileY*32+1, 30, 30))) {
					return false;
				}
			}
			
			if (tileX < 0 || tileX > 32 || tileY < 0 || tileY > 24) {
				continue;
			}
			
			tileID = currMap.getTileId(tileX, tileY, 0);
			
			if (tileID == 49 || tileID == 187) {
				return false;
			}
		}
		
		return true;
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		currLevel.waveTimer += delta;
		
		
		
		if (currLevel.currWave.checkNextEnemy(currLevel.waveTimer)) {
			addE.add(currLevel.currWave.getNextEnemy());
		}
		if (currLevel.checkSendWave()) {
			currLevel.getNextWave();
		}
		
		for (Enemy e : monsters ) {
			if ( ( (int) e.pos[0] == (int) e.destination.x ) && ( (int) e.pos[1] == (int) e.destination.y ) ) {
				//Reached waypoint, new waypoint
				e.getNextDest();
			}
			if (e.success) {
				player.lives--;
				removeE.add(e);
			} else {
				e.getDirection();
				e.pos[0] += e.dir[0]*e.walkSpeed;
				e.pos[1] += e.dir[1]*e.walkSpeed;
			}
			
			//Leaving range of towers
			for (Tower t : e.hittingT) {
				if (!t.rangeInd.intersects(e.getHitbox()) ) {
					//no longer in range, remove from hittingT, remove that tower's target on enemy
					t.target = null;
					
					//If dictionary has entries already...
					if (remTfromE.containsKey(e)) {
						//add tower to curr ArrayList
						ArrayList<Tower> temp = remTfromE.get(e);
						temp.add(t);
						remTfromE.put(e, temp);
					} else {
						//add entry to dictionary, add new arrayList to dictionary
						ArrayList<Tower> temp = new ArrayList<Tower>();
						temp.add(t);
						remTfromE.put(e, temp);
					}
				}
			}
			
			//Entering range of towers
			for (Tower t: towers) {
				//Check if tower needs to check firing
				if (t.canFire == false) {
					if (t.cooldown > t.fireRate*1000) {
						t.canFire = true;
						t.cooldown = 0;
					}
				}
				//Range detection
				if (t.rangeInd.intersects(e.getHitbox()) ) {
					
					//In Range, determine if has target
					if (t.target == null) {
						t.target = e;
						
						//if addTtoE has more than 1 target already...
						if (addTtoE.containsKey(e)) {
							//add tower to curr ArrayList
							ArrayList<Tower> temp = addTtoE.get(e);
							temp.add(t);
							addTtoE.put(e, temp);
						} else {
							//add entry to dictionary, add new arrayList to dictionary
							ArrayList<Tower> temp = new ArrayList<Tower>();
							temp.add(t);
							addTtoE.put(e, temp);
						}

					}
					else {
						//If has target, determine if curr Target is ahead of other possible targets
						if (e.distToGoal() < t.target.distToGoal()) {
							//new target acquired
							
							//if t's target is leaving multiple targets...
							if (remTfromE.containsKey(t.target)) {
								//add tower to curr ArrayList
								ArrayList<Tower> temp = remTfromE.get(t.target);
								temp.add(t);
								remTfromE.put(t.target, temp);
							} else {
								//add entry to dictionary, add new arrayList to dictionary
								ArrayList<Tower> temp = new ArrayList<Tower>();
								temp.add(t);
								remTfromE.put(t.target, temp);
							}
							
							t.target = e;

							//if addTtoE has more than 1 target already...
							if (addTtoE.containsKey(e)) {
								//add tower to curr ArrayList
								ArrayList<Tower> temp = addTtoE.get(e);
								temp.add(t);
								addTtoE.put(e, temp);
							} else {
								//add entry to dictionary, add new arrayList to dictionary
								ArrayList<Tower> temp = new ArrayList<Tower>();
								temp.add(t);
								addTtoE.put(e, temp);
							}
						}
					}
				}
			}
		}
		
		for ( Projectile p : bullets ) {
			if (p.getHitbox().intersects(p.target.getBulletBox()) ) {
				//Bullet hit! drain some HP
				p.target.currHealth -= p.damage;
				if (p.target.currHealth <= 0) {						
					removeE.add(p.target);
					for (Tower t : p.target.hittingT ) {
						t.target = null;
					}
					player.gold += p.target.bounty;
				}
				removeB.add(p);
			}
		}
		
		for (Tower t : towers) {
			//If target is acquired, attack it
			if (t.target != null) {
				//Spawn bullet that flies at shortest path to target
				if (t.canFire) {
					addB.add(t.fireBullet());
					t.canFire = false;
				} else {
					//Adding time in milliseconds since last fired to cooldown
					t.cooldown += delta;
				}
			}
		}
		
		//Dealing with bullet Travel
		for (Projectile p : bullets) {
			if (removeE.contains(p.target)) {
				removeB.add(p);
				continue;
			}
			//Update direction and movement
			p.getDirection();
			p.pos[0] += p.dir[0]*p.speed;
			p.pos[1] += p.dir[1]*p.speed;
		}
		
		//Remove all bullets that need to be removed
		for (Projectile p : removeB) {
			bullets.remove(p);
		}
		
		for (Enemy e : removeE) {
			monsters.remove(e);
		}

		//Remove all towers that have left range on enemy
		for (Enemy e : remTfromE.keySet()) {
			for (Tower t : remTfromE.get(e)) {
				e.hittingT.remove(t);
			}
		}

		//Add new bullets
		for (Projectile p : addB) {
			bullets.add(p);
		}
		
		for (Enemy e : addE) {
			monsters.add(e);
		}

		for (Tower t : addT) {
			towers.add(t);
		}
		
		//Add all towers that are hitting enemy
		for (Enemy e : addTtoE.keySet()) {
			for (Tower t : addTtoE.get(e)) {
				e.hittingT.add(t);
			}
		}
		
		addB.clear();
		addE.clear();
		addT.clear();
		addTtoE.clear();
		remTfromE.clear();
		removeB.clear();
		removeE.clear();
		
	}
	
	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		//Determine coordinate location

		player.currMouseLoc[0] = newx;
		player.currMouseLoc[1] = newy;
		
		//Convert to nearest grid location
		
		player.currMouseLoc[0] = player.currMouseLoc[0] - player.currMouseLoc[0]%32;
		player.currMouseLoc[1] = player.currMouseLoc[1] - player.currMouseLoc[1]%32;
		
	}
	
	@Override
	public void mousePressed(int button, int x, int y) {
		if (player.holding != "none" ) {
			if (canPutDown(player.getTowerShape()) && (player.gold >= player.getCostOfTower()) ) {
				player.gold -= player.getCostOfTower();
				addT.add(player.makeNewTower());
				player.holding = "none";
			}
		}
		if (button == 0) {
			if ((x >= 600 && x <= 632) && (y >= 700 && x <= 732)) {
				player.holding = "test";
			}
		}
	}
	
	@Override
	public void keyPressed(int key, char c) {
		if (key == Input.KEY_T) {
			player.holding = "test";
		}
	}
}