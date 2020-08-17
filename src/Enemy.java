import nWiweEngine.GameController;
import nWiweEngine.GameObjectMoving;

public abstract class Enemy extends GameObjectMoving {
	protected boolean preventKnockback = false;
	
	public Enemy(GameController gameController, float posX, float posY, int hitboxWidth, int hitboxHeight) {
		super(gameController, posX, posY, hitboxWidth, hitboxHeight);
	}
	
	public abstract void hit();
	
	public void addKnokback(float x, float y) {
		if(!preventKnockback) {
			addMomentum(x, y);			
		}
	}
}
