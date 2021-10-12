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
* 
* @see Rule
* @see Terrain
* @see Species
* @see Entity
*/
public class GameManager implements Serializable {

	private Integer frame;

	private Terrain terrain;

	private List<Surface> surfaces;
	private List<Species> species;

	private Map<Species, GenerationRule> speciesToGenRule;
	private Map<Species, MovementRule> speciesToMoveRule;
	private Map<Species, DeathRule> speciesToDeathRule;

	private transient Species currentSpecies;
	private transient Entity currentEntity;

	private Network terrainNet, genNet, moveNet, deathNet;

	private transient List<ChangeListener> gameListeners, surfaceListeners, speciesListeners;

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
		species.add(Species.getEmpty());

		speciesToGenRule = new HashMap<Species, GenerationRule>();
		speciesToMoveRule = new HashMap<Species, MovementRule>();
		speciesToDeathRule = new HashMap<Species, DeathRule>();

		currentSpecies = null;
		currentEntity = null;

		terrainNet = new Network();
		genNet = new Network();
		moveNet = new Network();
		deathNet = new Network();

		gameListeners = new LinkedList<ChangeListener>();
		surfaceListeners = new LinkedList<ChangeListener>();
		speciesListeners = new LinkedList<ChangeListener>();
	}
	
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
	public void reinitWorld() {
		frame = 0;
		exterminateAllSpeciesMembers();
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
		triggerSurfaceListeners();
	}
	
	/**
	* Swap two surfaces positions in the surfaces list (no check on indexes)
	*/
	public void swapSurfaces(int firstIndex, int secondIndex) {
		Surface surfTemp = surfaces.get(firstIndex);
		surfaces.set(firstIndex, surfaces.get(secondIndex));
		surfaces.set(secondIndex, surfTemp);
		triggerSurfaceListeners();
	}
	
	/**
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
	*/
	public void exterminateSpeciesMembers(Species sp) {
		sp.removeAllMembers();
	}

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
	* @return the death netork
	*/ 
	public void copyTerrain_TerrainNet(GameManager copiedManager) { 
		terrain = copiedManager.getTerrain();
		terrainNet = copiedManager.getTerrainNet();
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
	* Remove all generation, movement & death rules linked to the specified species.
	* 
	* @param rule
	* @param sp
	*/
	public void disconnectAllRulesFromSpecies(Species sp) {
		if(speciesToGenRule.containsKey(sp))
			speciesToGenRule.remove(sp);
		if(speciesToMoveRule.containsKey(sp))
			speciesToMoveRule.remove(sp);
		if(speciesToDeathRule.containsKey(sp))
			speciesToDeathRule.remove(sp);
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
	public void evolveGameState() {
		if(terrain.trigger(frame)) {
			for(TerrainSlot slot : terrain.getSlots())
				if(slot.isOccupied()) {
					apply(slot);
				}
		}

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
	* Gives a written description of the current state of the game.
	* Can be useful for basic testing.
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


	// ========== Change Listeners ==========

	/**
	* The listener will receive an event when the game state gets updated 
	* 
	* @param listener
	*/ 
	public void addGameListener(ChangeListener listener) { gameListeners.add(listener); }

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
