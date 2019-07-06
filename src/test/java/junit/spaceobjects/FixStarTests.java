package junit.spaceobjects;


import static junit.testhelpers.FakeManagerFactory.freshNewCollisionManager;
import static junit.testhelpers.FakeSpaceObjectFactory.fakeStar;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import junit.fakes.interfaces.FakeDestructibleObject;
import logic.manager.CollisionManager;
import space.advanced.FixStar;
import space.core.Star;

class FixStarTests {

	@Tag("fast")
	@Test
	void testConstructor_isBuildAlive() {
		FixStar testObject = new FixStar("Test",0.5,0.5,10);
		
		assertFalse(testObject.isDead());
	}
	
	@Tag("fast")
	@Tag("core")
	@RepeatedTest(3)
	@Test
	void testDie_shouldBeDead() {
		FixStar testObject = new FixStar("Test",0.5,0.5,10);
		
		testObject.die();
		
		assertTrue(testObject.isDead());
	}
	
	@Tag("fast")
	@Test
	void testDie_dieTwice_shouldBeDead() {
		FixStar testObject = new FixStar("Test",0.5,0.5,10);
		
		testObject.die();
		testObject.die();
		
		assertTrue(testObject.isDead());
	}
	
	@Tag("fast")
	@Tag("core")
	@Test
	void testConstructor_timerShouldExist() {
		FixStar testObject = new FixStar("Test",0.5,0.5,10);
		
		assertTrue(testObject.timer !=null);
	}	

	@Tag("fast")
	@Tag("core")
	@RepeatedTest(5)
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

	@Tag("fast")
	@Tag("core")
	@Test
	void testDistanceTo_shouldBeMax() {
		FixStar testObject = new FixStar("Test",0.5,0.5,10);
		Star fakeAnchor = fakeStar(0,0);
		
		assertEquals(Double.MAX_VALUE, testObject.distanceTo(fakeAnchor),0);
	}

}
