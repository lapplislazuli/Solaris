package logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import fakes.FakeDrawingContext;
import fakes.FakeDrawingObject;
import logic.manager.DrawingManager;

public class DrawingManagerTests implements SharedManagerTests {
	
	@Test
	public void testDrawItems_drawSingleItem_shouldBeDrawn() {
		DrawingManager mnger = fakeDrawingManager();
		FakeDrawingObject fake = new FakeDrawingObject();
		
		mnger.registerItem(fake);
		
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
		mnger.registerItem(fake);
		
		mnger.toggleUpdate();
		mnger.update();
		
		assertFalse(fake.drawn);
	}
	
	public static DrawingManager fakeDrawingManager() {
		DrawingManager mnger = new DrawingManager();
		FakeDrawingContext fakeContext = new FakeDrawingContext();

		mnger.initDrawingManager(fakeContext);
		return mnger;
	}
	
}
