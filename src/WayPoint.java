import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import nWiweEngine.EditorObject;
import nWiweEngine.GameController;
import nWiweEngine.GameObject;
import nWiweEngine.Sprite;
import nWiweEngine.SpriteBasic;

public class WayPoint extends GameObject {
	private Sprite sprite;

	public WayPoint(GameController gameController, float posX, float posY) {
		super(gameController, posX, posY, gameController.getGridSize(), gameController.getGridSize());

		BufferedImage image = new BufferedImage(gameController.getGridSize(), gameController.getGridSize(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.createGraphics();
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, gameController.getGridSize(), gameController.getGridSize());
		g.dispose();
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
		return new EditorWayPoint();
	}

	@Override
	public void kill() {}

	@Override
	public void restartLevel() {}

	@Override
	public void spawn() {
		BufferedImage image = new BufferedImage(gameController.getGridSize(), gameController.getGridSize(), BufferedImage.TYPE_INT_ARGB);
		sprite = new SpriteBasic(gameController, this, image); 
	}

	@Override
	public String toString() {
		return "WayPoint";
	}

	@Override
	public void update() {}
}
