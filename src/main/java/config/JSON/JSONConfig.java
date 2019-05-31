package config.JSON;

import org.json.JSONArray;
import org.json.JSONObject;

import config.interfaces.Config;
import config.interfaces.KeyConfig;
import config.interfaces.LoggerSettings;
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

	public LoggerSettings getLoggerSettings() {
		return loggerSettings;
	}

	public void setLoggerSettings(LoggerSettings loggerSettings) {
		if(loggerSettings instanceof JSONLoggerSettings)
			this.loggerSettings = (JSONLoggerSettings)loggerSettings;
	}
	
	public String getPath() {return path;}
	
}
