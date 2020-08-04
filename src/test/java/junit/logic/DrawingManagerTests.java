package junit.logic;



import static junit.testhelpers.FakeManagerFactory.fakeDrawingManager;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import junit.fakes.interfaces.FakeDrawingContext;
import junit.fakes.interfaces.FakeDrawingObject;
import logic.manager.DrawingManager;
import logic.manager.ManagerRegistry;

public class DrawingManagerTests implements SharedManagerTests {
	@AfterEach
	void resetManagers() {
		ManagerRegistry.reset();
	}
	
	@Test
	public void testDrawItems_drawSingleItem_shouldBeDrawn() {
		DrawingManager mnger = fakeDrawingManager();
		FakeDrawingObject fake = new FakeDrawingObject();
		
		mnger.registerItem(fake);
		ManagerRegistry.getUpdateManager().registerItem(fake);
		
		mnger.update();
		
		assertTrue(fake.drawn);
	}
	
	@Test
	public void testDrawItems_drawSingleItem_shouldBeInDrawingContext() {
		DrawingManager mnger = new DrawingManager();
		FakeDrawingObject fake = new FakeDrawingObject();
		FakeDrawingContext fakeContext = new FakeDrawingContext();
		mnger.initDrawingManager(fakeContext);
		
		mnger.registerItem(fake);
		ManagerRegistry.getUpdateManager().registerItem(fake);
		
		mnger.update();
		
		assertTrue(fakeContext.drawnItems.contains(fake));
	}
	
	@Test
	public void testDrawItems_faultedContext_shouldNotBeDrawn() {
		DrawingManager mnger = new DrawingManager();
		FakeDrawingObject fake = new FakeDrawingObject();
		FakeDrawingContext fakeContext = new FakeDrawingContext();
		fakeContext.error = "Doo Doo Context";
		
		mnger.initDrawingManager(fakeContext);


		mnger.update();
		
		assertFalse(fake.drawn);
		assertFalse(fakeContext.drawnItems.contains(fake));
	}
	
	@Test
	public void testDrawItems_noContext_shouldNotBeDrawn() {
		DrawingManager mnger = new DrawingManager();
		FakeDrawingObject fake = new FakeDrawingObject();
		
		mnger.update();
		
		assertFalse(fake.drawn);
	}
	
	@Test
	public void testRegisterItems_registeredObjectShouldContainItem_shouldBeTrue() {
		DrawingManager mnger = fakeDrawingManager();
		FakeDrawingObject fake = new FakeDrawingObject();
		
		mnger.registerItem(fake);
		
		assertTrue(mnger.getRegisteredItems().contains(fake));
		assertEquals(1,mnger.getRegisteredItems().size());
	}
	
	@Test
	public void testDoubleRegisterItems_CheckSize_shouldBeOne() {
		DrawingManager mnger = fakeDrawingManager();
		FakeDrawingObject fake = new FakeDrawingObject();
		
		mnger.registerItem(fake);
		mnger.registerItem(fake);
		
		assertTrue(mnger.getRegisteredItems().contains(fake));
		assertEquals(1,mnger.getRegisteredItems().size());
	}
	
	@Test
	public void testReset_addItemAndReset_shouldBeEmpty() {
		DrawingManager mnger = fakeDrawingManager();
		FakeDrawingObject fake = new FakeDrawingObject();
		
		mnger.registerItem(fake);
		
		mnger.reset();
		
		assertFalse(mnger.getRegisteredItems().contains(fake));
		assertEquals(0,mnger.getRegisteredItems().size());
	}
	
	@Test
	public void testReset_togglePauseAndReset_shouldBeOn() {
		DrawingManager mnger = fakeDrawingManager();
		mnger.toggleUpdate();
		
		mnger.reset();
		
		assertTrue(mnger.isRunning());
	}
	
	@Test
	public void testTogglePause_toggleOff_shouldBeOff() {
		DrawingManager mnger = fakeDrawingManager();
		
		mnger.toggleUpdate();
		
		assertFalse(mnger.isRunning());
	}
	
	@Test
	public void testTogglePause_toggleOffOn_shouldBeOn() {
		DrawingManager mnger = fakeDrawingManager();
		
		mnger.toggleUpdate();
		mnger.toggleUpdate();
		
		assertTrue(mnger.isRunning());
	}
	
	@Test
	public void testState_UpdateWhenPaused_shouldNotDoAnything() {
		DrawingManager mnger = fakeDrawingManager();
		FakeDrawingObject fake = new FakeDrawingObject();
		// This adds the item but does not draw
		mnger.scheduleRegistration(fake);
		mnger.update();
		
		mnger.toggleUpdate();
		mnger.update();
		
		assertFalse(fake.drawn);
	}
	
}
