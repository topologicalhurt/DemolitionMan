package demolition.geo;

import java.util.HashMap;

import demolition.constants.Constants;

public enum Direction {
	NORTH (new Position(0, -1)),
	EAST (new Position(1, 0)),
	SOUTH (new Position(0, 1)),
	WEST (new Position(-1, 0)),
	NONE (null);

	public static final HashMap<Direction, Direction> clockwise = new HashMap<Direction, Direction>() {{
			put(NORTH, EAST);
			put(EAST, SOUTH);
			put(SOUTH, WEST);
			put(WEST, NORTH);
		}};;

	public final Position dir;
	Direction(Position dir) {
		this.dir = dir;
	}
}