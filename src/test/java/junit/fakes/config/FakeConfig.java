package junit.fakes.config;

import config.interfaces.Config;
import config.interfaces.KeyConfig;
import config.interfaces.LoggerSettings;
import config.interfaces.MouseConfig;
import config.interfaces.Settings;

public class FakeConfig implements Config{
	
	public FakeSettings settings;
	public FakeMouseConfig mouse;
	public FakeKeyConfig keys;
	
	
	public Settings getSettings() {
		return settings;
	}

	public void setSettings(Settings settings) {
		if(settings instanceof FakeSettings)
		this.settings=(FakeSettings)settings;
	}
	public KeyConfig getKeyConfig() {
		return keys;
	}
	public void setKeyConfig(KeyConfig keyConfig) {
		keys = (FakeKeyConfig) keyConfig;
	}
	public MouseConfig getMouseConfig() {
		return mouse;
	}
	public void setMouseConfig(MouseConfig mouseConfig) {
		mouse = (FakeMouseConfig) mouseConfig;
	}
	public LoggerSettings getLoggerSettings() {return null;}
	public void setLoggerSettings(LoggerSettings loggerSettings) {}

}
