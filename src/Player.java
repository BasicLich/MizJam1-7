import java.awt.Color;
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
	private Sprite sprite;
	private Sprite spriteHit;
	private UI ui;
	private GraphicCanvas graphicCanvas;
	private int speed = 4;
	private boolean facingRight = true;
	private boolean hit = false;
	private int attackCooldown = 0;
	private int areaCooldown = 0;
	private int stun = 0;
	private int manaCooldown = 0;
	
	public Player(GameController gameController, float posX, float posY, BufferedImage sprites, UI ui) {
		super(gameController, posX, posY, gameController.getGridSize(), gameController.getGridSize());
		this.sprites = sprites;
		this.ui = ui;
		graphicCanvas = gameController.getGameWindow().getGraphicCanvas();
		
		addSolidClass((new CampFire(gameController, 0, 0, sprites)).getClass());
		addSolidClass((new Tree(gameController, 0, 0, sprites)).getClass());
		addSolidClass((new Wall(gameController, 0, 0, sprites)).getClass());
		addSolidClass((new Water(gameController, 0, 0)).getClass());
		addSolidClass((new Door(gameController, 0, 0, sprites)).getClass());
		setIgnoreBorder(true);

		BufferedImage image = Sprite.getSprite(sprites, 321, 114, 13, 13);	
		BufferedImage imageHit = new BufferedImage(13, 13, BufferedImage.TYPE_INT_ARGB);
		for(int x=0; x<13; x++) {
			for(int y=0; y<13; y++) {
				Color c = new Color(image.getRGB(x, y));
				if(!(c.getRed()==0 && c.getGreen()==0 && c.getBlue()==0)) {
					imageHit.setRGB(x, y, Color.WHITE.getRGB());
				}
			}
		}
		
		sprite = new SpriteBasic(gameController, this, image);
		spriteHit = new SpriteBasic(gameController, this, imageHit);
	}

	@Override
	public void construct() {}

	@Override
	public Sprite getSprite() {	
		if(hit) {
			if(facingRight) {
				spriteHit.setReverse(false);			
			} else {
				spriteHit.setReverse(true);
			}
			hit = false;
			return spriteHit;
		} else {
			if(facingRight) {
				sprite.setReverse(false);			
			} else {
				sprite.setReverse(true);
			}
			return sprite;
		}
	}

	@Override
	public EditorObject initEditorObject() {
		return new EditorPlayer(sprites, ui);
	}

	@Override
	public void kill() {
		initialPosition();
		graphicCanvas.setOffsetX(0);
		graphicCanvas.setOffsetY(0);
	}

	@Override
	public void restartLevel() {
		initialPosition();
		graphicCanvas.setOffsetX(0);
		graphicCanvas.setOffsetY(0);
		ui.setLife(6);
		ui.setKeys(0);
		ui.setMaxMana(4);
		ui.setMana(4);
	}

	@Override
	public void spawn() {}

	@Override
	public String toString() {
		return "Player";
	}

	@Override
	public void update() {
		InputController inputC = gameController.getInputController();
		float dx = 0;
		float dy = 0;
		float offsetX = 0;
		float offsetY = 0;
		
		if(stun == 0) {
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
			if(inputC.isKeyPressed(32) && areaCooldown == 0 && ui.getMana()>=1) {
				ui.increaseMana(-1);
				areaCooldown = 20;
				stun = 5;
				levelController.addGameObject(new AreaSpell(gameController, getMidX(), getMidY(), this));
			}
			
			//mouse
			if(inputC.isMouseKeyPressed(1) && attackCooldown == 0 && ui.getMana()>=2) {
				ui.increaseMana(-2);
				int spellSpeed = 16;
				float mouseX = inputC.getMouseX();
				float mouseY = inputC.getMouseY();

				float[] dirSpell = MyUtil.getDirection(getMidX()-graphicCanvas.getOffsetX(), getMidY()-graphicCanvas.getOffsetY(), mouseX, mouseY, spellSpeed);
				float dxSpell = dirSpell[0];
				float dySpell = dirSpell[1];

				attackCooldown = 25;
				stun = 25;
				levelController.addGameObject(new SpellSpike(gameController, posX, posY, sprites, this, dxSpell, dySpell));
				addMomentum(-(dxSpell*10)/8, -(dySpell*10)/8);
								
				Color smokeColor = new Color(55, 55, 55, 150);
				levelController.addGameObject(new Particle(gameController, posX, posY, 16, smokeColor, dxSpell/2, dySpell/2));	
				levelController.addGameObject(new Particle(gameController, posX, posY, 16, smokeColor, dxSpell/2, dySpell/2));	
			}
		}
		
		float oldX = posX; 
		float oldY = posY;
		float[] newPos = move(dx+getHorizontalGravity() , dy+getVerticalGravity(false));
		offsetX = newPos[0] - oldX;
		offsetY = newPos[1] - oldY;

		graphicCanvas.increaseOffsetX(offsetX);
		graphicCanvas.increaseOffsetY(offsetY);
		
		stun = Math.max(0, stun-1);
		attackCooldown = Math.max(0, attackCooldown-1);
		areaCooldown = Math.max(0, areaCooldown-1);
		
		if(manaCooldown == 0) {
			if(ui.getMana() != ui.getMaxMana()) {
				manaCooldown = 60;
			}
		} else {
			manaCooldown--;
			if(manaCooldown == 0) ui.increaseMana(1);
		}
	}

	public void hit() {
		ui.increaeLife(-1);
		if(ui.getLife() == 0) {
			levelController.restartLevel();
		}
		hit = true;
	}

	public void giveKey() {
		ui.increaseKeys(1);
	}

	public void takeKey() {
		ui.increaseKeys(-1);
	}

	public int getKeys() {
		return ui.getKeys();
	}
}
