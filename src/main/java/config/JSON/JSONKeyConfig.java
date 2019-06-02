package config.JSON;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.json.*;

import config.interfaces.KeyConfig;

public class JSONKeyConfig implements KeyConfig {
	
	private final Map<String,String> keyBindings = new HashMap<String,String>();
	
	public JSONKeyConfig(JSONArray bindings) {
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
	
	public JSONArray toJSON() {
		JSONArray bindings =  new JSONArray();
		for(Entry<String,String> e : keyBindings.entrySet()) {
			JSONObject binding = new JSONObject();
			binding.put("key",e.getKey());
			binding.put("action", e.getValue());
			bindings.put(binding);
		}
		return bindings;
	}
}
