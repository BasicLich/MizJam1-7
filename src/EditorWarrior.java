
import java.awt.image.BufferedImage;

import nWiweEngine.EditorObject;
import nWiweEngine.GameController;
import nWiweEngine.GameObject;

public class EditorWarrior extends EditorObject {	
	private BufferedImage sprites;
	
	public EditorWarrior(BufferedImage sprites) {
		this.sprites = sprites;
	}
	
	@Override
	public GameObject createObject(GameController gameController, float posX, float posY) {
		obj = new Warrior(gameController, posX, posY, sprites);
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
		return new Warrior(gameController, posX, posY, sprites);
	}
}
