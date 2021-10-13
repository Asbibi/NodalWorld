package gamelogic.nodes;

import gamelogic.Node;
import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.Output;
import gamelogic.NetworkIOException;

import java.lang.Class;

/**
* The node model used to compute the min of two objects. <br/>
* 
* Inputs : val1, val2 <br/>
* Outputs : res
* 
* @see GameManager
*/
public abstract class MinNode<T> extends Node {

	private Class<T> dataClass;

	/**
	* @param dataClass the class object representing the type of the objects being added
	*/ 
	public MinNode(String name, Class<T> dataClass) {
		super(name);
		this.dataClass = dataClass;
		addInput(new Input("val1", dataClass));
		addInput(new Input("val2", dataClass));
		addOutput(new Output("res", dataClass));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {
		T val1 = getInput("val1").getData(dataClass);
		T val2 = getInput("val2").getData(dataClass);
		getOutput("res").setData(min(val1 ,val2));
	}

	/**
	* @param val1
	* @param val2
	* @return the min of val1 and val2
	*/ 
	protected abstract T min(T val1, T val2);


	// ========== CONCRETE SUBCLASSES ==========

	public static MinNode<Integer> buildMinIntNode() {
		return new MinNode<Integer>("Min : Int", Integer.class) {
			@Override
			protected Integer min(Integer val1, Integer val2) {
				return Math.min(val1, val2);
			}
		};
	}

	public static MinNode<Double> buildMinDoubleNode() {
		return new MinNode<Double>("Min : Double", Double.class) {
			@Override
			protected Double min(Double val1, Double val2) {
				return Math.min(val1,val2);
			}
		};
	}

}
