package gamelogic.nodes;

import gamelogic.GameManager;
import gamelogic.TerrainNode;
import gamelogic.terrains.TerrainModelRectangle;
import gamelogic.Vec2D;
import gamelogic.Input;
import gamelogic.NetworkIOException;

public class TerrainNodeRectangle extends TerrainNode<TerrainModelRectangle> {

	public TerrainNodeRectangle() {
		super("Terrain Rectangle", new TerrainModelRectangle());
		addInput(new Input("corner", Vec2D.class));
		addInput(new Input("dim", Vec2D.class));
	}

	@Override
	public void initModel() throws NetworkIOException {
		Vec2D corner = getInput("corner").getData(Vec2D.class);
		model.setCorner(corner);

		Vec2D dim = getInput("dim").getData(Vec2D.class);
		model.setDimensions(dim);
	}

}
