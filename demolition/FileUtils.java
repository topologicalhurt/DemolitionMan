package demolition.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import java.util.Arrays;
import java.util.ArrayList;

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
			throw new FileNotFoundException("Invalid Filename.");
		}
	}
}