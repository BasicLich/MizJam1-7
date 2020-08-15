import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import nWiweEngine.EditorObject;
import nWiweEngine.GameController;
import nWiweEngine.GameObjectMoving;
import nWiweEngine.Sprite;
import nWiweEngine.SpriteBasic;

public class Particle extends GameObjectMoving {
	private Color c;
	private int size;
	private Sprite sprite;
	private float dx;
	private float dy;
	private int chance = 2;
	private Random rand;
	
	public Particle(GameController gameController, float posX, float posY, int size, Color c, float dx, float dy) {
		super(gameController, posX, posY, size, size);
		this.c = c;
		this.dx = dx;
		this.dy = dy;
		this.size = size;
		rand = new Random();
		setIgnoreBorder(true);
	}

	@Override
	public void construct() {}

	@Override
	public Sprite getSprite() {
		BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.createGraphics();
		g.setColor(c);
		g.fillOval(0, 0, size, size);
		g.dispose();
		return new SpriteBasic(gameController, this, image);
	}

	@Override
	public EditorObject initEditorObject() {
		return null;
	}

	@Override
	public void kill() {}

	@Override
	public void restartLevel() {}

	@Override
	public void spawn() {}

	@Override
	public String toString() {
		return "Particle";
	}

	@Override
	public void update() {
		if(rand.nextInt(chance) == 0) {
			size--;
		}
		
		if(size == 0) {
			levelController.removeGameObject(this);
		} else {
			if(rand.nextInt(chance) == 0) {
				if(dx > 0) {
					dx--;
				} else if(dx < 0) {
					dx++;
				}
			}
			
			if(rand.nextInt(chance) == 0) {
				if(dy > 0) {
					dy--;
				} else if(dy < 0) {
					dy++;
				}
			}
			
			move(dx, dy);	
		}
	}
}
