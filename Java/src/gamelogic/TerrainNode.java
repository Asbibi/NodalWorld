package gamelogic;

public abstract class TerrainNode<T extends TerrainModel> extends Node {

	protected T model;

	public TerrainNode(String name, T model) {
		super(name);
		this.model = model;
		addInput(new Input("surface", Surface.getEmpty()));
	}

	public Surface getSurfaceAt(Vec2D pos) {
		initModel();
		if(model.hasSurfaceAt(pos)) {
			return getInput("surface").getData(Surface.class);
		}
		return Surface.getEmpty();
	}

	protected abstract void initModel();

}
