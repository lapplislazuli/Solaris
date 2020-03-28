package config.interfaces;


public interface Config {

	public Settings getSettings();

	public void setSettings(Settings settings);

	public KeyConfig getKeyConfig();

	public void setKeyConfig(KeyConfig keyConfig);

	public MouseConfig getMouseConfig();

	public void setMouseConfig(MouseConfig mouseConfig);

}