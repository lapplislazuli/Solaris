package config.json;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;

import config.interfaces.MouseConfig;
import javafx.scene.input.MouseButton;

public class JSONMouseConfig implements MouseConfig {

	private final Map<MouseButton,String> mouseBindings = new HashMap<MouseButton,String>();
	
	public JSONMouseConfig(JsonArray bindings) {
		for (int i = 0; i < bindings.size(); i++)
		{
		    String key = bindings.getJsonObject(i).getString("button");
		    String actionName = bindings.getJsonObject(i).getString("action");
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
	
	public JsonArray toJSON() {
		var bindingsbuilder = Json.createArrayBuilder();
		for(Entry<MouseButton,String> e : mouseBindings.entrySet()) {
			JsonObject binding = Json.createObjectBuilder()
			.add("button", parseButtonToString(e.getKey()))
			.add("action", e.getValue())
			.build();
			bindingsbuilder.add(binding);
		}
		return bindingsbuilder.build();
	}
	
	private String parseButtonToString(MouseButton b) {
		if(b == MouseButton.PRIMARY)
			return "PRIMARY";
		else if(b == MouseButton.SECONDARY)
			return "SECONDARY";
		else if(b == MouseButton.MIDDLE)
			return "MIDDLE";
		else
			return "NONE";  //Catch Mistakes to a Key that does nothing
	}
	
	private MouseButton parseButtonFromString(String s) {
		if(s.equals("PRIMARY"))
			return MouseButton.PRIMARY;
		else if(s.equals("SECONDARY"))
			return MouseButton.SECONDARY;
		else if(s.equals("MIDDLE"))
			return MouseButton.MIDDLE;
		else
			return MouseButton.NONE; //Catch Mistakes to a Key that does nothing
	}
}
