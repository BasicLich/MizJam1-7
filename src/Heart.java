import java.awt.image.BufferedImage;

import nWiweEngine.EditorObject;
import nWiweEngine.GameController;
import nWiweEngine.GameObject;
import nWiweEngine.Sprite;
import nWiweEngine.SpriteBasic;

public class Heart extends GameObject {
	private BufferedImage sprites;
	private UI ui;
	private Sprite sprite;
	private Player player;
	
	public Heart(GameController gameController, float posX, float posY, BufferedImage sprites, UI ui) {
		super(gameController, posX, posY, gameController.getGridSize(), gameController.getGridSize());
		this.sprites = sprites;
		this.ui = ui;
		
		BufferedImage heart = Sprite.getSprite(sprites, 673, 162, 14, 12);
		sprite = new SpriteBasic(gameController, this, heart);
	}

	@Override
	public void construct() {
		for(GameObject obj : levelController.getGameObjects()) {
			if(obj instanceof Player) {
				player = (Player) obj;
			}
		}
	}

	@Override
	public Sprite getSprite() {
		return sprite;
	}

	@Override
	public EditorObject initEditorObject() {
		return new EditorHeart(sprites, ui);
	}

	@Override
	public void kill() {}

	@Override
	public void restartLevel() {}

	@Override
	public void spawn() {}

	@Override
	public String toString() {
		return "Heart";
	}

	@Override
	public void update() {
		if(collide(player)) {
			ui.increaeLife(1);
			levelController.removeGameObject(this);
		}
	}
}
