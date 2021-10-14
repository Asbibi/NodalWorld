package gamelogic.nodes;

import gamelogic.Node;
import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.Output;
import gamelogic.NetworkIOException;

import java.lang.Class;

/**
* The abstract node model used to clamp a value in a specific range (concrete subclasses are instanciated by static methods). <br/>
* 
* Inputs : val, rmin, rmax <br/>
* Outputs : res
* 
*/
public abstract class ClampNode<T> extends Node {

	private Class<T> dataClass;

	/**
	* @param dataClass the class object representing the type of the objects being added
	*/ 
	public ClampNode(String name, Class<T> dataClass) {
		super(name);
		this.dataClass = dataClass;
		addInput(new Input("val", dataClass));
		addInput(new Input("rmin", dataClass));
		addInput(new Input("rmax", dataClass));
		addOutput(new Output("res", dataClass));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {
		T val = getInput("val").getData(dataClass);
		T rmin = getInput("rmin").getData(dataClass);
		T rmax = getInput("rmax").getData(dataClass);
		getOutput("res").setData(clamp(val, rmin, rmax));
	}

	/**
	* @param val
	* @param rmin
	* @param rmax
	* @return the clamped value
	*/ 
	protected abstract T clamp(T val, T rmin, T rmax);


	// ========== CONCRETE SUBCLASSES ==========

	/**
	* @return a node model used to clamp an integer between two integers
	*/ 
	public static ClampNode<Integer> buildClampIntNode() {
		return new ClampNode<Integer>("Clamp : Int", Integer.class) {
			@Override
			protected Integer clamp(Integer val, Integer rmin, Integer rmax) {
				if(rmin > rmax) return null;
				return Math.min(Math.max(val, rmin), rmax);
			}
		};
	}

	/**
	* @return a node model used to clamp an double between two doubles
	*/ 
	public static ClampNode<Double> buildClampDoubleNode() {
		return new ClampNode<Double>("Clamp : Double", Double.class) {
			@Override
			protected Double clamp(Double val, Double rmin, Double rmax) {
				if(rmin > rmax) return null;
				return Math.min(Math.max(val, rmin), rmax);
			}
		};
	}

}
