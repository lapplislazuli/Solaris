package junit.json;



import static junit.testhelpers.TestJSONFactory.makeEmptySettingsJSON;
import static junit.testhelpers.TestJSONFactory.makeFaultySettingsJSON;
import static junit.testhelpers.TestJSONFactory.makeFullSettingsJSON;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.json.JsonObject;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import config.JSON.JSONSettings;
import config.interfaces.Settings;

class JSONSettingTests implements JSONTests {

	@Tag("JSON")
	@Tag("fast")
	@Tag("config")
	@Test
	public void testLoadFromJSON_allItemsThere_shouldBeRead() {
		//Also check getters on the way
		JsonObject toLoad = makeFullSettingsJSON();
		Settings testObject = new JSONSettings(toLoad);
		
		assertEquals(200,testObject.getScreenHeight());
		assertEquals(200,testObject.getScreenWidth());
		assertEquals(50,testObject.getUpdateIntervall());
		assertTrue(testObject.isPaused());
		assertTrue(testObject.isCollision());
	}
	
	@Tag("JSON")
	@Tag("fast")
	@Tag("config")
	@Test
	public void testLoadFromJSON_someItemsAreMissing_ShouldFail() {
		JsonObject toLoad = makeEmptySettingsJSON();
		assertThrows(Exception.class,
				() -> new JSONSettings(toLoad));
	}
	
	@Tag("JSON")
	@Tag("fast")
	@Tag("config")
	@Test
	public void testLoadFromJSON_FaultyJSONObject_ShouldFail() {
		JsonObject toLoad = makeFaultySettingsJSON();
		assertThrows(Exception.class,
				() -> new JSONSettings(toLoad));
	}
	
	@Tag("JSON")
	@Tag("fast")
	@Tag("config")
	@RepeatedTest(3)
	@Test
	public void testPushToJSON_shouldBeSameAsLoaded() {
		JsonObject toLoad = makeFullSettingsJSON();
		JSONSettings testObject = new JSONSettings(toLoad);
		
		JsonObject result = testObject.toJSON();
		
		assertEquals(toLoad.toString(),result.toString());
	}
	
	@Tag("JSON")
	@Tag("fast")
	@Tag("config")
	@Test
	public void testPushToJSON_someThingAltered_shouldBeDifferent() {
		JsonObject toLoad = makeFullSettingsJSON();
		JSONSettings testObject = new JSONSettings(toLoad);
		
		testObject.setCollision(false);
		testObject.setPaused(false);
		testObject.setScreenHeight(500);
		testObject.setScreenWidth(200);
		testObject.setUpdateIntervall(51);
		
		JsonObject result = testObject.toJSON();
		
		assertNotEquals(toLoad,result);
	}
	
	@Tag("JSON")
	@Tag("fast")
	@Tag("config")
	@RepeatedTest(3)
	@Test
	public void testPushToJSON_reload_shouldBeRead() {
		JsonObject toLoad = makeFullSettingsJSON();
		JSONSettings testObject = new JSONSettings(toLoad);
		
		JsonObject intermediate = testObject.toJSON();
		
		JSONSettings testResult = new JSONSettings(intermediate);
		
		assertEquals(200,testResult.getScreenHeight());
		assertEquals(200,testResult.getScreenWidth());
		assertEquals(50,testResult.getUpdateIntervall());
		assertTrue(testResult.isPaused());
		assertTrue(testResult.isCollision());
	}
	
	
}
