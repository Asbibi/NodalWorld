package gamelogic.nodes;

import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.NetworkIOException;
import gamelogic.Node;
import gamelogic.Output;
import gamelogic.Vec2D;

/**
* The abstract node model used to perform a power calculation. <br/>
* 
* Inputs : x, n <br/>
* Outputs : x^n
* 
*/
public abstract class PowNode<T> extends Node {

	private Class<T> dataClass;

	/**
	* @param name 
	* @param dataClass
	*/ 
	public PowNode(String name, Class<T> dataClass) {
		super(name);
		this.dataClass = dataClass;
		addInput(new Input("x", dataClass));
		Input nInput = new Input("n", Double.class);
		nInput.setManualValue(2.);
		addInput(nInput);
		addOutput(new Output("x^n", dataClass));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {
		getOutput("x^n").setData(pow(getInput("x").getData(dataClass), getInput("n").getData(Double.class)));
	}

	/**
	* @param val
	* @param rmin
	* @param rmax
	* @return the clamped value
	*/ 
	protected abstract T pow(T x, double n);


	// ========== CONCRETE SUBCLASSES ==========

	/**
	* @return a node model used to compute the power of an integer by a double, truncated to an integer
	*/ 
	public static PowNode<Integer> buildPowIntNode() {
		return new PowNode<Integer>("Pow : Int", Integer.class) {
			@Override
			protected Integer pow(Integer x, double n) {
				if (n < 0)
					return null;
				return (int)Math.pow(x, n);
			}
		};
	}

	/**
	* @return a node model used to compute the power of an double by another one
	*/ 
	public static PowNode<Double> buildPowDoubleNode() {
		return new PowNode<Double>("Pow : Double", Double.class) {
			@Override
			protected Double pow(Double x, double n) {
				if (n < 0)
					return null;
				return Math.pow(x, n);
			}
		};
	}

	/**
	* @return a node model used to compute the power of both components of a vector by a double, and then truncate the results to obtain a new vector
	*/ 
	public static PowNode<Vec2D> buildPowVecNode() {
		return new PowNode<Vec2D>("Pow : Vector", Vec2D.class) {
			@Override
			protected Vec2D pow(Vec2D vec, double n) {
				if (n < 0)
					return null;
				return new Vec2D((int)Math.pow(vec.getX(),n), (int)Math.pow(vec.getY(),n));
			}
		};
	}

}
