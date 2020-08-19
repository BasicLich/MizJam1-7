import java.util.ArrayList;

import nWiweEngine.EditorObject;
import nWiweEngine.GameController;
import nWiweEngine.GameObject;
import nWiweEngine.Sprite;
import nWiweEngine.SpriteTileset;

public class Water extends GameObject {
	private SpriteTileset sprite;

	public Water(GameController gameController, float posX, float posY) {
		super(gameController, posX, posY, gameController.getGridSize(), gameController.getGridSize());
		
		ArrayList<String> lst = new ArrayList<String>();
		lst.add("src/images/w_deep.png");
		lst.add("src/images/w_n.png");
		lst.add("src/images/w_ne.png");
		lst.add("src/images/w_nw.png");
		lst.add("src/images/w_e.png");
		lst.add("src/images/w_w.png");
		lst.add("src/images/w_se.png");
		lst.add("src/images/w_sw.png");
		lst.add("src/images/w_s.png");
		lst.add("src/images/w_deep.png");
		lst.add("src/images/w_deep.png");
		lst.add("src/images/w_deep.png");
		lst.add("src/images/w_deep.png");
		lst.add("src/images/w_deep.png");
		sprite = new SpriteTileset(gameController, this, lst, gameController.getGridSize(), gameController.getGridSize());
	}

	@Override
	public void construct() {
		sprite.calculateSprite(this.getClass(), posX, posY);
	}

	@Override
	public Sprite getSprite() {
		return sprite;
	}

	@Override
	public EditorObject initEditorObject() {
		return new EditorWater();
	}

	@Override
	public void kill() {}

	@Override
	public void restartLevel() {}

	@Override
	public void spawn() {}

	@Override
	public String toString() {
		return "Water";
	}

	@Override
	public void update() {}
}
