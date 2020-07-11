package junit.spaceobjects.spacecrafts;


import static junit.testhelpers.FakeSpaceObjectFactory.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.scene.paint.Color;
import junit.fakes.FakeSensor;
import junit.fakes.interfaces.FakeCollidingObject;
import logic.manager.ManagerRegistry;

import space.advanced.Asteroid;
import space.core.SpaceObject;
import space.spacecraft.ships.devices.WeaponFactory;
import space.spacecrafts.ships.Spaceshuttle;
import space.spacecrafts.ships.DroneFactory;
import space.spacecrafts.ships.LaserDrone;

class LaserDroneTests {
	
	@BeforeEach
	void initManagerRegistry() {
		ManagerRegistry.getInstance();
	}
	@AfterEach
	void resetManagerRegistry() {
		ManagerRegistry.reset();
	}
	
	@Test
	public void testConstructor_shouldNotFail() {
		SpaceObject root = fakeStar(0,0);
		new LaserDrone("TestObject", root,0,10, 0);
		
		return;
	}

	@Test
	public void testShootLaser_shouldSpawnLaser() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle testObject= new LaserDrone("TestObject", root,0,10, 0);
		
		testObject.shootLaser(root);
		
		assertFalse(testObject.getTrabants().isEmpty());
		assertEquals(1,testObject.getTrabants().size());
	}
	
	
	@Test
	void testGetNearestPossibleTarget_noItemsDetected_shouldBeEmptyOptional() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle testObjectSource = makeBattleCarrier();
			
		LaserDrone testObject = (LaserDrone) testObjectSource.getDrones().get(0);
		
		FakeSensor stubSensor = new FakeSensor();
		testObject.setSensor(stubSensor);
		
		assertFalse(testObject.getNearestPossibleTarget().isPresent());
	}
	
	@Test
	void testGetNearestPossibleTarget_notDestructible_shouldBeEmptyOptional(){
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle testObjectSource = makeBattleCarrier();
		
		LaserDrone testObject = (LaserDrone) testObjectSource.getDrones().get(0);
		
		FakeSensor stubSensor = new FakeSensor();
		testObject.setSensor(stubSensor);
		stubSensor.detectedItems.add(root);
		
		assertFalse(testObject.getNearestPossibleTarget().isPresent());
	}
	
	@Test
	void testGetNearestPossibleTarget_SensorNonEmpty_noSpaceObjectInSensor_shouldBeEmptyOptional() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle testObjectSource = makeBattleCarrier();
		
		LaserDrone testObject = (LaserDrone) testObjectSource.getDrones().get(0);
		
		FakeSensor stubSensor = new FakeSensor();
		testObject.setSensor(stubSensor);
		stubSensor.detectedItems.add(new FakeCollidingObject());
		
		assertFalse(testObject.getNearestPossibleTarget().isPresent());
	}
	
	@Test
	void testGetNearestPossibleTarget_DestructibleItemInSensor_shouldBeNonEmptyOptional() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle testObjectSource = makeBattleCarrier();
		
		LaserDrone testObject = (LaserDrone) testObjectSource.getDrones().get(0);
		
		FakeSensor stubSensor = new FakeSensor();
		testObject.setSensor(stubSensor);
		
		Asteroid a = new Asteroid("stubDestructible",root,0,0);
		stubSensor.detectedItems.add(a);
		
		assertTrue(testObject.getNearestPossibleTarget().isPresent());
	}
	
	@Test
	void testGetNearestPossibleTarget_DestructibleItemInSensor_shouldReturnTheDestructibleInOptional() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle testObjectSource = makeBattleCarrier();
		
		LaserDrone testObject = (LaserDrone) testObjectSource.getDrones().get(0);
		
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
		Spaceshuttle testObjectSource = makeBattleCarrier();
		
		LaserDrone testObject = (LaserDrone) testObjectSource.getDrones().get(0);
		
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
		Spaceshuttle testObjectSource = makeBattleCarrier();
		
		LaserDrone testObject = (LaserDrone) testObjectSource.getDrones().get(0);
		
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
	

	@Test
	void testGetNearestPossibleTarget_OnlyOtherDronesInSensor_shouldBeEmptyOptional() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle testObjectSource = makeBattleCarrier();
		
		LaserDrone testObject = (LaserDrone) testObjectSource.getDrones().get(0);
		
		FakeSensor stubSensor = new FakeSensor();
		testObject.setSensor(stubSensor);
		
		stubSensor.detectedItems.addAll(testObjectSource.getDrones());
		//stubSensor.detectedItems.add(testObject);
		stubSensor.detectedItems.add(testObjectSource);

		var result = testObject.getNearestPossibleTarget();
		
		assertFalse(result.isPresent());
	}
	
	@Test
	void testGetNearestPossibleTarget_CarrierIsInSensor_shouldBeEmptyOptional() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle testObjectSource = makeBattleCarrier();
		
		LaserDrone testObject = (LaserDrone) testObjectSource.getDrones().get(0);
		
		FakeSensor stubSensor = new FakeSensor();
		testObject.setSensor(stubSensor);
		
		stubSensor.detectedItems.add(testObjectSource);

		assertFalse(testObject.getNearestPossibleTarget().isPresent());
	}
	
	public static Spaceshuttle makeBattleCarrier() {
		SpaceObject carrierRoot = fakeStar(0,0);
		var b = new Spaceshuttle.Builder("TestCarrier", carrierRoot);
		for(int i = 0;i<4;i++) {
			b.addDroneMount(DroneFactory::standardLaserDrone);
		}
		
		return b.color(Color.ALICEBLUE)
				 .size(4)
				 .distance_to_parent(40)
				 .rotationSpeed(Math.PI)
				 .speed(-Math.PI)
				 .levelOfDetail(3)
				 .addMountedWeapon(WeaponFactory::standardRocketLauncher)
				 .addMountedWeapon(WeaponFactory::standardRocketLauncher)
				 .addMountedWeapon(WeaponFactory::standardLaserCannon)
				 .build();
	}
}
	
