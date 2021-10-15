package gamelogic.nodes;

import gameinterface.PrinterMessage;
import gameinterface.components.PrinterComponent;
import gamelogic.GameManager;
import gamelogic.Input;
import gamelogic.NetworkIOException;
import gamelogic.Node;
import gamelogic.Output;
import gamelogic.TerrainModel;
import gamelogic.Vec2D;

/**
* The node model used to print informations on an object, using its toString method. <br/>
* 
* Inputs : val, label, enable <br/>
* Outputs : val
* 
*/
public class PrintNode<T> extends Node {
	
	private Class<T> dataClass;

	/**
	* @param name 
	* @param dataClass
	*/ 
	public PrintNode(String name, Class<T> dataClass) {
		super(name);
		this.dataClass = dataClass;
		addInput(new Input("val", dataClass));
		addInput(new Input("label", String.class));
		Input enableInput = new Input("enable", Boolean.class);
		enableInput.setManualValue(true);
		addInput(enableInput);
		addOutput(new Output("val", dataClass));
	}

	/**
	* @param game
	*/ 
	@Override
	public void evaluate(GameManager game) throws NetworkIOException {
		T val = getInput("val").getData(dataClass);
		if (getInput("enable").getData(Boolean.class))	{
			String label = getInput("label").getData(String.class);	
			
			if (dataClass != TerrainModel.class) {
				String message = label.equals("") ? val.toString() : label + "     \t|     " + val.toString();
				System.out.println("Node Print: " + message);
				PrinterComponent.addMessage(new PrinterMessage(game.getFrame(), message));
				
			} else {
				TerrainModel model = (TerrainModel)val;
				for (int y = 0; y < game.gridHeight(); y++) {
					String message = (label.equals("") ? "" : label + "     \t|     ") + (y==0 ? "[" : " ");
					for(int x = 0; x < game.gridWidth(); x++)
						//message += (char)(model.hasSurfaceAt(new Vec2D(x,y)) ? 254 : 255) + " ";
						message += (model.hasSurfaceAt(new Vec2D(x,y)) ? '#' : '_') + " ";
					
					if (y == game.gridHeight() -1)
						message += "]";
					System.out.println("Node Print: " + message);
					PrinterComponent.addMessage(new PrinterMessage(game.getFrame(), message));
				}
			}				
		}
		getOutput("val").setData(val);
	}

}
