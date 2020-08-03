package junit.logic;



import static junit.testhelpers.FakeSpaceObjectFactory.fakePlanet;
import static junit.testhelpers.FakeSpaceObjectFactory.fakeStar;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import interfaces.logical.CollidingObject;
import interfaces.logical.MovingUpdatingObject;
import junit.fakes.interfaces.FakeUpdatableCollidingObject;
import junit.fakes.interfaces.FakeUpdatingObject;
import logic.manager.ManagerRegistry;
import logic.manager.UpdateManager;
import space.core.SpaceObject;

class UpdateManagerTests implements SharedManagerTests{
	
	@AfterEach
	void resetManagers() {
		ManagerRegistry.reset();
	}
	
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
		mnger.toggleUpdate();
		
		mnger.reset();
		
		assertTrue(mnger.isRunning());
	}
	
	@Test
	public void testTogglePause_toggleOff_shouldBeOff() {
		UpdateManager mnger =new UpdateManager();
		
		mnger.toggleUpdate();
		
		assertFalse(mnger.isRunning());
	}
	
	@Test
	public void testTogglePause_toggleOffOn_shouldBeOn() {
		UpdateManager mnger = new UpdateManager();
		
		mnger.toggleUpdate();
		mnger.toggleUpdate();
		
		assertTrue(mnger.isRunning());
	}
	
	@Test
	public void testState_UpdateWhenPaused_shouldNotDoAnything() {
		UpdateManager mnger = new UpdateManager();
		FakeUpdatingObject fake = new FakeUpdatingObject();
		mnger.registerItem(fake);
		
		mnger.toggleUpdate();
		mnger.update();
		
		assertFalse(fake.updatet);
	}
	
	@Test
	public void testExportColliders_shouldContainCollider() {
		UpdateManager source = new UpdateManager();
	
		FakeUpdatableCollidingObject collider = new FakeUpdatableCollidingObject();
		source.registerItem(collider);
		
		Collection<CollidingObject> results = source.getAllActiveColliders();
		
		assertTrue(results.contains(collider));
	}
	
	@Test
	public void testExportColliders_shouldContainRecursiveColliders() {
		UpdateManager source = new UpdateManager();
	
		FakeUpdatableCollidingObject parent = new FakeUpdatableCollidingObject();
		FakeUpdatableCollidingObject kid = new FakeUpdatableCollidingObject();
		parent.kid=kid;
		source.registerItem(parent);
		
		Collection<CollidingObject> results = source.getAllActiveColliders();
		
		assertTrue(results.contains(parent));
		assertTrue(results.contains(kid));
	}
	
	@Test
	public void testExportColliders_shouldNotContainNoCollider() {
		UpdateManager source = new UpdateManager();
		
		FakeUpdatingObject notReturned = new FakeUpdatingObject();
		source.registerItem(notReturned);
		
		Collection<CollidingObject> results = source.getAllActiveColliders();
		
		assertTrue(results.isEmpty());
	}
	
	@Test
	public void testAddSpaceObject_ObjectIsInUpdateManager() {
		UpdateManager source = new UpdateManager();
		
		SpaceObject stub = fakeStar(0,0);
		
		source.addSpaceObject(stub);
		
		assertTrue(source.getRegisteredItems().contains(stub));
	}
	
	@Test
	public void testAddSpaceObject_TrabantsAreInCollisionManager() {
		UpdateManager source = new UpdateManager();
		
		SpaceObject stub = fakeStar(0,0);
		MovingUpdatingObject stubChild = fakePlanet(stub,10);
		
		source.addSpaceObject(stub);
		
		assertTrue(ManagerRegistry.getCollisionManager().getRegisteredItems().contains(stubChild));
	}
	
}
