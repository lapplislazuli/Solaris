package fakes.config;

import config.interfaces.Config;
import config.interfaces.KeyConfig;
import config.interfaces.LoggerSettings;
import config.interfaces.MouseConfig;
import config.interfaces.Settings;

public class FakeConfig implements Config{
	
	private FakeSettings settings;
	
	public Settings getSettings() {
		return settings;
	}

	public void setSettings(Settings settings) {
		if(settings instanceof FakeSettings)
		this.settings=(FakeSettings)settings;
	}
	public KeyConfig getKeyConfig() {return null;}
	public void setKeyConfig(KeyConfig keyConfig) {}
	public MouseConfig getMouseConfig() {return null;}
	public void setMouseConfig(MouseConfig mouseConfig) {}
	public LoggerSettings getLoggerSettings() {return null;}
	public void setLoggerSettings(LoggerSettings loggerSettings) {}

}
