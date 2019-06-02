package junit.actions;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import config.interfaces.Config;
import config.interfaces.KeyConfig;
import logic.interaction.Action;
import logic.interaction.KeyBoardManager;
import logic.interaction.SimpleAction;
import javafx.scene.input.KeyEvent;
import junit.fakes.config.FakeConfig;
import junit.fakes.config.FakeKeyConfig;
import logic.manager.ManagerRegistry;

public class KeyBoardManagerTests {
	
	@AfterEach
	public void resetManagerRegistry() {
		ManagerRegistry.reset();
		doneSomething=false;
	}
	
	private static boolean doneSomething=false;
	private static void doSomething() {
		doneSomething=true;
	}
	private static void undoSomething() {
		doneSomething=false;
	}
	
	@Test
	public void testConstructor_hasNoRegisteredItems() {
		KeyBoardManager testObject = new KeyBoardManager();
		
		assertTrue(testObject.getRegisteredItems().isEmpty());
	}
	
	@Test
	public void testConstructor_shouldBeRunning() {
		KeyBoardManager testObject = new KeyBoardManager();
		assertTrue(testObject.isRunning());
	}
	
	@Test
	public void testConstructor_noButtonIsPressed() {
		KeyBoardManager testObject = new KeyBoardManager();
		
		assertEquals(Character.UNASSIGNED,testObject.getCurrentPressed());
	}
	
	@Test
	public void registerAction_shouldBeRegistered() {
		KeyBoardManager testObject = new KeyBoardManager();

		char a = 'a';
		Action sampleAction = new SimpleAction("TestAction",()->doSomething());
		
		testObject.registerKeyBinding(a, sampleAction);
		
		assertTrue(testObject.getRegisteredItems().contains(sampleAction));
	}
	/*
	@Test
	public void testConstructor_MousePosIsNull() {
		KeyBoardManager testObject = new KeyBoardManager();
		
		//assertEquals(null,testObject.getMousePos());
	}
	*/
	@Test
	public void registerAction_registerTwice_shouldBeOverwritten() {
		KeyBoardManager testObject = new KeyBoardManager();
		
		char a = 'a';
		Action sampleAction = new SimpleAction("Any",()->doSomething());
		Action sampleOverwrite = new SimpleAction("Other",()->doSomething());

		testObject.registerKeyBinding(a, sampleAction);
		testObject.registerKeyBinding(a, sampleOverwrite);
		
		assertFalse(testObject.getRegisteredItems().contains(sampleAction));
		assertTrue(testObject.getRegisteredItems().contains(sampleOverwrite));
	}
	
	@Test
	public void testInit_emptyConfig_hasNoRegisteredItems() {
		KeyBoardManager testObject = new KeyBoardManager();
		

		KeyConfig fake = new FakeKeyConfig();
		Config topLevelFake= new FakeConfig();
		topLevelFake.setKeyConfig(fake);
		
		testObject.init(topLevelFake);
		
		assertTrue(testObject.getRegisteredItems().isEmpty());
	}
	
	@Test
	public void testInit_configHasItem_isNotInActionManager_shouldBeEmpty() {
		KeyBoardManager testObject = new KeyBoardManager();
		
		KeyConfig fake = new FakeKeyConfig();
		Config topLevelFake= new FakeConfig();
		topLevelFake.setKeyConfig(fake);
		
		fake.putKeyBinding("a", "not registered");
		
		testObject.init(topLevelFake);
		
		assertTrue(testObject.getRegisteredItems().isEmpty());
	}

	@Test
	public void testInit_configHasItem_shouldNotBeEmpty() {
		KeyBoardManager testObject = new KeyBoardManager();
		
		KeyConfig fake = new FakeKeyConfig();
		Config topLevelFake= new FakeConfig();
		topLevelFake.setKeyConfig(fake);
		

		//The Quit Action is in the standard Action Registry
		fake.putKeyBinding("a","Quit");
		
		testObject.init(topLevelFake);
		
		assertFalse(testObject.getRegisteredItems().isEmpty());
	}
	
	@Test
	public void testInit_configHasQuitAction_shouldContainQuitAction() {
		KeyBoardManager testObject = new KeyBoardManager();

		KeyConfig fake = new FakeKeyConfig();
		Config topLevelFake= new FakeConfig();
		topLevelFake.setKeyConfig(fake);

		//The Quit Action is in the standard Action Registry
		fake.putKeyBinding("a", "Quit");
		
		testObject.init(topLevelFake);
		
		assertTrue(testObject.getRegisteredItems().contains(ManagerRegistry.getActionManager().getActionByName("Quit")));
	}
	
	@Test
	public void testToggleUpdate_WasOff_shouldBeOn() {
		KeyBoardManager testObject = new KeyBoardManager();
		
		testObject.toggleUpdate();
		testObject.toggleUpdate();
		
		assertTrue(testObject.isRunning());
	}
	
	@Test
	public void testToggleUpdate_WasOn_shouldBeOff() {
		KeyBoardManager testObject = new KeyBoardManager();
		
		testObject.toggleUpdate();
		
		assertFalse(testObject.isRunning());
	}
	
	@Test
	public void testReset_shouldHaveNoRegisteredItems() {
		KeyBoardManager testObject = new KeyBoardManager();

		char a = 'a';
		Action sampleAction = new SimpleAction("TestAction",()->doSomething());
		testObject.registerKeyBinding(a, sampleAction);
		
		testObject.reset();
		
		assertTrue(testObject.getRegisteredItems().isEmpty());
	}
	
	@Test
	public void testPressButton_NoActionAssigned_NothingHappens() {
		KeyBoardManager testObject = new KeyBoardManager();
		
		KeyEvent fakeEvent = new KeyEvent(KeyEvent.KEY_TYPED, "a", null, null, false, false, false, false);
		
		testObject.keyTyped(fakeEvent);
		
		assertFalse(doneSomething);
	}
	
	@Test
	public void testPressButton_ActionAssigned_ActionIsDone() {
		KeyBoardManager testObject = new KeyBoardManager();

		char a = 'a';
		Action sampleAction = new SimpleAction("TestAction",()->doSomething());
		testObject.registerKeyBinding(a, sampleAction);

		KeyEvent fakeEvent = new KeyEvent(KeyEvent.KEY_TYPED, "a", null, null, false, false, false, false);
		
		testObject.keyTyped(fakeEvent);
		
		assertTrue(doneSomething);
	}
	
	@Test
	public void testPressButton_ButtonIsAlreadyPressed_ActionIsNotPerformedTwice() {
		KeyBoardManager testObject = new KeyBoardManager();

		char a = 'a';
		Action sampleAction = new SimpleAction("TestAction",()->doSomething());
		testObject.registerKeyBinding(a, sampleAction);

		KeyEvent fakeEvent = new KeyEvent(KeyEvent.KEY_TYPED, "a", null, null, false, false, false, false);
		
		testObject.keyTyped(fakeEvent);
		doneSomething=false;
		testObject.keyTyped(fakeEvent);
		
		assertFalse(doneSomething);
	}
	
	@Test
	public void testPressButton_AnotherButtonIsAlreadyPressed_newActionIsNotPerformed() {
		KeyBoardManager testObject = new KeyBoardManager();

		char a = 'a';
		Action sampleAction = new SimpleAction("TestAction",()->doSomething());
		testObject.registerKeyBinding(a, sampleAction);
		
		char b = 'b';
		Action undoAction = new SimpleAction("TestAction",()->undoSomething());
		testObject.registerKeyBinding(b, undoAction);
		
		KeyEvent fakeEvent = new KeyEvent(KeyEvent.KEY_TYPED, "a", null, null, false, false, false, false);
		KeyEvent undoEvent = new KeyEvent(KeyEvent.KEY_TYPED, "b", null, null, false, false, false, false);
		
		// Do the Event to do Something
		testObject.keyTyped(fakeEvent);
		// While the button is still pressed down, do something else
		testObject.keyTyped(undoEvent);
		// The Second Action is not performed
		assertTrue(doneSomething);
	}
	
	@Test
	public void testPressButton_currentPressedButtonIsSet() {
		KeyBoardManager testObject = new KeyBoardManager();

		char a = 'a';
		Action sampleAction = new SimpleAction("TestAction",()->doSomething());
		testObject.registerKeyBinding(a, sampleAction);

		KeyEvent fakeEvent = new KeyEvent(KeyEvent.KEY_TYPED, "a", null, null, false, false, false, false);
		
		testObject.keyTyped(fakeEvent);
		
		assertEquals(a,testObject.getCurrentPressed());
	}
	
	@Test
	public void testReleaseButton_currentPressedButtonIsReset() {
		KeyBoardManager testObject = new KeyBoardManager();

		char a = 'a';
		Action sampleAction = new SimpleAction("TestAction",()->doSomething());
		testObject.registerKeyBinding(a, sampleAction);

		KeyEvent fakeEvent = new KeyEvent(KeyEvent.KEY_TYPED, "a", null, null, false, false, false, false);
		KeyEvent fakeRelease= new KeyEvent(KeyEvent.KEY_RELEASED,"a",null, null, false, false, false, false);
		
		testObject.keyTyped(fakeEvent);
		testObject.keyReleased(fakeRelease);
		
		assertEquals(Character.UNASSIGNED,testObject.getCurrentPressed());
	}
	
}
