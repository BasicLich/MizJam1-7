import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import nWiweEngine.GameController;
import nWiweEngine.GameObject;
import nWiweEngine.GameVisual;
import nWiweEngine.GameWindow;
import nWiweEngine.LevelController;
import nWiweEngine.Sprite;

public class Main {
	public static void main(String args[]) {
		Dimension dim = new Dimension(300, 200);
		
		JTextArea text = new JTextArea("Please select resolution:");
		text.setBackground(null);
		text.setEditable(false);
		
		String[] lst = {"1920x1080", "1600x900", "1360x768"};
		JComboBox<String> ddOptions = new JComboBox<String>(lst);
		
		JFrame frame = new JFrame();
		JButton button = new JButton("Confirm");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Thread t = new Thread(new Runnable() {
					@Override
					public void run() {
						String res = (String) ddOptions.getSelectedItem();
						float scale;
						if(res.equals("1920x1080")) {
							scale = 1.0f;
						} else if(res.equals("1600x900")) {
							scale = 0.83f;
						} else {
							scale = 0.71f;
						}
						
						method("The adventure of the ugly blue thing", 1820, 980, scale, 32, 60, false);						
					}
				});
				t.start();
				frame.dispose();
			}
		});
		
		GridBagConstraints c = new GridBagConstraints();
			
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridBagLayout());
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setSize(dim);
		frame.setMinimumSize(dim);
		frame.setMaximumSize(dim);
		frame.setPreferredSize(dim);
		
		c.gridx = 0;
		c.gridy = 0;
		frame.add(text, c);
		
		c.gridx = 0;
		c.gridy = 1;
		frame.add(ddOptions, c);
		
		c.gridx = 0;
		c.gridy = 2;
		frame.add(button, c);
	}
	
	public static void method(String title, int width, int height, float scale, int gridSize, int fps, boolean showDebug) {
		//Sprite kit
		BufferedImage sprites = Sprite.readImage("images/colored_transparent_packed.png");
	
		//GameController
		GameController gameController = new GameController(title, width, height, scale, gridSize);
		
		//UI
		UI ui = new UI(gameController, sprites, scale);

		gameController.constructControllers(ui);
		
		//Add different objects
		GameObject[] objectTypes = {
				new Player(gameController, 0, 0, sprites, ui),
				new Tree(gameController, 0, 0, sprites),
				new Wall(gameController, 0, 0, sprites),
				new Water(gameController, 0, 0),
				new Warrior(gameController, 0, 0, sprites),
				new Patrol(gameController, 0, 0, sprites),
				new Wizard(gameController, 0, 0, sprites),
				new Mana(gameController, 0, 0, sprites, ui),
				new Heart(gameController, 0, 0, sprites, ui),
				new Key(gameController, 0, 0, sprites),
				new Door(gameController, 0, 0, sprites),
				new CampFire(gameController, 0, 0, sprites),
				new ButtonStart(gameController, 0, 0, ui),
				new ButtonCredits(gameController, 0, 0),
				new ButtonControls(gameController, 0, 0),
				new ButtonExit(gameController, 0, 0),
				new WayPoint(gameController, 0, 0),
				new Goal(gameController, 0, 0, sprites, ui)};
		gameController.setObjectTypes(objectTypes);
		
		//Add different visuals
		GameVisual[] gameVisuals = {
				new Floor(gameController, 0, 0, sprites),
				new Floor2(gameController, 0, 0, sprites),
				new Floor3(gameController, 0, 0, sprites)
		};
		gameController.setVisualTypes(gameVisuals);
		
		LevelController levelController = gameController.getLevelController();
		
		boolean useEditor = false;
		if(useEditor) {
			levelController.useEditor();			
		} else {	
			gameController.playSound("sounds/reNovation.wav");
			levelController.loadLevel("menu.lvl");
			levelController.startGame();
		}
		
		GameWindow gameWindow = gameController.getGameWindow();
		gameWindow.constructWindow();
		gameWindow.getGraphicCanvas().setDefaultColor(new Color(55, 65, 35));
		gameController.startGame(fps, showDebug);
	}
}
