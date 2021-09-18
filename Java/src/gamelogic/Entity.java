package gamelogic;

/**
* Each entity is part of a species.
* At each frame it can move and/or die.
* 
* @see Species
* @see MovementRule
* @see DeathRule
*/ 
public class Entity {

	private Species species;
	private Vec2D pos;
	private Integer birthTime;

	public Entity(Species species, Vec2D pos, Integer birthTime) {
		this.species = species;
		this.pos = pos;
		this.birthTime = birthTime;
	}

	public Vec2D getPos() {
		return pos;
	}

	public void setPos(Vec2D pos) {
		this.pos = pos;
	}

	public Species getSpecies() {
		return species;
	}

	public Integer getBirthTime() {
		return birthTime;
	}

}
