package demolition.entities;

import demolition.geo.Position;
import demolition.geo.Direction;
import demolition.geo.Map;

import java.util.HashMap;

import processing.core.PApplet;
import processing.core.PImage;

public class Player extends Entity {

	Player(PApplet app, HashMap<String, PImage> imageRegister, Map map) {
		super(app, imageRegister, map);
	}

	@Override 
	public void draw() {

	}

	@Override
	public Position getNextPosition() {
		return null;
	}

}