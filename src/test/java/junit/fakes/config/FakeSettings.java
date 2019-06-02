package junit.fakes.config;



import config.interfaces.Settings;

public class FakeSettings implements Settings {
	
	public int screenWidth,screenHeight,updateIntervall;
	public boolean paused,collision;
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
