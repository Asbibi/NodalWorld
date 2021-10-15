package gameinterface;

import gamelogic.GameManager;
import gamelogic.rules.*;

import javax.swing.JComponent;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;


/**
* A class with only static methods, meant to build the standard node editors we use in the software.
*  
* @see NodalEditor
*/ 
public class NodalEditorBuilder {

	/**
	* @param game
	* @return a tabbed pane containing nodal editors for : terrain, generation, movement and death
	*/ 
	public static JTabbedPane buildTabbedEditors(GameManager game) {
		JTabbedPane tabs = new JTabbedPane();

		tabs.add("Terrain", buildTerrainEditor(game));
		tabs.add("Generation", buildGenerationEditor(game));
		tabs.add("Movement", buildMovementEditor(game));
		tabs.add("Death", buildDeathEditor(game));

		return tabs;
	}

	/**
	* @param game
	* @return nodal editor for the game's terrain network
	*/ 
	public static JComponent buildTerrainEditor(GameManager game) {
		NodalEditor editor = new NodalEditor(game, game.getTerrainNet());
		editor.setTerrainCreator();
		disable(editor, new String[] {
			"Generation", "Movement", "Death", 
			"Current Species", "Current Entity", "Surface At"
		});

		JScrollPane infoScroll = new JScrollPane(editor.getCurrentInfoPanel());
		editor.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				infoScroll.setViewportView(editor.getCurrentInfoPanel());
			}
		});

		JSplitPane panel = new JSplitPane(JSplitPane.VERTICAL_SPLIT, editor, infoScroll);
		panel.setResizeWeight(0.75);

		return panel;
	}

	/**
	* @param game
	* @return nodal editor for the game's generation network
	*/ 
	public static JComponent buildGenerationEditor(GameManager game) {
		NodalEditor editor = new NodalEditor(game, game.getGenNet());
		editor.setRuleCreator(GenerationRule.class);
		disable(editor, new String[] {
			"Movement", "Death", 
			"Terrain Unit", "Terrain Rectangle", "Terrain Ellipse", "Masked Terrain", 
			"Current Entity"
		});

		JScrollPane infoScroll = new JScrollPane(editor.getCurrentInfoPanel());
		editor.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				infoScroll.setViewportView(editor.getCurrentInfoPanel());
			}
		});

		JSplitPane panel = new JSplitPane(JSplitPane.VERTICAL_SPLIT, editor, infoScroll);
		panel.setResizeWeight(0.75);

		return panel;
	}

	/**
	* @param game
	* @return nodal editor for the game's movement network
	*/ 
	public static JComponent buildMovementEditor(GameManager game) {
		NodalEditor editor = new NodalEditor(game, game.getMoveNet());
		editor.setRuleCreator(MovementRule.class);
		disable(editor, new String[] {
			"Generation", "Death", 
			"Terrain Unit", "Terrain Rectangle", "Terrain Ellipse", "Masked Terrain"
		});

		JScrollPane infoScroll = new JScrollPane(editor.getCurrentInfoPanel());
		editor.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				infoScroll.setViewportView(editor.getCurrentInfoPanel());
			}
		});

		JSplitPane panel = new JSplitPane(JSplitPane.VERTICAL_SPLIT, editor, infoScroll);
		panel.setResizeWeight(0.75);

		return panel;
	}

	/**
	* @param game
	* @return nodal editor for the game's death network
	*/ 
	public static JComponent buildDeathEditor(GameManager game) {
		NodalEditor editor = new NodalEditor(game, game.getDeathNet());
		editor.setRuleCreator(DeathRule.class);
		disable(editor, new String[] {
			"Generation", "Movement", 
			"Terrain Unit", "Terrain Rectangle", "Terrain Ellipse", "Masked Terrain"
		});

		JScrollPane infoScroll = new JScrollPane(editor.getCurrentInfoPanel());
		editor.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				infoScroll.setViewportView(editor.getCurrentInfoPanel());
			}
		});

		JSplitPane panel = new JSplitPane(JSplitPane.VERTICAL_SPLIT, editor, infoScroll);
		panel.setResizeWeight(0.75);

		return panel;
	}

	private static void disable(NodalEditor editor, String[] nodeNames) {
		for(String nodeName : nodeNames) {
			editor.disable(nodeName);
		}
	}

}
