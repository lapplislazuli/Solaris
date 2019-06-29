package junit.testhelpers;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;


public abstract class TestJSONFactory {

	public static JsonObject makeFullSettingsJSON() {
		var obj = Json.createObjectBuilder()
		.add("screenWidth", 200)
		.add("screenHeight", 200)
		.add("paused", true)
		.add("updateIntervall", 50)
		.add("collision", true);
		return obj.build();
	}
	
	public static JsonObject makeFaultySettingsJSON() {
		var obj = Json.createObjectBuilder()
		.add("rubbish", 133769420);
		return obj.build();
	}
	
	public static JsonObject makeEmptySettingsJSON() {
		var obj = Json.createObjectBuilder();
		return obj.build();
	}

	public static JsonObject makeFullLoggerSettingsJSON() {
		var obj = Json.createObjectBuilder()
		.add("level", "ERROR") //Should work
		.add("logfile","TestFile.txt")
		.add("append", true);
		return obj.build();
	}
	
	public static JsonObject makeFaultyLoggerSettingsJSON() {
		var obj = Json.createObjectBuilder()
		.add("rubbish", "ERROR");
		return obj.build();
	}
	public static JsonArray makeJSONKeyConfigWithEntries() {
		
		var bindings = Json.createArrayBuilder();
		
		var firstBinding = Json.createObjectBuilder()
		.add("key","KEY1")
		.add("action","ACTION1")
		.build();
		
		var secondBinding = Json.createObjectBuilder()
		.add("key","KEY2")
		.add("action", "ACTION2")
		.build();
		
		bindings.add(firstBinding);
		bindings.add(secondBinding);
		
		return bindings.build();
	}
	
	public static JsonArray makeEmptyJsonArray() {
		var bindings =  Json.createArrayBuilder();
		
		return bindings.build();
	}
	
	public static boolean compareArrays (JsonArray left, JsonArray right) {
		if(left.size()!=right.size())
			return false;

		var rightToCheck = right.toArray();
		var leftToCheck = left.toArray();
		
		return rightToCheck==leftToCheck;
	}

	public static JsonArray makeFaultyJSONKeyConfig() {
		var bindings =  Json.createArrayBuilder();
		
		var obj = Json.createObjectBuilder()
		.add("rubbish","some more rubbish")
		.add("action","ACTION1")
		.build();
		
		bindings.add(obj);
		
		return bindings.build();
	}
	public static JsonArray makeJSONMouseConfigWithEntries() {
		var bindings =  Json.createArrayBuilder();
		
		var firstBinding = Json.createObjectBuilder()
		.add("button","PRIMARY")
		.add("action","ACTION1")
		.build();
		
		var secondBinding = Json.createObjectBuilder()
		.add("button","SECONDARY")
		.add("action", "ACTION2")
		.build();
		
		var thirdBinding = Json.createObjectBuilder()
		.add("button","MIDDLE")
		.add("action", "ACTION3")
		.build();
		
		bindings.add(firstBinding);
		bindings.add(secondBinding);
		bindings.add(thirdBinding);
		
		return bindings.build();
	}
	
	public static JsonArray makeFaultyJSONMouseConfig() {
		var bindings =  Json.createArrayBuilder();

		var firstBinding = Json.createObjectBuilder()
		.add("somethingElse","rubbish")
		.add("action","ACTION1")
		.build();
		
		
		bindings.add(firstBinding);
		
		return bindings.build();
	}
	
	public static JsonArray makeEmptyMouseConfigJsonArray() {
		var bindings =  Json.createArrayBuilder();
		return bindings.build();
	}

	
	public static JsonObject makeFullConfigJSON() {
		var json = Json.createObjectBuilder()
		.add("settings",makeFullSettingsJSON())
		.add("keyBindings",makeJSONKeyConfigWithEntries())
		.add("mouseBindings",makeJSONMouseConfigWithEntries())
		.add("loggerSettings", makeFullLoggerSettingsJSON());
		
		return json.build();
	}
	
	public static JsonObject makeFaultyConfigJSON() {
		var json = Json.createObjectBuilder()
		.add("rubbish","More Rubbish");
		return json.build();
	}

	public static JsonObject makeConfigJSONWithFaultrySubJSON() {
		var json = Json.createObjectBuilder()
		.add("settings",makeFaultySettingsJSON())
		.add("keyBindings",makeJSONKeyConfigWithEntries())
		.add("mouseBindings",makeJSONMouseConfigWithEntries())
		.add("loggerSettings", makeFullLoggerSettingsJSON());

		return json.build();
	}
}
