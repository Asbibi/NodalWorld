package gamelogic.nodes;

import gamelogic.Node;
import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.Output;
import gamelogic.Vec2D;
import gamelogic.NetworkIOException;

import java.lang.Class;

/**
* The abstract node model used to substract two objects (concrete subclasses are instanciated by static methods). <br/>
* 
* Inputs : a, b <br/>
* Outputs : a-b
* 
*/
public abstract class SubNode<T> extends Node {

	private Class<T> dataClass;

	/**
	* @param name 
	* @param dataClass
	*/ 
	public SubNode(String name, Class<T> dataClass) {
		super(name);
		this.dataClass = dataClass;
		addInput(new Input("a", dataClass));
		addInput(new Input("b", dataClass));
		addOutput(new Output("a-b", dataClass));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {
		T val1 = getInput("a").getData(dataClass);
		T val2 = getInput("b").getData(dataClass);
		getOutput("a-b").setData(sub(val1, val2));
	}

	/**
	* @param val1
	* @param val2
	* @return the difference between val1 and val2
	*/ 
	abstract T sub(T val1, T val2); 


	// ========== CONCRETE SUBCLASSES ==========

	/**
	* @return a node model used to substract two integers
	*/ 
	public static SubNode<Integer> buildSubIntNode() {
		return new SubNode<Integer>("Sub : Int", Integer.class) {
			@Override
			protected Integer sub(Integer val1, Integer val2) {
				return val1-val2;
			}
		};
	}

	/**
	* @return a node model used to substract two doubles
	*/ 
	public static SubNode<Double> buildSubDoubleNode() {
		return new SubNode<Double>("Sub : Double", Double.class) {
			@Override
			protected Double sub(Double val1, Double val2) {
				return val1-val2;
			}
		};
	}

	/**
	* @return a nodel model used to substract two vectors
	*/  
	public static SubNode<Vec2D> buildSubVecNode() {
		return new SubNode<Vec2D>("Sub : Vector", Vec2D.class) {
			@Override
			protected Vec2D sub(Vec2D val1, Vec2D val2) {
				return Vec2D.sub(val1, val2);
			}
		};
	}

}
