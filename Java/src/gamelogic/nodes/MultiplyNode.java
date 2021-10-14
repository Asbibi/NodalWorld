package gamelogic.nodes;

import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.NetworkIOException;
import gamelogic.Node;
import gamelogic.Output;
import gamelogic.Vec2D;

/**
* The abstract node model used to multiply two objects (concrete subclasses are instanciated by static methods). <br/>
* 
* Inputs : a, b <br/>
* Outputs : a*b
* 
*/
public abstract class MultiplyNode<T> extends Node {

	private Class<T> dataClass;

	/**
	* @param name 
	* @param dataClass
	*/ 
	public MultiplyNode(String name, Class<T> dataClass) {
		super(name);
		this.dataClass = dataClass;
		addInput(new Input("a", dataClass));
		addInput(new Input("b", dataClass));
		addOutput(new Output("a*b", dataClass));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {
		T val1 = getInput("a").getData(dataClass);
		T val2 = getInput("b").getData(dataClass);
		getOutput("a*b").setData(mul(val1 ,val2));
	}

	/**
	* @param val1
	* @param val2
	* @return the sum of val1 and val2
	*/ 
	protected abstract T mul(T val1, T val2);


	// ========== CONCRETE SUBCLASSES ==========

	/**
	* @return a node model used to multiply two integers
	*/ 
	public static MultiplyNode<Integer> buildMulIntNode() {
		return new MultiplyNode<Integer>("Multiply : Int", Integer.class) {
			@Override
			protected Integer mul(Integer val1, Integer val2) {
				return val1*val2;
			}
		};
	}

	/**
	* @return a node model used to multiply two doubles
	*/ 
	public static MultiplyNode<Double> buildMulDoubleNode() {
		return new MultiplyNode<Double>("Multiply : Double", Double.class) {
			@Override
			protected Double mul(Double val1, Double val2) {
				return val1*val2;
			}
		};
	}

}