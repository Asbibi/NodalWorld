package game;

import gamelogic.*;
import gamelogic.rules.*;
import gameinterface.NodalEditorBuilder;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import java.awt.BorderLayout;

public class TestNodalEditor extends JFrame {

	private GameManager game;

	public TestNodalEditor() {
		super();

		game = new GameManager(5, 5);

		game.getTerrain().addSlot();
		game.getTerrain().addSlot();
		game.getTerrain().addSlot();

		game.addSpecies(new Species("human", "res/Animal_Human"));
		game.addSpecies(new Species("birch", "res/Tree_Birch.png"));

		buildUI();
	}

	private void buildUI() {
		setTitle("Test Nodal Editor");
		setSize(900, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(NodalEditorBuilder.buildTabbedEditors(game));
	}

	public static void main(String[] args) {
		TestNodalEditor window = new TestNodalEditor();
		window.setVisible(true);
	}

}
