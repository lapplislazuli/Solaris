package actions;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import logic.interaction.Action;
import logic.interaction.SimpleAction;
import logic.manager.ManagerRegistry;

class SimpleActionTests {
	
	/*
	 * Easy helpers for very simple actions
	 */
	public static boolean actionDone=false;
	public static void truer() {
		actionDone=true;
	}
	public static void empty() {}
	
	@AfterEach
	public void resetActionDone() {
		actionDone=false;
	}
	
	@Test
	void testConstructor_hasNameSet() {
		Action testObject = new SimpleAction(
				"Test" ,
				"A Test Action" ,
				()->truer());
		
		assertEquals("Test",testObject.getName());
		assertEquals("A Test Action",testObject.getDescription());
	}
	
	@Test
	void testConstructor_smallerConstructor_hasName() {
		Action testObject = new SimpleAction(
				"Test" ,
				()->truer());
		
		assertEquals("Test",testObject.getName());
	}
	
	@Test
	void testConstructor_smallerConstructor_getDescription_isNull() {
		Action testObject = new SimpleAction(
				"Test" ,
				()->truer());
		//The most important thing about this test is that there is no error thrown
		assertEquals(null,testObject.getDescription());
	}
	
	@Test
	void testConstructor_actionIsNotDone() {
		Action testObject = new SimpleAction(
				"Test" ,
				"A Test Action" ,
				()->truer());
		
		assertFalse(actionDone);
	}

	@Test
	void testDoAction_actionShouldBeDone() {
		Action testObject = new SimpleAction(
				"Test" ,
				"A Test Action" ,
				()->truer());
		
		testObject.doAction();
		
		assertTrue(actionDone);
	}
	
	@Test
	void testSetDescription_shouldBeNewDescription() {
		Action testObject = new SimpleAction(
				"Test" ,
				"A Test Action" ,
				()->truer());
		
		testObject.setDescription("A new Description");
		
		assertEquals("A new Description",testObject.getDescription());
	}
	
	@Test
	void testEquals_sameName_shouldBeEquals() {
		Action testObject = new SimpleAction(
				"SameTestName" ,
				"A Test Action With the same name as another" ,
				()->truer());
		
		Action comparison = new SimpleAction(
				"SameTestName" ,
				"The Action to Compare with a different Function" ,
				()->empty());
		
		
		assertEquals(comparison,testObject);
	}
	
	@Test
	void testEquals_sameActions_differentNames_shouldNotBeEqual() {
		Action testObject = new SimpleAction(
				"Testname" ,
				"A Test Action With the same name as another" ,
				()->truer());
		
		Action comparison = new SimpleAction(
				"DifferentName" ,
				"The Action to Compare with the same Function" ,
				()->truer());
		
		
		assertNotEquals(comparison,testObject);
	}
	
	@Test
	void testEquals_compareToSomeOtherObject() {
		Action testObject = new SimpleAction(
			"Testname" ,
			"A Test Action With the same name as another" ,
			()->truer());
		
		Integer sample = new Integer(5);
		
		assertNotEquals(sample,testObject);
	}
	
}
