package spaceobjects.spacecrafts;

import static helpers.FakeSpaceObjectFactory.fakeStar;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fakes.FakeSensor;
import fakes.interfaces.FakeCollidingObject;
import geom.AbsolutePoint;
import interfaces.geom.Point;
import interfaces.logical.MovingUpdatingObject;
import logic.manager.ManagerRegistry;
import space.advanced.Asteroid;
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
	void testConstructor_shouldNotBePlayer() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle testObject= new ArmedSpaceShuttle("Army",root,0,50,0);
		
		assertFalse(testObject.isPlayer());
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
	
	@Test
	void testDestruct_checkPlayerManagerDeathCount_shouldBeZero() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle testObject= new ArmedSpaceShuttle("Army",root,0,50,0);
		
		testObject.destruct();
		//The armedship is not the player, therefore the PlayerManager should not Care
		assertEquals(0,ManagerRegistry.getPlayerManager().getPlayerDeaths());
	}
	
	@Test
	void testGetNearestPossibleTarget_noItemsDetected_shouldBeEmptyOptional() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle testObject= new ArmedSpaceShuttle("Army",root,0,50,0);
		
		FakeSensor stubSensor = new FakeSensor();
		testObject.setSensor(stubSensor);
		
		assertFalse(testObject.getNearestPossibleTarget().isPresent());
	}
	
	@Test
	void testGetNearestPossibleTarget_notDestructible_shouldBeEmptyOptional(){
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle testObject= new ArmedSpaceShuttle("Army",root,0,50,0);
		
		FakeSensor stubSensor = new FakeSensor();
		testObject.setSensor(stubSensor);
		stubSensor.detectedItems.add(root);
		
		assertFalse(testObject.getNearestPossibleTarget().isPresent());
	}
	
	@Test
	void testGetNearestPossibleTarget_SensorNonEmpty_noSpaceObjectInSensor_shouldBeEmptyOptional() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle testObject= new ArmedSpaceShuttle("Army",root,0,50,0);
		
		FakeSensor stubSensor = new FakeSensor();
		testObject.setSensor(stubSensor);
		stubSensor.detectedItems.add(new FakeCollidingObject());
		
		assertFalse(testObject.getNearestPossibleTarget().isPresent());
	}
	
	@Test
	void testGetNearestPossibleTarget_DestructibleItemInSensor_shouldBeNonEmptyOptional() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle testObject= new ArmedSpaceShuttle("Army",root,0,50,0);
		
		FakeSensor stubSensor = new FakeSensor();
		testObject.setSensor(stubSensor);
		
		Asteroid a = new Asteroid("stubDestructible",root,0,0);
		stubSensor.detectedItems.add(a);
		
		assertTrue(testObject.getNearestPossibleTarget().isPresent());
	}
	
	@Test
	void testGetNearestPossibleTarget_DestructibleItemInSensor_shouldReturnTheDestructibleInOptional() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle testObject= new ArmedSpaceShuttle("Army",root,0,50,0);
		
		FakeSensor stubSensor = new FakeSensor();
		testObject.setSensor(stubSensor);
		
		Asteroid a = new Asteroid("stubDestructible",root,0,0);
		stubSensor.detectedItems.add(a);
		
		Optional<SpaceObject> expected = Optional.of(a);
		Optional<SpaceObject> result = testObject.getNearestPossibleTarget();
		
		assertEquals(expected,result);
	}
	@Test
	void testGetNearestPossibleTarget_MultipleQualifiedItemsInSensor_shouldBeNonEmptyOptional() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle testObject= new ArmedSpaceShuttle("Army",root,0,50,0);
		
		FakeSensor stubSensor = new FakeSensor();
		testObject.setSensor(stubSensor);
		
		Asteroid a = new Asteroid("stubDestructible",root,0,0);
		stubSensor.detectedItems.add(a);
		Asteroid b = new Asteroid("stubDestructible",root,0,0);
		stubSensor.detectedItems.add(b);
		
		assertTrue(testObject.getNearestPossibleTarget().isPresent());
	}
	
	@Test
	void testGetNearestPossibleTarget_MultipleQualifiedItemsInSensor_shouldReturnFirst() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle testObject= new ArmedSpaceShuttle("Army",root,0,50,0);
		
		FakeSensor stubSensor = new FakeSensor();
		testObject.setSensor(stubSensor);
		
		Asteroid a = new Asteroid("stubDestructible",root,0,0);
		stubSensor.detectedItems.add(a);
		Asteroid b = new Asteroid("stubDestructible",root,0,0);
		stubSensor.detectedItems.add(b);
		Asteroid c = new Asteroid("stubDestructible",root,0,0);
		stubSensor.detectedItems.add(c);
		
		Optional<SpaceObject> expected = Optional.of(a);
		Optional<SpaceObject> result = testObject.getNearestPossibleTarget();
		
		assertEquals(expected,result);
	}

}
