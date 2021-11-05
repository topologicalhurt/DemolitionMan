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

	public Map(PApplet app, HashMap<String, PImage> imageRegister, String fName) throws InvalidMapException {
		super(app, imageRegister);
		try {
			ArrayList<ArrayList<Character>> level = FileUtils.readFileAsChar2DArray(fName);
			grid = new Tile[Constants.MAP_HEIGHT][Constants.MAP_WIDTH];
			for(int i = 0; i < Constants.MAP_HEIGHT; i++) {
				for(int j = 0; j < Constants.MAP_WIDTH; j++) {
					grid[i][j] = new Tile(level.get(i).get(j), new Position(j * Constants.CELL_SIZE.x,
						i * Constants.CELL_SIZE.y + Constants.TOP_PADDING));
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
		cache[0] = Position.add(new Position(1, 0), pos);
		cache[1] = Position.add(new Position(-1, 0), pos);
		cache[2] = Position.add(new Position(0, 1), pos);
		cache[3] = Position.add(new Position(0, -1), pos);
		int j = 0;
		for(int i = 0; i < 3; i++) { 
			if(!Position.between(cache[i], new Position(0, 0),
			 new Position(Constants.MAP_WIDTH - 1, Constants.MAP_HEIGHT - 1))) {
			 	neighbors[j++] = cache[i];
			}
		}
		return neighbors;
	}	

	public void draw() {
		for(Tile[] row : grid) { 
			for(Tile t : row) { 
				app.image(imageRegister.get(t.state.assetName), (float) t.position.x, (float) t.position.y);
			}
		}
	}	
}

