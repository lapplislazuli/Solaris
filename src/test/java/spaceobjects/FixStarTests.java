package spaceobjects;

import static helpers.FakeManagerFactory.freshNewCollisionManager;
import static helpers.FakeSpaceObjectFactory.fakeStar;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import fakes.interfaces.FakeDestructibleObject;
import logic.manager.CollisionManager;
import space.advanced.FixStar;
import space.core.Star;

class FixStarTests {

	@Test
	void testConstructor_isBuildAlive() {
		FixStar testObject = new FixStar("Test",0.5,0.5,10);
		
		assertFalse(testObject.isDead());
	}
	
	@Test
	void testDie_shouldBeDead() {
		FixStar testObject = new FixStar("Test",0.5,0.5,10);
		
		testObject.die();
		
		assertTrue(testObject.isDead());
	}
	
	@Test
	void testDie_dieTwice_shouldBeDead() {
		FixStar testObject = new FixStar("Test",0.5,0.5,10);
		
		testObject.die();
		testObject.die();
		
		assertTrue(testObject.isDead());
	}
	
	@Test
	void testConstructor_timerShouldExist() {
		FixStar testObject = new FixStar("Test",0.5,0.5,10);
		
		assertTrue(testObject.timer !=null);
	}
	
	@Test
	void testCollision_shouldNotDestroyAnyOther() {
		CollisionManager mnger = freshNewCollisionManager();
		FakeDestructibleObject fakeDestructibleObject = new FakeDestructibleObject();
		FixStar testObject = new FixStar("Test",0.5,0.5,10);
		
		mnger.registerItem(fakeDestructibleObject);
		mnger.registerItem(testObject);
		
		mnger.doCollisions();
		
		assertFalse(fakeDestructibleObject.destroyed);
	}
	
	@Test
	void testDistanceTo_shouldBeMax() {
		FixStar testObject = new FixStar("Test",0.5,0.5,10);
		Star fakeAnchor = fakeStar(0,0);
		
		assertEquals(Double.MAX_VALUE, testObject.distanceTo(fakeAnchor),0);
	}

}
