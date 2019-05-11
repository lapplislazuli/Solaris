package logic;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import logic.manager.UpdateManager;
import fakes.FakeUpdatingObject;

class UpdateManagerTests implements SharedManagerTests{
	
	@Test
	void testUpdate_objectCanbeUpdatet_shouldBeUpdatet() {
		UpdateManager mnger = new UpdateManager();
		FakeUpdatingObject fake = new FakeUpdatingObject();
		
		mnger.registerItem(fake);
		mnger.update();
		
		assertTrue(fake.updatet);
	}
	
	@Test
	void testUpdate_objectCannotUpdatet_shouldNotBeUpdatet() {
		UpdateManager mnger = new UpdateManager();
		FakeUpdatingObject fake = new FakeUpdatingObject();
		fake.canBeUpdatet = false;

		mnger.registerItem(fake);
		mnger.update();
		
		assertFalse(fake.updatet);
	}
	
	@Test
	void testUpdate_objectCanbeUpdatet_updateTwice_shouldBeUpdatet() {
		UpdateManager mnger = new UpdateManager();
		FakeUpdatingObject fake = new FakeUpdatingObject();

		mnger.registerItem(fake);
		mnger.update();
		mnger.update();
		
		assertTrue(fake.updatet);
	}
	
	@Test
	void testUpdate_objectCannotUpdatet_updateTwice_shouldNotBeUpdatet() {
		UpdateManager mnger = new UpdateManager();
		FakeUpdatingObject fake = new FakeUpdatingObject();
		fake.canBeUpdatet = false;

		mnger.registerItem(fake);
		mnger.update();
		mnger.update();
		
		assertFalse(fake.updatet);
	}
	
	@Test
	void testRecursiveUpdate_shouldBeBothUpdatet() {
		UpdateManager mnger =new UpdateManager();
		FakeUpdatingObject fakeParent = new FakeUpdatingObject();
		FakeUpdatingObject fakeKid = new FakeUpdatingObject();
		fakeParent.kid=fakeKid;

		mnger.registerItem(fakeParent);
		mnger.update();
		
		assertTrue(fakeParent.updatet);
		assertTrue(fakeKid.updatet);
	}
	
	@Test
	void testRecursiveUpdate_KidCannotBeUpdatet_ParentShouldBeUpdatet() {
		UpdateManager mnger = new UpdateManager();
		FakeUpdatingObject fakeParent = new FakeUpdatingObject();
		FakeUpdatingObject fakeKid = new FakeUpdatingObject();
		
		fakeKid.canBeUpdatet=false;
		fakeParent.kid=fakeKid;

		mnger.registerItem(fakeParent);
		mnger.update();
		
		assertTrue(fakeParent.updatet);
		assertFalse(fakeKid.updatet);
	}
	
	@Test
	void testRecursiveUpdate_ParentCannotBeUpdatet_NothingShouldBeUpdatet() {
		UpdateManager mnger = new UpdateManager();
		FakeUpdatingObject fakeParent = new FakeUpdatingObject();
		FakeUpdatingObject fakeKid = new FakeUpdatingObject();
		
		fakeParent.canBeUpdatet=false;
		fakeParent.kid=fakeKid;

		mnger.registerItem(fakeParent);
		mnger.update();
		
		assertFalse(fakeParent.updatet);
		assertFalse(fakeKid.updatet);
	}
	
	@Test
	public void testRegisterItems_registeredObjectShouldContainItem_shouldBeTrue() {
		UpdateManager mnger =new UpdateManager();
		FakeUpdatingObject fake = new FakeUpdatingObject();
		
		mnger.registerItem(fake);
		
		assertTrue(mnger.getRegisteredItems().contains(fake));
		assertEquals(1,mnger.getRegisteredItems().size());
	}
	
	@Test
	public void testDoubleRegisterItems_CheckSize_shouldBeOne() {
		UpdateManager mnger = new UpdateManager();
		FakeUpdatingObject fake = new FakeUpdatingObject();
		
		mnger.registerItem(fake);
		mnger.registerItem(fake);
		
		assertTrue(mnger.getRegisteredItems().contains(fake));
		assertEquals(1,mnger.getRegisteredItems().size());
	}
	
	@Test
	public void testReset_addItemAndReset_shouldBeEmpty() {
		UpdateManager mnger = new UpdateManager();
		FakeUpdatingObject fake = new FakeUpdatingObject();
		
		mnger.registerItem(fake);
		
		mnger.reset();
		
		assertFalse(mnger.getRegisteredItems().contains(fake));
		assertEquals(0,mnger.getRegisteredItems().size());
	}
	
	@Test
	public void testReset_togglePauseAndReset_shouldBeOn() {
		UpdateManager mnger = new UpdateManager();
		mnger.togglePause();
		
		mnger.reset();
		
		assertTrue(mnger.getState());
	}
	
	@Test
	public void testTogglePause_toggleOff_shouldBeOff() {
		UpdateManager mnger =new UpdateManager();
		
		mnger.togglePause();
		
		assertFalse(mnger.getState());
	}
	
	@Test
	public void testTogglePause_toggleOffOn_shouldBeOn() {
		UpdateManager mnger = new UpdateManager();
		
		mnger.togglePause();
		mnger.togglePause();
		
		assertTrue(mnger.getState());
	}
	
	@Test
	public void testState_UpdateWhenPaused_shouldNotDoAnything() {
		UpdateManager mnger = new UpdateManager();
		FakeUpdatingObject fake = new FakeUpdatingObject();
		mnger.registerItem(fake);
		
		mnger.togglePause();
		mnger.update();
		
		assertFalse(fake.updatet);
	}
	
}
