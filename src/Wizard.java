import java.awt.image.BufferedImage;

import nWiweEngine.EditorObject;
import nWiweEngine.GameController;
import nWiweEngine.GameObjectMoving;
import nWiweEngine.Sprite;
import nWiweEngine.SpriteBasic;

public class Wizard extends GameObjectMoving {
	private BufferedImage sprites;
	private float defX;
	private float defY;
	private Sprite sprite;
	
	public Wizard(GameController gameController, float posX, float posY, BufferedImage sprites) {
		super(gameController, posX, posY, gameController.getGridSize(), gameController.getGridSize());
		this.sprites = sprites;
		defX = posX;
		defY = posY;
		
		BufferedImage image = Sprite.getSprite(sprites, 384, 16, 16, 16);
		sprite = new SpriteBasic(gameController, this, image);
	}

	@Override
	public void construct() {}

	@Override
	public Sprite getSprite() {
		return sprite;
	}

	@Override
	public EditorObject initEditorObject() {
		return new EditorWizard(sprites);
	}

	@Override
	public void kill() {}

	@Override
	public void restartLevel() {
		setPos((int) defX, (int) defY);
	}

	@Override
	public void spawn() {}

	@Override
	public String toString() {
		return "Wizard";
	}

	@Override
	public void update() {}
}
