package demolition.geo;

import demolition.exceptions.MapSymbolNotFoundException;
import demolition.misc.Event;

public class Tile implements Orientation {

	public enum TState {

		SOLID ('W', "solid", false, false, false, false),
		BROKEN ('B', "broken", false, true, false, false),
		EMPTY (' ', "empty",  true, false, false, false),
		GOAL ('G', "goal", true, false, true, false),
		PLAYER ('P', "empty", true, false, false, true),
		ENEMYY ('Y', "empty", true, true, false, true),
		ENEMYR ('R', "empty", true, true, false, true),
		BOMB (null, "bomb", false, false, false, true);

		private final Character repr; 
		public final String assetName;
		public final boolean canWalk;
		public final boolean canBreak;
		public final boolean isWin;
		public final boolean holdsEntity;
		TState(Character repr, String assetName, boolean canWalk, boolean canBreak, 
				boolean isWin, boolean holdsEntity) {
			this.repr = repr;
			this.assetName = assetName;
			this.canWalk = canWalk; 
			this.canBreak = canBreak;
			this.isWin = isWin;
			this.holdsEntity = holdsEntity;
		}
	}

	public TState state;
	private Event event;
	public Position position;
	private static Direction direction;

	private final class SpawnPlayer extends Event { 

		@Override 
		public void run() {

		}
	}

	private final class SpawnRedEnemy extends Event {

		@Override 
		public void run() {

		}
	}

	private final class SpawnYellowEnemy extends Event {

		@Override 
		public void run() {
		}
	}

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
