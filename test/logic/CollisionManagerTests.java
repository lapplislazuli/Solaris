package logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

import fakes.FakeCollidingObject;
import fakes.FakeDestructibleObject;
import logic.manager.CollisionManager;
import logic.manager.ManagerRegistry;

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
	public void testRegisterItems_registeredObjectShouldContainItem_shouldBeTrue() {
		CollisionManager mnger =new CollisionManager();
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
	
	public static CollisionManager freshNewCollisionManager() {
		ManagerRegistry.getInstance();
		CollisionManager mnger = new CollisionManager();
		mnger.init(null);
		return mnger;
	}
}
