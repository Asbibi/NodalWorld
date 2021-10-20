package gamelogic;

import java.awt.Color;
import java.io.File;

import gameinterface.NewWorldTemplate;

/**
* Class with only static methods meant to build new game managers when the user wants to start a new project or to load previously saved projects.
* 
* @see GameManager
* @see NewWorldTemplate
* @see Saver
*/ 
public class GameManagerBuilder {

	// ========== BUILDER METHODS ==========

	/**
	* @param template
	* @param width
	* @param height
	* @return the newly created game manager, based on the template given
	*/ 
	public static GameManager buildGameFromTemplate(NewWorldTemplate template, int width, int height, String savefilePath) {
		if (template.isEqual(NewWorldTemplate.empty))
			return buildEmptyGame(width, height);
		else if (template.isEqual(NewWorldTemplate.basic))
			return buildBasicGame(width, height);
		else if (template.isEqual(NewWorldTemplate.island))
			return buildIslandGame(width, height);
		else if (template.isEqual(NewWorldTemplate.completeDemo))
			return buildDemoGame(width, height);
		else if (template.isEqual(NewWorldTemplate.loadElements))
			return buildDataLoadedGame(savefilePath,width, height);
		else if (template.isEqual(NewWorldTemplate.loadElementsTerrain))
			return buildTerrainLoadedGame(savefilePath,width, height);
		else if (template.isEqual(NewWorldTemplate.loadElementsAllNodes))
			return buildAllNetsLoadedGame(savefilePath,width, height, true);
		else if (template.isEqual(NewWorldTemplate.loadFullSave))
			return buildFullLoadedGame(savefilePath,width, height);
		else {
			System.err.println("Template given is incorrect");
			return null;
		}
	}
	
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
	public static GameManager buildIslandGame(int width, int height) {
		GameManager islandManager =  buildAllNetsLoadedGame("res" + File.separator + "IslandSaveFile.nws", width, height, true);
		return islandManager;
	}
	
	/**
	* @param width_failsafe	used only if loading fails
	* @param height_failsafe used only if loading fails
	* @return the newly created game manager used for demonstration purposes
	*/ 
	public static GameManager buildDemoGame(int width_failsafe, int height_failsafe) {
		return buildAllNetsLoadedGame("res" + File.separator + "DemoSaveFile.nws", width_failsafe, height_failsafe, false);
	}

	
	
	/**
	* If loading fails, it will return an empty game manager with given width and height.
	* @param saveFilePath the path to the savefile used as surface and species bank
	* @param width
	* @param height
	* @return the newly created game manager with all the surfaces and species of a loaded savefile
	*/ 
	public static GameManager buildDataLoadedGame(String saveFilePath, int width, int height) {
		GameManager game = buildEmptyGame(width, height);
		GameManager gameSaved = Saver.loadGame(saveFilePath);
		if (gameSaved != null)
			copyElementsGameManager(game, gameSaved);
		
		return game;
	}
	
	/**
	* If loading fails, it will return an empty game manager with given width and height.
	* @param saveFilePath the path to the savefile used as surface and species bank
	* @param width
	* @param height
	* @return the newly created game manager with all the surfaces, species and terrain graph of a loaded savefile
	*/ 
	public static GameManager buildTerrainLoadedGame(String saveFilePath, int width, int height) {
		GameManager gameSaved = Saver.loadGame(saveFilePath);
		if (gameSaved != null) {
			GameManager game = buildEmptyGame(width, height);
			gameSaved.initTransientFields();
			copyElementsGameManager(game, gameSaved);
			game.copyTerrain_TerrainNet(gameSaved);
			return game;
		}
		else
			return buildEmptyGame(width, height);
	}
	
	/**
	* If loading fails, it will return an empty game manager with given width and height.
	* @param saveFilePath the path to the savefile used as surface and species bank
	* @param width
	* @param height
	* @param withDimension indicates if the dimension given should be used on the new terrain or only as failsafe values
	* @return the newly created game manager with all the data and graphs of a loaded savefile, but with no members and starting at frame 0
	*/ 
	public static GameManager buildAllNetsLoadedGame(String saveFilePath, int width, int height, boolean withDimension) {
		GameManager game = Saver.loadGame(saveFilePath);
		if (game == null)
			return buildEmptyGame(width, height);

		if (withDimension ) {
			game.getTerrain().setWidth(width);
			game.getTerrain().setHeight(height);
		}
		game.initTransientFields();
		game.reinitWorld();
		return game;
	}
	
	/**
	* If loading fails, it will return an empty game manager with given width and height.
	* @param saveFilePath the path to the savefile used
	* @param width_failsafe	used only if loading fails
	* @param height_failsafe used only if loading fails
	* @return the loaded game manager as it was saved
	*/ 
	public static GameManager buildFullLoadedGame(String saveFilePath, int width_failsafe, int height_failsafe) {
		GameManager game = Saver.loadGame(saveFilePath);
		game.initTransientFields();
		return game != null ? game : buildEmptyGame(width_failsafe, height_failsafe);
	}


	// ========== UTILITY METHODS ==========

	private static Surface ground, water, grassD, grassB, sand, stoneD, stoneB, magma, lava;
	private static Species human, chicken, birch, oak;
	//private static Species boar;			// Boar disabled to have only 4 different species which is better looking + to have an image available for the demo

	/**
	* Creates all the basic surfaces.
	*/ 
	private static void initBasicSurfaces() {
		ground = new Surface("Ground", "res/Tile_Dirt.png"); 
		ground.setColor(new Color(196,161,126));

		water = new Surface("Water", "res/Tile_Water.png");
		water.setColor(new Color(57,68,166));

		grassD = new Surface("Grass Dark", "res/Tile_Grass.png");
		grassD.setColor(new Color(42,98,61));

		grassB = new Surface("Grass Bright", "res/Tile_GrassBright.png");
		grassB.setColor(new Color(50,182,98));

		sand = new Surface("Sand", "res/Tile_Sand.png");
		sand.setColor(new Color(229,212,155));
		stoneD = new Surface("Stone Dark", "res/Tile_StoneDark.png");
		stoneD.setColor(new Color(50,47,47));
		stoneB = new Surface("Stone Bright", "res/Tile_Stone.png");
		stoneB.setColor(new Color(154,134,134));
		magma = new Surface("Magma", "res/Tile_Magma.png");
		magma.setColor(new Color(82,6,0));
		lava = new Surface("Lava", "res/Tile_Lava.png");
		lava.setColor(new Color(214,77,24));
	}

	/**
	* Creates all the basic surfaces.
	*/ 
	private static void initBasicSpecies() {
		human = new Species("Human", "res/Animal_Human.png");
		chicken = new Species("Chicken", "res/Animal_Chicken.png");
		//boar = new Species("Boar", "res/Animal_Boar.png");
		birch = new Species("Birch", "res/Tree_Birch.png");
		birch.setTriggerTime(10);
		oak = new Species("Oak", "res/Tree_Oak.png");
		oak.setTriggerTime(15);
	}

	/**
	* Adds all basic surfaces and species to game
	* @param game
	*/ 
	private static void initBasicGameManager(GameManager game) {
		// Surfaces
		game.addSurface(ground);
		game.addSurface(water);
		game.addSurface(grassD);
		game.addSurface(grassB);
		game.addSurface(sand);
		game.addSurface(stoneD);
		game.addSurface(stoneB);
		game.addSurface(magma);
		game.addSurface(lava);

		// Species
		game.addSpecies(human);
		game.addSpecies(chicken);
		//game.addSpecies(boar);
		game.addSpecies(birch);
		game.addSpecies(oak);
	}

	/**
	* Adds terrain rules to the demo GameManager.
	* @param game
	*/ 
	private static void addDemoTerrain(GameManager game) {
		// TODO
		// fill grid with water
		// create island with ground
		// add come grass
	}

	/**
	* Adds generation rules to the demo GameManager.
	* @param game
	*/ 
	private static void addDemoGenRule(GameManager game) {
		// TODO
		// generate new humans at the center of the map
	}
	
	/**
	* Adds generation rules to the demo GameManager.
	* @param game
	*/ 
	private static void addDemoMoveRule(GameManager game) {
		// TODO
		// move in a random direction
	}

	/**
	* Adds generation rules to the demo GameManager.
	* @param game
	*/ 
	private static void addDemoDeathRule(GameManager game) {
		// TODO
		// die if too old or if in water
	}

	/**
	* Copies all elements (i.e. Surface and Species) from a GameManager to another.
	* @param gameCopy the GameManager that copies the other
	* @param gameCopied the GameManager that is used as source by the other
	*/ 
	private static void copyElementsGameManager(GameManager gameCopy, GameManager gameCopied) {
		for (int i = 1; i < gameCopied.getSurfaceArray().size(); i++)
				gameCopy.addSurface(gameCopied.getSurfaceArray().get(i));
		for (int i = 1; i < gameCopied.getSpeciesArray().size(); i++) {
			gameCopied.getSpeciesArray().get(i).removeAllMembers();
			gameCopy.addSpecies(gameCopied.getSpeciesArray().get(i));
		}
	}
}
