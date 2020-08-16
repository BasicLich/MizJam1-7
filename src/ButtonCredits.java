import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import nWiweEngine.EditorObject;
import nWiweEngine.GameController;
import nWiweEngine.Sprite;
import nWiweEngine.SpriteBasic;

public class ButtonCredits extends Button {
	private Sprite sprite;
	
	public ButtonCredits(GameController gameController, float posX, float posY) {
		super(gameController, posX, posY);
		
		BufferedImage image = new BufferedImage(getHitboxWidth(), getHitboxHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.createGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getHitboxWidth(), getHitboxHeight());
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, getHitboxWidth(), getHitboxHeight());
		g.setFont(new Font("Arial", Font.PLAIN, 32));
		g.drawString("C R E D I T S", 30, 42);
		g.dispose();
		sprite = new SpriteBasic(gameController, this, image);
	}

	@Override
	public void clicked() {
		String s = "This game is implemented for the game jam: Miz Jam 1\n\n"
				+ "Game jam: https://itch.io/jam/miz-jam-1\n"
				+ "The game is programmed by: Nicolai Wiwe Andersen\n"
				+ "The game is implemented in the engine: nWiweEngine\n"
				+ "The engine is programmed by Nicolai Wiwe Andersen\n"
				+ "Art kit from: https://kenney.nl/assets/bit-pack\n";
		
		JTextArea text = new JTextArea(s);
		text.setEditable(false);
		text.setBackground(null);
		text.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		text.setAlignmentY(JComponent.CENTER_ALIGNMENT);
		
		Dimension dim = new Dimension(500, 500);
		
		JFrame frame = new JFrame();
		frame.setTitle("Credits");
		frame.setLayout(new GridBagLayout());
		frame.setSize(dim);
		frame.setPreferredSize(dim);
		frame.setMinimumSize(dim);
		frame.setMaximumSize(dim);
		frame.add(text);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	@Override
	public void construct() {}

	@Override
	public Sprite getSprite() {
		return sprite;
	}

	@Override
	public EditorObject initEditorObject() {
		return new EditorButtonCredits();
	}

	@Override
	public void kill() {}

	@Override
	public void restartLevel() {}

	@Override
	public void spawn() {}

	@Override
	public String toString() {
		return "ButtonCredits";
	}
}
