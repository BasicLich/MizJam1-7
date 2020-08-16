import java.awt.image.BufferedImage;

import nWiweEngine.EditorObject;
import nWiweEngine.GameController;
import nWiweEngine.GameObject;
import nWiweEngine.Sprite;
import nWiweEngine.SpriteBasic;

public class CampFire extends GameObject {
	private BufferedImage sprites;
	private Sprite sprite;
	
	public CampFire(GameController gameController, float posX, float posY, BufferedImage sprites) {
		super(gameController, posX, posY, gameController.getGridSize(), gameController.getGridSize());
		this.sprites = sprites;
		
		BufferedImage image = Sprite.getSprite(sprites, 225, 161, 12, 13);
		sprite = new SpriteBasic(gameController, this, image);
	}

	@Override
	public void construct() {}

	@Override
	public void spawn() {}

	@Override
	public void update() {}

	@Override
	public void kill() {}

	@Override
	public void restartLevel() {}

	@Override
	public EditorObject initEditorObject() {
		return new EditorCampFire(sprites);
	}

	@Override
	public Sprite getSprite() {
		return sprite;
	}

	@Override
	public String toString() {
		return "CampFire";
	}
}
