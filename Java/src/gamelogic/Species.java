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

	public Species(String name) {
		this.name = name;
		members = new ArrayList<Entity>();
	}

	public String getName() {
		return name;
	}

	public void addMemberAt(Vec2D pos, Integer birthTime) {
		members.add(new Entity(this, pos, birthTime));
	}

	public void removeMember(Entity member) {
		members.remove(member);
	}

	public Collection<Entity> getMembers() {
		return members;
	}

}
