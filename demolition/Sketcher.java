package demolition;

import java.util.HashMap;

import processing.core.PApplet;
import processing.core.PImage;

public abstract class Sketcher {
	protected final PApplet app;
	protected final HashMap<String, PImage> imageRegister;

	public Sketcher(PApplet app, HashMap<String, PImage> imageRegister) {
		this.app = app;
		this.imageRegister = imageRegister;
	}
}