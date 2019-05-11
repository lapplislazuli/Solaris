package logic;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import logic.manager.UpdateManager;
import fakes.FakeUpdatingObject;

class UpdateManagerTests {
	
	@AfterEach
	void resetUpdateManger() {
		UpdateManager.getInstance().reset();
	}
	
	@Test
	void testUpdate_objectCanbeUpdatet_shouldBeUpdatet() {
		UpdateManager mnger = UpdateManager.getInstance();
		FakeUpdatingObject fake = new FakeUpdatingObject();
		
		mnger.registerItem(fake);
		mnger.update();
		
		assertTrue(fake.updatet);
	}
	
	@Test
	void testUpdate_objectCannotUpdatet_shouldNotBeUpdatet() {
		UpdateManager mnger = UpdateManager.getInstance();
		FakeUpdatingObject fake = new FakeUpdatingObject();
		fake.canBeUpdatet = false;

		mnger.registerItem(fake);
		mnger.update();
		
		assertFalse(fake.updatet);
	}
	
	@Test
	void testUpdate_objectCanbeUpdatet_updateTwice_shouldBeUpdatet() {
		UpdateManager mnger = UpdateManager.getInstance();
		FakeUpdatingObject fake = new FakeUpdatingObject();

		mnger.registerItem(fake);
		mnger.update();
		mnger.update();
		
		assertTrue(fake.updatet);
	}
	
	@Test
	void testUpdate_objectCannotUpdatet_updateTwice_shouldNotBeUpdatet() {
		UpdateManager mnger = UpdateManager.getInstance();
		FakeUpdatingObject fake = new FakeUpdatingObject();
		fake.canBeUpdatet = false;

		mnger.registerItem(fake);
		mnger.update();
		mnger.update();
		
		assertFalse(fake.updatet);
	}
	
	@Test
	void testRecursiveUpdate_shouldBeBothUpdatet() {
		UpdateManager mnger = UpdateManager.getInstance();
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
		UpdateManager mnger = UpdateManager.getInstance();
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
		UpdateManager mnger = UpdateManager.getInstance();
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
	void testRegisterItems_registeredObjectShouldContainItem_shouldBeTrue() {
		UpdateManager mnger = UpdateManager.getInstance();
		FakeUpdatingObject fake = new FakeUpdatingObject();
		
		mnger.registerItem(fake);
		
		assertTrue(mnger.getRegisteredItems().contains(fake));
		assertEquals(1,mnger.getRegisteredItems().size());
	}
	
	@Test
	void testRegisterItems_doubleRegisterDoesntWork_shouldBeTrue() {
		UpdateManager mnger = UpdateManager.getInstance();
		FakeUpdatingObject fake = new FakeUpdatingObject();
		
		mnger.registerItem(fake);
		mnger.registerItem(fake);
		
		assertTrue(mnger.getRegisteredItems().contains(fake));
		assertEquals(1,mnger.getRegisteredItems().size());
	}
	
	@Test
	void testReset_addItemAndReset_shouldBeEmpty() {
		UpdateManager mnger = UpdateManager.getInstance();
		FakeUpdatingObject fake = new FakeUpdatingObject();
		
		mnger.registerItem(fake);
		
		mnger.reset();
		
		assertFalse(mnger.getRegisteredItems().contains(fake));
		assertEquals(0,mnger.getRegisteredItems().size());
	}
	
	@Test
	void testReset_togglePauseAndReset_shouldBeTrue() {
		UpdateManager mnger = UpdateManager.getInstance();
		mnger.togglePause();
		
		mnger.reset();
		
		assertTrue(mnger.getState());
	}
	
	@Test
	void testTogglePause_toggleOff_shouldBeOff() {
		UpdateManager mnger = UpdateManager.getInstance();
		
		mnger.togglePause();
		
		assertFalse(mnger.getState());
	}
	
	@Test
	void testTogglePause_toggleOffOn_shouldBeOn() {
		UpdateManager mnger = UpdateManager.getInstance();
		
		mnger.togglePause();
		mnger.togglePause();
		
		assertTrue(mnger.getState());
	}
	
	@Test
	void testState_UpdateWhenPaused_shouldNotUpdate() {
		UpdateManager mnger = UpdateManager.getInstance();
		FakeUpdatingObject fake = new FakeUpdatingObject();
		mnger.registerItem(fake);
		
		mnger.togglePause();
		mnger.update();
		
		assertFalse(fake.updatet);
	}
	
}
