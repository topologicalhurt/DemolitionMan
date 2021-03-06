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

	private int lastKeyCode;
	private int currentKeyCode;
	public boolean won;

	public void setCurrentKeyCode(int currentKeyCode) {
		this.currentKeyCode = currentKeyCode;
	}

	public Player(PApplet app, Map map) {
		super(app, map);
		position = map.playerStartPosition;
		drawPosition = Position.add(Position.scale(position, Constants.CELL_SIZE), 
									new Position(0, Constants.CELL_SIZE));
		nextPosition = position;
		anims.put(Direction.NORTH, new Animation(app, 4, drawPosition, "/src/main/resources/player/player_up", "png", Constants.SPRITE_DELAY));
		anims.put(Direction.SOUTH, new Animation(app, 4, drawPosition, "/src/main/resources/player/player", "png", Constants.SPRITE_DELAY));
		anims.put(Direction.WEST, new Animation(app, 4, drawPosition, "/src/main/resources/player/player_left", "png", Constants.SPRITE_DELAY));
		anims.put(Direction.EAST, new Animation(app, 4, drawPosition, "/src/main/resources/player/player_right", "png", Constants.SPRITE_DELAY));
		current = anims.get(Direction.SOUTH);
	}


	public void checkWin() {
		if(map.grid[position.y][position.x].state.isWin) {
			won = true;
		} else {
			won = false;
		}
	}

	@Override
	public void getNextPosition() {
		switch(currentKeyCode) {
			case KeyEvent.VK_UP:
				direction = Direction.NORTH;
				break;
			case KeyEvent.VK_DOWN:
				direction = Direction.SOUTH;
				break;
			case KeyEvent.VK_LEFT:
				direction = Direction.WEST;
				break;
			case KeyEvent.VK_RIGHT:
				direction = Direction.EAST;
				break;
			default:
				return;
		}
		current = anims.get(direction);
		if(currentKeyCode != lastKeyCode) {
			lastKeyCode = currentKeyCode;
			return;
		}
		nextPosition = Position.add(position, direction.dir);
	}

}