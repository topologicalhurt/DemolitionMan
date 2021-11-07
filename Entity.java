package demolition.entities;

import demolition.Sketcher;
import demolition.geo.Orientation;
import demolition.geo.Navigation;
import demolition.geo.Collision;
import demolition.geo.Position;
import demolition.geo.Direction;
import demolition.geo.Map;
import demolition.geo.Tile;
import demolition.utils.Animation;

import demolition.constants.Constants;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Random;
import java.util.HashMap;

import processing.core.PApplet;
import processing.core.PImage;

public abstract class Entity implements Orientation, Navigation, Collision { 

	public Direction direction;
	public Position position;
	protected Position nextPosition;
	protected Position drawPosition;
	protected final Map map;
	protected final PApplet app;
	protected HashMap<Direction, Animation> anims;
	protected Animation current;

	Entity(PApplet app, Map map) {
		this.app = app;
		this.map = map;
		anims = new HashMap<Direction, Animation>();
	}

	public void draw() {
		current.draw();
	}

	public boolean detect() {
		return map.tileFromPosition(nextPosition).state.canWalk;
	}

	public void react() {
		position = detect() ? nextPosition : position;
	}

	protected void updateDrawPos() {
		drawPosition = Position.add(Position.scale(position, Constants.CELL_SIZE), 
									new Position(0, Constants.CELL_SIZE));
		current.setPosition(drawPosition);
	}

	public void move() {
		getNextPosition();
		react();
		updateDrawPos();
	}

	public void getNextPosition() {
		Tile[] neighboringTiles = new Tile[4];
		Position[] neighboringPositions = Map.getNeighboringPositions(position);
		for(int i = 0; i < 3; i++) {
			neighboringTiles[i] = map.tileFromPosition(neighboringPositions[i]);
		}
		ArrayList<Position> accepted = new ArrayList<Position>();
		Random random = new Random();
		for(Tile n : neighboringTiles) { 
			if(n.state.canWalk) { 
				accepted.add(n.position);
			}
		}
		nextPosition = accepted.get(random.nextInt(accepted.size()));
	}
}