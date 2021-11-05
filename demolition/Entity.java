package demolition.entities;

import demolition.Sketcher;
import demolition.geo.Orientation;
import demolition.geo.Navigation;
import demolition.geo.Position;
import demolition.geo.Direction;
import demolition.geo.Map;
import demolition.geo.Tile;

import java.util.Arrays;
import java.util.Random;

import java.util.HashMap;

import processing.core.PApplet;
import processing.core.PImage;

public abstract class Entity extends Sketcher implements Orientation, Navigation { 

	protected Direction direction;
	protected Position position;
	protected final Map map;

	Entity(PApplet app, HashMap<String, PImage> imageRegister, Map map) {
		super(app, imageRegister);
		this.map = map;
		draw();
	}

	public void draw() {}

	public void move() {
		position = getNextPosition();
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