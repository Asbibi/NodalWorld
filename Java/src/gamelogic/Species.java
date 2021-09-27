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
* @see GenerationRule
*/ 
public class Species {

	private String name;
	private Image image;
	private Collection<Entity> members;
	private Integer triggerTime;

	/**
	* @param name
	* @param path to the image
	*/ 
	public Species(String name, String imagePath) {
		this(name, Toolkit.getDefaultToolkit().getImage(imagePath));
	}
	
	/**
	* @param name
	* @param image of the species for display
	*/ 
	public Species(String name, Image image) {
		this.name = name;
		this.image = image;
		members = new ArrayList<Entity>();
		triggerTime = 1;
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
	* @return the image for display of the species
	*/ 
	public Image getImage() {
		return image;
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
