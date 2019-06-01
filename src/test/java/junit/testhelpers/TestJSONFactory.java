package junit.testhelpers;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class TestJSONFactory {

	
	public static JSONObject makeFullSettingsJSON() {
		JSONObject obj = new JSONObject();
		obj.put("screenWidth", 200);
		obj.put("screenHeight", 200);
		obj.put("paused", true);
		obj.put("updateIntervall", 50);
		obj.put("collision", true);
		return obj;
	}
	
	public static JSONObject makeFaultySettingsJSON() {
		JSONObject obj = new JSONObject();
		obj.put("rubbish", 133769420);
		return obj;
	}
	
	public static JSONObject makeEmptySettingsJSON() {
		JSONObject obj = new JSONObject();
		return obj;
	}

	public static JSONObject makeFullLoggerSettingsJSON() {
		JSONObject obj = new JSONObject();
		obj.put("level", "ERROR"); //Should work
		obj.put("logfile","TestFile.txt");
		obj.put("append", true);
		return obj;
	}
	
	public static JSONObject makeFaultyLoggerSettingsJSON() {
		JSONObject obj = new JSONObject();
		obj.put("rubbish", "ERROR");
		return obj;
	}
	public static JSONArray makeJSONKeyConfigWithEntries() {
		JSONArray bindings =  new JSONArray();
		
		JSONObject firstBinding = new JSONObject();
		firstBinding.put("key","KEY1");
		firstBinding.put("action","ACTION1");
		
		JSONObject secondBinding = new JSONObject();
		secondBinding.put("key","KEY2");
		secondBinding.put("action", "ACTION2");
		
		bindings.put(firstBinding);
		bindings.put(secondBinding);
		
		return bindings;
	}
	
	public static JSONArray makeEmptyJSONArray() {
		JSONArray bindings =  new JSONArray();
		return bindings;
	}
	
	public static boolean compareArrays (JSONArray left, JSONArray right) {
		if(left.length()!=right.length())
			return false;

		List<Object> rightToCheck = right.toList();
		List<Object> leftToCheck =  left.toList();
		for(Object o : rightToCheck)
			if(!leftToCheck.contains(o))
				return false;
		for(Object o : leftToCheck)
			if(!rightToCheck.contains(o))
				return false;		
		return true;
	}

	public static JSONArray makeFaultyJSONKeyConfig() {
		JSONArray bindings =  new JSONArray();
		
		JSONObject firstBinding = new JSONObject();
		firstBinding.put("rubbish","some more rubbish");
		firstBinding.put("action","ACTION1");
		
		bindings.put(firstBinding);
		
		return bindings;
	}
	public static JSONArray makeJSONMouseConfigWithEntries() {
		JSONArray bindings =  new JSONArray();
		
		JSONObject firstBinding = new JSONObject();
		firstBinding.put("button","PRIMARY");
		firstBinding.put("action","ACTION1");
		
		JSONObject secondBinding = new JSONObject();
		secondBinding.put("button","SECONDARY");
		secondBinding.put("action", "ACTION2");
		
		JSONObject thirdBinding = new JSONObject();
		thirdBinding.put("button","MIDDLE");
		thirdBinding.put("action", "ACTION3");
		
		bindings.put(firstBinding);
		bindings.put(secondBinding);
		bindings.put(thirdBinding);
		
		return bindings;
	}
	
	public static JSONArray makeFaultyJSONMouseConfig() {
		JSONArray bindings =  new JSONArray();
		
		JSONObject firstBinding = new JSONObject();
		firstBinding.put("somethingElse","rubbish");
		firstBinding.put("action","ACTION1");
		
		
		bindings.put(firstBinding);
		
		return bindings;
	}
	
	public static JSONArray makeEmptyMouseConfigJSONArray() {
		JSONArray bindings =  new JSONArray();
		return bindings;
	}

	
	public static JSONObject makeFullConfigJSON() {
		JSONObject json = new JSONObject();
		json.put("settings",makeFullSettingsJSON());
		json.put("keyBindings",makeJSONKeyConfigWithEntries());
		json.put("mouseBindings",makeJSONMouseConfigWithEntries());
		json.put("loggerSettings", makeFullLoggerSettingsJSON());
		return json;
	}
	
	public static JSONObject makeFaultyConfigJSON() {
		JSONObject json = new JSONObject();
		json.put("rubbish","More Rubbish");
		return json;
	}

	public static JSONObject makeConfigJSONWithFaultrySubJSON() {
		JSONObject json = new JSONObject();
		json.put("settings",makeFaultySettingsJSON());
		json.put("keyBindings",makeJSONKeyConfigWithEntries());
		json.put("mouseBindings",makeJSONMouseConfigWithEntries());
		json.put("loggerSettings", makeFullLoggerSettingsJSON());
		return json;
	}
}
