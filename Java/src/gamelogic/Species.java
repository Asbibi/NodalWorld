package gamelogic;

import java.util.Collection;
import java.util.ArrayList;

/**
* A species represents a group of homogeneous entities, which all behave according to the same rules.
* At each frame it can generate new entities.
* 
* @see Entity
* @see GenerationRule
*/ 
public class Species {

	private String name;
	private Collection<Entity> members;

	/**
	* @param name
	*/ 
	public Species(String name) {
		this.name = name;
		members = new ArrayList<Entity>();
	}

	/**
	* @return the name of the species
	*/ 
	@Override
	public String toString() {
		return name;
	}

	/**
	* @param pos
	* @param birthTime
	*/ 
	public void addMemberAt(Vec2D pos, Integer birthTime) {
		members.add(new Entity(this, pos, birthTime));
	}

	/**
	* @param member
	*/ 
	public void removeMember(Entity member) {
		members.remove(member);
	}

	/**
	* @return all the members of the species
	*/ 
	public Collection<Entity> getMembers() {
		return members;
	}

}
