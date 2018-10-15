package config;

import org.json.*;

public class Settings {
	
	public int screenWidth, screenHeight;
	public int updateIntervall;
	public boolean paused;
	public boolean collision;
	
	public Settings(JSONObject configJSON) {
		screenWidth = configJSON.getInt("screenWidth");
		screenHeight = configJSON.getInt("screenHeight");
		updateIntervall = configJSON.getInt("updateIntervall");
		paused = configJSON.getBoolean("paused");
		collision = configJSON.getBoolean("collision");
	}
	
	public JSONObject toJson() {
		return new JSONObject(this);
	}
	
}
