package config;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.json.JSONObject;

public class ConfigReader {
	
	static public CompleteConfiguration read(String path) {
		if(!path.endsWith(".json"))
			throw new IllegalArgumentException("Faulty Path!");
		try {
			Path castedPath = Paths.get(path);
			List<String> allLines = Files.readAllLines(castedPath);
			
			String completeFile = "";
			for(String line : allLines)
				completeFile+=line;
			JSONObject read = new JSONObject(completeFile);
			return new CompleteConfiguration(read);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	static public void save(CompleteConfiguration config, String path) {
		if(!path.endsWith(".json"))
			path+="/config.json";
		try {
			File f = new File(path);
			FileWriter fW = new FileWriter(f);
			fW.write(config.toJSON().toString());
			fW.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
