package shuttle;

import static helpers.FakeSpaceObjectFactory.fakeStar;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fakes.interfaces.FakeCollidingObject;
import logic.manager.ManagerRegistry;
import space.core.SpaceObject;
import space.spacecrafts.ships.ArmedSpaceShuttle;
import space.spacecrafts.ships.missiles.Laserbeam;

public class LaserTests {
	
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
		ArmedSpaceShuttle testEmitter = new ArmedSpaceShuttle("test",shipRoot,5,5,5);
		Laserbeam testLaser = new Laserbeam("TestLaser",testEmitter,1.0,5);
		
		assertEquals("TestLaser",testLaser.getName());
	}
	
	@Test
	void testRotate_shouldNotRotate() {
		SpaceObject shipRoot = fakeStar(0,0);
		ArmedSpaceShuttle testEmitter = new ArmedSpaceShuttle("test",shipRoot,5,5,5);
		Laserbeam testLaser = new Laserbeam("TestLaser",testEmitter,1.0,5);
		
		double oldRotation=testLaser.getRotation();
		
		testLaser.rotate();
		
		assertTrue(testLaser.getRotation()==oldRotation);
	}
	
	@Test
	void testMove_shouldBeMoved() {
		SpaceObject shipRoot = fakeStar(0,0);
		ArmedSpaceShuttle testEmitter = new ArmedSpaceShuttle("test",shipRoot,5,5,5);
		Laserbeam testLaser = new Laserbeam("TestLaser",testEmitter,1.0,5);
		
		double oldDistance = testLaser.getDistance();
		
		testLaser.update();
		
		assertTrue(testLaser.getDistance()>oldDistance);
	}
	
	@Test
	void testMove_checkDistanceToParent_shouldBeHigher() {
		SpaceObject shipRoot = fakeStar(0,0);
		ArmedSpaceShuttle testEmitter = new ArmedSpaceShuttle("test",shipRoot,5,5,5);
		Laserbeam testLaser = new Laserbeam("TestLaser",testEmitter,1.0,5);
		double oldDistanceToParent = testLaser.distanceTo(testEmitter);
		
		testLaser.update();
		
		assertTrue(testLaser.distanceTo(testEmitter)>oldDistanceToParent);
	}

	@Test
	public void testConstructors_IsInParentsChildren() {
		SpaceObject shipRoot = fakeStar(0,0);
		ArmedSpaceShuttle testEmitter = new ArmedSpaceShuttle("test",shipRoot,5,5,5);
		Laserbeam testLaser = new Laserbeam("TestLaser",testEmitter,1.0,5);
		
		assertTrue(testEmitter.getTrabants().contains(testLaser));
	}

	@Test
	public void testConstructors_isNotOrphaned() {
		SpaceObject shipRoot = fakeStar(0,0);
		ArmedSpaceShuttle testEmitter = new ArmedSpaceShuttle("test",shipRoot,5,5,5);
		Laserbeam testLaser = new Laserbeam("TestLaser",testEmitter,1.0,5);
		
		assertFalse(testLaser.isOrphan());
	}

	@Test
	public void testRemove_isNotInParentsChildren() {
		SpaceObject shipRoot = fakeStar(0,0);
		ArmedSpaceShuttle testEmitter = new ArmedSpaceShuttle("test",shipRoot,5,5,5);
		Laserbeam testLaser = new Laserbeam("TestLaser",testEmitter,1.0,5);
		
		testLaser.remove();
		
		assertFalse(testEmitter.getTrabants().contains(testLaser));
	}

	@Test
	public void testRemove_isOrphaned() {
		SpaceObject shipRoot = fakeStar(0,0);
		ArmedSpaceShuttle testEmitter = new ArmedSpaceShuttle("test",shipRoot,5,5,5);
		Laserbeam testLaser = new Laserbeam("TestLaser",testEmitter,1.0,5);
		
		testLaser.remove();
		
		assertTrue(testLaser.isOrphan());
	}
	
	@Test
	public void testCollision_checkEmitter_shouldNotCollideEmitter() {
		SpaceObject shipRoot = fakeStar(0,0);
		ArmedSpaceShuttle testEmitter = new ArmedSpaceShuttle("test",shipRoot,5,5,5);
		Laserbeam testLaser = new Laserbeam("TestLaser",testEmitter,1.0,5);
		
		assertFalse(testLaser.collides(testEmitter));
	}
	
	@Test
	public void testCollision_fakeCollider_shouldCollideNormally() {
		SpaceObject shipRoot = fakeStar(0,0);
		ArmedSpaceShuttle testEmitter = new ArmedSpaceShuttle("test",shipRoot,5,5,5);
		Laserbeam testLaser = new Laserbeam("TestLaser",testEmitter,1.0,5);
		
		FakeCollidingObject stubCollider = new FakeCollidingObject();
		stubCollider.collides=true;
		

		assertTrue(stubCollider.collides(testLaser));
		assertFalse(testLaser.collides(stubCollider));
	}
	
	
	@Test
	public void testMove_MoveVeryFar_shouldBeRemoved() {
		SpaceObject shipRoot = fakeStar(500,500);
		ArmedSpaceShuttle testEmitter = new ArmedSpaceShuttle("test",shipRoot,5,5,5);
		Laserbeam testLaser = new Laserbeam("TestLaser",testEmitter,1.0,5);
		
		for(int i=0;i<500;i++) {
			testLaser.update();
		}
		
		assertTrue(testLaser.isOrphan());
	}
}
