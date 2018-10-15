package config;

import org.json.JSONArray;
import org.json.JSONObject;

public class Config {
	public Settings settings;
	public KeyConfig keyConfig;
	public MouseConfig mouseConfig;
	
	public Config(JSONObject obj) {
		JSONObject generalConfigJSON= obj.getJSONObject("settings");
		settings=new Settings(generalConfigJSON);
		
		JSONArray keyBindings = obj.getJSONArray("keyBindings");
		keyConfig=new KeyConfig(keyBindings);
		
		JSONArray mouseBindings = obj.getJSONArray("mouseBindings");
		mouseConfig=new MouseConfig(mouseBindings);
	}
	
	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		json.put("settings",settings.toJSON());
		json.put("keyBindings",keyConfig.toJSON());
		json.put("mouseBindings",mouseConfig.toJSON());
		return json;
	}
}
