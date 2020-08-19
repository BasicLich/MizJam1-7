import java.awt.image.BufferedImage;

import nWiweEngine.EditorObject;
import nWiweEngine.GameController;
import nWiweEngine.GameObject;
import nWiweEngine.Sprite;
import nWiweEngine.SpriteBasic;

public class Goal extends GameObject {
	private BufferedImage sprites;
	private UI ui;
	private Sprite sprite;
	private Player player;
	
	public Goal(GameController gameController, float posX, float posY, BufferedImage sprites, UI ui) {
		super(gameController, posX, posY, gameController.getGridSize(), gameController.getGridSize());
		this.sprites = sprites;
		this.ui = ui;
		
		BufferedImage image = Sprite.getSprite(sprites, 706, 34, 11, 11);
		sprite = new SpriteBasic(gameController, this, image);
	}

	@Override
	public void construct() {
		for(GameObject obj : levelController.getGameObjects()) {
			if(obj instanceof Player) {
				player = (Player) obj;
				break;
			}
		}
	}

	@Override
	public Sprite getSprite() {
		return sprite;
	}

	@Override
	public EditorObject initEditorObject() {
		return new EditorGoal(sprites, ui);
	}

	@Override
	public void kill() {}

	@Override
	public void restartLevel() {}

	@Override
	public void spawn() {}

	@Override
	public String toString() {
		return "Goal";
	}

	@Override
	public void update() {
		if(collide(player)) {
			ui.setVictory();
			levelController.removeGameObject(this);
		}
	}
}
