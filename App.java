package demolition;

import demolition.geo.Map;
import demolition.utils.FileUtils;
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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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
    private static ArrayList<String> levels;
    private static ArrayList<Long> times;
    private static int levelPointer = 0;
    private static String currentLevel;
    private static int lives;
    private static long counter = 0;
    private static long timeLeft;
    private static PFont font; 
    public static GameState gameState = GameState.PLAY;
    private static HashMap<String, PImage> scene;
    private static ArrayList<RedEnemy> redEnemies;
    private static ArrayList<YellowEnemy> yellowEnemies;
    private static boolean completed;

    private void loadMap() {
        try {
            map = new Map(this, scene,currentLevel);
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

    public void checkGoal() {
        player.checkWin();
        if(player.won) {
            if(levelPointer + 1 < levels.size()) {
                levelPointer++;
                timeLeft = times.get(levelPointer);
                currentLevel = levels.get(levelPointer);
                player.won = false;
                setup();
            } else {
                completed = true;
            }
        }
    }

    public App() {
        scene = new HashMap<String, PImage>();
        JSONObject jobj = FileUtils.readInConfig("config.json");
        levels = new ArrayList<String>();
        times = new ArrayList<Long>();
        JSONArray jlevels =(JSONArray) jobj.get("levels");
        for(int i = 0; i < jlevels.size(); i++){
            JSONObject tmp = (JSONObject) jlevels.get(i);
            levels.add(tmp.get("path").toString());
            times.add(Long.parseLong(tmp.get("time").toString()));
        }
        currentLevel = levels.get(0);
        timeLeft = times.get(0);
        lives = Integer.parseInt(jobj.get("lives").toString());
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
        } else if(completed) {
            gameState = GameState.WIN;
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
        checkGoal();

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
