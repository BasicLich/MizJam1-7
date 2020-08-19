import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import nWiweEngine.EditorObject;
import nWiweEngine.GameController;
import nWiweEngine.Sprite;
import nWiweEngine.SpriteBasic;

public class ButtonControls extends Button {
	private Sprite sprite;
	private JFrame frame;
	private int row = 0;
	
	public ButtonControls(GameController gameController, float posX, float posY) {
		super(gameController, posX, posY);
		
		BufferedImage image = new BufferedImage(getHitboxWidth(), getHitboxHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.createGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getHitboxWidth(), getHitboxHeight());
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, getHitboxWidth(), getHitboxHeight());
		g.setFont(new Font("Arial", Font.PLAIN, 32));
		g.drawString("C O N T R O L S", 10, 42);
		g.dispose();
		sprite = new SpriteBasic(gameController, this, image);
	}

	@Override
	public void clicked() {
		String s = "move up: w\n"
				+ "move down: s\n"
				+ "move left: a\n"
				+ "move right: d\n"
				+ "attack1: space\n"
				+ "attack2: left mouse button";
		
		JTextArea text = new JTextArea(s);
		text.setEditable(false);
		text.setBackground(null);
		text.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		text.setAlignmentY(JComponent.CENTER_ALIGNMENT);
		
		Dimension dim = new Dimension(300, 200);
		GridBagConstraints c = new GridBagConstraints();

		frame = new JFrame();
		frame.setTitle("Controls");
		frame.setLayout(new GridBagLayout());
		frame.setSize(dim);
		frame.setPreferredSize(dim);
		frame.setMinimumSize(dim);
		frame.setMaximumSize(dim);
		//frame.add(text);
		
		addLine("Move up:", "w", c, frame);
		addLine("Move down:", "s", c, frame);
		addLine("Move left:", "a", c, frame);
		addLine("Move right:", "d", c, frame);
		addLine("Attack1:", "space", c, frame);
		addLine("Attack2:", "left mouse button", c, frame);
		
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public void addLine(String s0, String s1, GridBagConstraints c, JFrame frame) {
		JLabel l0 = new JLabel(s0);
		JLabel l1 = new JLabel("   "+s1);
		
		c.gridx = 0;
		c.gridy = row;
		c.anchor = GridBagConstraints.EAST;
		frame.add(l0, c);
		
		c.gridx = 1;
		c.gridy = row;
		c.anchor = GridBagConstraints.WEST;
		frame.add(l1, c);

		row++;
	}

	@Override
	public void construct() {}

	@Override
	public Sprite getSprite() {
		return sprite;
	}

	@Override
	public EditorObject initEditorObject() {
		return new EditorButtonControls();
	}

	@Override
	public void kill() {}

	@Override
	public void restartLevel() {}

	@Override
	public void spawn() {}

	@Override
	public String toString() {
		return "ButtonControls";
	}
}
