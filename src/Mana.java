import java.awt.image.BufferedImage;

import nWiweEngine.EditorObject;
import nWiweEngine.GameController;
import nWiweEngine.GameObject;
import nWiweEngine.Sprite;
import nWiweEngine.SpriteBasic;

public class Mana extends GameObject {
	private BufferedImage sprites;
	private UI ui;
	private Sprite sprite;
	private Player player;
	
	public Mana(GameController gameController, float posX, float posY, BufferedImage sprites, UI ui) {
		super(gameController, posX, posY, gameController.getGridSize(), gameController.getGridSize());
		this.sprites = sprites;
		this.ui = ui;
		
		BufferedImage emptyMana = Sprite.getSprite(sprites, 640, 177, 14, 14);
		sprite = new SpriteBasic(gameController, this, emptyMana);
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
		return new EditorMana(sprites, ui);
	}

	@Override
	public void kill() {}

	@Override
	public void restartLevel() {}

	@Override
	public void spawn() {}

	@Override
	public String toString() {
		return "Mana";
	}

	@Override
	public void update() {
		if(collide(player)) {
			ui.setMaxMana(ui.getMaxMana()+2);
			levelController.removeGameObject(this);
		}
	}
}
