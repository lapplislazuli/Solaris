package config;

import java.util.HashMap;
import java.util.Map;

import org.json.*;

import javafx.scene.input.MouseButton;

@SuppressWarnings("restriction")
public class MouseManagerConfig {

	private final Map<MouseButton,String> mouseBindings = new HashMap<MouseButton,String>();
	
	public MouseManagerConfig(JSONArray bindings) {
		for (int i = 0; i < bindings.length(); i++)
		{
		    String key = bindings.getJSONObject(i).getString("button");
		    String actionName = bindings.getJSONObject(i).getString("action");
		    mouseBindings.put(parseButtonString(key), actionName);
		}
	}
	
	public Map<MouseButton,String> getKeyBindings(){
		return mouseBindings;
	}
	
	public void putKeyBinding(MouseButton button, String actionName) {
		mouseBindings.put(button, actionName);
	}
	public void putKeyBinding(String buttonString, String actionName) {
		mouseBindings.put(parseButtonString(buttonString), actionName);
	}
	public JSONArray toJson() {
		return new JSONArray(mouseBindings);
	}
	

	private MouseButton parseButtonString(String s) {
		if(s.equals("PRIMARY"))
			return MouseButton.PRIMARY;
		else if(s.equals("SECONDARY"))
			return MouseButton.SECONDARY;
		else if(s.equals("MIDDLE"))
			return MouseButton.MIDDLE;
		else
			return MouseButton.NONE;
	}
}
