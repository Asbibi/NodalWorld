package gamelogic.nodes;

import gamelogic.Node;
import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.Output;
import gamelogic.NetworkIOException;

import java.lang.Class;

/**
* The abstract node model used to compute the min of two objects (concrete subclasses are instanciated by static methods). <br/>
* 
* Inputs : a, b <br/>
* Outputs : min(a,b)
* 
*/
public abstract class MinNode<T> extends Node {

	private Class<T> dataClass;

	/**
	* @param name 
	* @param dataClass
	*/ 
	public MinNode(String name, Class<T> dataClass) {
		super(name);
		this.dataClass = dataClass;
		addInput(new Input("a", dataClass));
		addInput(new Input("b", dataClass));
		addOutput(new Output("min(a,b)", dataClass));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {
		T val1 = getInput("a").getData(dataClass);
		T val2 = getInput("b").getData(dataClass);
		getOutput("min(a,b)").setData(min(val1 ,val2));
	}

	/**
	* @param val1
	* @param val2
	* @return the min of val1 and val2
	*/ 
	protected abstract T min(T val1, T val2);


	// ========== CONCRETE SUBCLASSES ==========

	/**
	* @return a node model used to compute the min of two integers
	*/ 
	public static MinNode<Integer> buildMinIntNode() {
		return new MinNode<Integer>("Min : Int", Integer.class) {
			@Override
			protected Integer min(Integer val1, Integer val2) {
				return Math.min(val1, val2);
			}
		};
	}

	/**
	* @return a node model used to compute the min of two doubles
	*/ 
	public static MinNode<Double> buildMinDoubleNode() {
		return new MinNode<Double>("Min : Double", Double.class) {
			@Override
			protected Double min(Double val1, Double val2) {
				return Math.min(val1,val2);
			}
		};
	}

}
