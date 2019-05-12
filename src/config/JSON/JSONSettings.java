package config.JSON;

import org.json.*;

import config.interfaces.Settings;

public class JSONSettings implements Settings {
	
	private int screenWidth, screenHeight;
	private int updateIntervall;
	private boolean paused;
	private boolean collision;
	
	public JSONSettings(JSONObject configJSON) {
		screenWidth = configJSON.getInt("screenWidth");
		screenHeight = configJSON.getInt("screenHeight");
		updateIntervall = configJSON.getInt("updateIntervall");
		paused = configJSON.getBoolean("paused");
		collision = configJSON.getBoolean("collision");
	}

	public JSONObject toJSON() {
		JSONObject obj = new JSONObject();
		obj.put("screenWidth", screenWidth);
		obj.put("screenHeight", screenHeight);
		obj.put("paused", paused);
		obj.put("updateIntervall", updateIntervall);
		obj.put("collision", collision);
		return obj;
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
