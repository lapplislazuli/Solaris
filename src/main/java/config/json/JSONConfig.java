package config.json;


import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;

import config.interfaces.Config;
import config.interfaces.KeyConfig;
import config.interfaces.MouseConfig;
import config.interfaces.Settings;

public class JSONConfig implements Config {
	private JSONSettings settings;
	private JSONKeyConfig keyConfig;
	private JSONMouseConfig mouseConfig;
	private String path; //So i can save it where it was drawn from
	
	public JSONConfig(String path, JsonObject obj) {
		this.path=path;
		
		JsonObject generalConfigJSON= obj.getJsonObject("settings");
		settings=new JSONSettings(generalConfigJSON);
		
		JsonArray keyBindingsJSON = obj.getJsonArray("keyBindings");
		keyConfig=new JSONKeyConfig(keyBindingsJSON);
		
		JsonArray mouseBindingsJSON = obj.getJsonArray("mouseBindings");
		mouseConfig=new JSONMouseConfig(mouseBindingsJSON);
		
	}
	
	public JsonObject toJSON() {
		return Json.createObjectBuilder()
		.add("settings",settings.toJSON())
		.add("keyBindings",keyConfig.toJSON())
		.add("mouseBindings",mouseConfig.toJSON())
		.build();
	}

	public Settings getSettings() {
		return settings;
	}

	public void setSettings(Settings settings) {
		if(settings instanceof JSONSettings)
			this.settings =(JSONSettings) settings;
	}

	public KeyConfig getKeyConfig() {
		return keyConfig;
	}

	public void setKeyConfig(KeyConfig keyConfig) {
		if(keyConfig instanceof JSONKeyConfig)
			this.keyConfig = (JSONKeyConfig)keyConfig;
	}

	public MouseConfig getMouseConfig() {
		return mouseConfig;
	}

	public void setMouseConfig(MouseConfig mouseConfig) {
		if(mouseConfig instanceof JSONMouseConfig)
			this.mouseConfig = (JSONMouseConfig)mouseConfig;
	}
	
	public String getPath() {return path;}
	
}
