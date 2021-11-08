package demolition.geo;

import demolition.Sketcher;
import demolition.exceptions.MapSymbolNotFoundException;
import demolition.exceptions.InvalidMapException;
import demolition.utils.FileUtils;
import demolition.constants.Constants;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.FileNotFoundException;

import processing.core.PApplet;
import processing.core.PImage;

public class Map extends Sketcher {

	public Tile[][] grid;
	private String fName;
	public Position playerStartPosition;
	public ArrayList<Position> enemyYellowStartPositions;
	public ArrayList<Position> enemyRedStartPositions;

	public Map(PApplet app, HashMap<String, PImage> imageRegister, String fName) throws InvalidMapException {
		super(app, imageRegister);
		try {
			enemyYellowStartPositions = new ArrayList<Position>();
			enemyRedStartPositions = new ArrayList<Position>();
			ArrayList<ArrayList<Character>> level = FileUtils.readFileAsChar2DArray(fName);
			grid = new Tile[Constants.MAP_HEIGHT][Constants.MAP_WIDTH];
			for(int i = 0; i < Constants.MAP_HEIGHT; i++) {
				for(int j = 0; j < Constants.MAP_WIDTH; j++) {
					grid[i][j] = new Tile(level.get(i).get(j), new Position(j * Constants.CELL_SIZE,
						i * Constants.CELL_SIZE + Constants.TOP_PADDING));
					switch(grid[i][j].state) {
						case PLAYER:
							playerStartPosition = new Position(j, i);
							break;
						case ENEMYY:
							enemyYellowStartPositions.add(new Position(j, i));
							break;
						case ENEMYR:
							enemyRedStartPositions.add(new Position(j, i));
							break;
						}
					}
				}
		} catch(FileNotFoundException | MapSymbolNotFoundException e) {
			throw new InvalidMapException(e.getMessage());
		}
	}

	public Tile tileFromPosition(Position pos) {
		return grid[pos.y][pos.x];
	}

	public static Position[] getNeighboringPositions(Position pos) { 
		Position[] neighbors = new Position[4];
		Position[] cache = new Position[4];
		cache[0] = Position.add(Direction.NORTH.dir, pos);
		cache[1] = Position.add(Direction.EAST.dir, pos);
		cache[2] = Position.add(Direction.SOUTH.dir, pos);
		cache[3] = Position.add(Direction.WEST.dir, pos);
		int j = 0;
		for(int i = 0; i < 4; i++) { 
			if(Position.between(cache[i], new Position(0, 0),
			 	new Position(Constants.MAP_WIDTH - 1, Constants.MAP_HEIGHT - 1))) {
			 	neighbors[j++] = cache[i];
			}
		}
		return neighbors;
	}	

	@Override
	public void draw() {
		for(Tile[] row : grid) { 
			for(Tile t : row) { 
				app.image(imageRegister.get(t.state.assetName), (float) t.position.x, (float) t.position.y);
			}
		}
	}	
}

