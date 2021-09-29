package gameinterface;

import gameinterface.nodaleditor.*;

import gamelogic.GameManager;
import gamelogic.Network;
import gamelogic.Node;
import gamelogic.Input;
import gamelogic.Output;

import javax.swing.JComponent;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.Graphics;
import java.awt.Graphics2D;

import java.util.Collection;

/**
* @see Network
* @see Node
* @see GameManager
*/ 
public class NodalEditor extends JComponent {

	private NodalEditorModel model;
	private NodeMenu nodeMenu;
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

		nodeMenu = new NodeMenu();
		nodeMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.addNode(nodeMenu.getNewNode(), nodeMenu.getXInvoke(), nodeMenu.getYInvoke());
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

	// Node Boxes

	public Collection<NodeBox> getBoxes() { return model.getBoxes(); }

	public Port getPort(Input input) { return model.getPort(input); }

	public Port getPort(Output output) { return model.getPort(output); }	

	// Interaction

	public void setEditingLink(boolean b) { model.setEditingLink(b); }

	public boolean isEditingLink() { return model.isEditingLink(); }

	public void setCursorPos(int x, int y) { model.setCursorPos(x, y); }

	public int getXCursor() { return model.getXCursor(); }

	public int getYCursor() { return model.getYCursor(); }

	public void setCurrentPort(Port port) { model.setCurrentPort(port); }

	public Port getCurrentPort() { return model.getCurrentPort(); }

	// Change Listeners

	public void addChangeListener(ChangeListener listener) { model.addChangeListener(listener); }

	public void removeChangeListener(ChangeListener listener) { model.removeChangeListener(listener); }


	// ========== Node Menu ==========

	public void showNodeMenu(int x, int y) { nodeMenu.show(this, x, y); }

}
