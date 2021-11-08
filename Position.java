package demolition.geo;

public class Position { 
	public int x;
	public int y;

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public static Position add(Position p, Position p2) {
		return new Position(p.x + p2.x, p.y + p2.y);
	}

	public static Position scale(Position p, int s) {
		return new Position(s * p.x, s * p.y);
	}

	public static boolean between(Position p, Position lower, Position upper) { 
		return (lower.x <= p.x && p.x <= upper.x) && (lower.y <= p.y && p.y <= upper.y);
	}

	public static Position clockwisePerp(Position p) {
		return new Position(p.y, -1 * p.x);
	}
}