package gamelogic;

import java.io.Serializable;

/**
* Each entity is part of a species.
* At each frame it can move and/or die.
* 
* @see Species
* @see MovementRule
* @see DeathRule
*/ 
public class Entity implements Serializable {

	private Species species;
	private Vec2D pos;
	private Integer birthTime;

	/**
	* @param species
	* @param pos
	* @param birthTime
	*/ 
	public Entity(Species species, Vec2D pos, Integer birthTime) {
		this.species = species;
		this.pos = pos;
		this.birthTime = birthTime;
	}

	/**
	* @return the entity's current position
	*/ 
	public Vec2D getPos() {
		return pos;
	}

	/**
	* @param pos
	*/ 
	public void setPos(Vec2D pos) {
		this.pos = pos;
	}

	/**
	* @return the species this entity belongs to
	*/ 
	public Species getSpecies() {
		return species;
	}

	/**
	* @return the entity's birth time
	*/ 
	public Integer getBirthTime() {
		return birthTime;
	}

}
