package demolition.utils;

import demolition.geo.Position;
import demolition.Sketcher;
import demolition.constants.Constants;

import java.io.FileNotFoundException;

import processing.core.PApplet;
import processing.core.PImage;

public class Animation extends Sketcher {

	private final int numFrames;
	private int currentFrameIndex;
	private PImage currentFrame;
	private Position position;
	private double cycleTime;
	private long counter = 0;

	public Animation(PApplet app, int numFrames, Position position, String prefix, String ext, double cycleTime) { 
		super(app, new PImage[numFrames]);
		this.numFrames = numFrames;
		this.position = position;
		this.cycleTime =  (cycleTime / numFrames) * Constants.FPS;
		currentFrameIndex = 0;
		for(int i = 0; i < numFrames; i++) {
			frames[i] = app.loadImage(String.format("%s%d", prefix, i + 1, ext) + "." + ext);
		}
		currentFrame = frames[0];
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	@Override 
	public void draw() {
		app.image(currentFrame, position.x, position.y);
		if(counter++ >= cycleTime) {
			counter = 0;
			currentFrameIndex = (currentFrameIndex + 1) % numFrames;
			currentFrame = frames[currentFrameIndex];
		}
	}
}