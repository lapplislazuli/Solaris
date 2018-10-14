package config;

import org.json.*;

public class GeneralConfig {
	
	public int screenWidth, screenHeight;
	public int updateIntervall;
	public boolean paused;
	public boolean collision;
	
	public GeneralConfig(JSONObject configJSON) {
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
