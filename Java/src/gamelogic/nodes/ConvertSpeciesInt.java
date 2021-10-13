package gamelogic.nodes;

import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.NetworkIOException;
import gamelogic.Node;
import gamelogic.Output;
import gamelogic.Species;

public class ConvertSpeciesInt extends Node {

	/**
	* 
	*/ 
	public ConvertSpeciesInt() {
		super("Species to Int");
		addInput(new Input("val", Species.class));
		addOutput(new Output("val", Integer.class));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {
		Species speciesVal = getInput("val").getData(Species.class);
		getOutput("val").setData(speciesVal.getId());		
	}
}
