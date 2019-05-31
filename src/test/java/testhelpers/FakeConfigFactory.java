package testhelpers;

import config.interfaces.Config;
import config.interfaces.Settings;
import fakes.config.FakeConfig;
import fakes.config.FakeKeyConfig;
import fakes.config.FakeMouseConfig;
import fakes.config.FakeSettings;

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
