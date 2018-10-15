package config;

import java.util.HashMap;
import java.util.Map;

import org.json.*;

public class KeyConfig {
	
	private final Map<String,String> keyBindings = new HashMap<String,String>();
	
	public KeyConfig(JSONArray bindings) {
		for (int i = 0; i < bindings.length(); i++)
		{
		    String key = bindings.getJSONObject(i).getString("key");
		    String actionName = bindings.getJSONObject(i).getString("action");
		    keyBindings.put(key, actionName);
		}
	}
	
	public Map<String,String> getKeyBindings(){
		return keyBindings;
	}
	
	public void putKeyBinding(String key, String actionName) {
		keyBindings.put(key, actionName);
	}
	
	public JSONArray toJson() {
		return new JSONArray(keyBindings);
	}
}
