import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;

import nWiweEngine.GameObject;
import nWiweEngine.LevelController;

public class MyUtil {
	private static Random rand = new Random();
	
	/*
	 * Returns true if this object can see the other object
	 */
	public static boolean canSee(LevelController levelController, GameObject obj0, GameObject obj1, int maxX, int maxY, int r) {
		if(r != -1) {
			if(rand.nextInt(r) != 0) return false;
		}
		
		float posX = obj0.getPosX();
		float posY = obj0.getPosY();
		
		float midX = posX+(obj0.getHitboxWidth()/2);
		float objX = obj1.getPosX()+(obj1.getHitboxWidth()/2);
		float distanceX = Math.max(midX, objX) - Math.min(midX, objX);
		if(distanceX > maxX) return false;
		
		float midY = posY+(obj0.getHitboxHeight()/2);
		float objY = obj1.getPosY()+(obj1.getHitboxHeight()/2);
		float distanceY = Math.max(midY, objY) - Math.min(midY, objY);
		if(distanceY > maxY) return false;

		Line2D line = new Line2D.Float(midX, midY, objX, objY);
		for(GameObject o : levelController.getGameObjects()) {
			if(o instanceof Wall || o instanceof Tree) {	
				Rectangle2D rect = new Rectangle2D.Float(o.getPosX(), o.getPosY(), o.getHitboxWidth(), o.getHitboxHeight());
				if(line.intersects(rect)) {
					return false;
				}
			}
		}
		return true;
	}
	
	/*
	 * Returns true if this object can see the other object
	 */
	public static boolean canSee(LevelController levelController, GameObject obj0, GameObject obj1, int maxX, int maxY) {
		return canSee(levelController, obj0, obj1, maxX, maxY, -1);
	}
	
	public static float getDifference(float pos0, float pos1) {
		return Math.max(pos0, pos1) - Math.min(pos0, pos1);
	}
	
	/*
	 * Given two points, this method returns dx and dy
	 * to get from point a to point b
	 */
	public static float[] getDirection(float ax, float ay, float bx, float by, float mult) {
		float dx = bx-ax;
		float dy = by-ay;
	
		float div = Math.max(Math.abs(dx), Math.abs(dy));
		dx = (dx/div)*mult;
		dy = (dy/div)*mult;
		float[] dir = {dx, dy};
		return dir;
	}
	
	/*
	 * Given two points, this method returns dx and dy
	 * to get from point a to point b
	 */
	public static float[] getDirection(float ax, float ay, float bx, float by) {
		return getDirection(ax, ay, bx, by, 1);
	}
}
