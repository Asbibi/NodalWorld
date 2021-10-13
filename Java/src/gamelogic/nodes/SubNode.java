package gamelogic.nodes;

import gamelogic.Node;
import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.Output;
import gamelogic.Vec2D;
import gamelogic.NetworkIOException;

import java.lang.Class;

/**
* The node model used to substract two objects. <br/>
* 
* Inputs : val1, val2 <br/>
* Outputs : res
* 
* @see GameManager
*/
public abstract class SubNode<T> extends Node {

	private Class<T> dataClass;

	/**
	* @param dataClass the class object representing the type of the objects being substracted
	*/ 
	public SubNode(String name, Class<T> dataClass) {
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
		getOutput("res").setData(sub(val1, val2));
	}

	/**
	* @param val1
	* @param val2
	* @return the difference between val1 and val2
	*/ 
	abstract T sub(T val1, T val2); 


	// ========== CONCRETE SUBCLASSES ==========

	public static SubNode<Integer> buildSubIntNode() {
		return new SubNode<Integer>("Sub : Int", Integer.class) {
			@Override
			protected Integer sub(Integer val1, Integer val2) {
				return val1-val2;
			}
		};
	}

	public static SubNode<Double> buildSubDoubleNode() {
		return new SubNode<Double>("Sub : Double", Double.class) {
			@Override
			protected Double sub(Double val1, Double val2) {
				return val1-val2;
			}
		};
	}

	public static SubNode<Vec2D> buildSubVecNode() {
		return new SubNode<Vec2D>("Sub : Vec", Vec2D.class) {
			@Override
			protected Vec2D sub(Vec2D val1, Vec2D val2) {
				return Vec2D.sub(val1, val2);
			}
		};
	}

}
