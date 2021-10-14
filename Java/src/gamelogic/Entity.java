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
	
	/**
	* @param areaStartPosition
	* @param areaDimensions
	* @return true if the entity is in the rectangle defined by the area start position and dimenions, otherwise false
	*/ 
	public boolean isInArea(Vec2D areaStartPosition, Vec2D areaDimensions) {
		int replacedX = pos.getX() - areaStartPosition.getX();
		int replacedY = pos.getY() - areaStartPosition.getY();
		if (replacedX < 0 || replacedX >= areaDimensions.getX())
			return false;
		if (replacedY < 0 || replacedY >= areaDimensions.getY())
			return false;
		return true;
	}

}
