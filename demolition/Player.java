package demolition.entities;

import demolition.geo.Position;
import demolition.geo.Direction;
import demolition.geo.Map;
import demolition.utils.Animation;
import demolition.constants.Constants;

import java.util.HashMap;
import java.awt.event.KeyEvent;

import processing.core.PApplet;

public class Player extends Entity {

	private int keyCode;
	private final Animation left, right, up, down;

	public Player(PApplet app, Map map) {
		super(app, map);
		position = map.playerStartPosition;
		left = new Animation(app, 4, position, "/src/main/resources/player/player_left", "png", 0.2f);
		right = new Animation(app, 4, position, "/src/main/resources/player/player_right", "png", 0.2f);
		up = new Animation(app, 4, position, "/src/main/resources/player/player_up", "png", 0.2f);
		down = new Animation(app, 4, position, "/src/main/resources/player/player", "png", 0.2f);
		current = down;
	}

	public void setPressedKey(int keyCode) {
		this.keyCode = keyCode;
	}

	@Override
	public void draw() {
		current.draw();
	}

	@Override
	public Position getNextPosition() {
		switch(keyCode) {
			case KeyEvent.VK_UP:
				direction = Direction.NORTH;
				current = up;
				return Position.add(position, new Position(0, -Constants.CELL_SIZE.y));
			case KeyEvent.VK_DOWN:
				direction = Direction.SOUTH;
				current = down;
				return Position.add(position, new Position(0, Constants.CELL_SIZE.y));
			case KeyEvent.VK_LEFT:
				direction = Direction.WEST;
				current = left;
				return Position.add(position, new Position(-Constants.CELL_SIZE.x, 0));
			case KeyEvent.VK_RIGHT:
				direction = Direction.EAST;
				current = right;
				return Position.add(position, new Position(Constants.CELL_SIZE.x, 0));
			default: 
				return position;
		}
	}

}