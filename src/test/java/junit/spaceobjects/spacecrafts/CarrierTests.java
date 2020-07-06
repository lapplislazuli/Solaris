package junit.spaceobjects.spacecrafts;


import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import geom.AbsolutePoint;
import interfaces.geom.Point;
import javafx.scene.paint.Color;
import junit.fakes.FakeSensor;
import junit.fakes.interfaces.FakeCollidingObject;
import logic.manager.ManagerRegistry;
import space.advanced.Asteroid;
import space.core.SpaceObject;
import space.spacecraft.ships.devices.WeaponFactory;
import space.spacecrafts.ships.Spaceshuttle;
import space.spacecrafts.ships.DroneFactory;

import static junit.testhelpers.FakeSpaceObjectFactory.*;

class CarrierTests {
	
	@BeforeEach
	void initManagerRegistry() {
		ManagerRegistry.getInstance();
	}
	@AfterEach
	void resteManagerRegistry() {
		ManagerRegistry.reset();
	}
	
	@Test
	void testBuilder_allValuesFine_shouldBuild() {
		SpaceObject carrierRoot = fakeStar(0,0);
		var b = new Spaceshuttle.Builder("TestCarrier", carrierRoot);
		for(int i = 0;i<4;i++) {
			b.addDroneMount(DroneFactory::standardLaserDrone);
		}
		
		var carrier = 
				b.color(Color.ALICEBLUE)
				 .size(4)
				 .distance_to_parent(40)
				 .rotationSpeed(Math.PI)
				 .speed(-Math.PI)
				 .levelOfDetail(3)
				 .addMountedWeapon(WeaponFactory::standardRocketLauncher)
				 .addMountedWeapon(WeaponFactory::standardRocketLauncher)
				 .addMountedWeapon(WeaponFactory::standardLaserCannon)
				 .build();
		
		assertEquals(4,carrier.getTrabants().size());
	}
	

	@Test
	void testBuilder_minimalBuilder_EmptyDrones_shouldBuild() {
		SpaceObject carrierRoot = fakeStar(0,0);
		var carrier = new Spaceshuttle.Builder("TestCarrier", carrierRoot).build();
		
		assertEquals(0,carrier.getTrabants().size());
	}
	
	
	@Test
	void testBuilder_negativeSize_shouldThrowError() {
		SpaceObject carrierRoot = fakeStar(0,0);
		var b = new Spaceshuttle.Builder("TestCarrier", carrierRoot);
		
		assertThrows(IllegalArgumentException.class, () -> b.size(-1));
	}
	@Test
	void testBuilder_negativeDistance_shouldThrowError() {
		SpaceObject carrierRoot = fakeStar(0,0);
		var b = new Spaceshuttle.Builder("TestCarrier", carrierRoot);
		
		assertThrows(IllegalArgumentException.class, () -> b.distance_to_parent(-1));
	}
	@Test
	void testBuilder_negativeLevelOfDetail_shouldThrowError() {
		SpaceObject carrierRoot = fakeStar(0,0);
		var b = new Spaceshuttle.Builder("TestCarrier", carrierRoot);
		
		assertThrows(IllegalArgumentException.class, () -> b.levelOfDetail(-1));
	}
	@Test
	void testBuilder_LevelOfDetailZero_shouldThrowError() {
		SpaceObject carrierRoot = fakeStar(0,0);
		var b = new Spaceshuttle.Builder("TestCarrier", carrierRoot);
		
		assertThrows(IllegalArgumentException.class, () -> b.levelOfDetail(0));
	}
	

	@Test
	void testBuilder_emptyName_shouldThrowError() {
		SpaceObject carrierRoot = fakeStar(0,0);
		assertThrows(IllegalArgumentException.class, () ->new Spaceshuttle.Builder("", carrierRoot));
	}
	@Test
	void testBuilder_nullName_shouldThrowError() {
		SpaceObject carrierRoot = fakeStar(0,0);
		assertThrows(IllegalArgumentException.class, () ->new Spaceshuttle.Builder(null, carrierRoot));
	}	
	@Test
	void testBuilder_nullRoot_shouldThrowError() {
		assertThrows(IllegalArgumentException.class, () ->new Spaceshuttle.Builder("TestCarrier", null));
	}
	
	@Test
	void testRevoke_inspectTarget_shouldHaveNoTrabants() {
		//TODO
	}
	
	
	@Test
	void testCollision_inspectCarrier_doesNotCollideDrones() {
		//TODO
	}
	
	@Test
	void testCollision_inspectDrones_DoNotCollideCarrier() {
		//TODO
	}
	
	/*
	 * Battle Spaceshuttle Tests
	 */
	
	
	@Test
	void testAttackTargetSpaceObject_shouldEqualLaunchShips() {
		//For more Input on Launching Ships check Spaceshuttle Tests
		SpaceObject carrierRoot = fakeStar(0,0);
		Spaceshuttle carrier = makeBattleCarrier();

		SpaceObject droneTarget = fakeStar(1000,1000);
		carrier.attack(droneTarget);
		assertEquals(4, droneTarget.getTrabants().size());
	}
	
	@Test
	void testAttackTargetPoint_shouldNotLaunchShips() {
		//For more Input on Launching Ships check Spaceshuttle Tests
		SpaceObject carrierRoot = fakeStar(0,0);
		Spaceshuttle carrier = makeBattleCarrier();
		
		Point target = new AbsolutePoint(1000,100);
		carrier.attack(target);
		
		assertEquals(4,carrier.getTrabants().size());
	}
	
	@Test
	void rebuildAt_shouldBeInstanceOfBattleCarrier() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Spaceshuttle carrier = makeBattleCarrier();
		
		Spaceshuttle copy = carrier.rebuildAt("copy",carrierRoot);
		
		assertTrue(copy instanceof Spaceshuttle);
	}
	

	
	@Test
	void testGetNearestPossibleTarget_noItemsDetected_shouldBeEmptyOptional() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle testObject = makeBattleCarrier();
			
		FakeSensor stubSensor = new FakeSensor();
		testObject.setSensor(stubSensor);
		
		assertFalse(testObject.getNearestPossibleTarget().isPresent());
	}
	
	@Test
	void testGetNearestPossibleTarget_notDestructible_shouldBeEmptyOptional(){
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle testObject = makeBattleCarrier();
		
		FakeSensor stubSensor = new FakeSensor();
		testObject.setSensor(stubSensor);
		stubSensor.detectedItems.add(root);
		
		assertFalse(testObject.getNearestPossibleTarget().isPresent());
	}
	
	@Test
	void testGetNearestPossibleTarget_SensorNonEmpty_noSpaceObjectInSensor_shouldBeEmptyOptional() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle testObject = makeBattleCarrier();
		
		FakeSensor stubSensor = new FakeSensor();
		testObject.setSensor(stubSensor);
		stubSensor.detectedItems.add(new FakeCollidingObject());
		
		assertFalse(testObject.getNearestPossibleTarget().isPresent());
	}
	
	@Test
	void testGetNearestPossibleTarget_DestructibleItemInSensor_shouldBeNonEmptyOptional() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle testObject = makeBattleCarrier();
		
		FakeSensor stubSensor = new FakeSensor();
		testObject.setSensor(stubSensor);
		
		Asteroid a = new Asteroid("stubDestructible",root,0,0);
		stubSensor.detectedItems.add(a);
		
		assertTrue(testObject.getNearestPossibleTarget().isPresent());
	}
	
	@Test
	void testGetNearestPossibleTarget_DestructibleItemInSensor_shouldReturnTheDestructibleInOptional() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle testObject = makeBattleCarrier();
		
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
		Spaceshuttle testObject = makeBattleCarrier();
		
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
		Spaceshuttle testObject = makeBattleCarrier();
		
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
	
/*
	@Test
	void testGetNearestPossibleTarget_OnlyDronesInSensor_shouldBeEmptyOptional() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle testObject = makeBattleCarrier();
		
		FakeSensor stubSensor = new FakeSensor();
		testObject.setSensor(stubSensor);
		
		stubSensor.detectedItems.addAll(testObject.getDrones());

		assertFalse(testObject.getNearestPossibleTarget().isPresent());
	}
	*/
	@Test
	void testGetNearestPossibleTarget_CarriesIsInSensor_shouldBeEmptyOptional() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle testObject = makeBattleCarrier();
		
		FakeSensor stubSensor = new FakeSensor();
		testObject.setSensor(stubSensor);
		
		stubSensor.detectedItems.add(testObject);

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
