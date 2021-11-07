package demolition.entities;

import demolition.geo.Position;
import demolition.geo.Direction;
import demolition.geo.Map;
import demolition.utils.Animation;
import demolition.constants.Constants;

import java.util.Random;
import java.util.HashMap;

import processing.core.PApplet;
import processing.core.PImage;

public class RedEnemy extends Entity {

	public RedEnemy(PApplet app, Map map, Position position) {
		super(app, map);
		this.position = position;
		drawPosition = Position.add(Position.scale(position, Constants.CELL_SIZE), 
									new Position(0, Constants.CELL_SIZE));
		Direction randDir = Direction.values()[new Random().nextInt(Direction.values().length)];
		direction = randDir;
		nextPosition = position;
		anims.put(Direction.NORTH, new Animation(app, 4, drawPosition, "/src/main/resources/red_enemy/red_up", "png", 0.2f));
		anims.put(Direction.SOUTH, new Animation(app, 4, drawPosition, "/src/main/resources/red_enemy/red", "png", 0.2f));
		anims.put(Direction.WEST, new Animation(app, 4, drawPosition, "/src/main/resources/red_enemy/red_left", "png", 0.2f));
		anims.put(Direction.EAST, new Animation(app, 4, drawPosition, "/src/main/resources/red_enemy/red_right", "png", 0.2f));
		current = anims.get(direction);
	}

	@Override 
	public void move() {
		getNextPosition();
		position = nextPosition;
		updateDrawPos();
	}

}