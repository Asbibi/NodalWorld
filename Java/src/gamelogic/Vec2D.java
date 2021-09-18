package gamelogic;

/**
* An instance of the Vec2D class is a 2D vector with integer coordinates.
* Some basic operations for these vectors are implemented, such as addition, substraction and equality testing. 
*/
public class Vec2D {

	private Integer x, y;

	public Vec2D(Integer x, Integer y) {
		this.x = x;
		this.y = y;
	}

	public Integer getX() {
		return x;
	}

	public Integer getY() {
		return y;
	}

	public static Vec2D add(Vec2D u, Vec2D v) {
		return new Vec2D(u.getX()+v.getX(), u.getY()+v.getY());
	}

	public static Vec2D sub(Vec2D u, Vec2D v) {
		return new Vec2D(u.getX()-v.getX(), u.getY()-v.getY());
	}

	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Vec2D)) return false;
		Vec2D v = (Vec2D) o;
		return (this.getX()==v.getX()) && (this.getY()==v.getY());
	}

}
