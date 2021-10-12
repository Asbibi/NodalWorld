package gamelogic.nodes;

import gamelogic.GameManager;
import gamelogic.TerrainNode;
import gamelogic.terrains.TerrainModelRectangle;
import gamelogic.Vec2D;
import gamelogic.Input;
import gamelogic.NetworkIOException;

public class TerrainNodeUnit extends TerrainNode<TerrainModelRectangle> {

	public TerrainNodeUnit() {
		super("Terrain Unit", new TerrainModelRectangle());
		addInput(new Input("pos", Vec2D.class));
	}

	@Override
	public void initModel() throws NetworkIOException {
		Vec2D pos = getInput("pos").getData(Vec2D.class);
		model.setCorner(pos);

		model.setDimensions(new Vec2D(1, 1));
	}

}
