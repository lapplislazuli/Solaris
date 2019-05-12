package config.JSON;

import org.json.JSONArray;
import org.json.JSONObject;

import config.interfaces.Config;
import config.interfaces.KeyConfig;
import config.interfaces.MouseConfig;
import config.interfaces.Settings;

public class JSONConfig implements Config {
	private JSONSettings settings;
	private JSONKeyConfig keyConfig;
	private JSONMouseConfig mouseConfig;
	private JSONLoggerSettings loggerSettings;
	private String path; //So i can save it where it was drawn from
	
	public JSONConfig(String path, JSONObject obj) {
		this.path=path;
		
		JSONObject generalConfigJSON= obj.getJSONObject("settings");
		settings=new JSONSettings(generalConfigJSON);
		
		JSONArray keyBindingsJSON = obj.getJSONArray("keyBindings");
		keyConfig=new JSONKeyConfig(keyBindingsJSON);
		
		JSONArray mouseBindingsJSON = obj.getJSONArray("mouseBindings");
		mouseConfig=new JSONMouseConfig(mouseBindingsJSON);
		
		JSONObject loggerJSON = obj.getJSONObject("loggerSettings");
		loggerSettings = new JSONLoggerSettings(loggerJSON);
	}
	
	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		json.put("settings",settings.toJSON());
		json.put("keyBindings",keyConfig.toJSON());
		json.put("mouseBindings",mouseConfig.toJSON());
		json.put("loggerSettings", loggerSettings.toJSON());
		return json;
	}

	public Settings getSettings() {
		return settings;
	}

	public void setSettings(JSONSettings settings) {
		this.settings = settings;
	}

	public KeyConfig getKeyConfig() {
		return keyConfig;
	}

	public void setKeyConfig(JSONKeyConfig keyConfig) {
		this.keyConfig = keyConfig;
	}

	public MouseConfig getMouseConfig() {
		return mouseConfig;
	}

	public void setMouseConfig(JSONMouseConfig mouseConfig) {
		this.mouseConfig = mouseConfig;
	}

	public JSONLoggerSettings getLoggerSettings() {
		return loggerSettings;
	}

	public void setLoggerSettings(JSONLoggerSettings loggerSettings) {
		this.loggerSettings = loggerSettings;
	}
	
	public String getPath() {return path;}
	
}
