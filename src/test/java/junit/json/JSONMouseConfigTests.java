package junit.json;



import static junit.testhelpers.TestJSONFactory.compareArrays;
import static junit.testhelpers.TestJSONFactory.makeFaultyJSONMouseConfig;
import static junit.testhelpers.TestJSONFactory.makeJSONMouseConfigWithEntries;
import static junit.testhelpers.TestJSONFactory.makeEmptyMouseConfigJsonArray;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.json.JsonArray;

import org.junit.jupiter.api.Test;

import config.JSON.JSONMouseConfig;
import config.interfaces.MouseConfig;
import javafx.scene.input.MouseButton;

class JSONMouseConfigTests implements JSONTests{

	@Test
	public void testLoadFromJSON_allItemsThere_shouldBeRead() {
		JsonArray stubBindings = makeJSONMouseConfigWithEntries();
		
		MouseConfig testObject = new JSONMouseConfig(stubBindings);
		
		assertEquals(3,testObject.getKeyBindings().size());
	}

	@Test
	public void testLoadFromJSON_validJsonArray_BindingsShouldBeAccessible() {
		JsonArray stubBindings = makeJSONMouseConfigWithEntries();
		
		MouseConfig testObject = new JSONMouseConfig(stubBindings);
		
		assertEquals("ACTION1",testObject.getKeyBindings().get(MouseButton.PRIMARY));
		assertEquals("ACTION2",testObject.getKeyBindings().get(MouseButton.SECONDARY));
		assertEquals("ACTION3",testObject.getKeyBindings().get(MouseButton.MIDDLE));
	}

	@Test
	public void testLoadFromJSON_faultyFormat_ShouldFail() {
		JsonArray stubBindings = makeFaultyJSONMouseConfig();
		
		assertThrows(Exception.class,
				() ->new JSONMouseConfig(stubBindings));
	}
	
	@Test
	public void testLoadFromJSON_someItemsAreMissing_ShouldFail() {
		JsonArray stubBindings = makeEmptyMouseConfigJsonArray();
		// Other items file, but i think it's perfectly reasonable to have no key bindings
		// So it's ok but should be Empty
		MouseConfig testObject = new JSONMouseConfig(stubBindings);
		
		assertEquals(0,testObject.getKeyBindings().size());
	}

	@Test
	public void testPushToJSON_shouldBeSameAsLoaded() {
		JsonArray stubBindings = makeJSONMouseConfigWithEntries();
		
		JSONMouseConfig testObject = new JSONMouseConfig(stubBindings);
		JsonArray result = testObject.toJSON();
		
		// I cannot use assertEquals as JSON Things behave quite strange
		assertTrue(compareArrays(stubBindings,result));
	}

	@Test
	public void testPushToJSON_someThingAltered_shouldBeDifferent() {
		JsonArray stubBindings = makeJSONMouseConfigWithEntries();
		
		JSONMouseConfig testObject = new JSONMouseConfig(stubBindings);
		
		testObject.putKeyBinding("KEY3","ACTION3");
		JsonArray result = testObject.toJSON();
		
		assertNotEquals(stubBindings.toString(),result.toString());
	}

	@Test
	public void putMouseBinding_overExistingBinding_shouldBeNew() {
		JsonArray stubBindings = makeJSONMouseConfigWithEntries();
		JSONMouseConfig testObject = new JSONMouseConfig(stubBindings);
		
		testObject.putKeyBinding(MouseButton.PRIMARY,"NEWACTION");
		
		assertEquals("NEWACTION",testObject.getKeyBindings().get(MouseButton.PRIMARY));
	}
	
	@Test
	public void testPushToJSON_reload_shouldBeRead() {
		JsonArray stubBindings = makeJSONMouseConfigWithEntries();
		
		JSONMouseConfig testObject = new JSONMouseConfig(stubBindings);
		JsonArray intermediate = testObject.toJSON();
		
		JSONMouseConfig result = new JSONMouseConfig(intermediate);
		
		assertEquals(3,result.getKeyBindings().size());
		assertEquals("ACTION1",result.getKeyBindings().get(MouseButton.PRIMARY));
		assertEquals("ACTION2",result.getKeyBindings().get(MouseButton.SECONDARY));
		assertEquals("ACTION3",result.getKeyBindings().get(MouseButton.MIDDLE));
	}
	
}
