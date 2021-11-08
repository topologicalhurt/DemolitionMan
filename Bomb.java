package demolition.entities;

import demolition.geo.Position;
import demolition.geo.Direction;
import demolition.geo.Tile;
import demolition.geo.Map;
import demolition.utils.Animation;
import demolition.constants.Constants;

import java.lang.Math;
import java.util.Random;
import java.util.HashMap;
import java.util.ArrayList;
import java.awt.event.KeyEvent;

import processing.core.PApplet;
import processing.core.PImage;

public class Bomb extends Entity {

	private int currentKeyCode;
	private long timeSincePress = 0;
	private long counter = 0;
	private final float explosionCycle = 0.5f * Constants.FPS;
	private final float detonationCycle = 2 * Constants.FPS;
	private boolean detonating;
	private boolean exploding;
	private static ArrayList<Position> explosionPositions;

	public void setCurrentKeyCode(int currentKeyCode) {
		this.currentKeyCode = currentKeyCode;
	}

	public Bomb(PApplet app, Map map) {
		super(app, map);
		direction = Direction.NORTH;
		anims.put(Direction.NONE, new Animation(app, 4, null, "/src/main/resources/bomb/bomb", "png", 2.0f));
		anims.put(Direction.NORTH, new Animation(app, 4, null, "/src/main/resources/bomb/bomb", "png", 2.0f));
		current = anims.get(Direction.NONE);
		explosionPositions = new ArrayList<Position>();
	}

	@Override
	public void draw() {
		if(detonating) {
			current.draw();
			if(++counter >= detonationCycle) {
				explode();
				counter = 0;
				detonating = false;
				exploding = true;
			}
		} else if(exploding) {
			if(++counter >= explosionCycle) {
				endExplosionTiles();
				exploding = false;
			}
		}
	}

	@Override 
	public void move() {}

	@Override 
	public void getNextPosition() {}

	@Override
	public boolean detect() {
		return map.tileFromPosition(nextPosition).state.combustible;
	}

	@Override 
	public void react() {
		double absDistX = Math.abs(position.x - nextPosition.x);
		double absDistY = Math.abs(position.y - nextPosition.y);

		if (absDistX == 2 && direction.equals(Direction.WEST)) {
			map.grid[nextPosition.y][nextPosition.x].state = Tile.TState.EXPLOSION_LEFT;
		} else if (absDistX == 2 && direction.equals(Direction.EAST)) {
			map.grid[nextPosition.y][nextPosition.x].state = Tile.TState.EXPLOSION_RIGHT;
		} else if (absDistY == 2 && direction.equals(Direction.SOUTH)) {
			map.grid[nextPosition.y][nextPosition.x].state = Tile.TState.EXPLOSION_BOTTOM;
		} else if (absDistY == 2 && direction.equals(Direction.NORTH)) {
			map.grid[nextPosition.y][nextPosition.x].state = Tile.TState.EXPLOSION_TOP;
		} else if(direction.equals(Direction.WEST) || direction.equals(Direction.EAST)) {
			map.grid[nextPosition.y][nextPosition.x].state = Tile.TState.EXPLOSION_HORIZONTAL;
		} else {
			map.grid[nextPosition.y][nextPosition.x].state = Tile.TState.EXPLOSION_VERTICAL;
		}

		explosionPositions.add(nextPosition);
	}

	private void endExplosionTiles() {
		map.grid[position.y][position.x].state = Tile.TState.EMPTY;
		for(Position p : explosionPositions) {
			map.grid[p.y][p.x].state = Tile.TState.EMPTY;
		}
	}

	private boolean destroy() {
		if(detect()) {
			react();
			return true;
		}
		return false;
	}

	@Override
	protected void updateDrawPos() {
		drawPosition = Position.add(Position.scale(Position.add(position, new Position(0, 1)), Constants.CELL_SIZE), 
									new Position(0, Constants.CELL_SIZE));
		current.setPosition(drawPosition);
	}

	public void place(Position position) {
		if(currentKeyCode == KeyEvent.VK_SPACE && !detonating) {
			detonating = true;
			this.position = position;
			updateDrawPos();
		}
	}

	private void explode() {
		for(int i = 0; i < 4; i++) {
			direction = direction.clockwise.get(direction);
			nextPosition = Position.add(position, direction.dir);
			if(map.grid[nextPosition.y][nextPosition.x].state.canBreak) {
				destroy();
				continue;
			}
			nextPosition = destroy() ? Position.add(nextPosition, direction.dir) : nextPosition;
			destroy();
		}
		map.grid[position.y][position.x].state = Tile.TState.EXPLOSION_CENTRE;
	}
}