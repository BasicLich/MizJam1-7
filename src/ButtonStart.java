import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import nWiweEngine.EditorObject;
import nWiweEngine.GameController;
import nWiweEngine.Sprite;
import nWiweEngine.SpriteBasic;

public class ButtonStart extends Button {
	private Sprite sprite;
	private UI ui;
	
	public ButtonStart(GameController gameController, float posX, float posY, UI ui) {
		super(gameController, posX, posY);
		this.ui = ui;
				
		BufferedImage image = new BufferedImage(getHitboxWidth(), getHitboxHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.createGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getHitboxWidth(), getHitboxHeight());
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, getHitboxWidth(), getHitboxHeight());
		g.setFont(new Font("Arial", Font.PLAIN, 32));
		g.drawString("S T A R T", 60, 42);
		g.dispose();
		sprite = new SpriteBasic(gameController, this, image);
	}

	@Override
	public void clicked() {
		ui.setActive(true);
		levelController.loadLevel("src/level1.lvl");
		levelController.startGame();
	}

	@Override
	public void construct() {}

	@Override
	public Sprite getSprite() {
		return sprite;
	}

	@Override
	public EditorObject initEditorObject() {
		return new EditorButtonStart(ui);
	}

	@Override
	public void kill() {}

	@Override
	public void restartLevel() {}

	@Override
	public void spawn() {}

	@Override
	public String toString() {
		return "ButtonStart";
	}

}
