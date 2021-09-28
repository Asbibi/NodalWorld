package game;

import gamelogic.GameManager;
import gameinterface.NodalEditor;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;

public class TestNodalEditor extends JFrame {

	private GameManager game;

	public TestNodalEditor() {
		super();
		game = new GameManager(5, 5);
		buildUI();
	}

	private void buildUI() {
		setTitle("Test Nodal Editor");
		setSize(900, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(buildContentPane());
	}

	private JPanel buildContentPane() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(new NodalEditor(game, game.getGenNet()), BorderLayout.CENTER);
		return panel;
	}

	public static void main(String[] args) {
		TestNodalEditor window = new TestNodalEditor();
		window.setVisible(true);
	}

}
