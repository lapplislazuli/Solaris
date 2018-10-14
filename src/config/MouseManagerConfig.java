package config;

import java.util.HashMap;
import java.util.Map;

import org.json.*;

public class MouseManagerConfig {

	private final Map<String,String> mouseBindings = new HashMap<String,String>();
	
	public MouseManagerConfig(JSONArray bindings) {
		for (int i = 0; i < bindings.length(); i++)
		{
		    String key = bindings.getJSONObject(i).getString("button");
		    String actionName = bindings.getJSONObject(i).getString("action");
		    mouseBindings.put(key, actionName);
		}
	}
	
	public Map<String,String> getKeyBindings(){
		return mouseBindings;
	}
	
	public void putKeyBinding(String key, String actionName) {
		mouseBindings.put(key, actionName);
	}
	
	public JSONArray toJson() {
		return new JSONArray(mouseBindings);
	}
}
