package gameinterface.nodaleditor;

import gamelogic.Node;
import gamelogic.Surface;
import gamelogic.Vec2D;
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
import java.util.Map;
import java.util.HashMap;

import java.util.function.Supplier;

/**
* Free floating menu that allows the user to choose new nodes to add in the nodal editor
* 
* @see Node
*/ 
public class NodeMenu extends JPopupMenu {

	private int xInvoke, yInvoke;
	private Map<String, AbstractAction> actions;
	private Node newNode;

	private Collection<ActionListener> actionListeners;

	/**
	*
	*/ 
	public NodeMenu() {
		super("Add a node...");
		actions = new HashMap<String, AbstractAction>();
		newNode = null;
		buildMenu();
		actionListeners = new LinkedList<ActionListener>();
	}


	// ========== Interface ==========

	private void buildMenu() {
		add(buildMenuRules());
		add(buildMenuTerrain());
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

	private JMenu buildMenuTerrain() {
		JMenu menuTerrain = new JMenu("Terrain");
		menuTerrain.add(buildNodeItem(() -> new TerrainNodeRectangle()));
		return menuTerrain;
	}

	private JMenu buildMenuRandom() {
		JMenu menuRandom = new JMenu("Random");
		menuRandom.add(buildNodeItem(() -> new RandDoubleNode()));
		menuRandom.add(buildNodeItem(() -> new RandIntNode()));
		return menuRandom;	
	}

	private JMenu buildMenuUtils() {
		JMenu menuUtils = new JMenu("Utils");
		JMenu menuVariable = new JMenu("Variable");
		menuUtils.add(menuVariable);
		menuVariable.add(buildNodeItem(() -> new VariableNode<Boolean>("Variable : Bool", Boolean.class)));
		menuVariable.add(buildNodeItem(() -> new VariableNode<Integer>("Variable : Int", Integer.class)));
		menuVariable.add(buildNodeItem(() -> new VariableNode<Double>("Variable : Double", Double.class)));
		menuVariable.add(buildNodeItem(() -> new VariableNode<Surface>("Variable : Surface", Surface.class)));
		menuUtils.add(buildNodeItem(() -> new VecToIntsNode()));
		menuUtils.add(buildNodeItem(() -> new IntsToVecNode()));
		menuUtils.add(buildNodeItem(() -> new SpeciesNode()));
		menuUtils.add(buildNodeItem(() -> new EntityNode()));
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
		JMenu menuEqual = new JMenu("Equal");
		menuPredicates.add(menuEqual);
		menuEqual.add(buildNodeItem(() -> new EqualNode<Integer>("Equal : Int", Integer.class)));
		menuEqual.add(buildNodeItem(() -> new EqualNode<Vec2D>("Equal : Vec", Vec2D.class)));
		menuEqual.add(buildNodeItem(() -> new EqualNode<Surface>("Equal : Surface", Surface.class)));
		JMenu menuCompare = new JMenu("Compare");
		menuPredicates.add(menuCompare);
		menuCompare.add(buildNodeItem(() -> new CompareNode<Integer>("Compare : Int", Integer.class)));
		menuCompare.add(buildNodeItem(() -> new CompareNode<Double>("Compare : Double", Double.class)));
		JMenu menuIfElse = new JMenu("If-Else");
		menuPredicates.add(menuIfElse);
		menuIfElse.add(buildNodeItem(() -> new IfElseNode<Integer>("If-Else : Int", Integer.class)));
		menuIfElse.add(buildNodeItem(() -> new IfElseNode<Vec2D>("If-Else : Vec", Vec2D.class)));
		menuIfElse.add(buildNodeItem(() -> new IfElseNode<Double>("If-Else : Double", Double.class)));
		menuIfElse.add(buildNodeItem(() -> new IfElseNode<Surface>("If-Else : Surface", Surface.class)));
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
		Node node = supplier.get();
		actions.put(node.toString(), new AbstractAction(node.toString()) {
			@Override
			public void actionPerformed(ActionEvent e) {
				newNode = supplier.get();
				fireActionListeners();
			}
		});
		JMenuItem item = new JMenuItem(actions.get(node.toString()));
		return item;
	}

	/**
	* @param nodeName
	*/ 
	public void disable(String nodeName) {
		AbstractAction action = actions.get(nodeName);
		if(action != null) {
			action.setEnabled(false);
		}
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

	/**
	* @return the newly created node (if there's one)
	*/ 
	public Node getNewNode() { return newNode; }


	// ========== Action Listeners ==========

	/**
	* @param listener
	*/ 
	public void addActionListener(ActionListener listener) { actionListeners.add(listener); }

	/**
	* @param listener
	*/ 
	public void removeActionListener(ActionListener listener) { actionListeners.remove(listener); }

	/**
	*
	*/ 
	public void fireActionListeners() {
		for(ActionListener listener : actionListeners) {
			listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "new node"));
		}
	}

}
