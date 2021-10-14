package gamelogic.nodes;

import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.NetworkIOException;
import gamelogic.Node;
import gamelogic.Output;
import gamelogic.Vec2D;

/**
* The abstract node model used to multiply a vector with a value (concrete subclasses are instanciated by static methods). <br/>
* 
* Inputs : vec, a <br/>
* Outputs : vec*a
* 
*/
public abstract class MultiplyVectorNode<T> extends Node {

	private Class<T> dataClass;

	/**
	* @param name 
	* @param dataClass
	*/ 
	public MultiplyVectorNode(String name, Class<T> dataClass) {
		super(name);
		this.dataClass = dataClass;
		addInput(new Input("vec", Vec2D.class));
		addInput(new Input("a", dataClass));
		addOutput(new Output("vec*a", Vec2D.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {
		Vec2D vector = getInput("vec").getData(Vec2D.class);
		T scalar = getInput("a").getData(dataClass);
		getOutput("vec*a").setData(mul(vector ,scalar));
	}

	/**
	* @param val1
	* @param val2
	* @return the sum of val1 and val2
	*/ 
	protected abstract Vec2D mul(Vec2D vec, T a);


	// ========== CONCRETE SUBCLASSES ==========

	/**
	* @return a node model used to multiply a vector with an integer
	*/ 
	public static MultiplyVectorNode<Integer> buildMulVecIntNode() {
		return new MultiplyVectorNode<Integer>("Multiply : Vect-Int", Integer.class) {
			@Override
			protected Vec2D mul(Vec2D vec, Integer a) {
				return Vec2D.multiply(a, vec);
			}
		};
	}

	/**
	* @return a node model used to multiply a vector with a double, and then truncate the components
	*/ 
	public static MultiplyVectorNode<Double> buildMulVecDoubleNode() {
		return new MultiplyVectorNode<Double>("Multiply : Vect-Double", Double.class) {
			@Override
			protected Vec2D mul(Vec2D vec, Double a) {
				return Vec2D.multiply(a, vec);
			}
		};
	}
}
