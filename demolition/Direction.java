package demolition.geo;

import demolition.constants.Constants;

public enum Direction {
	NORTH (Constants.PI / 2),
	EAST (0.0f),
	SOUTH (-3 * Constants.PI / 2),
	WEST (Constants.PI),
	NONE (null);
	public final Float theta;
	Direction(Float theta) {
		this.theta = theta;
	}
}