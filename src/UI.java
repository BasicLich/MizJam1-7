import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import nWiweEngine.DrawOverlay;
import nWiweEngine.Sprite;

public class UI extends DrawOverlay {
	private BufferedImage sprites;
	private BufferedImage key;
	private BufferedImage heart;
	private BufferedImage halfHeart;
	private int space = 8;
	private int pos0 = 32;
	private int pos1 = 32+pos0+space;
	private int pos2 = 32+pos1+space;
	private int life = 6;
	private int keys = 0;
	
	public UI(BufferedImage sprites) {
		this.sprites = sprites;
		
		key= Sprite.getSprite(sprites, 545, 177, 16, 16);
		heart = Sprite.getSprite(sprites, 673, 162, 14, 12);
		halfHeart = Sprite.getSprite(sprites, 657, 162, 14, 12);
	}
	
	@Override
	public void draw(Graphics g) {
		if(life == 1) {
			g.drawImage(halfHeart, pos0, 32, 32, 32, null);
		} else if(life >= 2) {
			g.drawImage(heart, pos0, 32, 32, 32, null);
		}
		
		if(life == 3) {
			g.drawImage(halfHeart, pos1, 32, 32, 32, null);
		} else if(life >= 4) {
			g.drawImage(heart, pos1, 32, 32, 32, null);
		}
		
		if(life == 5) {
			g.drawImage(halfHeart, pos2, 32, 32, 32, null);
		} else if(life == 6) {
			g.drawImage(heart, pos2, 32, 32, 32, null);
		}
		
		if(keys > 0) {
			g.drawImage(key, pos0, 96, 32, 32, null);
			g.setColor(new Color(200, 20, 20, 255));
			Font font = new Font("Arial", Font.PLAIN, 32);
			g.setFont(font);
			g.drawString(""+keys, pos1, 96+24);
		}
	}
	
	public int cab(int i) {
		return Math.max(0, Math.min(i, 6));
	}
	
	public void increaeLife(int i) {
		life = cab(life+i);
	}
	
	public void setLife(int i) {
		life = cab(i);
	}

	public int getLife() {
		return life;
	}

	public void increaseKeys(int i) {
		keys += i;
	}

	public int getKeys() {
		return keys;
	}
}
