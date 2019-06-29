package junit.json;



import static org.junit.jupiter.api.Assertions.*;

import javax.json.JsonArray;

import static junit.testhelpers.TestJSONFactory.*;
 
import org.junit.jupiter.api.Test;

import config.JSON.JSONKeyConfig;
import config.interfaces.KeyConfig;

class JSONKeyConfigTests implements JSONTests{

	@Test
	public void testLoadFromJSON_allItemsThere_shouldBeRead() {
		JsonArray stubBindings = makeJSONKeyConfigWithEntries();
		
		KeyConfig testObject = new JSONKeyConfig(stubBindings);
		
		assertEquals(2,testObject.getKeyBindings().size());
	}

	@Test
	public void testLoadFromJSON_validJsonArray_BindingsShouldBeAccessible() {
		JsonArray stubBindings = makeJSONKeyConfigWithEntries();
		
		KeyConfig testObject = new JSONKeyConfig(stubBindings);
		
		assertEquals("ACTION1",testObject.getKeyBindings().get("KEY1"));
		assertEquals("ACTION2",testObject.getKeyBindings().get("KEY2"));
	}
	
	@Test
	public void testLoadFromJSON_faultyFormat_ShouldFail() {
		JsonArray stubBindings = makeFaultyJSONKeyConfig();
		
		assertThrows(Exception.class,
				() ->new JSONKeyConfig(stubBindings));
	}

	@Test
	public void testLoadFromJSON_someItemsAreMissing_ShouldFail() {
		JsonArray stubBindings = makeEmptyJsonArray();
		// Other items file, but i think it�s perfectly reasonable to have no key bindings
		// So it�s ok but should be Empty
		KeyConfig testObject = new JSONKeyConfig(stubBindings);
		
		assertEquals(0,testObject.getKeyBindings().size());
	}

	@Test
	public void testPushToJSON_shouldBeSameAsLoaded() {
		JsonArray stubBindings = makeJSONKeyConfigWithEntries();
		
		JSONKeyConfig testObject = new JSONKeyConfig(stubBindings);
		JsonArray result = testObject.toJSON();
		
		// I cannot use assertEquals as JSON Things behave quite strange
		assertTrue(compareArrays(stubBindings,result));
	}

	@Test
	public void testPushToJSON_someThingAltered_shouldBeDifferent() {
		JsonArray stubBindings = makeJSONKeyConfigWithEntries();
		
		JSONKeyConfig testObject = new JSONKeyConfig(stubBindings);
		
		testObject.putKeyBinding("KEY3","ACTION3");
		JsonArray result = testObject.toJSON();
		
		assertNotEquals(stubBindings.toString(),result.toString());
	}

	@Test
	public void testPushToJSON_reload_shouldBeRead() {
		JsonArray stubBindings = makeJSONKeyConfigWithEntries();
		
		JSONKeyConfig testObject = new JSONKeyConfig(stubBindings);
		JsonArray intermediate = testObject.toJSON();
		
		JSONKeyConfig result = new JSONKeyConfig(intermediate);
		
		assertEquals(2,result.getKeyBindings().size());
		assertEquals("ACTION1",result.getKeyBindings().get("KEY1"));
		assertEquals("ACTION2",result.getKeyBindings().get("KEY2"));
	}
	
}
