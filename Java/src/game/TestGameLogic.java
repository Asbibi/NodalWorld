package game;

import gamelogic.*;
import gamelogic.rules.*;
import gamelogic.nodes.*;
import gamelogic.actions.*;

/**
* Class used to customize and run some small interactive tests for the gamelogic package.
* When using this class as entry point, the user should pass the following arguments : [grid width] [grid height] [number of iterations]
*/ 
public class TestGameLogic {

	private GameManager game;
	private Surface soil;
	private Species humans;

	public TestGameLogic(int width, int height) {
		game = new GameManager(width, height);
		soil = new Surface("soil");
		humans = new Species("human");
	}

	private GenerationRule makeGenRandomOnSoilRule() {
		GenerationRule rule = new GenerationRule();
		Network net = rule.getNetwork();

		RandIntNode nodeRandX = new RandIntNode(game.gridWidth()); 
		net.addNode(nodeRandX);
		RandIntNode nodeRandY = new RandIntNode(game.gridHeight()); 
		net.addNode(nodeRandY);
		IntsToVecNode nodeGather = new IntsToVecNode(); 
		net.addNode(nodeGather);
		net.link(nodeRandX, "val", nodeGather, "x");
		net.link(nodeRandY, "val", nodeGather, "y");

		SurfaceAtNode nodeSurf = new SurfaceAtNode(); 
		net.addNode(nodeSurf);
		net.link(nodeGather, "vec", nodeSurf, "position");

		ValueNode<Surface> nodeSoil = new ValueNode<Surface>(Surface.class);
		nodeSoil.setValue(soil); 
		net.addNode(nodeSoil);
		EqualNode<Surface> nodeEq = new EqualNode<Surface>(Surface.class); 
		net.addNode(nodeEq);
		net.link(nodeSurf, "surface", nodeEq, "val1");
		net.link(nodeSoil, "val", nodeEq, "val2");

		GenerateNode nodeEnd = rule.getTerminalNode();
		net.link(nodeEq, "res", nodeEnd, "generate");
		net.link(nodeGather, "vec", nodeEnd, "position");

		return rule;
	}

	public static void main(String[] args) {
		try {
			int width, height, nIter;
			width = Integer.parseInt(args[0]);
			height = Integer.parseInt(args[1]);
			nIter = Integer.parseInt(args[2]);
			if(width <= 0 || height <= 0 || nIter <= 0) {
				System.out.println("Arguments must be positive");
				return;
			}

			TestGameLogic test = new TestGameLogic(width, height);

			Terrain ground = new Terrain(test.game.gridWidth(), test.game.gridHeight());
			ground.fill(test.soil);
			test.game.pushTerrain(ground);

			test.game.addSpecies(test.humans);

			GenerationRule rule = test.makeGenRandomOnSoilRule();
			test.game.addRule(rule);
			test.game.connectRuleToSpecies(rule, test.humans);

			for(int i=0; i<nIter; i++) {
				System.out.println(test.game);
				test.game.evolveGameState();
			}
			System.out.println(test.game);
		} catch(NumberFormatException e) {
			System.out.println("Arguments must be integers");
			return;
		}
	}

}
