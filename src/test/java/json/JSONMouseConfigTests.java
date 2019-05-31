package json;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import javafx.scene.input.MouseButton;

import config.JSON.JSONMouseConfig;
import config.interfaces.MouseConfig;

import static helpers.TestJSONFactory.*;

@SuppressWarnings("restriction")
class JSONMouseConfigTests implements JSONTests{

	@Test
	public void testLoadFromJSON_allItemsThere_shouldBeRead() {
		JSONArray stubBindings = makeJSONMouseConfigWithEntries();
		
		MouseConfig testObject = new JSONMouseConfig(stubBindings);
		
		assertEquals(3,testObject.getKeyBindings().size());
	}

	@Test
	public void testLoadFromJSON_validJSONArray_BindingsShouldBeAccessible() {
		JSONArray stubBindings = makeJSONMouseConfigWithEntries();
		
		MouseConfig testObject = new JSONMouseConfig(stubBindings);
		
		assertEquals("ACTION1",testObject.getKeyBindings().get(MouseButton.PRIMARY));
		assertEquals("ACTION2",testObject.getKeyBindings().get(MouseButton.SECONDARY));
		assertEquals("ACTION3",testObject.getKeyBindings().get(MouseButton.MIDDLE));
	}

	@Test
	public void testLoadFromJSON_faultyFormat_ShouldFail() {
		JSONArray stubBindings = makeFaultyJSONMouseConfig();
		
		assertThrows(Exception.class,
				() ->new JSONMouseConfig(stubBindings));
	}
	
	@Test
	public void testLoadFromJSON_someItemsAreMissing_ShouldFail() {
		JSONArray stubBindings = makeEmptyMouseConfigJSONArray();
		// Other items file, but i think it�s perfectly reasonable to have no key bindings
		// So it�s ok but should be Empty
		MouseConfig testObject = new JSONMouseConfig(stubBindings);
		
		assertEquals(0,testObject.getKeyBindings().size());
	}

	@Test
	public void testPushToJSON_shouldBeSameAsLoaded() {
		JSONArray stubBindings = makeJSONMouseConfigWithEntries();
		
		JSONMouseConfig testObject = new JSONMouseConfig(stubBindings);
		JSONArray result = testObject.toJSON();
		
		// I cannot use assertEquals as JSON Things behave quite strange
		assertTrue(compareArrays(stubBindings,result));
	}

	@Test
	public void testPushToJSON_someThingAltered_shouldBeDifferent() {
		JSONArray stubBindings = makeJSONMouseConfigWithEntries();
		
		JSONMouseConfig testObject = new JSONMouseConfig(stubBindings);
		
		testObject.putKeyBinding("KEY3","ACTION3");
		JSONArray result = testObject.toJSON();
		
		assertNotEquals(stubBindings.toString(),result.toString());
	}

	@Test
	public void putMouseBinding_overExistingBinding_shouldBeNew() {
		JSONArray stubBindings = makeJSONMouseConfigWithEntries();
		JSONMouseConfig testObject = new JSONMouseConfig(stubBindings);
		
		testObject.putKeyBinding(MouseButton.PRIMARY,"NEWACTION");
		
		assertEquals("NEWACTION",testObject.getKeyBindings().get(MouseButton.PRIMARY));
	}
	
	@Test
	public void testPushToJSON_reload_shouldBeRead() {
		JSONArray stubBindings = makeJSONMouseConfigWithEntries();
		
		JSONMouseConfig testObject = new JSONMouseConfig(stubBindings);
		JSONArray intermediate = testObject.toJSON();
		
		JSONMouseConfig result = new JSONMouseConfig(intermediate);
		
		assertEquals(3,result.getKeyBindings().size());
		assertEquals("ACTION1",result.getKeyBindings().get(MouseButton.PRIMARY));
		assertEquals("ACTION2",result.getKeyBindings().get(MouseButton.SECONDARY));
		assertEquals("ACTION3",result.getKeyBindings().get(MouseButton.MIDDLE));
	}
	
}
