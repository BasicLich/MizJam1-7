import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import nWiweEngine.DrawOverlay;
import nWiweEngine.Sprite;

public class UI extends DrawOverlay {
	private BufferedImage sprites;
	private float scale;
	private BufferedImage key;
	private BufferedImage heart;
	private BufferedImage halfHeart;
	private BufferedImage emptyMana;
	private BufferedImage halfMana;
	private BufferedImage fullMana;
	private int space = 8;
	private int pos0 = 32;
	private int pos1 = 32+pos0+space;
	private int pos2 = 32+pos1+space;
	private int life = 6;
	private int keys = 0;
	private boolean active;
	private int maxMana = 4;
	private int mana = 4;
	
	public UI(BufferedImage sprites, float scale) {
		this.sprites = sprites;
		this.scale = scale;
		
		key= Sprite.getSprite(sprites, 545, 177, 16, 16);
		heart = Sprite.getSprite(sprites, 673, 162, 14, 12);
		halfHeart = Sprite.getSprite(sprites, 657, 162, 14, 12);
		emptyMana = Sprite.getSprite(sprites, 640, 177, 14, 14);
		halfMana = Sprite.getSprite(sprites, 656, 177, 14, 14);
		fullMana = Sprite.getSprite(sprites, 672, 177, 14, 14);
	}
	
	@Override
	public void draw(Graphics graph) {
		if(!active) return;
		
		Graphics2D g = (Graphics2D) graph;
		g.scale(scale, scale);
		
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
		
		//mana
		for(int i=0; i<maxMana/2; i++) {
			int value = mana - (i*2);
			if(value <= 0) {
				g.drawImage(emptyMana, pos0+(i*32), 96, 32, 32, null);						
			} else if(value == 1) {
				g.drawImage(halfMana, pos0+(i*32), 96, 32, 32, null);
			} else {
				g.drawImage(fullMana, pos0+(i*32), 96, 32, 32, null);
			}
		}
		
		//keys
		for(int i=0; i<keys; i++) {
			g.drawImage(key, pos0+(i*32+space), 160, 32, 32, null);
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

	public void setActive(boolean b) {
		active = b;
	}

	public void setKeys(int i) {
		keys = i;
	}

	public void setMaxMana(int i) {
		maxMana = i;
	}
	
	public void setMana(int i) {
		mana = i;
	}

	public void increaseMana(int i) {
		mana += i;
	}
	
	public int getMana() {
		return mana;
	}

	public int getMaxMana() {
		return maxMana;
	}
}
