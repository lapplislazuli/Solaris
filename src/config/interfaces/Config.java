package config.interfaces;

import config.JSON.JSONKeyConfig;
import config.JSON.JSONLoggerSettings;
import config.JSON.JSONMouseConfig;
import config.JSON.JSONSettings;

public interface Config {

	Settings getSettings();

	void setSettings(JSONSettings settings);

	KeyConfig getKeyConfig();

	void setKeyConfig(JSONKeyConfig keyConfig);

	MouseConfig getMouseConfig();

	void setMouseConfig(JSONMouseConfig mouseConfig);

	LoggerSettings getLoggerSettings();

	void setLoggerSettings(JSONLoggerSettings loggerSettings);

}