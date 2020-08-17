import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import nWiweEngine.EditorObject;
import nWiweEngine.GameController;
import nWiweEngine.GameObject;
import nWiweEngine.Sprite;
import nWiweEngine.SpriteBasic;

public class AreaSpell extends GameObject {
	private static int AREA = 128;
	private BufferedImage image0;
	private BufferedImage image1;
	private BufferedImage image2;
	private BufferedImage image3;
	private int life = 10;
	private int areaX;
	private int areaY;
	private boolean used = false;
	
	public AreaSpell(GameController gameController, float posX, float posY, GameObject owner) {
		super(gameController, posX, posY, AREA, AREA);
	
		image0 = new BufferedImage(AREA, AREA, BufferedImage.TYPE_INT_ARGB);
		image1 = new BufferedImage(AREA, AREA, BufferedImage.TYPE_INT_ARGB);
		image2 = new BufferedImage(AREA, AREA, BufferedImage.TYPE_INT_ARGB);
		image3 = new BufferedImage(AREA, AREA, BufferedImage.TYPE_INT_ARGB);
		
		areaX = areaY = AREA/4;
		Graphics g = image0.createGraphics();
		g.setColor(new Color(30, 80, 105));
		g.fillOval(areaX, areaY, AREA-(2*areaX), AREA-(2*areaY));
		g.dispose();
		
		areaX = areaY = AREA/8;
		g = image1.createGraphics();
		g.setColor(new Color(60, 172, 215));
		g.fillOval(areaX, areaY, AREA-(2*areaX), AREA-(2*areaY));
		g.dispose();
		
		areaX = areaY = 0;
		g = image2.createGraphics();
		g.setColor(Color.WHITE);
		g.fillOval(areaX, areaY, AREA, AREA);
		g.dispose();
		
		areaX = areaY = AREA/4;
		g = image3.createGraphics();
		g.setColor(new Color(60, 172, 215));
		g.fillOval(areaX, areaY, AREA-(2*areaX), AREA-(2*areaY));
		g.dispose();
	}

	@Override
	public void construct() {
		
	}

	@Override
	public Sprite getSprite() {
		if(life > 8) {
			return new SpriteBasic(gameController, this, image0);
		} else if(life > 6) {
			return new SpriteBasic(gameController, this, image1);
		} else if(life > 4) {
			return new SpriteBasic(gameController, this, image2);
		} else {
			return new SpriteBasic(gameController, this, image3);
		}
	}

	@Override
	public EditorObject initEditorObject() {
		return null;
	}

	@Override
	public void kill() {}

	@Override
	public void restartLevel() {
		levelController.removeGameObject(this);
	}

	@Override
	public void spawn() {
		posX = posX-(AREA/2);
		posY = posY-(AREA/2);
	}

	@Override
	public String toString() {
		return "AreaSpell";
	}

	@Override
	public void update() {
		if(!used) {
			for(GameObject obj : levelController.getGameObjects()) {
				if(obj instanceof Enemy && collide(posX, posY, getEndX(), getEndY(), obj)) {
					Enemy enemy = (Enemy) obj;
					enemy.hit();
					float[] dir = MyUtil.getDirection(obj.getMidX(), obj.getMidY(), getMidX(), getMidY(), 20);
					enemy.addKnokback(-dir[0], -dir[1]);
					used = true;
					break;
				}			
			}
		}
		
		
		life--;
		if(life <= 0) {
			levelController.removeGameObject(this);
		}
	}
}
