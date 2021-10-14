package gamelogic.nodes;

import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.NetworkIOException;
import gamelogic.Node;
import gamelogic.Output;
import gamelogic.Vec2D;

/**
* The abstract node model used to divide a vector with a value (concrete subclasses are instanciated by static methods). <br/>
* 
* Inputs : vec, b <br/>
* Outputs : vec/b
* 
*/
public abstract class DivVectorNode<T> extends Node {

	private Class<T> dataClass;

	/**
	* @param name 
	* @param dataClass
	*/ 
	public DivVectorNode(String name, Class<T> dataClass) {
		super(name);
		this.dataClass = dataClass;
		addInput(new Input("vec", Vec2D.class));
		Input input = new Input("b", dataClass);
		if (dataClass == Integer.class)
			input.setManualValue(1);
		else
			input.setManualValue(1.);
		addInput(input);
		addOutput(new Output("vec/b", Vec2D.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {
		Vec2D vector = getInput("vec").getData(Vec2D.class);
		T scalar = getInput("b").getData(dataClass);
		getOutput("vec/b").setData(div(vector ,scalar));
	}

	/**
	* @param val1
	* @param val2
	* @return the sum of val1 and val2
	*/ 
	protected abstract Vec2D div(Vec2D vec, T a);


	// ========== CONCRETE SUBCLASSES ==========

	/**
	* @return a node model used to divide a vector with an integer
	*/ 
	public static DivVectorNode<Integer> buildDivVecIntNode() {
		return new DivVectorNode<Integer>("Divide : Vect-Int", Integer.class) {
			@Override
			protected Vec2D div(Vec2D vec, Integer a) {
				return Vec2D.divide(a, vec);
			}
		};
	}

	/**
	* @return a node model used to divide a vector with a double, and then truncate the components
	*/ 
	public static DivVectorNode<Double> buildDivVecDoubleNode() {
		return new DivVectorNode<Double>("Divide : Vect-Double", Double.class) {
			@Override
			protected Vec2D div(Vec2D vec, Double a) {
				return Vec2D.divide(a, vec);
			}
		};
	}
}