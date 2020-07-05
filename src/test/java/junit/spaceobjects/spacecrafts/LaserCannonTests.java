package junit.spaceobjects.spacecrafts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import geom.AbsolutePoint;
import interfaces.geom.Point;
import interfaces.logical.MovingUpdatingObject;
import logic.manager.ManagerRegistry;
import space.core.SpaceObject;
import space.spacecraft.ships.devices.LaserCannon;
import space.spacecrafts.ships.Spaceshuttle;
import space.spacecrafts.ships.missiles.Laserbeam;
import space.spacecrafts.ships.missiles.Missile;

import static junit.testhelpers.FakeSpaceObjectFactory.fakeStar;

public class LaserCannonTests {

	@BeforeEach
	void initManagerRegistry() {
		ManagerRegistry.getInstance();
	}
	@AfterEach
	void resetManagerRegistry() {
		ManagerRegistry.reset();
	}

	@Test
	void testActivate_noTargetSet_doesNotSpawnLaser() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle emitter= new Spaceshuttle("Army",root,0,50,0);
		
		LaserCannon testObject = new LaserCannon(emitter);
		
		testObject.activate();
		
		assertEquals(0,emitter.getTrabants().size());
	}
	
	@Test
	void testShootLaser_hasCooldown_shouldNotSpawnLaser() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle emitter= new Spaceshuttle("Army",root,0,50,0);
		Point target = new AbsolutePoint(1000,1000);
		
		LaserCannon testObject = new LaserCannon(emitter);
		
		testObject.setTarget(target);
		testObject.activate();
		
		testObject.setTarget(target);
		testObject.activate();
		
		assertEquals(1,emitter.getTrabants().size());
	}
	

	@Test
	void testShootLaserAtPoint_shouldBeShot() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle emitter= new Spaceshuttle("Army",root,0,50,0);
		Point target = new AbsolutePoint(1000,1000);

		LaserCannon testObject = new LaserCannon(emitter);
		
		testObject.setTarget(target);
		testObject.activate();
		
	    MovingUpdatingObject missile = emitter.getTrabants().get(0);
	    assertTrue(missile instanceof Missile);
		assertTrue(missile instanceof Laserbeam);
	}
	
	@Test
	void testShootLaserAtTarget_shouldBeShot() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle emitter= new Spaceshuttle("Army",root,0,50,0);
		SpaceObject target = fakeStar(1000,1000);

		LaserCannon testObject = new LaserCannon(emitter);
		
		testObject.setTarget(target);
		testObject.activate();
		
		
		MovingUpdatingObject missile = emitter.getTrabants().get(0);
	    assertTrue(missile instanceof Missile);
		assertTrue(missile instanceof Laserbeam);
	}
	
	@Test
	void testShootLaserAtDegree_shouldBeShot() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle emitter= new Spaceshuttle("Army",root,0,50,0);

		LaserCannon testObject = new LaserCannon(emitter);
		
		testObject.setTarget(Math.PI); // Shoot straight line
		testObject.activate();
		
		MovingUpdatingObject missile = emitter.getTrabants().get(0);
	    assertTrue(missile instanceof Missile);
		assertTrue(missile instanceof Laserbeam);
	}

	@Test
	void testShootLaser_checkCooldown_shouldBeSet() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle emitter= new Spaceshuttle("Army",root,0,50,0);
		Point target = new AbsolutePoint(1000,1000);

		LaserCannon testObject = new LaserCannon(emitter);
		
		testObject.setTarget(target);
		testObject.activate();
		
		assertNotEquals(0,testObject.currentCooldown());
	}
	
	@Test
	void testUpdate_laserCoolDownAtMinimum_shouldNotChange() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle emitter= new Spaceshuttle("Army",root,0,50,0);

		LaserCannon testObject = new LaserCannon(emitter);
		
		testObject.update();
		
		assertEquals(0,testObject.currentCooldown());
	}

	
	@Test
	void testShootLaser_laserCooldownShouldBeDefaultMax() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle emitter= new Spaceshuttle("Army",root,0,50,0);
		Point target = new AbsolutePoint(1000,1000);

		LaserCannon testObject = new LaserCannon(emitter);
		
		testObject.setTarget(target);
		testObject.activate();
		
		assertEquals(Laserbeam.COMMON_MAX_COOLDOWN,testObject.currentCooldown());
	}
	
	@Test
	void testUpdate_laserCoolDownFresh_shouldBeLowerThanBefore() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle emitter= new Spaceshuttle("Army",root,0,50,0);
		Point target = new AbsolutePoint(1000,1000);

		LaserCannon testObject = new LaserCannon(emitter);
		
		testObject.setTarget(target);
		testObject.activate();
		
		testObject.update();
		
		assertTrue(testObject.currentCooldown()<Laserbeam.COMMON_MAX_COOLDOWN);
	}
	
	@Test
	void testConstructor_nonDefaultMaxCooldown_shootLaser_shouldBeNonDefaultMaxCooldown() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle emitter= new Spaceshuttle("Army",root,0,50,0);
		Point target = new AbsolutePoint(1000,1000);
		double manual_cooldown = 500;

		LaserCannon testObject = new LaserCannon(emitter,manual_cooldown);
		
		testObject.setTarget(target);
		testObject.activate();
		
		assertEquals(manual_cooldown,testObject.currentCooldown());
	}
	
	@Test
	void testGetParent_shouldBeEmitter() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle emitter= new Spaceshuttle("Army",root,0,50,0);


		LaserCannon testObject = new LaserCannon(emitter);

		assertEquals(emitter,testObject.getParent());
	}
	
	@Test
	void testConstructor_nullEmitter_shouldThrowError() {
		assertThrows(IllegalArgumentException.class, () -> new LaserCannon(null));
	}@Test
	void testConstructor_nullEmitter_shouldThrowError2() {
		assertThrows(IllegalArgumentException.class, () -> new LaserCannon(null,2));
	}
	@Test
	void testConstructor_zeroCooldown_shouldThrowError() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle emitter= new Spaceshuttle("Army",root,0,50,0);
		
		assertThrows(IllegalArgumentException.class, () -> new LaserCannon(emitter,0));
	}
	@Test
	void testConstructor_negativeCooldown_shouldThrowError() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle emitter= new Spaceshuttle("Army",root,0,50,0);
		
		assertThrows(IllegalArgumentException.class, () -> new LaserCannon(emitter,-42));	
	}
}
