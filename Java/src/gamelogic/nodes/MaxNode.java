package gamelogic.nodes;

import gamelogic.Node;
import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.Output;
import gamelogic.NetworkIOException;

import java.lang.Class;

/**
* The node model used to compute the max of two objects. <br/>
* 
* Inputs : val1, val2 <br/>
* Outputs : res
* 
* @see GameManager
*/
public abstract class MaxNode<T> extends Node {

	private Class<T> dataClass;

	/**
	* @param dataClass the class object representing the type of the objects being added
	*/ 
	public MaxNode(String name, Class<T> dataClass) {
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
		getOutput("res").setData(max(val1 ,val2));
	}

	/**
	* @param val1
	* @param val2
	* @return the max of val1 and val2
	*/ 
	protected abstract T max(T val1, T val2);


	// ========== CONCRETE SUBCLASSES ==========

	public static MaxNode<Integer> buildMaxIntNode() {
		return new MaxNode<Integer>("Max : Int", Integer.class) {
			@Override
			protected Integer max(Integer val1, Integer val2) {
				return Math.max(val1, val2);
			}
		};
	}

	public static MaxNode<Double> buildMaxDoubleNode() {
		return new MaxNode<Double>("Max : Double", Double.class) {
			@Override
			protected Double max(Double val1, Double val2) {
				return Math.max(val1,val2);
			}
		};
	}

}
