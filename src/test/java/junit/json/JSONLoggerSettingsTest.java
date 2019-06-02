package junit.json;



import static org.junit.jupiter.api.Assertions.*;

import org.apache.logging.log4j.Level;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import config.JSON.JSONLoggerSettings;
import config.interfaces.LoggerSettings;

import static junit.testhelpers.TestJSONFactory.*;

class JSONLoggerSettingsTest implements JSONTests {

	@Test
	public void testLoadFromJSON_allItemsThere_shouldBeRead() {
		//Also check getters on the way
		JSONObject toLoad = makeFullLoggerSettingsJSON();
		LoggerSettings testObject = new JSONLoggerSettings(toLoad);
		
		assertEquals("TestFile.txt",testObject.getLogfile());
		assertEquals(Level.ERROR,testObject.getLevel());
		assertTrue(testObject.isAppend());
	}
	
	@Test
	public void testLoadFromJSON_someItemsAreMissing_ShouldFail() {
		JSONObject toLoad = makeEmptySettingsJSON();
		assertThrows(Exception.class,
				() -> new JSONLoggerSettings(toLoad));
	}
	
	@Test
	public void testLoadFromJSON_faultyFormat_ShouldFail() {
		JSONObject toLoad = makeFaultyLoggerSettingsJSON();
		assertThrows(Exception.class,
				() -> new JSONLoggerSettings(toLoad));
	}
	
	@Test
	public void testPushToJSON_shouldBeSameAsLoaded() {
		JSONObject toLoad = makeFullLoggerSettingsJSON();
		JSONLoggerSettings testObject = new JSONLoggerSettings(toLoad);
		
		JSONObject result = testObject.toJSON();
		
		assertEquals(toLoad.toString(),result.toString());
	}
	
	@Test
	public void testSetLevel_enterValidBigString_shouldbeParsed() {
		JSONObject toLoad = makeFullLoggerSettingsJSON();
		JSONLoggerSettings testObject = new JSONLoggerSettings(toLoad);
		
		testObject.setLevel("DEBUG");
		
		assertEquals(Level.DEBUG,testObject.getLevel());
	}
	
	@Test
	public void testSetLevel_enterValidSmallString_shouldbeParsed() {
		JSONObject toLoad = makeFullLoggerSettingsJSON();
		JSONLoggerSettings testObject = new JSONLoggerSettings(toLoad);
		
		testObject.setLevel("debug");
		
		assertEquals(Level.DEBUG,testObject.getLevel());
	}
	
	@Test
	public void testSetLevel_enterValidString_shouldbeParsed() {
		JSONObject toLoad = makeFullLoggerSettingsJSON();
		JSONLoggerSettings testObject = new JSONLoggerSettings(toLoad);
		
		testObject.setLevel("Debug");
		
		assertEquals(Level.DEBUG,testObject.getLevel());
	}
	
	@Test
	public void testSetLevel_enterBadString_shouldbeDefault() {
			JSONObject toLoad = makeFullLoggerSettingsJSON();
			JSONLoggerSettings testObject = new JSONLoggerSettings(toLoad);
			
			testObject.setLevel("rubbish");
			
			assertEquals(Level.ERROR,testObject.getLevel());
	}
	
	@Test
	public void testPushToJSON_someThingAltered_shouldBeDifferent() {
		JSONObject toLoad = makeFullLoggerSettingsJSON();
		JSONLoggerSettings testObject = new JSONLoggerSettings(toLoad);
		
		testObject.setAppend(false);
		testObject.setLogfile("OtherFile.txt");
		testObject.setLevel(Level.DEBUG);
		JSONObject result = testObject.toJSON();
		
		assertNotEquals(toLoad,result);
	}
	
	@Test
	public void testPushToJSON_reload_shouldBeRead() {
		JSONObject toLoad = makeFullLoggerSettingsJSON();
		JSONLoggerSettings testObject = new JSONLoggerSettings(toLoad);
		
		JSONObject intermediate = testObject.toJSON();
		
		JSONLoggerSettings testResult = new JSONLoggerSettings(intermediate);
		
		assertEquals("TestFile.txt",testResult.getLogfile());
		assertEquals(Level.ERROR,testResult.getLevel());
		assertTrue(testResult.isAppend());
	}
	
	@Test
	public void testParseLogLevel_EnterTrace_shouldBeTrace() {
		Level result = JSONLoggerSettings.tryParseLogLevel("TRACE");
		
		assertEquals(Level.TRACE,result);
	}
	@Test
	public void testParseLogLevel_EnterInfo_shouldBeInfo() {
		Level result = JSONLoggerSettings.tryParseLogLevel("INFO");
		
		assertEquals(Level.INFO,result);
	}
	@Test
	public void testParseLogLevel_EnterOff_shouldBeOff() {
		Level result = JSONLoggerSettings.tryParseLogLevel("OFF");
		
		assertEquals(Level.OFF,result);
	}
	
	@Test
	public void testParseLogLevel_EnterWarn_shouldBeWarn() {
		Level result = JSONLoggerSettings.tryParseLogLevel("WARN");
		
		assertEquals(Level.WARN,result);
	}
	@Test
	public void testParseLogLevel_EnterError_shouldBeError() {
		Level result = JSONLoggerSettings.tryParseLogLevel("ERROR");
		
		assertEquals(Level.ERROR,result);
	}
	@Test
	public void testParseLogLevel_EnterDebug_shouldBeDebug() {
		Level result = JSONLoggerSettings.tryParseLogLevel("DEBUG");
		
		assertEquals(Level.DEBUG,result);
	}
	
	@Test
	public void testParseLogLevel_enterStringWithLeadingWhiteSpace_shouldBeParsedCorrect() {
		Level result = JSONLoggerSettings.tryParseLogLevel(" WARN");
		
		assertEquals(Level.WARN,result);
	}
	
	@Test
	public void testParseLogLevel_enterStringWithTailingWhiteSpace_shouldBeParsedCorrect() {
		Level result = JSONLoggerSettings.tryParseLogLevel("WARN ");
		
		assertEquals(Level.WARN,result);
	}
	
	@Test
	public void testParseLogLevel_StringHasWhiteSpacesInIt_shouldYieldDefaultValue() {
		Level result = JSONLoggerSettings.tryParseLogLevel("WA RN");
		
		assertEquals(Level.ERROR,result);
	}
	
}
