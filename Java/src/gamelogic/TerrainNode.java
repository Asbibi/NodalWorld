package gamelogic;

/**
* Abstract model for specific nodes used in the terrain network to define a specific terrain layer.
* It is parameterized by a terrain model.
*  
* @see TerrainSlot
* @see TerrainModel
*/ 
public abstract class TerrainNode<T extends TerrainModel> extends Node {

	private Surface surface;
	protected T model;

	/**
	* @param name
	* @param model
	*/ 
	public TerrainNode(String name, T model) {
		super(name);
		this.model = model;
		addInput(new Input("surface", Surface.class));
		addOutput(new Output("layer", TerrainModel.class));
	}

	/**
	* @param pos
	* @return the surface computed by the model at the given position
	*/ 
	public Surface getSurfaceAt(Vec2D pos) {
		if(model.hasSurfaceAt(pos)) {
			return surface;
		}
		return Surface.getEmpty();
	}

	/**
	* @param game
	*/ 
	public void evaluate(GameManager game) throws NetworkIOException {
		surface = getInput("surface").getData(Surface.class);
		initModel();
		getOutput("layer").setData(model);
	}

	/**
	* Specifies the correspondance between the node's inputs and the model parameters.
	*/ 
	protected abstract void initModel() throws NetworkIOException;

}
