package demolition;

import demolition.geo.Map;
import demolition.utils.Animation;
import demolition.constants.Constants;
import demolition.exceptions.InvalidMapException;
import demolition.entities.Player;

import java.util.HashMap;

import processing.core.PApplet;
import processing.core.PImage;

enum GameState {
    PLAY,
    DEATH,
    WIN
}

public final class App extends PApplet {

    private static Map map;
    private static Player player;
    private static int level = 1;
    public static GameState gameState = GameState.PLAY;
    private static HashMap<String, PImage> scene;

    private void loadMap() {
        try {
            map = new Map(this, scene, String.format("level%d.txt", level));
        } catch(InvalidMapException e) {
            e.printStackTrace();
        }
    }

    public App() {
        scene = new HashMap<String, PImage>();
    }

    public void settings() {
        size(Constants.WIDTH, Constants.HEIGHT);
    }

    public void setup() {
        frameRate(Constants.FPS);
        scene.put("solid", loadImage("/src/main/resources/wall/solid.png"));
        scene.put("goal", loadImage("/src/main/resources/goal/goal.png"));
        scene.put("empty", loadImage("/src/main/resources/empty/empty.png"));
        scene.put("broken", loadImage("/src/main/resources/broken/broken.png"));

        loadMap();

        player = new Player(this, map); 

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
        player.draw();
    }

    private void deathScreen() {

    }

    private void winScreen() {

    }

    public void keyPressed() {
        switch(gameState) {
            case PLAY:
                player.setCurrentKeyCode(keyCode);
                player.move();
                break;
            case DEATH:
                break; 
            case WIN:
                break;
        }
    }

    public static void main(String[] args) {
        PApplet.main("demolition.App");
    }
}
