package gamelogic.nodes;

import gamelogic.GameManager;
import gamelogic.TerrainNode;
import gamelogic.terrains.TerrainModelEllipse;
import gamelogic.Vec2D;
import gamelogic.Input;
import gamelogic.NetworkIOException;

/**
* The node model used to create an elliptic terrain layer. <br/>
* 
* Inputs : surface, center, rx, ry <br/>
* Outputs : layer
* 
*/
public class TerrainNodeEllipse extends TerrainNode<TerrainModelEllipse> {

	public TerrainNodeEllipse() {
		super("Terrain Ellipse", new TerrainModelEllipse());
		addInput(new Input("center", Vec2D.class));
		addInput(new Input("rx", Integer.class));
		addInput(new Input("ry", Integer.class));
	}

	@Override
	public void initModel() throws NetworkIOException {
		Vec2D center = getInput("center").getData(Vec2D.class);
		model.setCenter(center);

		Integer rx = getInput("rx").getData(Integer.class);
		model.setRadiusX(rx);

		Integer ry = getInput("ry").getData(Integer.class);
		model.setRadiusY(ry);
	}

}
