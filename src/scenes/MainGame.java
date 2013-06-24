package scenes;

import java.util.ArrayList;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public class MainGame extends StateBasedGame {

	public final static int GAME_WIDTH = 768;
	public final static int GAME_HEIGHT = 1024;
	public static ArrayList<TiledMap> maps = new ArrayList<TiledMap>();
	
	public MainGame() {
		super("Codename TTD");
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			AppGameContainer app = new AppGameContainer(new MainGame());
			app.setDisplayMode(GAME_WIDTH, GAME_HEIGHT, false);
			app.setTargetFrameRate(60);
			app.setShowFPS(true);

		}
		catch (SlickException e) {
				e.printStackTrace();
		}
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		// TODO Auto-generated method stub
        addState(new MapState(0));
	}

}
