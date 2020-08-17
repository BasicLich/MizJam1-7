
import nWiweEngine.EditorObject;
import nWiweEngine.GameController;
import nWiweEngine.GameObject;

public class EditorButtonStart extends EditorObject {	
	private UI ui;
	
	public EditorButtonStart(UI ui) {
		this.ui = ui;
	}
	
	@Override
	public GameObject createObject(GameController gameController, float posX, float posY) {
		obj = new ButtonStart(gameController, posX, posY, ui);
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
		return new ButtonStart(gameController, posX, posY, ui);
	}
}
