package config;

import org.json.JSONObject;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ConfigFactory implements KeyListener {
	private static ConfigFactory INSTANCE;
	private static Config conf;
	static public Config read(String path) {
		if(!path.endsWith(".json"))
			throw new IllegalArgumentException("Faulty Path!");
		try {
			Path castedPath = Paths.get(path);
			List<String> allLines = Files.readAllLines(castedPath);
			
			String completeFile = "";
			for(String line : allLines)
				completeFile+=line;
			JSONObject read = new JSONObject(completeFile);
			return new Config(path,read);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}



	public static ConfigFactory getInstance() {
		if(INSTANCE==null)
			INSTANCE=new ConfigFactory();
		return INSTANCE;
	}
	
	static public void save(Config config) {
		if(config.path==null||config.path=="")
			return;
		try {
			File f = new File(config.path);
			if(!f.exists())
				f.createNewFile();
			FileWriter fW = new FileWriter(f);
			config.toJSON().write(fW);
			fW.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	static public void saveConfigTo(Config config, String path) {
		if(!path.endsWith(".json"))
			path+="/configSave.json";
		try {
			File f = new File(path);
			if(!f.exists())
				f.createNewFile();
			FileWriter fW = new FileWriter(f);
			config.toJSON().write(fW);
			fW.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	 public void shutDown(){
		if(conf.path==null||conf.path=="")
			return ;

		File file = new File(conf.path);
		if (file.exists()) {
			ConfigFactory.save(conf);
			System.exit(0);

		}




	}

	

}



