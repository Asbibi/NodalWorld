package gamelogic.nodes;

import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.NetworkIOException;
import gamelogic.Node;
import gamelogic.Output;
import gamelogic.Vec2D;

public abstract class DivVectorNode<T> extends Node {

	private Class<T> dataClass;

	/**
	* @param dataClass the class object representing the type of the objects being added
	*/ 
	public DivVectorNode(String name, Class<T> dataClass) {
		super(name);
		this.dataClass = dataClass;
		addInput(new Input("vec", Vec2D.class));
		addInput(new Input("a", dataClass));
		addOutput(new Output("vec/a", Vec2D.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {
		Vec2D vector = getInput("vec").getData(Vec2D.class);
		T scalar = getInput("a").getData(dataClass);
		getOutput("vec/a").setData(div(vector ,scalar));
	}

	/**
	* @param val1
	* @param val2
	* @return the sum of val1 and val2
	*/ 
	protected abstract Vec2D div(Vec2D vec, T a);


	// ========== CONCRETE SUBCLASSES ==========

	public static DivVectorNode<Integer> buildDivVecIntNode() {
		return new DivVectorNode<Integer>("Divide : Vect-Int", Integer.class) {
			@Override
			protected Vec2D div(Vec2D vec, Integer a) {
				return Vec2D.divide(a, vec);
			}
		};
	}

	public static DivVectorNode<Double> buildDivVecDoubleNode() {
		return new DivVectorNode<Double>("Divide : Vect-Double", Double.class) {
			@Override
			protected Vec2D div(Vec2D vec, Double a) {
				return Vec2D.divide(a, vec);
			}
		};
	}
}