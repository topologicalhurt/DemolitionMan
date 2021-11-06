package demolition.entities;

import demolition.Sketcher;
import demolition.geo.Orientation;
import demolition.geo.Navigation;
import demolition.geo.Position;
import demolition.geo.Direction;
import demolition.geo.Map;
import demolition.geo.Tile;
import demolition.utils.Animation;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.HashMap;

import processing.core.PApplet;
import processing.core.PImage;

public abstract class Entity implements Orientation, Navigation { 

	public Direction direction;
	public Position position;
	protected final Map map;
	protected final PApplet app;
	protected Animation current = null;

	Entity(PApplet app, Map map) {
		this.app = app;
		this.map = map;
	}

	public void draw() {}

	public void move() {
		position = getNextPosition();
		current.setPosition(position);
	}

	public Position getNextPosition() {
		Tile[] neighboringTiles = new Tile[4];
		Position[] neighboringPositions = Map.getNeighboringPositions(position);
		for(int i = 0; i < 3; i++) {
			neighboringTiles[i] = map.tileFromPosition(neighboringPositions[i]);
		}
		Position[] accepted = new Position[4];
		Random random = new Random();
		int j = 0;
		for(Tile n : neighboringTiles) { 
			if(n.state.canWalk) { 
				accepted[j++] = n.position;
			}
		}
		return accepted[random.nextInt(j)];
	}
}