package config;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONObject;

public class ConfigReader {
	
	public CompleteConfiguration read(String path) {
		if(!path.endsWith(".json"))
			throw new IllegalArgumentException("Faulty Path!");
		
		JSONObject read = new JSONObject(path);
		
		return new CompleteConfiguration(read);
	}
	
	public void save(CompleteConfiguration config, String path) {
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
