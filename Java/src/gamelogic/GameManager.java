package gamelogic;

import gamelogic.rules.*;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;

import java.lang.StringBuilder;
import java.lang.Class;

/**
* The game manager is responsible for the initialization and evolution of the game state at each frame using the rules defined by the user.
* 
* @see Rule
* @see Terrain
* @see Species
* @see Entity
*/
public class GameManager {

	private Integer frame;

	private Terrain terrain;

	private List<Surface> surfaces;
	private List<Species> species;

	private Map<Species, GenerationRule> speciesToGenRule;
	private Map<Species, MovementRule> speciesToMoveRule;
	private Map<Species, DeathRule> speciesToDeathRule;

	private Species currentSpecies;
	private Entity currentEntity;

	private Network terrainNet, genNet, moveNet, deathNet;

	/**
	* @param width
	* @param height
	*/ 
	public GameManager(int width, int height) {
		frame = 0;

		terrain = new Terrain(width, height, 1);

		surfaces = new LinkedList<Surface>();
		surfaces.add(Surface.getEmpty());

		species = new LinkedList<Species>();

		speciesToGenRule = new HashMap<Species, GenerationRule>();
		speciesToMoveRule = new HashMap<Species, MovementRule>();
		speciesToDeathRule = new HashMap<Species, DeathRule>();

		currentSpecies = null;
		currentEntity = null;

		terrainNet = new Network();
		genNet = new Network();
		moveNet = new Network();
		deathNet = new Network();
	}

	/**
	* @return the grid's width
	*/ 
	public Integer gridWidth() {
		return terrain.getWidth();
	}

	/**
	* @return the grid's height
	*/
	public Integer gridHeight() {
		return terrain.getHeight();
	}

	/**
	* @return the current frame
	*/
	public Integer getFrame() { return frame; }

	/**
	 * Mainly used for display on the interface
	* @return the current terrain
	*/
	public Terrain getTerrain() { return terrain; }
	
	/**
	* @param pos
	* @return the surface stored in the tile at the given position
	*/
	public Surface surfaceAt(Vec2D pos) { return terrain.getSurfaceAt(pos); }
	
	/**
	* @return reference to the surface list
	*/
	public List<Surface> getSurfaceArray() {
		return surfaces;
	}

	/**
	* @param name
	* @return the surface corresponding to the given name, empty surface if it doesn't exist
	*/
	public Surface getSurface(String name) {
		return surfaces.stream().filter(sp -> sp.toString().equals(name)).findFirst().orElse(Surface.getEmpty());
	}

	/**
	* @param index of the surface in the array
	* @return the surface corresponding to the given index, empty surface if it doesn't exist
	*/
	public Surface getSurface(int index) {
		if (index >=0 && index <surfaces.size())
			return surfaces.get(index);
		else
			return Surface.getEmpty();
	}
	
	/**
	* @param sp
	*/
	public void addSurface(Surface surf) {
		surfaces.add(surf);
	}

	/**
	* @return reference to the surface list
	*/
	public List<Species> getSpeciesArray() {
		return species;
	}

	/**
	* @param name
	* @return the species corresponding to the given name, null if it doesn't exist
	*/
	public Species getSpecies(String name) {
		return species.stream()
					.filter(sp -> sp.toString().equals(name))
					.findFirst()
					.orElse(null);
	}
	
	/**
	* @param index of the species in the array
	* @return the species corresponding to the given index, null if it doesn't exist
	*/
	public Species getSpecies(int index) {
		if (index >=0 && index <species.size())
			return species.get(index);
		else
			return null;
	}

	/**
	* @param sp
	*/
	public void addSpecies(Species sp) { species.add(sp); }

	/**
	* @return the species being processed in the game loop
	*/
	public Species getCurrentSpecies() { return currentSpecies; }

	/**
	* @return the entity being processed in the game loop
	*/
	public Entity getCurrentEntity() { return currentEntity; }

	/**
	* @return the terrain network
	*/ 
	public Network getTerrainNet() { return terrainNet; }

	/**
	* @return the generation netork
	*/ 
	public Network getGenNet() { return genNet; }

	/**
	* @return the movement netork
	*/ 
	public Network getMoveNet() { return moveNet; }

	/**
	* @return the death netork
	*/ 
	public Network getDeathNet() { return deathNet; }

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

	public <R extends Rule> void connectRuleToSpecies(R rule, Species sp) {
		if(rule instanceof GenerationRule) {
			connectRuleToSpecies((GenerationRule) rule, sp);
		} else if(rule instanceof MovementRule) {
			connectRuleToSpecies((MovementRule) rule, sp);
		} else if(rule instanceof DeathRule) {
			connectRuleToSpecies((DeathRule) rule, sp);
		}
	}

	public <R extends Rule> R getRule(Class<R> ruleClass, Species sp) {
		if(ruleClass.equals(GenerationRule.class)) {
			return ruleClass.cast(speciesToGenRule.get(sp));
		} else if(ruleClass.equals(MovementRule.class)) {
			return ruleClass.cast(speciesToMoveRule.get(sp));
		} else if(ruleClass.equals(DeathRule.class)) {
			return ruleClass.cast(speciesToDeathRule.get(sp));
		}
		return null;
	}

	/**
	* Applies the rules and executes the corresponding actions in the following order : generation, movement, death.
	*/ 
	public int evolveGameState() {
		for(Species sp : species) {
			if(speciesToGenRule.containsKey(sp)) {
				genNet.evaluate(this);
				apply(speciesToGenRule.get(sp), sp);
			}
			if(speciesToMoveRule.containsKey(sp)) {
				moveNet.evaluate(this);
				apply(speciesToMoveRule.get(sp), sp);
			}
			if(speciesToDeathRule.containsKey(sp)) {
				deathNet.evaluate(this);
				apply(speciesToDeathRule.get(sp), sp);
			}
		}
		frame++;			// TODO : think about frame number limit ?
		return frame-1;
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

		sb.append("Terrain : \n"); // HERE
		int height = gridHeight();
		int width = gridWidth();
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
	public String arraysToString() {
		StringBuilder sb = new StringBuilder();

		sb.append("Surfaces:\t[ ");
		for(Surface surf : surfaces) {
			sb.append(surf);
			sb.append(", ");			
		}
		sb.append("]\n");

		sb.append("Species:\t[ ");
		for(Species sp : species) {
			sb.append(sp);
			sb.append(", ");			
		}
		sb.append("]\n");

		return sb.toString();
	}
}
