package fakes.config;

import java.util.HashMap;
import java.util.Map;

import config.interfaces.KeyConfig;

@SuppressWarnings("restriction")
public class FakeKeyConfig implements KeyConfig {
	
	public Map<String, String> keyBindings= new HashMap<String,String>();
	
	public Map<String, String> getKeyBindings() {
		return keyBindings;
	}

	@Override
	public void putKeyBinding(String key, String actionName) {
		keyBindings.put(key, actionName);
	}

}
