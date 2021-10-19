package gamelogic.terrains;

import gamelogic.TerrainModel;
import gamelogic.Vec2D;

import java.util.Random;

/**
* A terrain model with randomly placed terrain units following a given density.
*/ 
public class TerrainModelRandom implements TerrainModel {

	private int seed;
	private double density;

	/**
	* @param seed the seed for the pseudo-random generator
	*/ 
	public void setSeed(int seed) {
		this.seed = seed;
	}

	/**
	* @param density the density of randomly placed terrain units
	*/ 
	public void setDensity(double density) {
		this.density = density;
	}

	/**
	* @param pos
	* @return true if the given position is inside one of the random terrain units, otherwise false
	*/ 
	@Override
	public boolean hasSurfaceAt(Vec2D pos) {
		return (randGen(pos.getX(), pos.getY(), seed) < density);
	}

	private double randGen(int a, int b, int c) {
		double val = Math.sin((47 + 13*a * 78*b)*(4237 - 153*c));
		return val-Math.floor(val);
	}

}
