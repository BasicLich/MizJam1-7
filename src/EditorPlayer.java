import java.awt.image.BufferedImage;

import nWiweEngine.EditorObject;
import nWiweEngine.GameController;
import nWiweEngine.GameObject;

public class EditorPlayer extends EditorObject {
	private BufferedImage sprites;
	private UI ui;
	
	public EditorPlayer(BufferedImage sprites, UI ui) {
		this.sprites = sprites;
		this.ui = ui;
	}
	
	@Override
	public GameObject createObject(GameController gameController, float posX, float posY) {
		obj = new Player(gameController, posX, posY, sprites, ui);
		return obj;
	}

	@Override
	public String exportObject() {
		return obj.toString()+" "+obj.getPosX()+" "+obj.getPosY();
	}

	@Override
	public GameObject importObject(GameController gameController, String s) {
		String lst[] = s.split(" ");
		float posX = Float.parseFloat(lst[1]);
		float posY = Float.parseFloat(lst[2]);
		return new Player(gameController, posX, posY, sprites, ui);
	}
}
