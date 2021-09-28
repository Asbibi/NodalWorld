package gameinterface;

import gameinterface.nodaleditor.NodalEditorModel;
import gameinterface.nodaleditor.NodalEditorUI;
import gameinterface.nodaleditor.NodeView;

import gamelogic.GameManager;
import gamelogic.Network;
import gamelogic.Node;

import javax.swing.JComponent;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import java.awt.Graphics;
import java.awt.Graphics2D;

/**
* @see Network
* @see Node
* @see GameManager
*/ 
public class NodalEditor extends JComponent {

	private NodalEditorModel model;
	private NodalEditorUI ui;

	/**
	*
	*/ 
	public NodalEditor(GameManager game, Network network) {
		super();

		model = new NodalEditorModel(game, network);
		model.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				repaint();
			}
		});

		ui = new NodalEditorUI();
		ui.installUI(this);
	}

	/**
	* @param g the graphic context
	*/ 
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		ui.paint(g2d, this);
	}


	// ========== Calls forwarded to the model ==========

	// Network

	public Network getNetwork() { return model.getNetwork(); }

	public void addNode(Node node, int x, int y) { model.addNode(node, x, y); }

	// Node view

	public NodeView getNodeView(Node node) { return model.getNodeView(node); }

	// Change Listeners

	public void addChangeListener(ChangeListener listener) { model.addChangeListener(listener); }

	public void removeChangeListener(ChangeListener listener) { model.removeChangeListener(listener); }

}
