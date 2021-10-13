package gamelogic.nodes;

import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.NetworkIOException;
import gamelogic.Node;
import gamelogic.Output;
import gamelogic.Vec2D;

public abstract class OppositeValueNode<T> extends Node {

	private Class<T> dataClass;

	/**
	* @param dataClass the class object representing the type of the objects being added
	*/ 
	public OppositeValueNode(String name, Class<T> dataClass) {
		super(name);
		this.dataClass = dataClass;
		addInput(new Input("val", dataClass));
		addOutput(new Output("-val", dataClass));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {
		T val = getInput("val").getData(dataClass);
		getOutput("-val").setData(opposite(val));
	}

	/**
	* @param val1
	* @param val2
	* @return the sum of val1 and val2
	*/ 
	protected abstract T opposite(T val);


	// ========== CONCRETE SUBCLASSES ==========

	public static OppositeValueNode<Integer> buildOppIntNode() {
		return new OppositeValueNode<Integer>("Opposite : Int", Integer.class) {
			@Override
			protected Integer opposite(Integer val) {
				return -1 * val;
			}
		};
	}

	public static OppositeValueNode<Double> buildOppDoubleNode() {
		return new OppositeValueNode<Double>("Opposite : Double", Double.class) {
			@Override
			protected Double opposite(Double val) {
				return -1 * val;
			}
		};
	}

	public static OppositeValueNode<Vec2D> buildOppVecNode() {
		return new OppositeValueNode<Vec2D>("Opposite : Vector", Vec2D.class) {
			@Override
			protected Vec2D opposite(Vec2D val) {
				return Vec2D.multiply(-1, val);
			}
		};
	}

}

