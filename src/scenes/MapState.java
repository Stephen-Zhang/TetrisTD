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
import towers.Tower;
import towers.TowerType;
import enemies.Enemy;

public class MapState extends BasicGameState {
	public static ArrayList<TiledMap> maps = new ArrayList<TiledMap>();
	
	int stateID = -1;
	private TiledMap currMap;
	public Level currLevel;
	public TextEvent textEvent;

	public Player player;
	
	public boolean sendEarly = false;
	
	public ArrayList<Enemy> monsters = new ArrayList<Enemy>();
	public ArrayList<Point> waypoints = new ArrayList<Point>();
	public ArrayList<Tower> towers = new ArrayList<Tower>();
	public ArrayList<Projectile> bullets = new ArrayList<Projectile>();
	
	public ArrayList<Projectile> removeB = new ArrayList<Projectile>();
	public ArrayList<Enemy> removeE = new ArrayList<Enemy>();
	public HashMap<Enemy, ArrayList<Tower>> remTfromE = new HashMap<Enemy, ArrayList<Tower>>();
	public HashMap<Tower, ArrayList<Enemy>> remEfromT = new HashMap<Tower, ArrayList<Enemy>>();

	public ArrayList<Projectile> addB = new ArrayList<Projectile>();
	public ArrayList<Enemy> addE = new ArrayList<Enemy>();
	public ArrayList<Tower> addT = new ArrayList<Tower>();
	public HashMap<Enemy, ArrayList<Tower>> addTtoE = new HashMap<Enemy, ArrayList<Tower>>();
	public HashMap<Tower, ArrayList<Enemy>> addEtoT = new HashMap<Tower, ArrayList<Enemy>>();

	public MapState(int id) {
		stateID = id;
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
		currMap = maps.get(0);

		player = new Player(20, 200);
		
		currLevel = new LevelOne();
		currLevel.getNextWave();		
		player.removeAllTowers();
		player.addBatchTowers(currLevel.currWave.updatePlayerTowers());
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
		
		if (sendEarly && !currLevel.isDone()) {
			g.drawString("Press P to", 900, 300);
			g.drawString("send next", 900, 325);
			g.drawString("wave!", 900, 350);
		}
		if (currLevel.isDone() && monsters.size() == 0) {
			g.drawString("You have", 900, 300);
			g.drawString("finished", 900, 325);
			g.drawString("the level!", 900, 350);			
		}
		
		for (TowerType t : player.availTowers) {
			Tower.drawTowerIcon(t);
		}
		
		for (Enemy e : monsters ) {
			e.render(g);
		}
		
		for (Tower t : towers ) {
			t.render(g);
			g.setColor(new Color (0, 255, 0) );
			g.draw(t.getShape());
		}
		
		for (Projectile p : bullets ) {
			p.render(g);
		}
		
		if (player.holding != TowerType.NULL) {
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
		
		if (this.textEvent != null) {
			this.textEvent.render(g);
			if (this.textEvent.textBox.contains(this.player.currMouseLoc[0], this.player.currMouseLoc[1])) {
				
			}
		}	
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
				if (t.getShape().contains(new Rectangle(tileX*32+1, tileY*32+1, 30, 30))) {
					return false;
				}
			}
			
			if (tileX < 0 || tileX > 32 || tileY < 0 || tileY > 24) {
				return false;
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
		if (this.textEvent != null) {
			//Continue on textEvent, don't update anything
		}
		else {
			if (this.currLevel.currWave.checkTextEvent()) {
				//Spawn text Event
				this.textEvent = new TextEvent(this.currLevel.currWave.getTextEvent());
				return;
			}
			
			currLevel.waveTimer += delta;
			
			if (currLevel.currWave.checkNextEnemy(currLevel.waveTimer)) {
				addE.add(currLevel.currWave.getNextEnemy());
			}
			if (currLevel.checkSendWave()) {
				currLevel.getNextWave();
				player.removeAllTowers();
				player.addBatchTowers(currLevel.currWave.updatePlayerTowers());
				sendEarly = false;
			}
			if (currLevel.currWave.waveDone() && monsters.size() == 0) {
				//Cleared wave, send early button becomes available
				sendEarly = true;
			}
			

			for (Enemy e : monsters ) {
				if ( ( (int) e.pos[0] == (int) e.destination.x ) && ( (int) e.pos[1] == (int) e.destination.y ) ) {
					//Reached waypoint, new waypoint
					e.getNextDest();
					if (e.success) {
						removeE.add(e);
					}
				}
				e.updateMovement();
				e.loseTowers(remTfromE, remEfromT);
			}
			
			for (Tower t: towers) {
				//Check if tower needs to check firing
				t.updateTime(delta);

				t.acquireTargets(monsters, addTtoE, remTfromE, addEtoT, remEfromT);

				t.fire(addB);
				System.out.println(t.target.size());
			}
			
			for ( Projectile p : bullets ) {
				if (removeE.contains(p.target)) {
					removeB.add(p);
					continue;
				}
				
				p.updateMovement();

				if (p.getHitbox().intersects(p.target.getBulletBox()) ) {
					//Bullet hit! drain some HP
					if (p.target.currHealth > 0) {
						p.target.currHealth -= p.damage;
						if (p.target.currHealth <= 0) {
							if (!removeE.contains(p.target)) {
								removeE.add(p.target);
								for (Tower t : p.target.hittingT ) {
									ArrayList<Enemy> remE = (remEfromT.containsKey(t)) ? remEfromT.get(t) : new ArrayList<Enemy>();
									remE.add(p.target);
									remEfromT.put(t, remE);
								}
							}
						}
					}
					removeB.add(p);
				}
			}
			
			//Remove all bullets that need to be removed
			for (Projectile p : removeB) {
				bullets.remove(p);
			}
			
			if (removeE.size() > 0) {
				System.out.println(removeE.size());
			}
			for (Enemy e : removeE) {
				monsters.remove(e);
				
				if (e.success) {
					player.lives--;
				} else {
					player.gold += e.bounty;
				}
			}

			//Remove all towers that have left range on enemy
			for (Enemy e : remTfromE.keySet()) {
				for (Tower t : remTfromE.get(e)) {
					e.hittingT.remove(t);
				}
			}

			//Remove all towers that have left range on enemy
			for (Tower t: remEfromT.keySet()) {
				for (Enemy e: remEfromT.get(t)) {
					t.target.remove(e);
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

			for (Tower t: addEtoT.keySet()) {
				for (Enemy e: addEtoT.get(t)) {
					t.target.add(e);
				}
			}
			
			addB.clear();
			addE.clear();
			addT.clear();
			addTtoE.clear();
			addEtoT.clear();
			remEfromT.clear();
			remTfromE.clear();
			removeB.clear();
			removeE.clear();			
		}
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
		if (player.holding != TowerType.NULL ) {
			if (canPutDown(player.getTowerShape()) && (player.gold >= player.getCostOfTower()) ) {
				player.gold -= player.getCostOfTower();
				addT.add(player.makeNewTower());
				player.holding = TowerType.NULL;
			}
		}
		if (button == 0) {
			if (this.textEvent != null) {
				if (this.textEvent.textBox.contains(x, y) ) {
					//Clicking on textbox makes it go away
					this.textEvent = null;
				}
			} else {
				for (TowerType t: player.availTowers) {
					if (Tower.towerIcon(t).contains(x, y)) {
						player.holding = t;
					}
				}
			}
		}
	}
	
	@Override
	public void keyPressed(int key, char c) {
		if (player.holding == TowerType.NULL) {
			for (TowerType t : player.availTowers) {
				if (key == Tower.towerKey(t)) {
					player.holding = t;
				}
			}
		}
		if (player.holding != TowerType.NULL && key == Input.KEY_ESCAPE) {
			player.holding = TowerType.NULL;
		}
		
		if (sendEarly && key == Input.KEY_P) {
			currLevel.getNextWave();
			player.removeAllTowers();
			player.addBatchTowers(currLevel.currWave.updatePlayerTowers());

			sendEarly = false;
		}
		if (this.textEvent!=null && (key == Input.KEY_ENTER || key == Input.KEY_SPACE) ) {
			//Get rid of textEvent, resume game
			this.textEvent = null;
		}
	}
}