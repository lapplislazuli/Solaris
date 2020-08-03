package config.json;

import javax.json.Json;
import javax.json.JsonObject;
import config.interfaces.Settings;

public class JSONSettings implements Settings {
	
	private int screenWidth, screenHeight;
	private int updateIntervall;
	private boolean paused;
	private boolean collision;
	
	public JSONSettings(JsonObject configJSON) {
		screenWidth = configJSON.getInt("screenWidth");
		screenHeight = configJSON.getInt("screenHeight");
		updateIntervall = configJSON.getInt("updateIntervall");
		paused = configJSON.getBoolean("paused");
		collision = configJSON.getBoolean("collision");
	}

	public JsonObject toJSON() {
		return Json.createObjectBuilder()
		.add("screenWidth", screenWidth)
		.add("screenHeight", screenHeight)
		.add("paused", paused)
		.add("updateIntervall", updateIntervall)
		.add("collision", collision).build();
	}

	public int getScreenWidth() {
		return screenWidth;
	}

	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}

	public int getUpdateIntervall() {
		return updateIntervall;
	}

	public void setUpdateIntervall(int updateIntervall) {
		this.updateIntervall = updateIntervall;
	}

	public boolean isPaused() {
		return paused;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}

	public boolean isCollision() {
		return collision;
	}

	public void setCollision(boolean collision) {
		this.collision = collision;
	}
	
}
