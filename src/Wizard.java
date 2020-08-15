import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Random;

import nWiweEngine.EditorObject;
import nWiweEngine.GameController;
import nWiweEngine.GameObject;
import nWiweEngine.Sprite;
import nWiweEngine.SpriteBasic;

public class Wizard extends Enemy {
	private BufferedImage sprites;
	private float defX;
	private float defY;
	private Sprite sprite;
	private Player player;
	private boolean aggresive = false;
	private int attackCooldown = 0;
	private Random rand;
	private float dx = 0;
	private float dy = 0;
	private int moving = 0;
	private int speed = 2;
	private int life = 3;
	private int hit = 0;
	private BufferedImage image;
	
	public Wizard(GameController gameController, float posX, float posY, BufferedImage sprites) {
		super(gameController, posX, posY, gameController.getGridSize(), gameController.getGridSize());
		this.sprites = sprites;
		defX = posX;
		defY = posY;
		
		rand = new Random();
		image = Sprite.getSprite(sprites, 384, 16, 16, 16);
		
		addSolidClass((new Tree(gameController, 0, 0, sprites)).getClass());
		addSolidClass((new Wall(gameController, 0, 0, sprites)).getClass());
		
		setIgnoreBorder(true);
	}

	@Override
	public void construct() {
		for(GameObject obj : levelController.getGameObjects()) {
			if(obj instanceof Player) {
				player = (Player) obj;
				break;
			}
		}
	}

	@Override
	public Sprite getSprite() {
		if(hit != 0) {
			BufferedImage imageHit = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
			for(int x=0; x<16; x++) {
				for(int y=0; y<16; y++) {
					Color c = new Color(image.getRGB(x, y));
					if(!(c.getRed()==0 && c.getGreen()==0 && c.getBlue()==0)) {
						imageHit.setRGB(x, y, Color.BLACK.getRGB());
					}
				}
			}
			sprite = new SpriteBasic(gameController, this, imageHit);
		} else {
			sprite = new SpriteBasic(gameController, this, image);
		}
		
		return sprite;
	}

	@Override
	public EditorObject initEditorObject() {
		return new EditorWizard(sprites);
	}

	@Override
	public void kill() {}

	@Override
	public void restartLevel() {
		setPos((int) defX, (int) defY);
	}

	@Override
	public void spawn() {
		if(player == null) {
			for(GameObject obj : levelController.getGameObjects()) {
				if(obj instanceof Player) {
					player = (Player) obj;
					break;
				}
			}			
		}
	}

	@Override
	public String toString() {
		return "Wizard";
	}
	
	@Override
	public void update() {
		hit = Math.max(0, hit-1);
		if(moving == 0) {
			if(aggresive) {
				int r = rand.nextInt(6);
				if(r == 0) {
					dx = speed;
				} else if(r == 1) {
					dx = -speed;
				} else if(r == 2) {
					dy = speed;
				} else if(r == 3) {
					dy = -speed;
				} else {
					dx = 0;
					dy = 0;
				}
				moving = 20;
			} else {
				dx = 0;
				dy = 0;
			}
		} else {
			moving--;
		}
		
		if(MyUtil.canSee(levelController, this, player)) {
			aggresive = true;			
			if(attackCooldown == 0) {
				if(rand.nextInt(20) == 0) {
					attackCooldown = 25;
					int spellSpeed = 16;
						
					float dxSpell = player.getMidX()-getMidX();
					float dySpell = player.getMidY()-getMidY();
				
					float div = Math.max(Math.abs(dxSpell), Math.abs(dySpell));
					dxSpell = (dxSpell/div)*spellSpeed;
					dySpell = (dySpell/div)*spellSpeed;
					
					levelController.addGameObject(new SpellSpike(gameController, posX, posY, sprites, this, dxSpell, dySpell));
					addMomentum(-dxSpell/2, -dySpell/2);
					
					Color smokeColor = new Color(55, 55, 55, 150);
					levelController.addGameObject(new Particle(gameController, posX, posY, 16, smokeColor, dxSpell/2, dySpell/2));	
					levelController.addGameObject(new Particle(gameController, posX, posY, 16, smokeColor, dxSpell/2, dySpell/2));	
				}
			} else {
				attackCooldown--;
			}
		} else {
			int r = rand.nextInt(20);
			if(r == 0) aggresive = false;
		}
		
		move(dx+getHorizontalGravity() , dy+getVerticalGravity(false));
	}

	public void hit() {
		life--;
		hit = 5;
		if(life == 0) {
			levelController.removeGameObject(this);
		}
	}
}
