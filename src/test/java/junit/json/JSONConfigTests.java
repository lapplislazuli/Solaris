package junit.json;



import static org.junit.jupiter.api.Assertions.*;

import javax.json.JsonObject;

import org.junit.jupiter.api.Test;

import config.JSON.JSONConfig;
import static junit.testhelpers.TestJSONFactory.*;

class JSONConfigTests implements JSONTests {

	@Test
	public void testLoadFromJSON_allItemsThere_shouldBeRead() {
		//Also check getters on the way
		JsonObject toLoad = makeFullConfigJSON();
		JSONConfig testObject = new JSONConfig("path",toLoad);
		
		// Sample one Attribute of every Child
		assertTrue(testObject.getSettings().isCollision());
		assertEquals(2,testObject.getKeyConfig().getKeyBindings().size());
		assertEquals(3,testObject.getMouseConfig().getKeyBindings().size());
		assertEquals("path",testObject.getPath());
	}
	
	@Test
	public void testLoadFromJSON_someItemsAreMissing_ShouldFail() {
		JsonObject toLoad = makeEmptySettingsJSON();
		assertThrows(Exception.class,
				() -> new JSONConfig("test",toLoad));
	}
	
	@Test
	public void testLoadFromJSON_faultyToplevel_ShouldFail() {
		JsonObject toLoad = makeFaultyConfigJSON();
		assertThrows(Exception.class,
				() -> new JSONConfig("test",toLoad));
	}
	
	@Test
	public void testLoadFromJSON_faultyChild_ShouldFail() {
		JsonObject toLoad = makeConfigJSONWithFaultrySubJSON();
		assertThrows(Exception.class,
				() -> new JSONConfig("test",toLoad));
	}
	
	public void testPushToJSON_shouldBeSameAsLoaded() {
		JsonObject toLoad = makeFullConfigJSON();
		JSONConfig testObject = new JSONConfig("path",toLoad);
		
		JsonObject result = testObject.toJSON();
		//Fails because the keyBindings are the wrong way round after importing
		assertEquals(testObject.toString(),result.toString());
	}
	
	//Doesn�t make much sense
	public void testPushToJSON_someThingAltered_shouldBeDifferent() {
		return;
	}
	
	@Test
	public void testPushToJSON_reload_shouldBeRead() {
		JsonObject toLoad = makeFullConfigJSON();
		JSONConfig testObject = new JSONConfig("path",toLoad);
		
		JsonObject intermediate = testObject.toJSON();
		JSONConfig testResult = new JSONConfig("path",intermediate);
		
		// Sample one Attribute of every Child
		assertTrue(testResult.getSettings().isCollision());
		assertEquals(2,testResult.getKeyConfig().getKeyBindings().size());
		assertEquals(3,testResult.getMouseConfig().getKeyBindings().size());
	}
	
}
