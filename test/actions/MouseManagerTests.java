package actions;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import config.interfaces.Config;
import config.interfaces.MouseConfig;
import fakes.config.FakeConfig;
import fakes.config.FakeMouseConfig;
import interfaces.geom.Point;
import javafx.scene.input.MouseButton;
import logic.interaction.Action;
import logic.interaction.MouseManager;
import logic.interaction.SimpleAction;
import logic.manager.ManagerRegistry;

import javafx.scene.input.MouseEvent;

@SuppressWarnings("restriction")
public class MouseManagerTests {
	
	@AfterEach
	public void resetManagerRegistry() {
		ManagerRegistry.reset();
		doneSomething=false;
	}
	
	private static boolean doneSomething=false;
	private static void doSomething() {
		doneSomething=true;
	}
	
	@Test
	public void testConstructor_hasNoRegisteredItems() {
		MouseManager testObject = new MouseManager();
		
		assertTrue(testObject.getRegisteredItems().isEmpty());
	}
	
	@Test
	public void testConstructor_shouldBeRunning() {
		MouseManager testObject = new MouseManager();
		
		assertTrue(testObject.isRunning());
	}
	
	@Test
	public void registerAction_shouldBeRegistered() {
		MouseManager testObject = new MouseManager();
		
		MouseButton right = MouseButton.PRIMARY;
		Action sampleAction = new SimpleAction("TestAction",()->doSomething());
		
		testObject.registerMouseBindings(right, sampleAction);
		
		assertTrue(testObject.getRegisteredItems().contains(sampleAction));
	}
	
	@Test
	public void testConstructor_MousePosIsNull() {
		MouseManager testObject = new MouseManager();
		
		assertEquals(null,testObject.getMousePos());
	}
	
	@Test
	public void registerAction_registerTwice_shouldBeOverwritten() {
		MouseManager testObject = new MouseManager();
		
		MouseButton right = MouseButton.PRIMARY;
		Action sampleAction = new SimpleAction("Any",()->doSomething());
		Action sampleOverwrite = new SimpleAction("Other",()->doSomething());
		
		testObject.registerMouseBindings(right, sampleAction);
		testObject.registerMouseBindings(right, sampleOverwrite);
		
		assertFalse(testObject.getRegisteredItems().contains(sampleAction));
		assertTrue(testObject.getRegisteredItems().contains(sampleOverwrite));
	}
	
	@Test
	public void testInit_emptyConfig_hasNoRegisteredItems() {
		MouseManager testObject = new MouseManager();
		
		MouseConfig fake = new FakeMouseConfig();
		Config topLevelFake= new FakeConfig();
		topLevelFake.setMouseConfig(fake);
		
		testObject.init(topLevelFake);
		
		assertTrue(testObject.getRegisteredItems().isEmpty());
	}
	
	@Test
	public void testInit_configHasItem_isNotInActionManager_shouldBeEmpty() {
		MouseManager testObject = new MouseManager();
		
		MouseConfig fake = new FakeMouseConfig();
		Config topLevelFake= new FakeConfig();
		topLevelFake.setMouseConfig(fake);
		
		fake.putKeyBinding(MouseButton.PRIMARY, "not registered");
		
		testObject.init(topLevelFake);
		
		assertTrue(testObject.getRegisteredItems().isEmpty());
	}

	@Test
	public void testInit_configHasItem_shouldNotBeEmpty() {
		MouseManager testObject = new MouseManager();
		
		MouseConfig fake = new FakeMouseConfig();
		Config topLevelFake= new FakeConfig();
		topLevelFake.setMouseConfig(fake);

		//The Quit Action is in the standard Action Registry
		fake.putKeyBinding(MouseButton.PRIMARY, "Quit");
		
		testObject.init(topLevelFake);
		
		assertFalse(testObject.getRegisteredItems().isEmpty());
	}
	
	@Test
	public void testInit_configHasQuitAction_shouldContainQuitAction() {
		MouseManager testObject = new MouseManager();
		
		MouseConfig fake = new FakeMouseConfig();
		Config topLevelFake= new FakeConfig();
		topLevelFake.setMouseConfig(fake);

		//The Quit Action is in the standard Action Registry
		fake.putKeyBinding(MouseButton.PRIMARY, "Quit");
		
		testObject.init(topLevelFake);
		
		assertTrue(testObject.getRegisteredItems().contains(ManagerRegistry.getActionManager().getActionByName("Quit")));
	}
	
	@Test
	public void testToggleUpdate_WasOff_shouldBeOn() {
		MouseManager testObject = new MouseManager();
		
		testObject.toggleUpdate();
		testObject.toggleUpdate();
		
		assertTrue(testObject.isRunning());
	}
	
	@Test
	public void testToggleUpdate_WasOn_shouldBeOff() {
		MouseManager testObject = new MouseManager();
		
		testObject.toggleUpdate();
		
		assertFalse(testObject.isRunning());
	}
	
	@Test
	public void testReset_shouldHaveNoRegisteredItems() {
		MouseManager testObject = new MouseManager();
		
		MouseButton right = MouseButton.PRIMARY;
		Action sampleAction = new SimpleAction("TestAction",()->doSomething());
		
		testObject.registerMouseBindings(right, sampleAction);
		
		testObject.reset();
		
		assertTrue(testObject.getRegisteredItems().isEmpty());
	}
	
	@Test
	public void testMouseClicked_NoActionBound_NothingHappens() {
		MouseManager testObject = new MouseManager();
		
		int x=10,y=10;
		MouseEvent fakeEvent = new MouseEvent(MouseEvent.MOUSE_CLICKED, x, y, 0, 0, MouseButton.PRIMARY, 0, false, false, false, false, false, false, false, false, false, false, null);
		
		testObject.mouseClicked(fakeEvent);
		
		assertFalse(doneSomething);
	}
	
	@Test
	public void testMouseClicked_ActionBound_ActionIsPerformed() {
		MouseManager testObject = new MouseManager();
		int x=10,y=10;
		MouseEvent fakeEvent = new MouseEvent(MouseEvent.MOUSE_CLICKED, x, y, 0, 0, MouseButton.PRIMARY, 0, false, false, false, false, false, false, false, false, false, false, null);
		
		MouseButton right = MouseButton.PRIMARY;
		Action sampleAction = new SimpleAction("TestAction",()->doSomething());
		testObject.registerMouseBindings(right,sampleAction);
		
		testObject.mouseClicked(fakeEvent);
		
		assertTrue(doneSomething);
	}
	
	@Test
	public void testMouseClicked_mousePositionIsUpdated() {
		MouseManager testObject = new MouseManager();
		int x=10,y=10;
		MouseEvent fakeEvent = new MouseEvent(MouseEvent.MOUSE_CLICKED, x, y, 0, 0, MouseButton.PRIMARY, 0, false, false, false, false, false, false, false, false, false, false, null);
		
		MouseButton right = MouseButton.PRIMARY;
		Action sampleAction = new SimpleAction("TestAction",()->doSomething());
		testObject.registerMouseBindings(right,sampleAction);
		
		testObject.mouseClicked(fakeEvent);
		
		Point result = testObject.getMousePos();
		
		assertEquals(x,result.getX());
		assertEquals(y,result.getY());
	}
	
	
}
