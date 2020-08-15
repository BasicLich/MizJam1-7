import java.awt.image.BufferedImage;

import nWiweEngine.EditorObject;
import nWiweEngine.GameController;
import nWiweEngine.GameObject;
import nWiweEngine.Sprite;
import nWiweEngine.SpriteBasic;

public class Door extends GameObject {
	private BufferedImage sprites;
	private Player player;
	private boolean locked = true;
	private Sprite sprite;
	
	public Door(GameController gameController, float posX, float posY, BufferedImage sprites) {
		super(gameController, posX, posY, 2*gameController.getGridSize(), gameController.getGridSize());
		this.sprites = sprites;
		
		BufferedImage image = Sprite.getSprite(sprites, 49, 65, 19, 13);
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
		return new EditorDoor(sprites);
	}

	@Override
	public void kill() {}

	@Override
	public void restartLevel() {
		locked = true;
	}

	@Override
	public void spawn() {}

	@Override
	public String toString() {
		return "Door";
	}

	@Override
	public void update() {
		if(player != null && player.getKeys() > 0) {
			float difX = MyUtil.getDifference(getMidX(), player.getMidX());
			float difY = MyUtil.getDifference(getMidY(), player.getMidY());
			if(difX+difY < 40) {
				locked = false;
				player.takeKey();
				levelController.removeGameObject(this);
			}
		}
	}
}
