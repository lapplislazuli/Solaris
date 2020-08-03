package config.interfaces;

public interface Settings {

	int getScreenWidth();

	void setScreenWidth(int screenWidth);

	int getScreenHeight();

	void setScreenHeight(int screenHeight);

	int getUpdateIntervall();

	void setUpdateIntervall(int updateIntervall);

	boolean isPaused();

	void setPaused(boolean paused);

	boolean isCollision();

	void setCollision(boolean collision);
	
	int getAimedFPS();
	
	void setAimedFPS(int aimedFPS);

}