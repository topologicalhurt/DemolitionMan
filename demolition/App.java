package demolition;

import demolition.geo.Map;
import demolition.constants.Constants;
import demolition.exceptions.InvalidMapException;

import java.util.HashMap;

import processing.core.PApplet;
import processing.core.PImage;

enum GameState {
    PLAY,
    DEATH,
    WIN
}

public final class App extends PApplet {

    private Map map;
    private int level = 1;
    public static GameState gameState = GameState.PLAY;
    private HashMap<String, PImage> imageRegister;

    private void loadMap() {
        try {
            map = new Map(this, imageRegister, String.format("level%d.txt", level));
        } catch(InvalidMapException e) {
            e.printStackTrace();
        }
    }

    public App() {
        imageRegister = new HashMap<String, PImage>();
    }

    public void settings() {
        size(Constants.WIDTH, Constants.HEIGHT);
    }

    public void setup() {
        frameRate(Constants.FPS);
        imageRegister.put("solid", loadImage("/src/main/resources/wall/solid.png"));
        imageRegister.put("goal", loadImage("/src/main/resources/goal/goal.png"));
        imageRegister.put("empty", loadImage("/src/main/resources/empty/empty.png"));
        imageRegister.put("broken", loadImage("/src/main/resources/broken/broken.png"));
        loadMap();
    }

    public void draw() {
        background(0, 0, 0);
        switch(gameState) {
            case PLAY:
                playScreen();
                break;
            case DEATH:
                deathScreen();
                break; 
            case WIN:
                winScreen();
                break;
        }
    }

    public void nextLevel() {
        level++;
        loadMap();
    }

    private void playScreen() {
        map.draw();
    }

    private void deathScreen() {

    }

    private void winScreen() {

    }

    public static void main(String[] args) {
        PApplet.main("demolition.App");
    }
}
