package demolition;

import java.util.HashMap;

import processing.core.PApplet;
import processing.core.PImage;

public abstract class Sketcher {
	protected final PApplet app;
	protected final HashMap<String, PImage> imageRegister;
	public final PImage[] frames;

	public void draw() {}

	public Sketcher(PApplet app, HashMap<String, PImage> imageRegister) {
		/* Constructor for sketching a scene from a collection of images */
		this.app = app;
		this.imageRegister = imageRegister;
		frames = null;
	}

	public Sketcher(PApplet app, PImage[] frames) {
		/* Overloaded constructor for sketching an animation */
		this.app = app;
		this.frames = frames;
		imageRegister = null;
	}


}