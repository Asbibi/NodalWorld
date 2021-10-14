package gamelogic.nodes;

import gamelogic.Entity;
import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.NetworkIOException;
import gamelogic.Node;
import gamelogic.Output;
import gamelogic.Species;
import gamelogic.Vec2D;

/**
* The node model used to detect if a species has at least one member in a rectangular area, and if so record its position. <br/>
* 
* Inputs : species, position, dimension <br/>
* Outputs : inside, position
* 
*/
public class SpeciesInAreaNode  extends Node {

	/**
	*
	*/ 
	public SpeciesInAreaNode() {
		super("Species In Area");
		addInput(new Input("species", Species.class));
		addInput(new Input("position", Vec2D.class));
		addInput(new Input("dimension", Vec2D.class));
		addOutput(new Output("inside", Boolean.class));
		addOutput(new Output("position", Vec2D.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {
		Vec2D areaPos = getInput("position").getData(Vec2D.class);
		Vec2D areaSize = getInput("dimension").getData(Vec2D.class);
		
		Species inputSp = getInput("species").getData(Species.class);
		if (inputSp.equals(Species.getEmpty())) {
			for (Species sp : game.getSpeciesArray()) {
				for (Entity en : sp.getMembers()) {
					if (en.isInArea(areaPos, areaSize)) {
						getOutput("inside").setData(true);
						getOutput("position").setData(en.getPos());
						return;
					}
				}
			}
		}
		else {
			for (Entity en : inputSp.getMembers()) {
				if (en.isInArea(areaPos, areaSize)) {
					getOutput("inside").setData(true);
					getOutput("position").setData(en.getPos());
					return;
				}
			}
		}
		getOutput("inside").setData(false);
		getOutput("position").setData(new Vec2D(-1,-1));
	}

}
