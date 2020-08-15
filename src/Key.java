import java.awt.image.BufferedImage;

import nWiweEngine.EditorObject;
import nWiweEngine.GameController;
import nWiweEngine.GameObject;
import nWiweEngine.Sprite;
import nWiweEngine.SpriteBasic;

public class Key extends GameObject {
	private BufferedImage sprites;
	private Sprite sprite;
	private Player player;
	
	public Key(GameController gameController, float posX, float posY, BufferedImage sprites) {
		super(gameController, posX, posY, gameController.getGridSize(), gameController.getGridSize());
		this.sprites = sprites;
		
		BufferedImage image = Sprite.getSprite(sprites, 545, 177, 16, 16);
		sprite = new SpriteBasic(gameController, this, image);
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
		return new EditorKey(sprites);
	}

	@Override
	public void kill() {}

	@Override
	public void restartLevel() {}

	@Override
	public void spawn() {}

	@Override
	public String toString() {
		return "Key";
	}

	@Override
	public void update() {
		if(collide(player)) {
			player.giveKey();
			levelController.removeGameObject(this);			
		}
	}
}
