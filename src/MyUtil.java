import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import nWiweEngine.GameObject;
import nWiweEngine.LevelController;

public class MyUtil {
	/*
	 * Returns true if this object can see the other object
	 */
	public static boolean canSee(LevelController levelController, GameObject obj0, GameObject obj1) {
		float posX = obj0.getPosX();
		float posY = obj0.getPosY();
		
		float midX = posX+(obj0.getHitboxWidth()/2);
		float objX = obj1.getPosX()+(obj1.getHitboxWidth()/2);
		float distanceX = Math.max(midX, objX) - Math.min(midX, objX);
		if(distanceX > 700) return false;
		
		float midY = posY+(obj0.getHitboxHeight()/2);
		float objY = obj1.getPosY()+(obj1.getHitboxHeight()/2);
		float distanceY = Math.max(midY, objY) - Math.min(midY, objY);
		if(distanceY > 400) return false;

		Line2D line = new Line2D.Float(midX, midY, objX, objY);
		for(GameObject o : levelController.getGameObjects()) {
			if(o instanceof Wall) {	
				Rectangle2D rect = new Rectangle2D.Float(o.getPosX(), o.getPosY(), o.getHitboxWidth(), o.getHitboxHeight());
				if(line.intersects(rect)) {
					return false;
				}
			}
		}
		return true;
	}
}
