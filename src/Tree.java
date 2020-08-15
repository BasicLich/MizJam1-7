import java.awt.image.BufferedImage;
import java.util.Random;

import nWiweEngine.EditorObject;
import nWiweEngine.GameController;
import nWiweEngine.GameObject;
import nWiweEngine.Sprite;
import nWiweEngine.SpriteBasic;

public class Tree extends GameObject {
	private BufferedImage sprites;
	private int treeType;
	private Sprite sprite;
	
	public Tree(GameController gameController, float posX, float posY, BufferedImage sprites) {
		super(gameController, posX, posY, gameController.getGridSize(), gameController.getGridSize());
		this.sprites = sprites;
		
		//sprite
		Random rand = new Random();
		treeType  = rand.nextInt(6);
		BufferedImage image = null;
		
		switch(treeType) {
			case 0:
				image = Sprite.getSprite(sprites, 0, 16, 16, 16);
				break;
			case 1:
				image = Sprite.getSprite(sprites, 16, 16, 16, 16);
				break;
			case 2:
				image = Sprite.getSprite(sprites, 32, 16, 16, 16);
				break;
			case 3:
				image = Sprite.getSprite(sprites, 48, 16, 16, 16);
				break;
			case 4:
				image = Sprite.getSprite(sprites, 64, 16, 16, 16);
				break;
			case 5:
				image = Sprite.getSprite(sprites, 80, 16, 16, 16);
				break;
		}
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
		return new EditorTree(sprites);
	}

	@Override
	public void kill() {}

	@Override
	public void restartLevel() {}

	@Override
	public void spawn() {}

	@Override
	public String toString() {
		return "Tree";
	}

	@Override
	public void update() {}
}
