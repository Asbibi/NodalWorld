package gamelogic;

import gamelogic.rules.*;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;

import java.lang.StringBuilder;
import java.io.Serializable;
import java.lang.Class;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

/**
* The game manager is responsible for the initialization and evolution of the game state at each frame using the rules defined by the user.
* It also centralizes the game data storage, like the list of surfaces, the list of species, the terrain, the species-rule connections and the networks.
* Its changes can be monitered by other objects using change listeners.
* 
* @see Rule
* @see Terrain
* @see Network
* @see Species
* @see Entity
*/
public class GameManager implements Serializable {


	// ========== MEMBER VARIABLES ==========

	private Integer frame;

	private Terrain terrain;

	private List<Surface> surfaces;
	private List<Species> species;

	private Map<Species, GenerationRule> speciesToGenRule;
	private Map<Species, MovementRule> speciesToMoveRule;
	private Map<Species, DeathRule> speciesToDeathRule;

	private transient Species currentSpecies;
	private transient Entity currentEntity;
	private transient List<Entity> deadEntities;

	private Network terrainNet, genNet, moveNet, deathNet;

	private transient List<ChangeListener> gameListeners, surfaceListeners, speciesListeners;


	// ========== INITIALIZATION ==========

	/**
	* Class constructor, creates an empty game with no species, rule or terrain.
	* 
	* @param width width of the world grid
	* @param height height of the world grid
	*/ 
	public GameManager(int width, int height) {
		frame = 0;

		terrain = new Terrain(width, height, 1);

		surfaces = new LinkedList<Surface>();
		surfaces.add(Surface.getEmpty());

		species = new LinkedList<Species>();
		species.add(Species.getEmpty());

		speciesToGenRule = new HashMap<Species, GenerationRule>();
		speciesToMoveRule = new HashMap<Species, MovementRule>();
		speciesToDeathRule = new HashMap<Species, DeathRule>();

		currentSpecies = null;
		currentEntity = null;
		deadEntities = null;

		terrainNet = new Network();
		genNet = new Network();
		moveNet = new Network();
		deathNet = new Network();

		gameListeners = new LinkedList<ChangeListener>();
		surfaceListeners = new LinkedList<ChangeListener>();
		speciesListeners = new LinkedList<ChangeListener>();
	}
	
	/**
	* Utility method for initializing transient fields when loading a saved game manager from memory.
	*/ 
	public void initTransientFields() {
		terrain.initTransientFields();
		terrainNet.initTransientFields();
		genNet.initTransientFields();
		moveNet.initTransientFields();
		deathNet.initTransientFields();
		if (gameListeners == null)
			gameListeners = new LinkedList<ChangeListener>();
		if (surfaceListeners == null)
			surfaceListeners = new LinkedList<ChangeListener>();
		if (speciesListeners == null)
			speciesListeners = new LinkedList<ChangeListener>();		
	}

	/**
	* Retart time and clear all members of every species.
	*/ 
	public void reinitWorld() {
		frame = 0;
		exterminateAllSpeciesMembers();
	}


	// ========== DIMENSIONS AND TIME ==========

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
	* @return the current frame (used as the time variable in the game)
	*/
	public Integer getFrame() { return frame; }


	// ========== TERRAIN AND SURFACES ==========

	/**
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
	* Add a new surface to the surface list.
	* 
	* @param surf the surface to add
	*/
	public void addSurface(Surface surf) {
		surfaces.add(surf);
		triggerSurfaceListeners();
	}
	
	/**
	* Swap two surfaces positions in the surfaces list (no check on indexes).
	*/
	public void swapSurfaces(int firstIndex, int secondIndex) {
		Surface surfTemp = surfaces.get(firstIndex);
		surfaces.set(firstIndex, surfaces.get(secondIndex));
		surfaces.set(secondIndex, surfTemp);
		triggerSurfaceListeners();
	}
	
	/**
	* Remove a surface from the surface list.
	* 
	* @param index of the species in the array
	*/
	public void removeSurface(int index) {
		if (index < 0 || index >= surfaces.size())
			return;

		Surface removedSurface = surfaces.get(index);
		terrainNet.replaceSurfaceByEmpty(removedSurface);
		genNet.replaceSurfaceByEmpty(removedSurface);
		moveNet.replaceSurfaceByEmpty(removedSurface);
		deathNet.replaceSurfaceByEmpty(removedSurface);
		surfaces.remove(index);
		triggerSurfaceListeners();
	}


	// ========== SPECIES ==========

	/**
	* @return reference to the species list
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
	* Add a new species to the species list.
	* The order of the species list is the order used when evaluating the rules.
	* 
	* @param sp the species to add
	*/
	public void addSpecies(Species sp) {
		species.add(sp);
		triggerSpeciesListeners();
	}
	
	/**
	* Swap two species positions in the species list (no check on indexes)
	*/
	public void swapSpecies(int firstIndex, int secondIndex) {
		Species spTemp = species.get(firstIndex);
		species.set(firstIndex, species.get(secondIndex));
		species.set(secondIndex, spTemp);
		triggerSpeciesListeners();
	}
	
	/**
	* Remove the species at the given index. 
	* 
	* @param index of the species in the array
	*/
	public void removeSpecies(int index) {
		if (index < 0 || index >= species.size())
			return;

		Species removedSpecies = species.get(index);
		disconnectAllRulesFromSpecies(removedSpecies);
		terrainNet.replaceSpeciesByEmpty(removedSpecies);
		genNet.replaceSpeciesByEmpty(removedSpecies);
		moveNet.replaceSpeciesByEmpty(removedSpecies);
		deathNet.replaceSpeciesByEmpty(removedSpecies);
		species.remove(index);
		triggerSpeciesListeners();
	}
	
	/**
	* Delete all the members of all the species
	*/
	public void exterminateAllSpeciesMembers() {
		for (Species sp : species)
			exterminateSpeciesMembers(sp);
		triggerSpeciesListeners();
	}

	/**
	* Delete all the members of a specific species
	* 
	* @param sp the species to clear
	*/
	public void exterminateSpeciesMembers(Species sp) {
		sp.removeAllMembers();
	}

	/**
	* @return the species being currently processed in the game loop
	*/
	public Species getCurrentSpecies() { return currentSpecies; }

	/**
	* @return the entity being currently processed in the game loop
	*/
	public Entity getCurrentEntity() { return currentEntity; }


	// ========== NETWORKS ==========

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
	* @param copiedManager
	*/ 
	public void copyTerrain_TerrainNet(GameManager copiedManager) { 
		terrain = copiedManager.getTerrain();
		terrainNet = copiedManager.getTerrainNet();
	}


	// ========== RULES AND THEIR CONNECTIONS TO SPECIES ==========
	
	/**
	* Connect a new generation rule to a species 
	* and ensure that each species has at most one generation rule.
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
	* Connect a new movement rule to a species 
	* and ensures that each species has at most one movement rule.
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
	* Connect a new death rule to a species 
	* and ensures that each species has at most one death rule.
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
	* Remove all generation, movement & death rules linked to the specified species.
	* 
	* @param rule
	* @param sp
	*/
	public <R extends Rule> void connectRuleToSpecies(R rule, Species sp) {
		if(rule instanceof GenerationRule) {
			connectRuleToSpecies((GenerationRule) rule, sp);
		} else if(rule instanceof MovementRule) {
			connectRuleToSpecies((MovementRule) rule, sp);
		} else if(rule instanceof DeathRule) {
			connectRuleToSpecies((DeathRule) rule, sp);
		}
	}

	/**
	* Disconnect a generation rule from all the species it is applying to.
	* 
	* @param rule
	*/ 
	public void disconnectRule(GenerationRule rule) {
		for(Species sp : species) {
			if(speciesToGenRule.containsKey(sp) && speciesToGenRule.get(sp) == rule) speciesToGenRule.remove(sp);
		}
	}

	/**
	* Disconnect a movement rule from all the species it is applying to.
	* 
	* @param rule
	*/ 
	public void disconnectRule(MovementRule rule) {
		for(Species sp : species) {
			if(speciesToMoveRule.containsKey(sp) && speciesToMoveRule.get(sp) == rule) speciesToMoveRule.remove(sp);
		}
	}

	/**
	* Disconnect a death rule from all the species it is applying to.
	* 
	* @param rule
	*/ 
	public void disconnectRule(DeathRule rule) {
		for(Species sp : species) {
			if(speciesToDeathRule.containsKey(sp) && speciesToDeathRule.get(sp) == rule) speciesToDeathRule.remove(sp);
		}
	}

	/**
	* Disconnect a given rule from all the species it is applying to.
	* 
	* @param rule
	*/ 
	public void disconnectRule(Rule rule) {
		if(rule instanceof GenerationRule) {
			disconnectRule((GenerationRule) rule);
		} else if(rule instanceof MovementRule) {
			disconnectRule((MovementRule) rule);
		} else if(rule instanceof DeathRule) {
			disconnectRule((DeathRule) rule);
		}
	}

	/**
	* Disconnect all generation rules linked to the specified species.
	* 
	* @param sp
	*/ 
	public void disconnectGenRuleFromSpecies(Species sp) {
		speciesToGenRule.remove(sp);
	}

	/**
	* Disconnect all movement rules linked to the specified species.
	* 
	* @param sp
	*/ 
	public void disconnectMoveRuleFromSpecies(Species sp) {
		speciesToMoveRule.remove(sp);
	}

	/**
	* Disconnect all death rules linked to the specified species.
	* 
	* @param sp
	*/ 
	public void disconnectDeathRuleFromSpecies(Species sp) {
		speciesToDeathRule.remove(sp);
	}

	/**
	* Disconnect all generation, movement & death rules linked to the specified species.
	* 
	* @param sp
	*/ 
	public void disconnectAllRulesFromSpecies(Species sp) {
		disconnectGenRuleFromSpecies(sp);
		disconnectMoveRuleFromSpecies(sp);
		disconnectDeathRuleFromSpecies(sp);
	}

	/**
	* Get the rule of the given subclass connected to the given species. 
	* 
	* @param ruleClass
	* @param sp
	* @return a rule of type corresponding to ruleClass connected to species sp (if it exists)
	*/ 
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


	// ========== GAME EVOLUTION : WORLD SIMULATION BY EVALUATING NETWORKS AND APPLYING RULES ==========

	/**
	* Applies the rules and executes the corresponding actions in the following order : generation, movement, death.
	*/ 
	public void evolveGameState() {
		if(terrain.trigger(frame)) {
			for(TerrainSlot slot : terrain.getSlots())
				if(slot.isOccupied()) {
					apply(slot);
				}
		}

		deadEntities = new LinkedList<Entity>();
		for(Species sp : species) {
			if(sp.trigger(frame)) {
				currentSpecies = sp;

				if(speciesToMoveRule.containsKey(sp)) {
					apply(speciesToMoveRule.get(sp), sp);
				}

				if(speciesToDeathRule.containsKey(sp)) {
					apply(speciesToDeathRule.get(sp), sp);
				}

				if(speciesToGenRule.containsKey(sp)) {
					apply(speciesToGenRule.get(sp), sp);
				}
			}
		}
		removeDeadEntities();

		triggerGameListeners();

		frame++;			// TODO : think about frame number limit ?
	}

	private void apply(TerrainSlot slot) {
		try {
			terrainNet.evaluate(this, slot.getTerrainNode());
		} catch(NetworkIOException e) {
			e.printStackTrace();
		}
	}

	private void apply(GenerationRule rule, Species sp) {
		try {
			genNet.evaluate(this, rule.getTerminalNode());
			rule.apply(this);
		} catch(NetworkIOException e) {
			e.printStackTrace();
		}
	}

	private void apply(MovementRule rule, Species sp) {
		if(sp.getMembers().isEmpty()) return;

		for(Entity member : sp.getMembers()) {
			currentEntity = member;
			try {
				moveNet.evaluate(this, rule.getTerminalNode());
				rule.apply(this);
			} catch(NetworkIOException e) {
				e.printStackTrace();
			}
		}
	}

	private void apply(DeathRule rule, Species sp) {
		if(sp.getMembers().isEmpty()) return;

		for(Entity member : sp.getMembers()) {
			currentEntity = member;
			try {
				deathNet.evaluate(this, rule.getTerminalNode());
				rule.apply(this);
			} catch(NetworkIOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	* Mark an entity as dead and to be removed from its species members list (entitites cannot be removed directly to avoid concurrency errors).
	* 
	* @param entity
	*/ 
	public void addDeadEntity(Entity entity) {
		deadEntities.add(entity);
	}

	private void removeDeadEntities() {
		for(Entity entity : deadEntities) entity.getSpecies().removeMember(entity);
	}


	// ========== PRINTING ==========

	/**
	* Gives a written description of the current state of the game.
	* Can be useful for basic testing.
	* 
	* @return a string descrption of the current game state
	*/ 
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("Terrain : \n");
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

	/**
	* @return a string description of the surface species lists
	*/ 
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


	// ========== CHANGE LISTENERS ==========

	/**
	* The listener will receive an event when the game state gets updated 
	* 
	* @param listener
	*/ 
	public void addGameListener(ChangeListener listener) { gameListeners.add(listener); }

	/**
	* Notify listeners when game state changes
	*/ 
	private void triggerGameListeners() {
		for(ChangeListener listener : gameListeners) 
			listener.stateChanged(new ChangeEvent(this));
	}

	/**
	* The listener will receive an event when a surface is created, changed or removed
	* 
	* @param listener
	*/ 
	public void addSurfaceListener(ChangeListener listener) { surfaceListeners.add(listener); }

	/**
	* Notify listeners when something related to surfaces happens 
	*/ 
	private void triggerSurfaceListeners() {
		for(ChangeListener listener : surfaceListeners) 
			listener.stateChanged(new ChangeEvent(this));
	}

	/**
	* The listener will receive an event when a species is created, changed or removed
	* 
	* @param listener
	*/ 
	public void addSpeciesListener(ChangeListener listener) { speciesListeners.add(listener); }

	/**
	* Notify listeners when something related to species happens
	*/ 
	private void triggerSpeciesListeners() {
		for(ChangeListener listener : speciesListeners) 
			listener.stateChanged(new ChangeEvent(this));
	}

	/**
	* The listener will receive an event when the terrain's slot list changes
	* 
	* @param listener
	*/ 
	public void addTerrainListener(ChangeListener listener) { terrain.addChangeListener(listener); }
}
