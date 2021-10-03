package gameinterface.nodaleditor;

import gamelogic.Node;
import gamelogic.nodes.*;

import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.AbstractAction;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.util.Collection;
import java.util.LinkedList;
import java.util.function.Supplier;

/**
*
*/ 
public class NodeMenu extends JPopupMenu {

	private int xInvoke, yInvoke;

	private Node newNode;

	private Collection<ActionListener> actionListeners;

	/**
	*
	*/ 
	public NodeMenu() {
		super("Add a node...");
		newNode = null;
		buildMenu();
		actionListeners = new LinkedList<ActionListener>();
	}


	// ========== Interface ==========

	private void buildMenu() {
		add(buildMenuRules());
		add(buildMenuRandom());
		add(buildMenuUtils());
		add(buildMenuOperations());
		add(buildMenuPredicates());
		add(buildMenuGame());
	}

	private JMenu buildMenuRules() {
		JMenu menuRules = new JMenu("Rules");
		menuRules.add(buildNodeItem(() -> new GenerateNode()));
		menuRules.add(buildNodeItem(() -> new MoveNode()));
		menuRules.add(buildNodeItem(() -> new KillNode()));
		return menuRules;
	}

	private JMenu buildMenuRandom() {
		JMenu menuRandom = new JMenu("Random");
		menuRandom.add(buildNodeItem(() -> new RandDoubleNode()));
		menuRandom.add(buildNodeItem(() -> new RandIntNode()));
		return menuRandom;	
	}

	private JMenu buildMenuUtils() {
		JMenu menuUtils = new JMenu("Utils");
		menuUtils.add(buildNodeItem(() -> new VecToIntsNode()));
		menuUtils.add(buildNodeItem(() -> new IntsToVecNode()));
		// TODO : constant value
		return menuUtils;
	}

	private JMenu buildMenuOperations() {
		JMenu menuOperations = new JMenu("Operations");
		menuOperations.add(buildNodeItem(() -> new EuclDivNode()));
		JMenu menuAdd = new JMenu("Add");
		menuOperations.add(menuAdd);
		menuAdd.add(buildNodeItem(() -> new IntAddNode()));
		menuAdd.add(buildNodeItem(() -> new DoubleAddNode()));
		menuAdd.add(buildNodeItem(() -> new VecAddNode()));
		JMenu menuSub = new JMenu("Sub");
		menuOperations.add(menuSub);
		menuSub.add(buildNodeItem(() -> new IntSubNode()));
		menuSub.add(buildNodeItem(() -> new DoubleSubNode()));
		menuSub.add(buildNodeItem(() -> new VecSubNode()));
		return menuOperations;
	}

	private JMenu buildMenuPredicates() {
		JMenu menuPredicates = new JMenu("Predicates");
		menuPredicates.add(buildNodeItem(() -> new NotNode()));
		menuPredicates.add(buildNodeItem(() -> new AndNode()));
		menuPredicates.add(buildNodeItem(() -> new OrNode()));
		// TODO : equals, compare, if-else
		return menuPredicates;
	}

	private JMenu buildMenuGame() {
		JMenu menuGame = new JMenu("Game");
		menuGame.add(buildNodeItem(() -> new TimeNode()));
		menuGame.add(buildNodeItem(() -> new DimNode()));
		menuGame.add(buildNodeItem(() -> new SurfaceAtNode()));
		menuGame.add(buildNodeItem(() -> new CurEntityNode()));
		menuGame.add(buildNodeItem(() -> new CurSpeciesNode()));
		return menuGame;
	}

	private JMenuItem buildNodeItem(Supplier<Node> supplier) {
		JMenuItem item = new JMenuItem(new AbstractAction(supplier.get().toString()) {
			@Override
			public void actionPerformed(ActionEvent e) {
				newNode = supplier.get();
				fireActionListeners();
			}
		});
		return item;
	}

	/**
	* @param itemName
	*/ 
	public void disable(String itemName) {
		// TODO
	}


	// ========== Position relative to invoker ==========

	/**
	* @param invoker
	* @param x
	* @param y
	*/ 
	@Override
	public void show(Component invoker, int x, int y) {
		super.show(invoker, x, y);
		xInvoke = x;
		yInvoke = y;
	}

	/**
	* @return x position of last invocation
	*/ 
	public int getXInvoke() { return xInvoke; }

	/**
	* @return y position of last invocation
	*/ 
	public int getYInvoke() { return yInvoke; }


	// ========== New Node ==========

	public Node getNewNode() { return newNode; }


	// ========== Action Listeners ==========

	public void addActionListener(ActionListener listener) { actionListeners.add(listener); }

	public void removeActionListener(ActionListener listener) { actionListeners.remove(listener); }

	public void fireActionListeners() {
		for(ActionListener listener : actionListeners) {
			listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "new node"));
		}
	}

}
