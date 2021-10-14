package gamelogic.nodes;

import gamelogic.Node;
import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.Output;
import gamelogic.Vec2D;
import gamelogic.NetworkIOException;

import java.lang.Class;

/**
* The abstract node model used to add two objects (concrete subclasses are instanciated by static methods). <br/>
* 
* Inputs : a, b <br/>
* Outputs : a+b
* 
*/
public abstract class AddNode<T> extends Node {

	private Class<T> dataClass;

	/**
	* @param name 
	* @param dataClass
	*/ 
	public AddNode(String name, Class<T> dataClass) {
		super(name);
		this.dataClass = dataClass;
		addInput(new Input("a", dataClass));
		addInput(new Input("b", dataClass));
		addOutput(new Output("a+b", dataClass));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {
		T val1 = getInput("a").getData(dataClass);
		T val2 = getInput("b").getData(dataClass);
		getOutput("a+b").setData(add(val1 ,val2));
	}

	/**
	* @param val1
	* @param val2
	* @return the sum of val1 and val2
	*/ 
	protected abstract T add(T val1, T val2);


	// ========== CONCRETE SUBCLASSES ==========

	/**
	* @return a node model used to add two integers
	*/ 
	public static AddNode<Integer> buildAddIntNode() {
		return new AddNode<Integer>("Add : Int", Integer.class) {
			@Override
			protected Integer add(Integer val1, Integer val2) {
				return val1+val2;
			}
		};
	}

	/**
	* @return a node model used to add two doubles
	*/ 
	public static AddNode<Double> buildAddDoubleNode() {
		return new AddNode<Double>("Add : Double", Double.class) {
			@Override
			protected Double add(Double val1, Double val2) {
				return val1+val2;
			}
		};
	}

	/**
	* @return a node model used to add two vectors
	*/ 
	public static AddNode<Vec2D> buildAddVecNode() {
		return new AddNode<Vec2D>("Add : Vector", Vec2D.class) {
			@Override
			protected Vec2D add(Vec2D val1, Vec2D val2) {
				return Vec2D.add(val1, val2);
			}
		};
	}

}
