package demolition.geo;

import demolition.exceptions.MapSymbolNotFoundException;

public class Tile implements Orientation {

	public static enum TState {

		SOLID ('W', "solid", false, false, false, false, false, false),
		BROKEN ('B', "broken", false, true, true, false, false, false),
		EMPTY (' ', "empty",  true, true, false, false, false, false),
		GOAL ('G', "goal", true, false, false, true, false, false),
		PLAYER ('P', "empty", true, true, false, false, true, false),
		ENEMYY ('Y', "empty", true, true, false, false, true, false),
		ENEMYR ('R', "empty", true, true, false, false, true, false),
		ENTITY (null, "empty", false, true, false, false, true, false),
		EXPLOSION_CENTRE (null, "centre", true, false, true, false, false, true),
		EXPLOSION_TOP (null, "end_top", true, false, true, false, false, true),
		EXPLOSION_BOTTOM (null, "end_bottom", true, false, true, false, false, true),
		EXPLOSION_LEFT (null, "end_left", true, false, true, false, false, true),
		EXPLOSION_RIGHT (null, "end_right", true, false, true, false, false, true),
		EXPLOSION_HORIZONTAL (null, "horizontal", true, false, true, false, false, true),
		EXPLOSION_VERTICAL (null, "vertical", true, false, true, false, false, true);

		private final Character repr; 
		public final String assetName;
		public final boolean canWalk;
		public final boolean combustible;
		public final boolean canBreak;
		public final boolean isWin;
		public final boolean holdsEntity;
		public final boolean death;
		TState(Character repr, String assetName, boolean canWalk, boolean combustible, boolean canBreak, 
				boolean isWin, boolean holdsEntity, boolean death) {
			this.repr = repr;
			this.assetName = assetName;
			this.canWalk = canWalk; 
			this.combustible = combustible;
			this.canBreak = canBreak;
			this.isWin = isWin;
			this.holdsEntity = holdsEntity;
			this.death = death;
		}
	}

	public TState state;
	public Position position;
	private static Direction direction;

	public Tile(Character repr, Position position) throws MapSymbolNotFoundException {
		switch(repr) {
			case 'W':
				state = TState.SOLID;
				break;
			case 'B':
				state = TState.BROKEN;
				break;
			case ' ':
				state = TState.EMPTY;
				break;
			case 'G':
				state = TState.GOAL;
				break;
			case 'P':
				state = TState.PLAYER;
				break;
			case 'Y':
				state = TState.ENEMYY;
				break;
			case 'R':
				state = TState.ENEMYR;
				break;
			default:
				throw new MapSymbolNotFoundException("Unknown symbol in file.");
		}
		this.position = position;
	}
}
