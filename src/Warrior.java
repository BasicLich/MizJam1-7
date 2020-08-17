import java.awt.Color;
import java.awt.image.BufferedImage;

import nWiweEngine.EditorObject;
import nWiweEngine.GameController;
import nWiweEngine.GameObject;
import nWiweEngine.Sprite;
import nWiweEngine.SpriteBasic;

public class Warrior extends Enemy {
	private BufferedImage sprites;
	private BufferedImage image;
	private Sprite sprite;
	private Sprite spriteHit;
	private Player player;
	private boolean hunting = false;
	private float playerX;
	private float playerY;
	private int speed = 2;
	private float dx = 0;
	private float dy = 0;
	private int attack = 0;
	private int cooldown = 0;
	private int life = 2;
	private int hit = 0;
	private int observation = 30;
	
	public Warrior(GameController gameController, float posX, float posY, BufferedImage sprites) {
		super(gameController, posX, posY, gameController.getGridSize(), gameController.getGridSize());
		this.sprites = sprites;
		
		image = Sprite.getSprite(sprites, 482, 1, 14, 16);
		
		addSolidClass((new CampFire(gameController, 0, 0, sprites)).getClass());
		addSolidClass((new Tree(gameController, 0, 0, sprites)).getClass());
		addSolidClass((new Wall(gameController, 0, 0, sprites)).getClass());
		addSolidClass((new Water(gameController, 0, 0)).getClass());
		addSolidClass((new Door(gameController, 0, 0, sprites)).getClass());
		setIgnoreBorder(true);
		
		if(hit != 0) {
			BufferedImage imageHit = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
			for(int x=0; x<14; x++) {
				for(int y=0; y<16; y++) {
					Color c = new Color(image.getRGB(x, y));
					if(!(c.getRed()==0 && c.getGreen()==0 && c.getBlue()==0)) {
						imageHit.setRGB(x, y, Color.BLACK.getRGB());
					}
				}
			}
			spriteHit = new SpriteBasic(gameController, this, imageHit);
		} else {
			sprite = new SpriteBasic(gameController, this, image);
		}
	}

	@Override
	public void construct() {
		for(GameObject obj : levelController.getGameObjects()) {
			if(obj instanceof Player) {
				player = (Player) obj;
			}
		}
	}

	@Override
	public Sprite getSprite() {
		if(hit == 0) {
			return sprite;
		} else {
			return spriteHit;
		}
	}

	@Override
	public EditorObject initEditorObject() {
		return new EditorWarrior(sprites);
	}

	@Override
	public void kill() {}

	@Override
	public void restartLevel() {
		life = 2;
		initialPosition();
		hunting = false;
		attack = 0;
		dx = 0;
		dy = 0;
	}

	@Override
	public void spawn() {}

	@Override
	public String toString() {
		return "Warrior";
	}

	@Override
	public void update() {
		if(attack == 0) {
			boolean cansee = MyUtil.canSee(levelController, this, player, 800, 550, observation);
			
			if(!hunting && cansee) {
				hunting = true;			
			}
			
			if(hunting) {
				if(cansee) {
					observation = 10;
					playerX = player.getMidX();
					playerY = player.getMidY();
					float[] dir = MyUtil.getDirection(getMidX(), getMidY(), playerX, playerY, speed);
					dx = dir[0];
					dy = dir[1];
					
					float distance = MyUtil.getDifference(getMidX(), playerX)+MyUtil.getDifference(getMidY(), playerY);
					if(distance < 100) {
						hunting = false;
						attack = 20;
						addMomentum(dx*8, dy*8);
						preventKnockback = true;
						dx = 0;
						dy = 0;						
					}
				}
				
				float goalX = MyUtil.getDifference(playerX, getMidX());
				float goalY = MyUtil.getDifference(playerY, getMidY());
				if(goalX < 2) {
					dx = 0;
				}
				if(goalY < 2) {
					dy = 0;
				}
				if(dx == 0 && dy == 0) {
					hunting = false;
				}
			}
		} else {
			preventKnockback = false;
			attack--;
		}
		
		float oldX = posX;
		float oldY = posY;
		move(dx+getHorizontalGravity(), dy+getVerticalGravity(false));
		float disX = MyUtil.getDifference(posX, oldX);
		float disY = MyUtil.getDifference(posY, oldY);
		if(hunting && disX < 0.5 && disY < 0.5) {
			hunting = false;
		}
		
		if(cooldown == 0) {
			if(collide(player)) {
				player.hit();
				cooldown = 10;
			}
		} else {
			cooldown--;
		}
		
		hit = Math.max(0, hit-1);
	}

	@Override
	public void hit() {
		life--;
		hit = 5;
		if(life == 0) {
			levelController.removeGameObject(this);
		}
	}
}
