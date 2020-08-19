import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import nWiweEngine.EditorObject;
import nWiweEngine.GameController;
import nWiweEngine.GameObject;
import nWiweEngine.Sprite;
import nWiweEngine.SpriteBasic;

public class Patrol extends Enemy {
	private BufferedImage sprites;
	private Sprite sprite;
	private Sprite spriteHit;
	private CampFire myCampFire;
	private GameObject currentObj;
	private ArrayList<GameObject> visited;
	private ArrayList<Patrol> bodies; 
	private float dx = 0;
	private float dy = 0;
	private float goalX = 0;
	private float goalY = 0;
	private int walk = 2;
	private int run = 4;
	private int speed = walk;
	private boolean onPatrol = false;
	private boolean hasSearched = false;
	private Random rand;
	private int restTime = 2500;
	private int life = 4;
	private int hit = 0;
	private boolean hunting = false;
	private Player player;
	private int attack = 0;
	private int cooldown = 0;
	private int stun = 0;
	private boolean bodyMove = false;
	private boolean homeless = false;
	
	public Patrol(GameController gameController, float posX, float posY, BufferedImage sprites) {
		super(gameController, posX, posY, gameController.getGridSize(), gameController.getGridSize());
		this.sprites = sprites;

		BufferedImage image = Sprite.getSprite(sprites, 498, 1, 12, 13);
		sprite = new SpriteBasic(gameController, this, image);
		
		BufferedImage imageHit = Sprite.getSprite(sprites, 498, 1, 12, 13);
		imageHit = new BufferedImage(12, 13, BufferedImage.TYPE_INT_ARGB);
		for(int x=0; x<12; x++) {
			for(int y=0; y<13; y++) {
				Color c = new Color(image.getRGB(x, y));
				if(!(c.getRed()==0 && c.getGreen()==0 && c.getBlue()==0)) {
					imageHit.setRGB(x, y, Color.BLACK.getRGB());
				}
			}
		}
		spriteHit = new SpriteBasic(gameController, this, imageHit);
		
		visited = new ArrayList<GameObject>();
		bodies = new ArrayList<Patrol>();
		rand = new Random();
		
		addSolidClass((new CampFire(gameController, 0, 0, sprites)).getClass());
		addSolidClass((new Tree(gameController, 0, 0, sprites)).getClass());
		addSolidClass((new Wall(gameController, 0, 0, sprites)).getClass());
		addSolidClass((new Water(gameController, 0, 0)).getClass());
		addSolidClass((new Door(gameController, 0, 0, sprites)).getClass());
		setIgnoreBorder(true);
	}

	@Override
	public void construct() {
		for(GameObject obj : levelController.getGameObjects()) {
			if(obj == this) continue;

			if(obj instanceof Player) {
				player = (Player) obj;
				continue;
			}
			
			boolean canSee = MyUtil.canSee(levelController, this, obj, 500, 500);
			
			if(obj instanceof CampFire && canSee) {
				myCampFire = (CampFire) obj;
				continue;
			}
			
			if(obj instanceof Patrol && canSee) {
				bodies.add((Patrol) obj);
				continue;
			}
		}
	}

	@Override
	public Sprite getSprite() {
		if(hit==0) {
			return sprite;
		} else {
			return spriteHit;
		}
	}

	@Override
	public EditorObject initEditorObject() {
		return new EditorPatrol(sprites);
	}

	@Override
	public void kill() {}

	@Override
	public void restartLevel() {
		visited = new ArrayList<GameObject>();
		hasSearched = false;
		onPatrol = false;
		dx = 0;
		dy = 0;
		life = 4;
		initialPosition();
		hunting = false;
		speed = walk;
		attack = 0;
		cooldown = 0;
		bodyMove = false;
		homeless = false;
	}

	@Override
	public void spawn() {}

	@Override
	public String toString() {
		return "Patrol";
	}

	@Override
	public void update() {
		boolean canSeePlayer = MyUtil.canSee(levelController, this, player, 800, 550, 30);
		
		//if not attacking
		if(attack == 0) {
			preventKnockback = false;
			
			//has seen the player
			if(hunting) {
				if(canSeePlayer) {
					float[] dir = MyUtil.getDirection(getMidX(), getMidY(), player.getMidX(), player.getMidY());
					dx = dir[0];
					dy = dir[1];
					
					float distance = MyUtil.getDifference(getMidX(), player.getMidX())+MyUtil.getDifference(getMidY(), player.getMidY());
					if(attack == 0 && distance < 128) {
						hunting = false;
						attack = 20;
						addMomentum(dx*8, dy*8);
						dx = 0;
						dy = 0;
						preventKnockback = true;
					}
				}
				
			//has not seen the player
			} else {
				//if currently on patrol
				if(onPatrol) {
					//scans for new way point or camp fire
					if(!hasSearched && !homeless) {
						for(GameObject obj : levelController.getGameObjects()) {
							if(obj instanceof WayPoint || obj instanceof CampFire) {
								//checks if it has already been visited
								boolean alreadyVisited = false;
								for(GameObject o : visited) {
									if(obj == o) {
										alreadyVisited = true;
										break;
									}
								}
								if(alreadyVisited) continue;
								
								//checks if the patrol can see the campfire or way point
								if(MyUtil.canSee(levelController, this, obj, 800, 550)) {
									float[] dir = MyUtil.getDirection(getMidX(), getMidY(), obj.getMidX(), obj.getMidY());
									currentObj = obj;
									dx = dir[0];
									dy = dir[1];
									goalX = obj.getMidX();
									goalY = obj.getMidY();
									hasSearched = true;
									break;
								}
							}
						}
						
						if(!hasSearched) {
							homeless = true;
						}
					//checks if he has arrived
					} else if(hasSearched) {
						float difX = MyUtil.getDifference(getMidX(), goalX);
						float difY = MyUtil.getDifference(getMidY(), goalY);
						float dif = MyUtil.getDifference(difX, difY);
						if(dif < 16) {
							dx = 0;
							dy = 0;
							visited.add(currentObj);
							onPatrol = false;
							hasSearched = false;
							myCampFire = null;
							
							if(currentObj instanceof CampFire) {
								visited.clear();
								myCampFire = (CampFire) currentObj;
							}
						}
					}
				//if no on patrol
				} else {
					int r = rand.nextInt(restTime);
					
					if(myCampFire == null) {
						onPatrol = true;
						bodyMove = false;
						for(Patrol p : bodies) {
							p.move();
						}
					} else if(bodyMove || r == 0){
						visited.add(myCampFire);
						onPatrol = true;
						bodyMove = false;
						for(Patrol p : bodies) {
							p.move();
						}
					}
				}
			}
			
			if(!hunting && canSeePlayer) {
				hunting = true;
				speed = run;
			}
		}
		
		if(stun == 0) {
			move((dx*speed)+getHorizontalGravity(), (dy*speed)+getVerticalGravity(false));
		} else {
			move(getHorizontalGravity(), getVerticalGravity(false));
		}
		
		if(collide(player) && cooldown==0) {
			cooldown = 20;
			player.hit();
			player.hit();
		}
		
		for(Patrol p : bodies) {
			if(rand.nextInt(5)==0 && collide(p)) {
				int x = (rand.nextInt(2) == 0) ? -32 : 32;
				int y = (rand.nextInt(2) == 0) ? -32 : 32;
				move(x, y);
			}
		}
		
		hit = Math.max(0, hit-1);
		cooldown = Math.max(0, cooldown-1);
		attack = Math.max(0, attack-1);
		stun = Math.max(0, stun-1);
	}

	private void move() {
		if(!onPatrol) {
			stun += 60;
			bodyMove = true;			
		}
	}

	@Override
	public void hit() {
		stun += 20;
		life--;
		hit = 5;
		if(life == 0) {
			levelController.removeGameObject(this);
		}
	}
}
