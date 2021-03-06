package demolition.entities;

import demolition.geo.Position;
import demolition.geo.Direction;
import demolition.geo.Map;
import demolition.geo.Tile;
import demolition.utils.Animation;
import demolition.constants.Constants;

import java.util.Random;
import java.util.HashMap;

import processing.core.PApplet;
import processing.core.PImage;

public class RedEnemy extends Entity {

	private static final Random random = new Random();
	private long counter = 0;
	private final float cycleTime = Constants.SPRITE_DELAY * Constants.FPS;

	public RedEnemy(PApplet app, Map map, Position position) {
		super(app, map);
		this.position = position;
		drawPosition = Position.add(Position.scale(position, Constants.CELL_SIZE), 
									new Position(0, Constants.CELL_SIZE));
		nextPosition = position;
		anims.put(Direction.NORTH, new Animation(app, 4, drawPosition, "/src/main/resources/red_enemy/red_up", "png", Constants.SPRITE_DELAY));
		anims.put(Direction.SOUTH, new Animation(app, 4, drawPosition, "/src/main/resources/red_enemy/red_down", "png", Constants.SPRITE_DELAY));
		anims.put(Direction.WEST, new Animation(app, 4, drawPosition, "/src/main/resources/red_enemy/red_left", "png", Constants.SPRITE_DELAY));
		anims.put(Direction.EAST, new Animation(app, 4, drawPosition, "/src/main/resources/red_enemy/red_right", "png", Constants.SPRITE_DELAY));
		decideDirection();
	}

	private void decideDirection() {
		Direction choice = Direction.values()[random.nextInt(Direction.values().length)];
		while(choice.equals(direction) || choice.equals(Direction.NONE)) {
			choice = Direction.values()[random.nextInt(Direction.values().length)];
		}
		direction = choice;
		current = anims.get(direction);
	}

	@Override 
	public void react() {
		if(counter++ >= cycleTime) {
			counter = 0;
			if(!detect()) { 
				decideDirection();
			} else {
				map.grid[position.y][position.x].state = Tile.TState.EMPTY;
				position = nextPosition;
				map.grid[position.y][position.x].state = Tile.TState.ENTITY;
			}
		}
	}
}