package gamelogic;

import java.util.Collection;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

/**
* A species represents a group of homogeneous entities, which all behave according to the same rules.
* At each frame it can generate new entities.
* 
* @see Entity
* @see GenerationRule, Element
*/ 
public class Species extends Element{

	private Collection<Entity> members;
	private Integer triggerTime;

	
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
		members = new ArrayList<Entity>();
		triggerTime = 1;
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

}
