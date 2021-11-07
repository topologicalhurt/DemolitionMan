package demolition.geo;

import demolition.constants.Constants;

public enum Direction {
	NORTH (new Position(0, -1)),
	EAST (new Position(1, 0)),
	SOUTH (new Position(0, 1)),
	WEST (new Position(-1, 0)),
	NONE (null);
	public final Position dir;
	Direction(Position dir) {
		this.dir = dir;
	}
}