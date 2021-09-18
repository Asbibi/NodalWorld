package gamelogic;

/**
* An instance of the Vec2D class is a 2D vector with integer coordinates.
* Some basic operations for these vectors are implemented, such as addition, substraction and equality testing. 
*/
public class Vec2D {

	private Integer x, y;

	/**
	* @param x 
	* @param y
	*/ 
	public Vec2D(Integer x, Integer y) {
		this.x = x;
		this.y = y;
	}

	/**
	* @return the 1st dimension of the vector
	*/ 
	public Integer getX() {
		return x;
	}

	/**
	* @return the 2nd dimension of the vector
	*/ 
	public Integer getY() {
		return y;
	}

	/**
	* @param u
	* @param v
	* @return the u+v vector
	*/ 
	public static Vec2D add(Vec2D u, Vec2D v) {
		return new Vec2D(u.getX()+v.getX(), u.getY()+v.getY());
	}

	/**
	* @param u
	* @param v
	* @return the u-v vector
	*/ 
	public static Vec2D sub(Vec2D u, Vec2D v) {
		return new Vec2D(u.getX()-v.getX(), u.getY()-v.getY());
	}

	/**
	* Checks the component-wise equality of the two vectors
	*/ 
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Vec2D)) return false;
		Vec2D v = (Vec2D) o;
		return (this.getX()==v.getX()) && (this.getY()==v.getY());
	}

	/**
	* @return a string in the format "(x, y)" describing the vector
	*/ 
	@Override
	public String toString() {
		return String.format("(%d, %d)", x, y);
	}

}
