package junit.spaceobjects.spacecrafts;


import static junit.testhelpers.FakeSpaceObjectFactory.fakeStar;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import junit.fakes.interfaces.FakeCollidingObject;
import junit.testhelpers.TestShipFactory;
import logic.manager.ManagerRegistry;
import space.core.SpaceObject;
import space.spacecrafts.ships.Spaceshuttle;
import space.spacecrafts.ships.missiles.Rocket;

class RocketTests {
	
	@BeforeEach
	void initManagerRegistry() {
		ManagerRegistry.getInstance();
	}
	@AfterEach
	void resteManagerRegistry() {
		ManagerRegistry.reset();
	}
	
	@Test
	void testConstructor_shouldBeBuild() {
		SpaceObject shipRoot = fakeStar(0,0);
		Spaceshuttle testEmitter = TestShipFactory.standardEmitter(shipRoot);
		Rocket testRocket = new Rocket("TestRocket",testEmitter,5);
		
		assertEquals("TestRocket",testRocket.getName());
	}
	
	@Test
	void testRotate_shouldNotRotate() {
		SpaceObject shipRoot = fakeStar(0,0);
		Spaceshuttle testEmitter = TestShipFactory.standardEmitter(shipRoot);
		Rocket testRocket = new Rocket("TestRocket",testEmitter,5);
		
		double oldRotation=testRocket.getRotation();
		
		testRocket.rotate();
		
		assertTrue(testRocket.getRotation()==oldRotation);
	}
	
	@Test
	void testMove_shouldBeMoved() {
		SpaceObject shipRoot = fakeStar(0,0);
		Spaceshuttle testEmitter = TestShipFactory.standardEmitter(shipRoot);
		Rocket testRocket = new Rocket("TestRocket",testEmitter,5);
		double oldDistance = testRocket.getDistance();
		
		testEmitter.update();
		
		assertTrue(testRocket.getDistance()>oldDistance);
	}
	
	@Test
	void testMove_checkDistanceToParent_shouldBeHigher() {
		SpaceObject shipRoot = fakeStar(0,0);
		Spaceshuttle testEmitter = TestShipFactory.standardEmitter(shipRoot);
		Rocket testRocket = new Rocket("TestRocket",testEmitter,5);
		double oldDistanceToParent = testRocket.distanceTo(testEmitter);
		
		testEmitter.update();
		
		assertTrue(testRocket.distanceTo(testEmitter)>oldDistanceToParent);
	}

	@Test
	public void testConstructors_IsInParentsChildren() {
		SpaceObject shipRoot = fakeStar(0,0);
		Spaceshuttle testEmitter = TestShipFactory.standardEmitter(shipRoot);
		Rocket testRocket = new Rocket("TestRocket",testEmitter,5);
		
		assertTrue(testEmitter.getTrabants().contains(testRocket));
	}

	@Test
	public void testConstructors_isNotOrphaned() {
		SpaceObject shipRoot = fakeStar(0,0);
		Spaceshuttle testEmitter = TestShipFactory.standardEmitter(shipRoot);
		Rocket testRocket = new Rocket("TestRocket",testEmitter,5);
		
		assertFalse(testRocket.isOrphan());
	}

	@Test
	public void testRemove_isNotInParentsChildren() {
		SpaceObject shipRoot = fakeStar(0,0);
		Spaceshuttle testEmitter = TestShipFactory.standardEmitter(shipRoot);
		Rocket testRocket = new Rocket("TestRocket",testEmitter,5);
		
		testRocket.remove();
		
		assertFalse(testEmitter.getTrabants().contains(testRocket));
	}

	@Test
	public void testRemove_isOrphaned() {
		SpaceObject shipRoot = fakeStar(0,0);
		Spaceshuttle testEmitter = TestShipFactory.standardEmitter(shipRoot);
		Rocket testRocket = new Rocket("TestRocket",testEmitter,5);
	
		testRocket.remove();
		
		assertTrue(testRocket.isOrphan());
	}
	
	@Test
	public void testCollision_checkEmitter_shouldNotCollideEmitter() {
		SpaceObject shipRoot = fakeStar(0,0);
		Spaceshuttle testEmitter = TestShipFactory.standardEmitter(shipRoot);
		Rocket testRocket = new Rocket("TestRocket",testEmitter,5);
	
		assertFalse(testRocket.collides(testEmitter));
	}
	
	@Test
	public void testCollision_fakeCollider_shouldCollideNormally() {
		SpaceObject shipRoot = fakeStar(0,0);
		Spaceshuttle testEmitter = TestShipFactory.standardEmitter(shipRoot);
		Rocket testRocket = new Rocket("TestRocket",testEmitter,5);
	
		FakeCollidingObject stubCollider = new FakeCollidingObject();
		stubCollider.collides=true;
		

		assertTrue(stubCollider.collides(testRocket));
		assertFalse(testRocket.collides(stubCollider));
	}
	
	@Test
	public void testDestruct_shouldBeRemoved() {
		SpaceObject shipRoot = fakeStar(0,0);
		Spaceshuttle testEmitter = TestShipFactory.standardEmitter(shipRoot);
		Rocket testRocket = new Rocket("TestRocket",testEmitter,5);
	
		testRocket.destruct();
		
		assertTrue(testRocket.isOrphan());
	}
	
	@Test
	public void testDestruct_shouldSpawnEffect() {
		SpaceObject shipRoot = fakeStar(0,0);
		Spaceshuttle testEmitter = TestShipFactory.standardEmitter(shipRoot);
		Rocket testRocket = new Rocket("TestRocket",testEmitter,5);
	
		testRocket.destruct();

		ManagerRegistry.getEffectManager().update();
		
		assertEquals(1,ManagerRegistry.getEffectManager().getRegisteredItems().size());
	}
	
	@Test
	public void testDestruct_doubleDestruct_doesRemove() {
		SpaceObject shipRoot = fakeStar(0,0);
		Spaceshuttle testEmitter = TestShipFactory.standardEmitter(shipRoot);
		Rocket testRocket = new Rocket("TestRocket",testEmitter,5);
	
		testRocket.destruct();
		testRocket.destruct();
		
		assertTrue(testRocket.isOrphan());
	}
	
	@Test
	public void testDestruct_doubleDestruct_spawnsOneEffectButNotTwo() {
		SpaceObject shipRoot = fakeStar(0,0);
		Spaceshuttle testEmitter = TestShipFactory.standardEmitter(shipRoot);
		Rocket testRocket = new Rocket("TestRocket",testEmitter,5);
	
		testRocket.destruct();
		testRocket.destruct();
		
		ManagerRegistry.getEffectManager().update();
		
		assertEquals(1,ManagerRegistry.getEffectManager().getRegisteredItems().size());	
	}
	
	@Test
	public void testMove_MoveVeryFar_shouldBeRemoved() {
		SpaceObject shipRoot = fakeStar(500,500);
		Spaceshuttle testEmitter = TestShipFactory.standardEmitter(shipRoot);
		Rocket testRocket = new Rocket("TestRocket",testEmitter,5);
	
		for(int i=0;i<500;i++) {
			testEmitter.update();
		}
		
		assertTrue(testRocket.isOrphan());
	}
	
	

	
}
