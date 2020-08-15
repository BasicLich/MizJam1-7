import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import nWiweEngine.EditorObject;
import nWiweEngine.GameController;
import nWiweEngine.GameObjectMoving;
import nWiweEngine.GraphicCanvas;
import nWiweEngine.InputController;
import nWiweEngine.Sprite;
import nWiweEngine.SpriteBasic;

public class Player extends GameObjectMoving {
	private BufferedImage sprites;
	private GraphicCanvas graphicCanvas;
	private int speed = 4;
	private boolean facingRight = true;
	private int attackCooldown = 0;
	
	public Player(GameController gameController, float posX, float posY, BufferedImage sprites) {
		super(gameController, posX, posY, gameController.getGridSize(), gameController.getGridSize());
		this.sprites = sprites;
		graphicCanvas = gameController.getGameWindow().getGraphicCanvas();
		
		addSolidClass((new Tree(gameController, 0, 0, sprites)).getClass());
		setIgnoreBorder(true);
	}

	@Override
	public void construct() {}

	@Override
	public Sprite getSprite() {		
		BufferedImage image;
		if(attackCooldown != 0) {
			image = Sprite.getSprite(sprites, 322, 116, 11, 10);
			BufferedImage attackImage = new BufferedImage(11, 10, BufferedImage.TYPE_INT_ARGB);
			Graphics g = attackImage.createGraphics();
			g.setColor(Color.WHITE);
			g.fillOval(0, 0, 11, 10);
			g.drawImage(image, 0, 0, null);
			g.dispose();
			image = attackImage;
		} else {
			image = Sprite.getSprite(sprites, 322, 116, 11, 10);
		}
		
		Sprite sprite = new SpriteBasic(gameController, this, image);
		if(facingRight) {
			sprite.setReverse(false);			
		} else {
			sprite.setReverse(true);
		}
		
		return sprite;
	}

	@Override
	public EditorObject initEditorObject() {
		return new EditorPlayer(sprites);
	}

	@Override
	public void kill() {}

	@Override
	public void restartLevel() {}

	@Override
	public void spawn() {}

	@Override
	public String toString() {
		return "Player";
	}

	@Override
	public void update() {
		InputController inputC = gameController.getInputController();
		int dx = 0;
		int dy = 0;
		float offsetX = 0;
		float offsetY = 0;
		
		if(attackCooldown == 0) {
			//up
			if(inputC.isKeyPressed(87) || inputC.isKeyPressed(38)) {
				dy -= speed;
			}

			//down
			if(inputC.isKeyPressed(83) || inputC.isKeyPressed(40)) {
				dy += speed;
			}

			//right
			if(inputC.isKeyPressed(39) || inputC.isKeyPressed(68)) {
				dx += speed;
				facingRight = true;
			}

			//left
			if(inputC.isKeyPressed(37) || inputC.isKeyPressed(65)) {
				dx -= speed;
				facingRight = false;
			}
			
			//space
			if(inputC.isMouseKeyPressed(1)) {
				int spellSpeed = 16;
				float mouseX = inputC.getMouseX();
				float mouseY = inputC.getMouseY();
				
				float meX = (posX+(getHitboxWidth()/2))-graphicCanvas.getOffsetX();
				float meY = (posY+(getHitboxHeight()/2))-graphicCanvas.getOffsetY();
				
				float dxSpell = mouseX-meX;
				float dySpell = mouseY-meY;
			
				float div = Math.max(Math.abs(dxSpell), Math.abs(dySpell));
				dxSpell = (dxSpell/div)*spellSpeed;
				dySpell = (dySpell/div)*spellSpeed;
				
				attackCooldown = 30;
				levelController.addGameObject(new SpellSpike(gameController, posX, posY, sprites, this, dxSpell, dySpell));	
				addMomentum(-dxSpell*4, -dySpell*4);
				move(-dxSpell, -dySpell);
			} else {
				float oldX = posX; 
				float oldY = posY;
				float[] newPos = move(dx, dy);
				offsetX = newPos[0] - oldX;
				offsetY = newPos[1] - oldY;
			}
		} else {
			attackCooldown--;
		}
		graphicCanvas.increaseOffsetX(offsetX);
		graphicCanvas.increaseOffsetY(offsetY);
	}
}
