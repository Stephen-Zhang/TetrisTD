package player;

public class Player {
	public int gold = 0;
	public int lives;
	
	public Player(int life) {
		lives = life;
	}
	
	public Player(int life, int gold) {
		lives = life;
		this.gold = gold;
	}
	
	//Additional Stuff for players such as place-able towers, achievements, powerups, etc.
}
