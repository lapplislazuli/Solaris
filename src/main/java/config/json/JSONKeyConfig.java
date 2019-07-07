package config.json;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;

import config.interfaces.KeyConfig;

public class JSONKeyConfig implements KeyConfig {
	
	private final Map<String,String> keyBindings = new HashMap<String,String>();
	
	public JSONKeyConfig(JsonArray bindings) {
		for (int i = 0; i < bindings.size(); i++)
		{
		    String key = bindings.getJsonObject(i).getString("key");
		    String actionName = bindings.getJsonObject(i).getString("action");
		    keyBindings.put(key, actionName);
		}
	}
	
	public Map<String,String> getKeyBindings(){
		return keyBindings;
	}
	
	public void putKeyBinding(String key, String actionName) {
		keyBindings.put(key, actionName);
	}
	

	public JsonArray toJSON() {
		var bindingsbuilder = Json.createArrayBuilder();
		for(Entry<String, String> e : keyBindings.entrySet()) {
			JsonObject binding = Json.createObjectBuilder()
			.add("key",e.getKey())
			.add("action", e.getValue())
			.build();
			bindingsbuilder.add(binding);
		}
		return bindingsbuilder.build();
	}
	
}
