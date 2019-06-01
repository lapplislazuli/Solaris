package junit.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static helpers.FakeManagerFactory.*;

import org.junit.Test;

import junit.fakes.interfaces.FakeCollidingObject;
import junit.fakes.interfaces.FakeDestructibleObject;
import junit.fakes.interfaces.FakeUpdatableCollidingObject;
import logic.manager.CollisionManager;
import logic.manager.ManagerRegistry;
import logic.manager.UpdateManager;

public class CollisionManagerTests implements SharedManagerTests {

	@Test
	public void testCollision_OtherIsDestructibleAndCollided_shouldbeDestroyed() {
		CollisionManager mnger = freshNewCollisionManager();
		FakeCollidingObject fakeCollider = new FakeCollidingObject();
		FakeDestructibleObject fakeDestructible = new FakeDestructibleObject();
		mnger.registerItem(fakeCollider);
		mnger.registerItem(fakeDestructible);
		
		mnger.doCollisions();
		
		assertTrue(fakeDestructible.destroyed);
	}
	
	@Test
	public void testCollision_OtherIsDestructibleButNotCollided_shouldNotDestroyed() {
		CollisionManager mnger = freshNewCollisionManager();
		FakeCollidingObject fakeCollider = new FakeCollidingObject();
		FakeDestructibleObject fakeDestructible = new FakeDestructibleObject();
		mnger.registerItem(fakeCollider);
		mnger.registerItem(fakeDestructible);
		
		fakeDestructible.collides=false;
		mnger.doCollisions();
		
		assertFalse(fakeDestructible.destroyed);
	}
	
	@Test
	public void testCollision_OtherIsDestructibleAndCollides_ButIsNotCollided_shouldNotBeDestroyed() {
		CollisionManager mnger = freshNewCollisionManager();
		FakeCollidingObject fakeCollider = new FakeCollidingObject();
		FakeDestructibleObject fakeDestructible = new FakeDestructibleObject();
		mnger.registerItem(fakeCollider);
		mnger.registerItem(fakeDestructible);
		
		fakeCollider.collides=false;
		
		mnger.doCollisions();
		
		assertFalse(fakeDestructible.destroyed);
	}
	
	@Test
	public void testCollision_BothDestructibleNoCollision_ShouldBothStay() {
		CollisionManager mnger = freshNewCollisionManager();
		FakeCollidingObject fakeCollider = new FakeCollidingObject();
		FakeDestructibleObject fakeDestructible = new FakeDestructibleObject();
		mnger.registerItem(fakeCollider);
		mnger.registerItem(fakeDestructible);
		
		fakeCollider.collides=false;
		fakeDestructible.collides=false;
		mnger.doCollisions();
		
		assertFalse(fakeDestructible.destroyed);
	}
	
	
	@Test
	public void TestRegisterItem_registerDestructibleItem_shouldBeThere() {
		CollisionManager mnger = freshNewCollisionManager();
		
		FakeDestructibleObject fakeDestructible = new FakeDestructibleObject();
		mnger.registerItem(fakeDestructible);
		
		assertTrue(mnger.getRegisteredDestructibles().contains(fakeDestructible));
	}
	
	@Test
	public void TestRegisterItem_registerNonDestructibleItem_DestructiblesShouldBeEmpty() {
		CollisionManager mnger = freshNewCollisionManager();
		FakeCollidingObject fakeCollider = new FakeCollidingObject();
		mnger.registerItem(fakeCollider);
		
		assertTrue(mnger.getRegisteredDestructibles().isEmpty());
	}
	
	@Test
	public void testEmpty_clearsBothLists() {
		CollisionManager mnger = freshNewCollisionManager();
		FakeCollidingObject fakeCollider = new FakeCollidingObject();
		FakeDestructibleObject fakeDestructible = new FakeDestructibleObject();
		mnger.registerItem(fakeCollider);
		mnger.registerItem(fakeDestructible);
		
		mnger.empty();
		
		assertTrue(mnger.getRegisteredDestructibles().isEmpty());
		assertTrue(mnger.getRegisteredItems().isEmpty());
	}
	
	@Test
	public void testRegisterItems_registeredObjectShouldContainItem_shouldBeTrue() {
		CollisionManager mnger =freshNewCollisionManager();
		FakeCollidingObject fake = new FakeCollidingObject();
		
		mnger.registerItem(fake);
		
		assertTrue(mnger.getRegisteredItems().contains(fake));
		assertEquals(1,mnger.getRegisteredItems().size());
	}
	
	@Test
	public void testDoubleRegisterItems_CheckSize_shouldBeOne() {
		CollisionManager mnger = freshNewCollisionManager();
		FakeCollidingObject fake = new FakeCollidingObject();
		
		mnger.registerItem(fake);
		mnger.registerItem(fake);
		
		assertTrue(mnger.getRegisteredItems().contains(fake));
		assertEquals(1,mnger.getRegisteredItems().size());
	}
	@Test
	public void testDoubleRegisterDestructibleItemsItems_CheckSize_shouldBeOne() {
		CollisionManager mnger = freshNewCollisionManager();
		FakeDestructibleObject fake = new FakeDestructibleObject();
		
		mnger.registerItem(fake);
		mnger.registerItem(fake);
		
		assertTrue(mnger.getRegisteredDestructibles().contains(fake));
		assertEquals(1,mnger.getRegisteredDestructibles().size());
	}
	
	@Test
	public void testReset_addItemAndReset_shouldBeEmpty() {
		CollisionManager mnger = freshNewCollisionManager();
		FakeCollidingObject fake = new FakeCollidingObject();
		
		mnger.registerItem(fake);
		
		mnger.reset();
		
		assertFalse(mnger.getRegisteredItems().contains(fake));
		assertEquals(0,mnger.getRegisteredItems().size());
	}
	
	@Test
	public void testReset_togglePauseAndReset_shouldBeOn() {
		CollisionManager mnger = freshNewCollisionManager();
		mnger.toggleUpdate();
		
		mnger.reset();
		
		assertTrue(mnger.isRunning());
	}
	
	@Test
	public void testTogglePause_toggleOff_shouldBeOff() {
		CollisionManager mnger =new CollisionManager();
		
		mnger.toggleUpdate();
		
		assertFalse(mnger.isRunning());
	}
	
	@Test
	public void testTogglePause_toggleOffOn_shouldBeOn() {
		CollisionManager mnger = freshNewCollisionManager();
		
		mnger.toggleUpdate();
		mnger.toggleUpdate();
		
		assertTrue(mnger.isRunning());
	}
	
	@Test
	public void testState_UpdateWhenPaused_shouldNotDoAnything() {
		CollisionManager mnger = freshNewCollisionManager();
		
		FakeCollidingObject fakeCollider = new FakeCollidingObject();
		FakeDestructibleObject fakeDestructible = new FakeDestructibleObject();
		
		mnger.registerItem(fakeCollider);
		mnger.registerItem(fakeDestructible);
		
		mnger.toggleUpdate();
		mnger.update();
		
		assertEquals(2,mnger.getRegisteredItems().size());
		assertFalse(fakeDestructible.destroyed);
	}
	
	@Test
	public void testReset_getItemsFromUpdateManager_shouldHaveUpdateManagersObjects() {
		UpdateManager source = new UpdateManager();
		CollisionManager mnger = freshNewCollisionManager();
		ManagerRegistry.setUpdateManager(source);
		
		FakeUpdatableCollidingObject parent = new FakeUpdatableCollidingObject();
		FakeUpdatableCollidingObject kid = new FakeUpdatableCollidingObject();
		parent.kid=kid;
		source.registerItem(parent);
		
		mnger.refresh();
		
		assertTrue(mnger.getRegisteredItems().contains(parent));
		assertTrue(mnger.getRegisteredItems().contains(kid));
	}
	
	@Test
	public void testUpdate_getItemsFromUpdateManager_shouldHaveUpdateManagersObjects() {
		UpdateManager source = new UpdateManager();
		CollisionManager mnger = freshNewCollisionManager();
		ManagerRegistry.setUpdateManager(source);

		FakeUpdatableCollidingObject parent = new FakeUpdatableCollidingObject();
		FakeUpdatableCollidingObject kid = new FakeUpdatableCollidingObject();
		parent.kid=kid;
		source.registerItem(parent);
		
		mnger.update();
		
		assertTrue(mnger.getRegisteredItems().contains(parent));
		assertTrue(mnger.getRegisteredItems().contains(kid));
	}
	
	@Test
	public void testRefresh_checkHitboxes_ShouldBeUpdatet() {
		UpdateManager source = new UpdateManager();
		CollisionManager mnger = freshNewCollisionManager();
		ManagerRegistry.setUpdateManager(source);

		FakeUpdatableCollidingObject parent = new FakeUpdatableCollidingObject();
		FakeUpdatableCollidingObject kid = new FakeUpdatableCollidingObject();
		parent.kid=kid;
		source.registerItem(parent);
		
		mnger.update();
		
		assertTrue(parent.hitBoxUpdatet);
		assertTrue(kid.hitBoxUpdatet);
	}
	
	@Test
	public void testUpdate_getItemsFromUpdateManager_shouldLooseOldObjects() {
		CollisionManager mnger = freshNewCollisionManager();
		FakeCollidingObject fakeCollider = new FakeCollidingObject();
		mnger.registerItem(fakeCollider);
		
		mnger.update();
		
		assertFalse(mnger.getRegisteredItems().contains(fakeCollider));
	}
	
	
	
}
