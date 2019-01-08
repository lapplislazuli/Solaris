package config;

import org.json.JSONArray;
import org.json.JSONObject;

public class Config {
	public Settings settings;
	public KeyConfig keyConfig;
	public MouseConfig mouseConfig;
	public LoggerSettings loggerSettings;
	public String path; //So i can save it where it was drawn from
	
	public Config(String path, JSONObject obj) {
		this.path=path;
		
		JSONObject generalConfigJSON= obj.getJSONObject("settings");
		settings=new Settings(generalConfigJSON);
		
		JSONArray keyBindingsJSON = obj.getJSONArray("keyBindings");
		keyConfig=new KeyConfig(keyBindingsJSON);
		
		JSONArray mouseBindingsJSON = obj.getJSONArray("mouseBindings");
		mouseConfig=new MouseConfig(mouseBindingsJSON);
		
		JSONObject loggerJSON = obj.getJSONObject("loggerSettings");
		loggerSettings = new LoggerSettings(loggerJSON);
	}
	
	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		json.put("settings",settings.toJSON());
		json.put("keyBindings",keyConfig.toJSON());
		json.put("mouseBindings",mouseConfig.toJSON());
		json.put("loggerSetings", loggerSettings.toJSON());
		return json;
	}
}
