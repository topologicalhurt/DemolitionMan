package demolition.utils;

import demolition.geo.Position;
import demolition.Sketcher;

import java.io.FileNotFoundException;

import processing.core.PApplet;
import processing.core.PImage;

public class Animation extends Sketcher {

	private final int numFrames;
	private int currentFrameIndex;
	private PImage currentFrame;
	private Position position;
	private float cycleTime;

	public Animation(PApplet app, int numFrames, Position position, String prefix, String ext, float cycleTime) { 
		super(app, new PImage[numFrames]);
		this.numFrames = numFrames;
		this.position = position;
		this.cycleTime = cycleTime;
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
		try {
			Thread.sleep((long) (1000*cycleTime / numFrames));
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		currentFrameIndex = (currentFrameIndex + 1) % numFrames;
		currentFrame = frames[currentFrameIndex];
		app.image(currentFrame, position.x, position.y);
	}
}