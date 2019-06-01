package junit.testhelpers;

import config.interfaces.Config;
import config.interfaces.Settings;
import junit.fakes.config.FakeConfig;
import junit.fakes.config.FakeKeyConfig;
import junit.fakes.config.FakeMouseConfig;
import junit.fakes.config.FakeSettings;


public abstract class FakeConfigFactory {
	
	public static Config standardConfig() {
		Config c = new FakeConfig();
		c.setSettings(commonFakeSettings());
		c.setMouseConfig(new FakeMouseConfig());
		c.setKeyConfig(new FakeKeyConfig());
		return c;
	}
	
	public static Settings commonFakeSettings() {
		Settings s = new FakeSettings();
		s.setCollision(true);
		s.setPaused(false);
		s.setScreenHeight(0);
		s.setScreenWidth(0);
		s.setUpdateIntervall(10);
		return s;
	}
	
	
}
