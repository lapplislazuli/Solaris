package config;

import org.json.JSONArray;
import org.json.JSONObject;

public class CompleteConfiguration {
	public GeneralConfig generalConfig;
	public KeyManagerConfig keyManagerConfig;
	public MouseManagerConfig mouseManagerConfig;
	
	public CompleteConfiguration(JSONObject obj) {
		JSONObject generalConfigJSON= obj.getJSONObject("generalConfig");
		generalConfig=new GeneralConfig(generalConfigJSON);
		
		JSONArray keyBindings = obj.getJSONArray("keyBindings");
		keyManagerConfig=new KeyManagerConfig(keyBindings);
		

		JSONArray mouseBindings = obj.getJSONArray("mouseBindings");
		mouseManagerConfig=new MouseManagerConfig(mouseBindings);
	}
	
	public JSONObject toJSON() {
		return new JSONObject(this);
	}
}
