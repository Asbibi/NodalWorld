package gamelogic.nodes;

import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.NetworkIOException;
import gamelogic.Node;
import gamelogic.Output;
import gamelogic.Species;

public class ConvertIntSpecies extends Node {

	/**
	* 
	*/ 
	public ConvertIntSpecies() {
		super("Int to Species");
		addInput(new Input("val", Integer.class));
		addOutput(new Output("val", Species.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {
		int speciesVal = getInput("val").getData(Integer.class);
		for (Species sp : game.getSpeciesArray())
			if (sp.getId() == speciesVal) {
				getOutput("val").setData(sp);
				return;
			}
		getOutput("val").setData(Species.getEmpty());	
	}

}
