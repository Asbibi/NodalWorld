package game;

import gamelogic.*;
import gamelogic.rules.*;
import gameinterface.NodalEditor;

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
		setContentPane(buildContentPane());
	}

	private JTabbedPane buildContentPane() {
		JTabbedPane tabs = new JTabbedPane();

		tabs.add("Terrain", buildTerrainEditor());
		tabs.add("Generation", buildGenerationEditor());
		tabs.add("Movement", buildMovementEditor());
		tabs.add("Death", buildDeathEditor());

		return tabs;
	}

	private JPanel buildTerrainEditor() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		NodalEditor editor = new NodalEditor(game, game.getTerrainNet());
		editor.setTerrainCreator();
		editor.disable("Generation");
		editor.disable("Movement");
		editor.disable("Death");
		editor.disable("Current Species");
		editor.disable("Current Entity");
		panel.add(editor, BorderLayout.CENTER);

		JScrollPane infoScroll = new JScrollPane(editor.getCurrentInfoPanel());
		editor.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				infoScroll.setViewportView(editor.getCurrentInfoPanel());
			}
		});
		panel.add(infoScroll, BorderLayout.SOUTH);

		return panel;
	}

	private JPanel buildGenerationEditor() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		NodalEditor editor = new NodalEditor(game, game.getGenNet());
		editor.setRuleCreator(GenerationRule.class);
		editor.disable("Movement");
		editor.disable("Death");
		editor.disable("Current Entity");
		panel.add(editor, BorderLayout.CENTER);

		JScrollPane infoScroll = new JScrollPane(editor.getCurrentInfoPanel());
		editor.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				infoScroll.setViewportView(editor.getCurrentInfoPanel());
			}
		});
		panel.add(infoScroll, BorderLayout.SOUTH);

		return panel;
	}

	private JPanel buildMovementEditor() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		NodalEditor editor = new NodalEditor(game, game.getMoveNet());
		editor.setRuleCreator(MovementRule.class);
		editor.disable("Generation");
		editor.disable("Death");
		panel.add(editor, BorderLayout.CENTER);

		JScrollPane infoScroll = new JScrollPane(editor.getCurrentInfoPanel());
		editor.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				infoScroll.setViewportView(editor.getCurrentInfoPanel());
			}
		});
		panel.add(infoScroll, BorderLayout.SOUTH);

		return panel;
	}

	private JPanel buildDeathEditor() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		NodalEditor editor = new NodalEditor(game, game.getDeathNet());
		editor.setRuleCreator(DeathRule.class);
		editor.disable("Generation");
		editor.disable("Movement");
		panel.add(editor, BorderLayout.CENTER);

		JScrollPane infoScroll = new JScrollPane(editor.getCurrentInfoPanel());
		editor.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				infoScroll.setViewportView(editor.getCurrentInfoPanel());
			}
		});
		panel.add(infoScroll, BorderLayout.SOUTH);

		return panel;
	}

	public static void main(String[] args) {
		TestNodalEditor window = new TestNodalEditor();
		window.setVisible(true);
	}

}
