package game;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;

import gameinterface.GameFrame;
import gamelogic.TerrainLayer;
import gamelogic.rules.GenerationRule;

public class TestInterfaceUpdating {
	
	public static GameFrame gameFrame;
	public static TestGameLogic test;
	
	public static void Update() {
		int frame = test.getGame().evolveGameState();
		System.out.println(test.getGame());
		gameFrame.update(frame);
	}
	
	public static void main(String[] args) {
		int width, height;
		width = 10;
		height = 10;

		test = new TestGameLogic(width, height);

		test.getGame().addSurface(test.getSoil());
		TerrainLayer ground = new TerrainLayer(test.getGame().gridWidth(), test.getGame().gridHeight());
		ground.fill(test.getSoil());
		test.getGame().pushTerrain(ground);

		test.getGame().addSpecies(test.getHumans());

		GenerationRule rule = test.makeGenRandomOnSoilRule();
		test.getGame().connectRuleToSpecies(rule, test.getHumans());

		System.out.println(test.getGame());
		
		gameFrame = new GameFrame(test.getGame());
		gameFrame.setVisible(true);
		
		JFrame debugFrame = new JFrame("Debug");
		//debugFrame.setPreferredSize(new Dimension(200, 100));
		debugFrame.setLayout(new BorderLayout());
		JButton button = new JButton("Debug");
		button.addActionListener( e -> Update());
		debugFrame.add(button, BorderLayout.CENTER);
		debugFrame.pack();
		debugFrame.setVisible(true);
		debugFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
