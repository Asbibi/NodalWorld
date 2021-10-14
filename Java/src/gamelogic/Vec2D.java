package gamelogic;

import java.io.Serializable;

/**
* An instance of the Vec2D class is a 2D vector with integer coordinates.
* Some basic operations for these vectors are implemented, such as addition, substraction, equality testing, dot product, etc.
*/
public class Vec2D implements Serializable {

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
	* @return the norm of the vector
	*/ 
	public double getNorm() {
		return Math.sqrt(x*x + y*y);
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
	* @param a
	* @param u
	* @return the a*u vector
	*/ 
	public static Vec2D multiply(int a, Vec2D u) {
		return new Vec2D(u.getX() * a, u.getY() * a);
	}
	/**
	* @param a
	* @param u
	* @return the u/a vector
	*/ 
	public static Vec2D divide(int a, Vec2D u) {
		if (a == 0)
			return null;
		return new Vec2D(u.getX() / a, u.getY() / a);
	}
	
	/**
	* @param a
	* @param u
	* @return the a*u vector (the components are truncated so that they remain integers)
	*/ 
	public static Vec2D multiply(double a, Vec2D u) {
		return new Vec2D((int)(u.getX() * a), (int)(u.getY() * a));
	}
	/**
	* @param a
	* @param u
	* @return the u/a vector (the components are truncated so that they remain integers)
	*/ 
	public static Vec2D divide(double a, Vec2D u) {
		if (a == 0)
			return null;
		return new Vec2D((int)(u.getX() / a), (int)(u.getY() / a));
	}	

	/**
	* @param u
	* @param v
	* @return the scalar product of u and v
	*/ 
	public static int scalar(Vec2D u, Vec2D v) {
		return u.getX()*v.getX() + u.getY()*v.getY();
	}

	/**
	* @param u
	* @param v
	* @return the vectorial product of u and v
	*/ 
	public static int vectorialProduct(Vec2D u, Vec2D v) {
		return u.getX()*v.getY() - u.getY()*v.getX();
	}

	/**
	* @param u
	* @param v
	* @return the angle between u and v
	*/ 
	public static double angleBetween(Vec2D u, Vec2D v) {
		double normProduct = u.getNorm() * v.getNorm();
		if (normProduct == 0)
			return 0;
		double cosAngle = scalar(u,v)/(normProduct);
		int signAngle = vectorialProduct(u,v);
		return (signAngle < 0 ? -1 : 1) * Math.acos(cosAngle);
	}
	

	/**
	* Checks the component-wise equality of the two vectors
	* 
	* @return true if both components are equal, otherwise false
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
