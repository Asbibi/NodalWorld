package gamelogic;

import gamelogic.rules.*;

import java.util.Map;
import java.util.HashMap;
import java.util.Collection;
import java.util.ArrayList;
import java.util.LinkedList;

import java.lang.StringBuilder;

/**
* The game manager is responsible for the initialization and evolution of the game state at each frame using the rules defined by the user.
* 
* @see Rule
* @see Action
* @see TerrainStack
* @see Species
* @see Entity
*/
public class GameManager {

	private Integer width, height;
	private Integer frame;
	private TerrainStack terrainStack;
	private Map<String, Species> species;

	private Collection<GenerationRule> genRules;
	private Collection<MovementRule> moveRules;
	private Collection<DeathRule> deathRules;

	private Map<Rule, Collection<Species>> rulesToSpecies;
	private Map<Species, GenerationRule> speciesToGenRule;
	private Map<Species, MovementRule> speciesToMoveRule;
	private Map<Species, DeathRule> speciesToDeathRule;

	private Species currentSpecies;
	private Entity currentEntity;

	/**
	* @param width
	* @param height
	*/ 
	public GameManager(Integer width, Integer height) {
		this.width = width;
		this.height = height;
		frame = 0;
		terrainStack = new TerrainStack();
		species = new HashMap<String, Species>();
		genRules = new ArrayList<GenerationRule>();
		moveRules = new ArrayList<MovementRule>();
		deathRules = new ArrayList<DeathRule>();
		rulesToSpecies = new HashMap<Rule, Collection<Species>>();
		speciesToGenRule = new HashMap<Species, GenerationRule>();
		speciesToMoveRule = new HashMap<Species, MovementRule>();
		speciesToDeathRule = new HashMap<Species, DeathRule>();
	}

	/**
	* @return the grid's width
	*/ 
	public Integer gridWidth() {
		return width;
	}

	/**
	* @return the grid's height
	*/
	public Integer gridHeight() {
		return height;
	}

	/**
	* @return the current frame
	*/
	public Integer getFrame() {
		return frame;
	}

	/**
	* @param pos
	* @return the surface stored in the tile at the given position
	*/
	public Surface surfaceAt(Vec2D pos) {
		return terrainStack.surfaceAt(pos);
	}

	/**
	* @param terrain
	*/
	public void pushTerrain(Terrain terrain) {
		terrainStack.pushTerrain(terrain);
	}

	/**
	* @param name
	* @return the species corresponding to the given name, null if it doesn't exist
	*/
	public Species getSpecies(String name) {
		return species.get(name);
	}

	/**
	* @param sp
	*/
	public void addSpecies(Species sp) {
		species.put(sp.toString(), sp);
	}

	/**
	* @return the species being processed in the game loop
	*/
	public Species getCurrentSpecies() {
		return currentSpecies;
	}

	/**
	* @return the entity being processed in the game loop
	*/
	public Entity getCurrentEntity() {
		return currentEntity;
	}

	/**
	* @param rule
	*/
	public void addRule(GenerationRule rule) {
		genRules.add(rule);
		rulesToSpecies.put(rule, new ArrayList<Species>());
	}

	/**
	* @param rule
	*/
	public void addRule(MovementRule rule) {
		moveRules.add(rule);
		rulesToSpecies.put(rule, new ArrayList<Species>());
	}

	/**
	* @param rule
	*/
	public void addRule(DeathRule rule) {
		deathRules.add(rule);
		rulesToSpecies.put(rule, new ArrayList<Species>());
	}

	/**
	* Ensures that each species has at most one generation rule.
	* 
	* @param rule
	* @param sp
	*/
	public void connectRuleToSpecies(GenerationRule rule, Species sp) {
		rulesToSpecies.get(rule).add(sp);
		if(speciesToGenRule.containsKey(sp) && !speciesToGenRule.get(sp).equals(rule)) {
			speciesToGenRule.replace(sp, rule);
		} else {
			speciesToGenRule.put(sp, rule);
		}
	}

	/**
	* Ensures that each species has at most one movement rule.
	* 
	* @param rule
	* @param sp
	*/
	public void connectRuleToSpecies(MovementRule rule, Species sp) {
		rulesToSpecies.get(rule).add(sp);
		if(speciesToMoveRule.containsKey(sp) && !speciesToMoveRule.get(sp).equals(rule)) {
			speciesToMoveRule.replace(sp, rule);
		} else {
			speciesToMoveRule.put(sp, rule);
		}
	}

	/**
	* Ensures that each species has at most one death rule.
	* 
	* @param rule
	* @param sp
	*/
	public void connectRuleToSpecies(DeathRule rule, Species sp) {
		rulesToSpecies.get(rule).add(sp);
		if(speciesToDeathRule.containsKey(sp) && !speciesToDeathRule.get(sp).equals(rule)) {
			speciesToDeathRule.replace(sp, rule);
		} else {
			speciesToDeathRule.put(sp, rule);
		}
	}

	/**
	* Applies the rules and executes the corresponding actions in the following order : generation, movement, death.
	*/ 
	public void evolveGameState() {
		Collection<Action> actions;
		actions = new LinkedList<Action>();
		for(GenerationRule rule : genRules) {
			for(Species sp : rulesToSpecies.get(rule)) {
				actions.addAll(apply(rule, sp));
			}
		}
		execute(actions);

		actions = new LinkedList<Action>();
		for(MovementRule rule : moveRules) {
			for(Species sp : rulesToSpecies.get(rule)) {
				actions.addAll(apply(rule, sp));
			}
		}
		execute(actions);

		actions = new LinkedList<Action>();
		for(DeathRule rule : deathRules) {
			for(Species sp : rulesToSpecies.get(rule)) {
				actions.addAll(apply(rule, sp));
			}
		}
		execute(actions);

		frame++;
	}

	private Collection<Action> apply(GenerationRule rule, Species sp) {
		Collection<Action> actions = new LinkedList<Action>();
		currentSpecies = sp;
		actions.addAll(rule.apply(this));
		return actions;
	}

	private Collection<Action> apply(MovementRule rule, Species sp) {
		Collection<Action> actions = new LinkedList<Action>();
		for(Entity member : sp.getMembers()) {
			currentEntity = member;
			actions.addAll(rule.apply(this));
		}
		return actions;
	}

	private Collection<Action> apply(DeathRule rule, Species sp) {
		Collection<Action> actions = new LinkedList<Action>();
		for(Entity member : sp.getMembers()) {
			currentEntity = member;
			actions.addAll(rule.apply(this));
		}
		return actions;
	}

	private void execute(Collection<Action> actions) {
		for(Action action : actions) {
			action.execute();
		}
	}

	/**
	* Gives a written description of the current state of the game.
	* Can be useful for basic testing.
	*/ 
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("Terrain : \n");
		for(int y=0; y<height; y++) {
			for(int x=0; x<width; x++) {
				sb.append(surfaceAt(new Vec2D(x, y)));
				sb.append(' ');
			}
			sb.append('\n');
		}
		sb.append('\n');

		sb.append("Species : \n");
		for(Species sp : species.values()) {
			sb.append(sp);
			sb.append(" : ");
			for(Entity member : sp.getMembers()) {
				sb.append(member.getPos());
				sb.append(' ');
			}
			sb.append('\n');
		}
		sb.append('\n');

		return sb.toString();
	}

}
