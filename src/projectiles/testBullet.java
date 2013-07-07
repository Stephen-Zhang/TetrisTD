package projectiles;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import enemies.Enemy;

public class testBullet extends Projectile { 
	public testBullet(Enemy t, double[] startingLoc) {
		speed = 20;
		damage = 1;
		target = t;

		try {
			sprite = new Image("resources/projectiles/testBullet.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Bullets also spawn from center of towers
		pos[0] = startingLoc[0]+12;
		pos[1] = startingLoc[1]+12;
		
	}
	
	
}
