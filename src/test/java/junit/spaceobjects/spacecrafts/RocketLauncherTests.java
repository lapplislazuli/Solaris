package junit.spaceobjects.spacecrafts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import geom.AbsolutePoint;
import interfaces.geom.Point;
import interfaces.logical.MovingUpdatingObject;
import junit.testhelpers.TestShipFactory;
import logic.manager.ManagerRegistry;
import space.core.SpaceObject;
import space.spacecraft.ships.devices.RocketLauncher;
import space.spacecrafts.ships.Spaceshuttle;
import space.spacecrafts.ships.missiles.Missile;
import space.spacecrafts.ships.missiles.Rocket;

import static junit.testhelpers.FakeSpaceObjectFactory.fakeStar;

public class RocketLauncherTests {

	@BeforeEach
	void initManagerRegistry() {
		ManagerRegistry.getInstance();
	}
	@AfterEach
	void resteManagerRegistry() {
		ManagerRegistry.reset();
	}

	@Test
	void testActivate_noTargetSet_doesNotShoot() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle emitter= TestShipFactory.standardArmedShuttle(root);
		
		RocketLauncher testObject = new RocketLauncher(emitter,10);
		
		testObject.activate();
		
		assertEquals(0,emitter.getTrabants().size());
	}

	@Test
	void testActivate_noRocketsLeft_doesNotShoot() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle emitter= TestShipFactory.standardArmedShuttle(root);
		Point target = new AbsolutePoint(1000,1000);
		
		RocketLauncher testObject = new RocketLauncher(emitter,0);

		testObject.setTarget(target);
		testObject.activate();
		
		assertEquals(0,emitter.getTrabants().size());
	}
	@Test
	void testShootRocket_shootTwice_twoShots() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle emitter= TestShipFactory.standardArmedShuttle(root);
		Point target = new AbsolutePoint(1000,1000);
		
		RocketLauncher testObject = new RocketLauncher(emitter,10);
		
		testObject.setTarget(target);
		testObject.activate();
		
		testObject.setTarget(target);
		testObject.activate();
		
		assertEquals(2,emitter.getTrabants().size());
	}
	
	@Test
	void testShootRocket_shootTwice_RunsOutOfMunition_twoShots() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle emitter= TestShipFactory.standardArmedShuttle(root);
		Point target = new AbsolutePoint(1000,1000);
		
		RocketLauncher testObject = new RocketLauncher(emitter,1);
		
		testObject.setTarget(target);
		testObject.activate();
		
		testObject.setTarget(target);
		testObject.activate();
		
		assertEquals(1,emitter.getTrabants().size());
	}
	
	@Test
	void testConstructor_shouldStartWithRightAmountOfMunition() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle emitter= TestShipFactory.standardArmedShuttle(root);
		Point target = new AbsolutePoint(1000,1000);
		
		RocketLauncher testObject = new RocketLauncher(emitter,10);
		
		
		assertEquals(10,testObject.getRemainingRockets());
	}

	@Test
	void testShootRocketAtPoint_shouldBeShot() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle emitter= TestShipFactory.standardArmedShuttle(root);
		Point target = new AbsolutePoint(1000,1000);

		RocketLauncher testObject = new RocketLauncher(emitter,10);
		
		testObject.setTarget(target);
		testObject.activate();
		
	    MovingUpdatingObject missile = emitter.getTrabants().get(0);
	    assertTrue(missile instanceof Missile);
		assertTrue(missile instanceof Rocket);
	}
	
	@Test
	void testShootRocketAtTarget_shouldBeShot() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle emitter= TestShipFactory.standardArmedShuttle(root);
		SpaceObject target = fakeStar(1000,1000);

		RocketLauncher testObject = new RocketLauncher(emitter,10);
		
		testObject.setTarget(target);
		testObject.activate();
		
		
		MovingUpdatingObject missile = emitter.getTrabants().get(0);
	    assertTrue(missile instanceof Missile);
		assertTrue(missile instanceof Rocket);
	}
	
	@Test
	void testShootRocketAtDegree_shouldBeShot() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle emitter= TestShipFactory.standardArmedShuttle(root);

		RocketLauncher testObject = new RocketLauncher(emitter,10);
		
		testObject.setTarget(Math.PI); // Shoot straight line
		testObject.activate();
		
		MovingUpdatingObject missile = emitter.getTrabants().get(0);
	    assertTrue(missile instanceof Missile);
		assertTrue(missile instanceof Rocket);
	}

	
	@Test
	void testGetParent_shouldBeEmitter() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle emitter= TestShipFactory.standardArmedShuttle(root);


		RocketLauncher testObject = new RocketLauncher(emitter,10);

		assertEquals(emitter,testObject.getParent());
	}
	
	@Test
	void testConstructor_nullEmitter_shouldThrowError() {
		assertThrows(IllegalArgumentException.class, () -> new RocketLauncher(null,2));
	}
	@Test
	void testConstructor_negativeCooldown_shouldThrowError() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle emitter= TestShipFactory.standardArmedShuttle(root);
		
		assertThrows(IllegalArgumentException.class, () -> new RocketLauncher(emitter,-42));	
	}
	
}
