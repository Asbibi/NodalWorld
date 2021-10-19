package gamelogic;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

import java.lang.Object;

/**
* A species represents a group of homogeneous entities, which all behave according to the same rules.
* At each frame it can generate new entities.
* 
* @see Entity
* @see GenerationRule
* @see Element
*/ 
public class Species extends Element{


	// ========== MEMBER VARIABLES ==========

	private static final long serialVersionUID = 5143931481581010388L;

	private static int idCounter = 0;

	private int id;
	private Collection<Entity> members;
	private Integer triggerTime;


	// ========== INITIALIZATION ==========
	
	/**
	* @param name
	*/ 
	public Species(String name) {
		this(name, null);
	}
	
	/**
	* @param name
	* @param path to the image
	*/ 	
	public Species(String name, String imagePath) {
		super(name, imagePath);
		id = idCounter;
		idCounter++;
		members = new ArrayList<Entity>();
		triggerTime = 1;
	}

	/**
	* Check if two species are the same (using a unique id generated for each species)
	*/ 
	@Override
	public boolean equals(Object o) {
		if(o == null) return false;
		if(!(o instanceof Species)) return false;

		Species sp = (Species) o;
		return id == sp.id;
	}
	
	/**
	* @return the unique species id
	*/ 
	public int getId() {
		return id;
	}

	/**
	* Create a new member of this species at a given position ans birth time
	* 
	* @param pos
	* @param birthTime
	*/ 
	public void addMemberAt(Vec2D pos, Integer birthTime) {
		members.add(new Entity(this, pos, birthTime));
	}

	/**
	* Remove a member from this species
	* 
	* @param member
	*/ 
	public void removeMember(Entity member) {
		members.remove(member);
	}
	
	/**
	* Clear the members list
	*/ 
	public void removeAllMembers() {
		members.clear();
	}
	
	/**
	* @return all the members of the species
	*/ 
	public Collection<Entity> getMembers() {
		return members;
	}

	/**
	* @return the species' trigger time
	*/ 
	public Integer getTriggerTime() {
		return triggerTime;
	}

	/**
	* @param time
	*/ 
	public void setTriggerTime(Integer time) {
		triggerTime = Math.max(time, 1);
	}

	/**
	* @param frame
	* @return true if species is triggered at the given frame, otherwise false
	*/ 
	public boolean trigger(Integer frame) {
		return (frame%triggerTime == 0);
	}

	
	// ========== EMPTY SPECIES ==========
	
	
	
	private static Species empty = new Species("empty");

	/**
	* @return an "empty" species used as a default values when one is needed
	*/ 
	public static Species getEmpty() {
		return empty;
	}
	
	/**
	* This method synchronizes the idCounter on a bunch of species, so that the id of a new species will be coherent with the ids of the species gave.
	* This method can only make the idCounter increase, which will happen if the idCounter is lower or equal at the id of one of the species gave. 
	* @param species the list of the species to sync the idCounter on
	*/ 
	public static void synchIdCounter(List<Species> species) {
		int lastId = 0;
		for (Species sp : species)
			lastId = Math.max(lastId, sp.id);
		idCounter = Math.max(idCounter, lastId+1);	// +1 because we want the counter to be the id of the NEXT species, and not the newer one
	}
}
