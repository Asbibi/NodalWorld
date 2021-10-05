package gamelogic;

/**
* Class with only static methods meant to build new game managers when the user wants to start a new project.
* 
* @see GameManager
*/ 
public class GameManagerBuilder {

	// ========== PUBLIC ==========

	/**
	* @param width
	* @param height
	* @return the newly created empty game manager
	*/ 
	public static GameManager buildEmptyGame(int width, int height) {
		GameManager game = new GameManager(width, height);
		return game;
	}

	/**
	* @param width
	* @param height
	* @return the newly created game manager with some basic settings
	*/ 
	public static GameManager buildBasicGame(int width, int height) {
		GameManager game = new GameManager(width, height);
		initBasicSurfaces();
		initBasicSpecies();
		initBasicGameManager(game);
		return game;
	}

	/**
	* @param width
	* @param height
	* @return the newly created game manager used for demonstration purposes
	*/ 
	public static GameManager buildDemoGame(int width, int height) {
		GameManager game = new GameManager(width, height);
		initBasicSurfaces();
		initBasicSpecies();
		initBasicGameManager(game);
		addDemoTerrain(game);
		addDemoGenRule(game);
		addDemoMoveRule(game);
		addDemoDeathRule(game);
		return game;
	}


	// ========== PRIVATE ==========

	private static Surface ground, water, grassD, grassB;
	private static Species human, chicken, boar, birch, oak;

	private static void initBasicSurfaces() {
		ground = new Surface("Ground", "res/Tile_Dirt.png"); 
		ground.setColor(new Color(196,161,126));

		water = new Surface("Water", "res/Tile_Water.png");
		water.setColor(new Color(57,68,166));

		grassD = new Surface("Grass Dark", "res/Tile_Grass.png");
		grassD.setColor(new Color(42,98,61));

		grassB = new Surface("Grass Bright", "res/Tile_GrassBright.png");
		grassB.setColor(new Color(50,182,98));
	}

	private static initBasicSpecies() {
		human = new Species("Human", "res/Animal_Human.png");
		chicken = new Species("Chicken", "res/Animal_Chicken.png");
		boar = new Species("Boar", "res/Animal_Boar.png");
		birch = new Species("Birch", "res/Tree_Birch.png");
		oak = new Species("Oak", "res/Tree_Oak.png");
	}

	private static void initBasicGameManager(GameManager game) {
		// Surfaces
		game.addSurface(ground);
		game.addSurface(water);
		game.addSurface(grassD);
		game.addSurface(grassB);

		// Species
		game.addSpecies(human);
		game.addSpecies(chicken);
		game.addSpecies(boar);
		game.addSpecies(birch);
		game.addSpecies(oak);
	}

	private static void addDemoTerrain(GameManager game) {
		// TODO
		// fill grid with water
		// create island with ground
		// add come grass
	}

	private static void addDemoGenRule(GameManager game) {
		// TODO
		// generate new humans at the center of the map
	}

	private static void addDemoMoveRule(GameManager game) {
		// TODO
		// move in a random direction
	}

	private static void addDemoDeathRule(GameManager game) {
		// TODO
		// die if too old or if in water
	}

}
