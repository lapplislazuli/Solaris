package config.interfaces;


public interface Config {

	Settings getSettings();

	void setSettings(Settings settings);

	KeyConfig getKeyConfig();

	void setKeyConfig(KeyConfig keyConfig);

	MouseConfig getMouseConfig();

	void setMouseConfig(MouseConfig mouseConfig);

}