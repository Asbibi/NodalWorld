package gamelogic.nodes;

import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.NetworkIOException;
import gamelogic.Node;
import gamelogic.Output;
import gamelogic.Vec2D;

/**
* The abstract node model used to compute the absolute value of a value (concrete subclasses are instanciated by static methods). <br/>
* 
* Inputs : a <br/>
* Outputs : |a|
* 
*/
public abstract class AbsNode<T> extends Node {

	private Class<T> dataClass;

	/**
	* @param name 
	* @param dataClass
	*/ 
	public AbsNode(String name, Class<T> dataClass) {
		super(name);
		this.dataClass = dataClass;
		addInput(new Input("a", dataClass));
		addOutput(new Output("|a|", dataClass));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {
		T val = getInput("a").getData(dataClass);
		getOutput("|a|").setData(abs(val));
	}

	/**
	* @param val
	* @param rmin
	* @param rmax
	* @return the clamped value
	*/ 
	protected abstract T abs(T val);


	// ========== CONCRETE SUBCLASSES ==========

	/**
	* @return a node model used to compute the absolute value of an integer
	*/ 
	public static AbsNode<Integer> buildAbsIntNode() {
		return new AbsNode<Integer>("Abs : Int", Integer.class) {
			@Override
			protected Integer abs(Integer val) {
				return Math.abs(val);
			}
		};
	}

	/**
	* @return a node model used to compute the absolute value of a double
	*/ 
	public static AbsNode<Double> buildAbsDoubleNode() {
		return new AbsNode<Double>("Abs : Double", Double.class) {
			@Override
			protected Double abs(Double val) {
				return Math.abs(val);
			}
		};
	}

	/**
	* @return a node model used to compute the absolute value on a vector component-wise
	*/ 
	public static AbsNode<Vec2D> buildAbsVecNode() {
		return new AbsNode<Vec2D>("Abs : Vector", Vec2D.class) {
			@Override
			protected Vec2D abs(Vec2D val) {
				return new Vec2D(Math.abs(val.getX()), Math.abs(val.getY()));
			}
		};
	}
}
