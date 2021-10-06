package gamelogic.nodes;

import gamelogic.Node;
import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.Output;
import gamelogic.Species;
import gamelogic.Entity;
import gamelogic.Vec2D;

/**
* The node model used to extract data from an entity. <br/>
* 
* Inputs : entity <br/>
* Outputs : pos, species, birth
* 
* @see GameManager
*/
public class EntityNode extends Node {

	public EntityNode() {
		super("Entity Data");
		addInput(new Input("entity", Entity.class));
		addOutput(new Output("pos", Vec2D.class));
		addOutput(new Output("species", Species.class));
		addOutput(new Output("birth", Integer.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) {
		Entity entity = getInput("entity").getData(Entity.class);
		getOutput("pos").setData(entity.getPos());
		getOutput("species").setData(entity.getSpecies());
		getOutput("birth").setData(entity.getBirthTime());
	}

}
