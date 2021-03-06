package gameinterface.nodaleditor;

import gamelogic.Entity;
import gamelogic.Node;
import gamelogic.Species;
import gamelogic.Surface;
import gamelogic.TerrainModel;
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
		add(buildMenuVariables());
		add(buildMenuOperations());
		add(buildMenuUtils());
		add(buildMenuPredicates());
		add(buildMenuRandom());
		add(buildMenuTrigo());
		add(buildMenuGame());
		add(buildMenuPrinters());
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
		JMenu menuPrimitiveTerrain = new JMenu("Primitives");
		menuPrimitiveTerrain.add(buildNodeItem(() -> new TerrainNodeUnit()));
		menuPrimitiveTerrain.add(buildNodeItem(() -> new TerrainNodeFill()));
		menuPrimitiveTerrain.add(buildNodeItem(() -> new TerrainNodeRectangle()));
		menuPrimitiveTerrain.add(buildNodeItem(() -> new TerrainNodeEllipse()));
		menuPrimitiveTerrain.add(buildNodeItem(() -> new TerrainNodeRandom()));
		JMenu menuModifierTerrain = new JMenu("Modifiers");
		menuModifierTerrain.add(buildNodeItem(() -> new TerrainNodeIntersect()));
		menuModifierTerrain.add(buildNodeItem(() -> new TerrainNodeUnion()));
		menuModifierTerrain.add(buildNodeItem(() -> new TerrainNodeMask()));
		menuModifierTerrain.add(buildNodeItem(() -> new TerrainNodeInvert()));
		menuModifierTerrain.add(buildNodeItem(() -> new TerrainNodeErode()));
		menuModifierTerrain.add(buildNodeItem(() -> new TerrainNodeExpand()));
		menuTerrain.add(menuPrimitiveTerrain);
		menuTerrain.add(menuModifierTerrain);
		return menuTerrain;
	}

	private JMenu buildMenuRandom() {
		JMenu menuRandom = new JMenu("Random");
		menuRandom.add(buildNodeItem(() -> new RandBoolNode()));		
		menuRandom.add(buildNodeItem(() -> new RandDoubleNode()));
		menuRandom.add(buildNodeItem(() -> new RandIntNode()));
		menuRandom.add(buildNodeItem(() -> new RandGaussDoubleNode()));
		return menuRandom;	
	}
	
	private JMenu buildMenuTrigo() {
		JMenu menuTrigo = new JMenu("Trigonometry");
		menuTrigo.add(buildNodeItem(() -> new TrigoPINode()));
		menuTrigo.add(buildNodeItem(() -> new TrigoCosNode()));
		menuTrigo.add(buildNodeItem(() -> new TrigoSinNode()));
		menuTrigo.add(buildNodeItem(() -> new TrigoAngleVectorsNode()));
		menuTrigo.add(buildNodeItem(() -> new TrigoRadialToVector()));
		menuTrigo.add(buildNodeItem(() -> new TrigoConvertRadDegNode()));
		menuTrigo.add(buildNodeItem(() -> new TrigoConvertDegRadNode()));
		return menuTrigo;	
	}
	
	private JMenu buildMenuVariables() {
		JMenu menuVariable = new JMenu("Variable");
		menuVariable.add(buildNodeItem(() -> new VariableNode<Boolean>("Variable : Bool", Boolean.class)));
		menuVariable.add(buildNodeItem(() -> new VariableNode<Integer>("Variable : Int", Integer.class)));
		menuVariable.add(buildNodeItem(() -> new VariableNode<Double>("Variable : Double", Double.class)));
		menuVariable.add(buildNodeItem(() -> new VectGatherNode()));
		menuVariable.add(buildNodeItem(() -> new VariableNode<Surface>("Variable : Surface", Surface.class)));
		menuVariable.add(buildNodeItem(() -> new VariableNode<Species>("Variable : Species", Species.class)));
		return menuVariable;
	}
	
	private JMenu buildMenuUtils() {
		JMenu menuUtils = new JMenu("Utils");
		JMenu menuConversion = new JMenu("Convert");
		menuUtils.add(menuConversion);
		menuConversion.add(buildNodeItem(() -> new ConvertDoubleIntNode()));
		menuConversion.add(buildNodeItem(() -> new ConvertDoubleIntApproxNode()));
		menuConversion.add(buildNodeItem(() -> new ConvertIntDoubleNode()));
		JMenu menuVector = new JMenu("Vector");
		menuUtils.add(menuVector);
		menuVector.add(buildNodeItem(() -> new VectSplitNode()));
		menuVector.add(buildNodeItem(() -> new VectNormNode()));
		menuVector.add(buildNodeItem(() -> new VectScalarProductNode()));
		menuVector.add(buildNodeItem(() -> new VectVectorialProductNode()));
		//--------
		menuUtils.add(buildNodeItem(() -> new OneMinusNode()));
		menuUtils.add(buildNodeItem(() -> new SpeciesNode()));
		menuUtils.add(buildNodeItem(() -> new EntityNode()));
		return menuUtils;
	}

	private JMenu buildMenuOperations() {
		JMenu menuOperations = new JMenu("Operations");
		JMenu menuAdd = new JMenu("Add");
		menuOperations.add(menuAdd);
		menuAdd.add(buildNodeItem(() -> AddNode.buildAddIntNode()));
		menuAdd.add(buildNodeItem(() -> AddNode.buildAddDoubleNode()));
		menuAdd.add(buildNodeItem(() -> AddNode.buildAddVecNode()));
		JMenu menuSub = new JMenu("Sub");
		menuOperations.add(menuSub);
		menuSub.add(buildNodeItem(() -> SubNode.buildSubIntNode()));
		menuSub.add(buildNodeItem(() -> SubNode.buildSubDoubleNode()));
		menuSub.add(buildNodeItem(() -> SubNode.buildSubVecNode()));
		JMenu menuMul = new JMenu("Mul");
		menuOperations.add(menuMul);
		menuMul.add(buildNodeItem(() -> MultiplyNode.buildMulIntNode()));
		menuMul.add(buildNodeItem(() -> MultiplyNode.buildMulDoubleNode()));
		menuMul.add(buildNodeItem(() -> new MultiplyIntDoubleNode()));		
		menuMul.add(buildNodeItem(() -> MultiplyVectorNode.buildMulVecIntNode()));
		menuMul.add(buildNodeItem(() -> MultiplyVectorNode.buildMulVecDoubleNode()));
		JMenu menuDiv = new JMenu("Div");
		menuOperations.add(menuDiv);
		menuDiv.add(buildNodeItem(() -> new DivEuclNode()));
		menuDiv.add(buildNodeItem(() -> new DivIntNode()));
		menuDiv.add(buildNodeItem(() -> new DivDoubleNode()));
		menuDiv.add(buildNodeItem(() -> DivVectorNode.buildDivVecIntNode()));
		menuDiv.add(buildNodeItem(() -> DivVectorNode.buildDivVecDoubleNode()));
		JMenu menuPow = new JMenu("Pow");
		menuOperations.add(menuPow);
		menuPow.add(buildNodeItem(() -> PowNode.buildPowIntNode()));
		menuPow.add(buildNodeItem(() -> PowNode.buildPowDoubleNode()));
		menuPow.add(buildNodeItem(() -> PowNode.buildPowVecNode()));
		JMenu menuAbs = new JMenu("Abs");
		menuOperations.add(menuAbs);
		menuAbs.add(buildNodeItem(() -> AbsNode.buildAbsIntNode()));
		menuAbs.add(buildNodeItem(() -> AbsNode.buildAbsDoubleNode()));
		menuAbs.add(buildNodeItem(() -> AbsNode.buildAbsVecNode()));
		JMenu menuOpposite = new JMenu("Opp");
		menuOperations.add(menuOpposite);
		menuOpposite.add(buildNodeItem(() -> OppositeValueNode.buildOppIntNode()));
		menuOpposite.add(buildNodeItem(() -> OppositeValueNode.buildOppDoubleNode()));
		menuOpposite.add(buildNodeItem(() -> OppositeValueNode.buildOppVecNode()));
		JMenu menuMax = new JMenu("Max");
		menuOperations.add(menuMax);
		menuMax.add(buildNodeItem(() -> MaxNode.buildMaxIntNode()));
		menuMax.add(buildNodeItem(() -> MaxNode.buildMaxDoubleNode()));
		JMenu menuMin = new JMenu("Min");
		menuOperations.add(menuMin);
		menuMin.add(buildNodeItem(() -> MinNode.buildMinIntNode()));
		menuMin.add(buildNodeItem(() -> MinNode.buildMinDoubleNode()));
		JMenu menuClamp = new JMenu("Clamp");
		menuOperations.add(menuClamp);
		menuClamp.add(buildNodeItem(() -> ClampNode.buildClampIntNode()));
		menuClamp.add(buildNodeItem(() -> ClampNode.buildClampDoubleNode()));
		return menuOperations;
	}

	private JMenu buildMenuPredicates() {
		JMenu menuPredicates = new JMenu("Predicates");
		JMenu menuOperation = new JMenu("Bool Operations");
		menuPredicates.add(menuOperation);
		menuOperation.add(buildNodeItem(() -> new NotNode()));
		menuOperation.add(buildNodeItem(() -> new AndNode()));
		menuOperation.add(buildNodeItem(() -> new OrNode()));
		menuOperation.add(buildNodeItem(() -> new XorNode()));
		JMenu menuEqual = new JMenu("Equal");
		menuPredicates.add(menuEqual);
		menuEqual.add(buildNodeItem(() -> new EqualNode<Integer>("Equal : Int", Integer.class)));
		menuEqual.add(buildNodeItem(() -> new EqualNode<Double>("Equal : Double", Double.class)));
		menuEqual.add(buildNodeItem(() -> new EqualNode<Vec2D>("Equal : Vector", Vec2D.class)));
		menuEqual.add(buildNodeItem(() -> new EqualNode<Surface>("Equal : Surface", Surface.class)));
		menuEqual.add(buildNodeItem(() -> new EqualNode<Species>("Equal : Species", Species.class)));
		JMenu menuCompare = new JMenu("Compare");
		menuPredicates.add(menuCompare);
		menuCompare.add(buildNodeItem(() -> new CompareNode<Integer>("Compare : Int", Integer.class)));
		menuCompare.add(buildNodeItem(() -> new CompareNode<Double>("Compare : Double", Double.class)));
		JMenu menuIfElse = new JMenu("If-Else");
		menuPredicates.add(menuIfElse);
		menuIfElse.add(buildNodeItem(() -> new IfElseNode<Integer>("If-Else : Int", Integer.class)));
		menuIfElse.add(buildNodeItem(() -> new IfElseNode<Double>("If-Else : Double", Double.class)));
		menuIfElse.add(buildNodeItem(() -> new IfElseNode<Vec2D>("If-Else : Vector", Vec2D.class)));
		menuIfElse.add(buildNodeItem(() -> new IfElseNode<Surface>("If-Else : Surface", Surface.class)));
		menuIfElse.add(buildNodeItem(() -> new IfElseNode<Species>("If-Else : Species", Species.class)));
		return menuPredicates;
	}

	private JMenu buildMenuGame() {
		JMenu menuGame = new JMenu("Game");
		menuGame.add(buildNodeItem(() -> new TimeNode()));
		menuGame.add(buildNodeItem(() -> new DimNode()));
		menuGame.add(buildNodeItem(() -> new OutsideNode()));
		menuGame.add(buildNodeItem(() -> new SurfaceAtNode()));
		menuGame.add(buildNodeItem(() -> new CurSpeciesNode()));
		menuGame.add(buildNodeItem(() -> new CurEntityNode()));
		menuGame.add(buildNodeItem(() -> new SpeciesInAreaNode()));
		menuGame.add(buildNodeItem(() -> new EntityInAreaNode()));
		return menuGame;
	}
	
	private JMenu buildMenuPrinters() {
		JMenu menuPrinters = new JMenu("Printers");
		menuPrinters.add(buildNodeItem(() -> new PrintNode<Boolean>	("Print : Boolean", Boolean.class)));
		menuPrinters.add(buildNodeItem(() -> new PrintNode<Integer>	("Print : Int", Integer.class)));
		menuPrinters.add(buildNodeItem(() -> new PrintNode<Double>	("Print : Double", Double.class)));
		menuPrinters.add(buildNodeItem(() -> new PrintNode<Vec2D>	("Print : Vector", Vec2D.class)));
		menuPrinters.add(buildNodeItem(() -> new PrintNode<Surface>	("Print : Surface", Surface.class)));
		menuPrinters.add(buildNodeItem(() -> new PrintNode<Species>	("Print : Species", Species.class)));
		menuPrinters.add(buildNodeItem(() -> new PrintNode<Entity>	("Print : Entity", Entity.class)));
		menuPrinters.add(buildNodeItem(() -> new PrintNode<TerrainModel> ("Print : Terrain Layer", TerrainModel.class)));
		return menuPrinters;
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
	* Notify the listeners that a new node has been created
	*/ 
	public void fireActionListeners() {
		for(ActionListener listener : actionListeners) {
			listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "new node"));
		}
	}

}
