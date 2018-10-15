package config;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.json.*;

import javafx.scene.input.MouseButton;

@SuppressWarnings("restriction")
public class MouseConfig {

	private final Map<MouseButton,String> mouseBindings = new HashMap<MouseButton,String>();
	
	public MouseConfig(JSONArray bindings) {
		for (int i = 0; i < bindings.length(); i++)
		{
		    String key = bindings.getJSONObject(i).getString("button");
		    String actionName = bindings.getJSONObject(i).getString("action");
		    mouseBindings.put(parseButtonFromString(key), actionName);
		}
	}
	
	public Map<MouseButton,String> getKeyBindings(){
		return mouseBindings;
	}
	
	public void putKeyBinding(MouseButton button, String actionName) {
		mouseBindings.put(button, actionName);
	}
	public void putKeyBinding(String buttonString, String actionName) {
		mouseBindings.put(parseButtonFromString(buttonString), actionName);
	}
	
	public JSONArray toJSON() {
		JSONArray bindings =  new JSONArray();
		for(Entry<MouseButton,String> e : mouseBindings.entrySet()) {
			JSONObject binding = new JSONObject();
			binding.put("key", parseButtonToString(e.getKey()));
			binding.put("action", e.getValue());
			bindings.put(binding);
		}
		return bindings;
	}
	private String parseButtonToString(MouseButton b) {
		if(b == MouseButton.PRIMARY)
			return "PRIMARY";
		else if(b == MouseButton.SECONDARY)
			return "SECONDARY";
		else if(b == MouseButton.MIDDLE)
			return "MIDDLE";
		else
			return "NONE"; //Error, but wont be problematic
	}
	private MouseButton parseButtonFromString(String s) {
		if(s.equals("PRIMARY"))
			return MouseButton.PRIMARY;
		else if(s.equals("SECONDARY"))
			return MouseButton.SECONDARY;
		else if(s.equals("MIDDLE"))
			return MouseButton.MIDDLE;
		else
			return MouseButton.NONE; //Error, but wont be problematic
	}
}
