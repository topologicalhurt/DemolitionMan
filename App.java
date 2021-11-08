package demolition;

import demolition.geo.Map;
import demolition.geo.Position;
import demolition.utils.Animation;
import demolition.constants.Constants;
import demolition.exceptions.InvalidMapException;
import demolition.entities.Player;
import demolition.entities.RedEnemy;
import demolition.entities.YellowEnemy;
import demolition.entities.Bomb;

import java.util.HashMap;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PFont;

enum GameState {
    PLAY,
    DEATH,
    WIN
}

public final class App extends PApplet {

    private static Map map;
    private static Player player;
    private static Bomb bomb;
    private static int level = 1;
    private static int lives = 3;
    private static long counter = 0;
    private static long timeLeft = 150;
    private static PFont font; 
    public static GameState gameState = GameState.PLAY;
    private static HashMap<String, PImage> scene;
    private static ArrayList<RedEnemy> redEnemies;
    private static ArrayList<YellowEnemy> yellowEnemies;

    private void loadMap() {
        try {
            map = new Map(this, scene, String.format("level%d.txt", level));
        } catch(InvalidMapException e) {
            e.printStackTrace();
        }
    }

    private void loadEnemies() {
        redEnemies = new ArrayList<RedEnemy>();
        yellowEnemies = new ArrayList<YellowEnemy>();
        for(Position p : map.enemyRedStartPositions) {
            redEnemies.add(new RedEnemy(this, map, p));
        }
        for(Position p : map.enemyYellowStartPositions) {
            yellowEnemies.add(new YellowEnemy(this, map, p));
        }
    }

    private void drawEnemies() {
        moveEnemies();
        for(RedEnemy e : redEnemies) { 
            if(e != null) {
                e.draw();
            }
        }
        for(YellowEnemy e : yellowEnemies) {
            if(e != null) {
                e.draw();
            }
        }
    }

    private void cleanKills() {
        player.death();
        for(int i = 0; i < redEnemies.size(); i++) {
            if(redEnemies.get(i) != null) {
                redEnemies.get(i).death();
                if(redEnemies.get(i).dead) {
                    redEnemies.set(i, null);
                }
            }
        }
        for(int i = 0; i < yellowEnemies.size(); i++) {
            if(yellowEnemies.get(i) != null) {
                yellowEnemies.get(i).death();
                if(yellowEnemies.get(i).dead) {
                    yellowEnemies.set(i, null);
                }
            }
        }
        if(player.dead) {
            lives--;
            setup();
        }
    }

    private void moveEnemies() {
        for(RedEnemy e : redEnemies) {
            if(e != null) {
                e.move();
            }
        }
        for(YellowEnemy e : yellowEnemies) {
            if(e != null) {
                e.move();
            }
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
        scene.put("centre", loadImage("/src/main/resources/explosion/centre.png"));
        scene.put("end_top", loadImage("/src/main/resources/explosion/end_top.png"));
        scene.put("end_bottom", loadImage("/src/main/resources/explosion/end_bottom.png"));
        scene.put("end_left", loadImage("/src/main/resources/explosion/end_left.png"));
        scene.put("end_right", loadImage("/src/main/resources/explosion/end_right.png"));
        scene.put("horizontal", loadImage("/src/main/resources/explosion/horizontal.png"));
        scene.put("vertical", loadImage("/src/main/resources/explosion/vertical.png"));
        scene.put("clock", loadImage("/src/main/resources/icons/clock.png"));
        scene.put("health", loadImage("/src/main/resources/icons/player.png"));

        font = createFont("/src/main/resources/PressStart2P-Regular.ttf", 32);
        textFont(font);
        fill(0, 0, 0);
        textAlign(CENTER, CENTER);

        loadMap();
        loadEnemies();
        player = new Player(this, map); 
        bomb = new Bomb(this, map);
    }

    public void draw() {
        background(239,129,0);

        if(lives <= 0 || timeLeft <= 0) {
            gameState = GameState.DEATH;
        }

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

    private void updateTime() {
        if(++counter > Constants.FPS) {
            timeLeft--;
            counter = 0;
        }
    }

    private void playScreen() {
        map.draw();
        cleanKills();
        player.draw();
        drawEnemies();
        bomb.draw();

        updateTime();
        text(String.format("%s", lives), Constants.WIDTH / 2 - 64, 32);
        text(String.format("%d", timeLeft), Constants.WIDTH / 2 + 64, 32);
        image(scene.get("clock"), Constants.WIDTH / 2 + 128, 16);
        image(scene.get("health"), Constants.WIDTH / 2 - 32, 16);

    }

    private void deathScreen() {
        text("GAME OVER", Constants.WIDTH / 2, Constants.HEIGHT / 2);
    }

    private void winScreen() {
        text("YOU WIN", Constants.WIDTH / 2, Constants.HEIGHT / 2);
    }

    public void keyPressed() {
        switch(gameState) {
            case PLAY:
                player.setCurrentKeyCode(keyCode);
                player.move();
                bomb.setCurrentKeyCode(keyCode);
                bomb.place(player.position);
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
