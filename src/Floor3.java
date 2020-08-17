import java.awt.image.BufferedImage;

import nWiweEngine.GameController;
import nWiweEngine.GameVisual;
import nWiweEngine.Sprite;
import nWiweEngine.SpriteBasic;

public class Floor3 extends GameVisual {
	private BufferedImage sprites;
	private Sprite sprite;
	
	public Floor3(GameController gameController, float posX, float posY, BufferedImage sprites) {
		super(gameController, posX, posY, gameController.getGridSize(), gameController.getGridSize());
		this.sprites = sprites;
		
		BufferedImage image = Sprite.getSprite(sprites, 273, 1, 13, 13);
		sprite = new SpriteBasic(gameController, this, image);
	}

	@Override
	public GameVisual createVisual(GameController gameController, float posX, float posY) {
		return new Floor3(gameController, posX, posY, sprites);
	}

	@Override
	public String exportObject() {
		return toString()+" "+getPosX()+" "+getPosY();
	}

	@Override
	public Sprite getSprite() {
		return sprite;
	}

	@Override
	public GameVisual importObject(GameController gameController, String s) {
		String lst[] = s.split(" ");
		float posX = Float.parseFloat(lst[1]);
		float posY = Float.parseFloat(lst[2]);
		return new Floor3(gameController, posX, posY, sprites);
	}

	@Override
	public String toString() {
		return "Floor3";
	}
}
