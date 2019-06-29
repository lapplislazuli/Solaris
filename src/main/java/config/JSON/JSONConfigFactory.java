package config.JSON;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;


public class JSONConfigFactory {
	
	static public JSONConfig read(String path) {
		if(!path.endsWith(".json"))
			throw new IllegalArgumentException("Faulty Path!");
		try {
			Path castedPath = Paths.get(path);
			List<String> allLines = Files.readAllLines(castedPath);
			
			String completeFile = "";
			for(String line : allLines)
				completeFile+=line;
			
			JsonReader jsonReader = Json.createReader(new StringReader(completeFile));
			JsonObject read = jsonReader.readObject();
			jsonReader.close();
			
			return new JSONConfig(path,read);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	static public void save(JSONConfig config) {
		if(config.getPath()==null||config.getPath()=="")
			return;
		try {
			File f = new File(config.getPath());
			if(!f.exists())
				f.createNewFile();
			FileWriter fW = new FileWriter(f);
			fW.write(config.toJSON().toString());
			fW.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	static public void saveConfigTo(JSONConfig config, String path) {
		if(!path.endsWith(".json"))
			path+="/configSave.json";
		try {
			File f = new File(path);
			if(!f.exists())
				f.createNewFile();
			FileWriter fW = new FileWriter(f);
			fW.write(config.toJSON().toString());
			fW.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
