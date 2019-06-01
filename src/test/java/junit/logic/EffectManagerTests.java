package junit.logic;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import junit.fakes.FakeEffect;
import logic.manager.EffectManager;
import static junit.testhelpers.FakeManagerFactory.fakeEfxManager;

public class EffectManagerTests implements SharedManagerTests {
	
	@Test
	public void testUpdate_addItemAndUpdate_shouldBeUpdatet() {
		EffectManager mnger= fakeEfxManager();
		FakeEffect fake = new FakeEffect();
		
		mnger.registerItem(fake);
		mnger.update();
		
		assertTrue(fake.updated);	
	}
	
	@Test
	public void testRemove_addAndRemoveAnItem_shouldBeRemoved() {
		EffectManager mnger= fakeEfxManager();
		FakeEffect fake = new FakeEffect();
		
		mnger.registerItem(fake);
		mnger.removeEffect(fake);
		
		assertFalse(mnger.getRegisteredItems().contains(fake));
	}
	
	@Test
	public void testRegisterItems_registeredObjectShouldContainItem_shouldBeTrue() {
		EffectManager mnger= fakeEfxManager();
		FakeEffect fake = new FakeEffect();
		
		mnger.registerItem(fake);
		
		assertTrue(mnger.getRegisteredItems().contains(fake));
		assertEquals(1,mnger.getRegisteredItems().size());
	}
	
	@Test
	public void testDoubleRegisterItems_CheckSize_shouldBeOne() {
		EffectManager mnger= fakeEfxManager();
		FakeEffect fake = new FakeEffect();
		
		mnger.registerItem(fake);
		mnger.registerItem(fake);
		
		assertTrue(mnger.getRegisteredItems().contains(fake));
		assertEquals(1,mnger.getRegisteredItems().size());
	}
	
	@Test
	public void testReset_addItemAndReset_shouldBeEmpty() {
		EffectManager mnger= fakeEfxManager();
		FakeEffect fake = new FakeEffect();
		
		mnger.registerItem(fake);
		
		mnger.reset();
		
		assertFalse(mnger.getRegisteredItems().contains(fake));
		assertEquals(0,mnger.getRegisteredItems().size());
	}
	
	@Test
	public void testReset_togglePauseAndReset_shouldBeOn() {
		EffectManager mnger= fakeEfxManager();
		mnger.toggleUpdate();
		
		mnger.reset();
		
		assertTrue(mnger.isRunning());
	}
	
	@Test
	public void testTogglePause_toggleOff_shouldBeOff() {
		EffectManager mnger= fakeEfxManager();
		
		mnger.toggleUpdate();
		
		assertFalse(mnger.isRunning());
	}
	
	@Test
	public void testTogglePause_toggleOffOn_shouldBeOn() {
		EffectManager mnger= fakeEfxManager();
		
		mnger.toggleUpdate();
		mnger.toggleUpdate();
		
		assertTrue(mnger.isRunning());
	}
	
	@Test
	public void testState_UpdateWhenPaused_shouldNotDoAnything() {
		EffectManager mnger= fakeEfxManager();
		FakeEffect fake = new FakeEffect();
		mnger.registerItem(fake);
		
		mnger.toggleUpdate();
		mnger.update();
		
		assertFalse(fake.updated);
	}
	
}
