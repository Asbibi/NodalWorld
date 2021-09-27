package gamelogic;

import gamelogic.rules.*;

import java.util.Map;
import java.util.HashMap;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

import java.lang.StringBuilder;

/**
* The game manager is responsible for the initialization and evolution of the game state at each frame using the rules defined by the user.
* 
* @see Rule
* @see TerrainStack
* @see Species
* @see Entity
*/
public class GameManager {

	private Integer width, height;
	private Integer frame;

	private TerrainStack terrainStack;

	private List<Species> species;

	private Collection<GenerationRule> genRules;
	private Collection<MovementRule> moveRules;
	private Collection<DeathRule> deathRules;

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
		species = new LinkedList<Species>();
		genRules = new ArrayList<GenerationRule>();
		moveRules = new ArrayList<MovementRule>();
		deathRules = new ArrayList<DeathRule>();
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
	 * Mainly used for display on the interface
	* @return the current terrains stack
	*/
	public TerrainStack getTerrainStack() {
		return terrainStack;
	}
	
	/**
	* @param pos
	* @return the surface stored in the tile at the given position
	*/
	public Surface surfaceAt(Vec2D pos) {
		return terrainStack.getSurfaceAt(pos);
	}

	/**
	* @param terrain
	*/
	public void pushTerrain(TerrainLayer terrain) {
		terrainStack.pushTerrain(terrain);
	}

	/**
	* @param name
	* @return the species corresponding to the given name, null if it doesn't exist
	*/
	public Species getSpecies(String name) {
		return species.stream().filter(sp -> sp.toString().equals(name)).findFirst().orElse(null);
	}

	/**
	* @param sp
	*/
	public void addSpecies(Species sp) {
		species.add(sp);
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
	}

	/**
	* @param rule
	*/
	public void addRule(MovementRule rule) {
		moveRules.add(rule);
	}

	/**
	* @param rule
	*/
	public void addRule(DeathRule rule) {
		deathRules.add(rule);
	}

	/**
	* Ensures that each species has at most one generation rule.
	* 
	* @param rule
	* @param sp
	*/
	public void connectRuleToSpecies(GenerationRule rule, Species sp) {
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
		for(Species sp : species) {
			if(speciesToGenRule.containsKey(sp)) {
				apply(speciesToGenRule.get(sp), sp);
			}
			if(speciesToMoveRule.containsKey(sp)) {
				apply(speciesToMoveRule.get(sp), sp);
			}
			if(speciesToDeathRule.containsKey(sp)) {
				apply(speciesToDeathRule.get(sp), sp);
			}
		}

		frame++;
	}

	private void apply(GenerationRule rule, Species sp) {
		currentSpecies = sp;
		if(sp.trigger(frame)) rule.apply(this);
	}

	private void apply(MovementRule rule, Species sp) {
		for(Entity member : sp.getMembers()) {
			currentEntity = member;
			if(member.trigger(frame)) rule.apply(this);
		}
	}

	private void apply(DeathRule rule, Species sp) {
		for(Entity member : sp.getMembers()) {
			currentEntity = member;
			if(member.trigger(frame)) rule.apply(this);
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
		for(Species sp : species) {
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
