package gameinterface;

import gamelogic.GameManager;
import gamelogic.rules.*;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import java.awt.BorderLayout;

public class NodalEditorBuilder {

	public static JTabbedPane buildTabbedEditors(GameManager game) {
		JTabbedPane tabs = new JTabbedPane();

		tabs.add("Terrain", buildTerrainEditor(game));
		tabs.add("Generation", buildGenerationEditor(game));
		tabs.add("Movement", buildMovementEditor(game));
		tabs.add("Death", buildDeathEditor(game));

		return tabs;
	}

	public static JPanel buildTerrainEditor(GameManager game) {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		NodalEditor editor = new NodalEditor(game, game.getTerrainNet());
		editor.setTerrainCreator();

		disable(editor, new String[] {
			"Generation", "Movement", "Death", 
			"Current Species", "Current Entity", "Surface At"
		});

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

	public static JPanel buildGenerationEditor(GameManager game) {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		NodalEditor editor = new NodalEditor(game, game.getGenNet());
		editor.setRuleCreator(GenerationRule.class);

		disable(editor, new String[] {
			"Movement", "Death", 
			"Terrain Rectangle", 
			"Current Entity"
		});

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

	public static JPanel buildMovementEditor(GameManager game) {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		NodalEditor editor = new NodalEditor(game, game.getMoveNet());
		editor.setRuleCreator(MovementRule.class);

		disable(editor, new String[] {
			"Generation", "Death", 
			"Terrain Rectangle"
		});

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

	public static JPanel buildDeathEditor(GameManager game) {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		NodalEditor editor = new NodalEditor(game, game.getDeathNet());
		editor.setRuleCreator(DeathRule.class);

		disable(editor, new String[] {
			"Generation", "Movement", 
			"Terrain Rectangle"
		});

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

	private static void disable(NodalEditor editor, String[] nodeNames) {
		for(String nodeName : nodeNames) {
			editor.disable(nodeName);
		}
	}

}
