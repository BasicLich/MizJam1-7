import nWiweEngine.GameController;
import nWiweEngine.GameObject;
import nWiweEngine.InputController;

public abstract class Button extends GameObject {
	private InputController inputController;
	
	public Button(GameController gameController, float posX, float posY) {
		super(gameController, posX, posY, 256, 64);
		inputController = gameController.getInputController();
	}

	@Override
	public void update() {
		if(inputController.isMouseKeyPressed(1)) {
			int mouseX = inputController.getMouseX();
			int mouseY = inputController.getMouseY();
			if(posX<mouseX && mouseX<getEndX() && posY<mouseY && mouseY<getEndY()) {
				clicked();
			}
		}
	}
	
	public abstract void clicked();
}
