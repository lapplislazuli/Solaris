package spaceobjects;

import static helpers.FakeSpaceObjectFactory.fakeStar;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import geom.AbsolutePoint;
import interfaces.geom.Point;
import interfaces.logical.MovingUpdatingObject;
import logic.manager.ManagerRegistry;
import space.core.SpaceObject;
import space.spacecrafts.ships.ArmedSpaceShuttle;
import space.spacecrafts.ships.Ship;
import space.spacecrafts.ships.missiles.Laserbeam;
import space.spacecrafts.ships.missiles.Missile;
import space.spacecrafts.ships.missiles.Rocket;

class ArmedSpaceShuttleTests {


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
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle testObject= new ArmedSpaceShuttle("Army",root,0,50,0);
		
		assertEquals(0,testObject.laserCoolDown);
		assertTrue(testObject.rocketsLeft>0);
	}
	
	@Test
	void testShootLaserAtPoint_shouldBeShot() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle testObject= new ArmedSpaceShuttle("Army",root,0,50,0);
		Point target = new AbsolutePoint(1000,1000);
		
		testObject.shootLaser(target);
		
	    MovingUpdatingObject missile = testObject.getTrabants().get(0);
	    assertTrue(missile instanceof Missile);
		assertTrue(missile instanceof Laserbeam);
	}
	
	@Test
	void testShootLaserAtTarget_shouldBeShot() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle testObject= new ArmedSpaceShuttle("Army",root,0,50,0);
		SpaceObject target = fakeStar(1000,1000);
		

		testObject.shootLaser(target);
		
		MovingUpdatingObject missile = testObject.getTrabants().get(0);
	    assertTrue(missile instanceof Missile);
		assertTrue(missile instanceof Laserbeam);
	}

	@Test
	void testShootLaser_checkCooldown_shouldBeSet() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle testObject= new ArmedSpaceShuttle("Army",root,0,50,0);
		Point target = new AbsolutePoint(1000,1000);
		
		testObject.shootLaser(target);
		
		assertNotEquals(0,testObject.laserCoolDown);
	}

	@Test
	void testShootRocketAtPoint_shouldBeShot() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle testObject= new ArmedSpaceShuttle("Army",root,0,50,0);
		Point target = new AbsolutePoint(1000,1000);
		
		testObject.shootRocket(target);
		
		MovingUpdatingObject missile = testObject.getTrabants().get(0);
	    assertTrue(missile instanceof Missile);
		assertTrue(missile instanceof Rocket);
	}
	
	@Test
	void testShootRocketAtTarget_shouldBeShot() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle testObject= new ArmedSpaceShuttle("Army",root,0,50,0);
		SpaceObject target = fakeStar(1000,1000);
		
		testObject.shootRocket(target);
		
		MovingUpdatingObject missile = testObject.getTrabants().get(0);
	    assertTrue(missile instanceof Missile);
		assertTrue(missile instanceof Rocket);
	}
	
	@Test
	void testShootRocket_checkRockets_shouldBeOneLess() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle testObject= new ArmedSpaceShuttle("Army",root,0,50,0);
		Point target = new AbsolutePoint(1000,1000);
		int startingRockets = testObject.rocketsLeft;
		
		testObject.shootRocket(target);
		
		assertEquals(startingRockets-1,testObject.rocketsLeft);
	}
	
	@Test
	void testShootLaser_hasCooldown_shouldNotSpawnLaser() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle testObject= new ArmedSpaceShuttle("Army",root,0,50,0);
		Point target = new AbsolutePoint(1000,1000);
		testObject.laserCoolDown=1000;
		
		testObject.shootLaser(target);
		
		assertTrue(testObject.getTrabants().isEmpty());
	}
	
	@Test
	void testShootRocket_NoRocketsLeft_shouldNotShoot() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle testObject= new ArmedSpaceShuttle("Army",root,0,50,0);
		Point target = new AbsolutePoint(1000,1000);
		testObject.rocketsLeft=0;
		
		testObject.shootRocket(target);
		
		assertTrue(testObject.getTrabants().isEmpty());
	}
	
	@Test
	void testUpdate_laserCoolDownAtMinimum_shouldNotChange() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle testObject= new ArmedSpaceShuttle("Army",root,0,50,0);
		testObject.laserCoolDown=0;
		
		testObject.update();
		
		assertEquals(0,testObject.laserCoolDown);
	}
	
	@Test
	void testUpdate_laserCoolDownFresh_shouldBeLowerThanBefore() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle testObject= new ArmedSpaceShuttle("Army",root,0,50,0);
		testObject.laserCoolDown=10;
		
		testObject.update();
		
		assertTrue(testObject.laserCoolDown<10);
	}
	
	@Test
	void testCollision_doesNotCollideOwnLasers() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle testObject= new ArmedSpaceShuttle("Army",root,0,50,0);
		SpaceObject target = fakeStar(1000,1000);
		

		testObject.shootLaser(target);
		
		MovingUpdatingObject missile = testObject.getTrabants().get(0);
	    Laserbeam castedLaser = (Laserbeam) missile;
	    assertFalse(testObject.collides(castedLaser));
	}
	
	@Test
	void testCollision_doesNotCollideOwnRockets() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle testObject= new ArmedSpaceShuttle("Army",root,0,50,0);
		SpaceObject target = fakeStar(1000,1000);
		

		testObject.shootRocket(target);
		
		MovingUpdatingObject missile = testObject.getTrabants().get(0);
	    Rocket castedRocket = (Rocket) missile;
	    assertFalse(testObject.collides(castedRocket));
	}
	
	@Test
	void testAttackPoint_shouldSpawnLaser() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle testObject= new ArmedSpaceShuttle("Army",root,0,50,0);
		Point target = new AbsolutePoint(1000,1000);
		
		testObject.attack(target);

		MovingUpdatingObject missile = testObject.getTrabants().get(0);
	    assertTrue(missile instanceof Missile);
		assertTrue(missile instanceof Laserbeam);
	}
	
	@Test
	void testAttackTarget_shouldSpawnLaser() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle testObject= new ArmedSpaceShuttle("Army",root,0,50,0);
		SpaceObject target = fakeStar(1000,1000);

		testObject.attack(target);

		MovingUpdatingObject missile = testObject.getTrabants().get(0);
	    assertTrue(missile instanceof Missile);
		assertTrue(missile instanceof Laserbeam);	
	}
	
	@Test
	void testRebuildAt_checkInstanceOfArmedSpaceShuttle_shouldBeTrue() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle testObject= new ArmedSpaceShuttle("Army",root,0,50,0);
		
		Ship copy = testObject.rebuildAt("copy",root);
		
		assertTrue(copy instanceof ArmedSpaceShuttle);
	}
}
