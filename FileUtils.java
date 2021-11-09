package demolition.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;

import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
 
import java.io.FileReader;
import java.util.Iterator;


public class FileUtils { 

	public static ArrayList<ArrayList<Character>> readFileAsChar2DArray(String fName) throws FileNotFoundException { 
		try { 
			File f = new File(fName);
			Scanner scanner = new Scanner(f);
			ArrayList<ArrayList<Character>> chars = new ArrayList<ArrayList<Character>>();
			while(scanner.hasNextLine()) { 
				String line = scanner.nextLine(); 
				line.trim();
				ArrayList<Character> tmp = new ArrayList<Character>();
				for(char c : line.toCharArray()) { 
					tmp.add(new Character(c));
				}
				chars.add(tmp);
			}
			return chars;
		} catch(NullPointerException e) { 
			throw new FileNotFoundException(e.getMessage());
		}
	}

	public static JSONObject readInConfig(String fname) {
		try {
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(new FileReader(fname));
			JSONObject jsonObject = (JSONObject) obj;
			return jsonObject;
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}