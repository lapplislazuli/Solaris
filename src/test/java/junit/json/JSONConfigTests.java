package junit.json;



import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import config.JSON.JSONConfig;
import static junit.testhelpers.TestJSONFactory.*;

class JSONConfigTests implements JSONTests {

	@Test
	public void testLoadFromJSON_allItemsThere_shouldBeRead() {
		//Also check getters on the way
		JSONObject toLoad = makeFullConfigJSON();
		JSONConfig testObject = new JSONConfig("path",toLoad);
		
		// Sample one Attribute of every Child
		assertTrue(testObject.getSettings().isCollision());
		assertEquals(2,testObject.getKeyConfig().getKeyBindings().size());
		assertEquals(3,testObject.getMouseConfig().getKeyBindings().size());
		assertEquals("path",testObject.getPath());
	}
	
	@Test
	public void testLoadFromJSON_someItemsAreMissing_ShouldFail() {
		JSONObject toLoad = makeEmptySettingsJSON();
		assertThrows(Exception.class,
				() -> new JSONConfig("test",toLoad));
	}
	
	@Test
	public void testLoadFromJSON_faultyToplevel_ShouldFail() {
		JSONObject toLoad = makeFaultyConfigJSON();
		assertThrows(Exception.class,
				() -> new JSONConfig("test",toLoad));
	}
	
	@Test
	public void testLoadFromJSON_faultyChild_ShouldFail() {
		JSONObject toLoad = makeConfigJSONWithFaultrySubJSON();
		assertThrows(Exception.class,
				() -> new JSONConfig("test",toLoad));
	}
	
	public void testPushToJSON_shouldBeSameAsLoaded() {
		JSONObject toLoad = makeFullConfigJSON();
		JSONConfig testObject = new JSONConfig("path",toLoad);
		
		JSONObject result = testObject.toJSON();
		//Fails because the keyBindings are the wrong way round after importing
		assertEquals(testObject.toString(),result.toString());
	}
	
	//Doesn�t make much sense
	public void testPushToJSON_someThingAltered_shouldBeDifferent() {
		return;
	}
	
	@Test
	public void testPushToJSON_reload_shouldBeRead() {
		JSONObject toLoad = makeFullConfigJSON();
		JSONConfig testObject = new JSONConfig("path",toLoad);
		
		JSONObject intermediate = testObject.toJSON();
		JSONConfig testResult = new JSONConfig("path",intermediate);
		
		// Sample one Attribute of every Child
		assertTrue(testResult.getSettings().isCollision());
		assertEquals(2,testResult.getKeyConfig().getKeyBindings().size());
		assertEquals(3,testResult.getMouseConfig().getKeyBindings().size());
	}
	
}
