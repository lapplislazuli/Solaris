package config.interfaces;

import java.util.Map;

public interface KeyConfig {

	Map<String, String> getKeyBindings();

	void putKeyBinding(String key, String actionName);

}