
import nWiweEngine.EditorObject;
import nWiweEngine.GameController;
import nWiweEngine.GameObject;

public class EditorButtonControls extends EditorObject {	
	public EditorButtonControls() {}
	
	@Override
	public GameObject createObject(GameController gameController, float posX, float posY) {
		obj = new ButtonControls(gameController, posX, posY);
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
		return new ButtonControls(gameController, posX, posY);
	}
}
