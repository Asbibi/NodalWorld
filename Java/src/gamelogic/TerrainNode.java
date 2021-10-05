package gamelogic;

/**
* Abstract model for specific nodes used in the terrain network to define a specific terrain layer.
* It is parameterized by a terrain model.
*  
* @see TerrainSlot
* @see TerrainModel
*/ 
public abstract class TerrainNode<T extends TerrainModel> extends Node {

	protected T model;

	/**
	* @param name
	* @param model
	*/ 
	public TerrainNode(String name, T model) {
		super(name);
		this.model = model;
		addInput(new Input("surface", Surface.class));
	}

	/**
	* @param pos
	* @return the surface computed by the model at the given position
	*/ 
	public Surface getSurfaceAt(Vec2D pos) {
		initModel();
		if(model.hasSurfaceAt(pos)) {
			return getInput("surface").getData(Surface.class);
		}
		return Surface.getEmpty();
	}

	/**
	* Specifies the correspondance between the node's inputs and the model parameters.
	*/ 
	protected abstract void initModel();

}
