package junit.spaceobjects.spacecrafts;


import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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
		//Add 3 normal Drones
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

	@Test
	void testGetNearestPossibleTarget_OnlyDronesInSensor_shouldBeEmptyOptional() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle testObject = makeBattleCarrier();
		
		FakeSensor stubSensor = new FakeSensor();
		testObject.setSensor(stubSensor);
		
		stubSensor.detectedItems.addAll(testObject.getDrones());
		
		var result = testObject.getNearestPossibleTarget(); 

		assertFalse(result.isPresent());
	}
	
	@ParameterizedTest
	@ValueSource(ints = { 0,1,3,5,10})
	void testConstructor_checkDrones_DronesShouldHaveDifferentPositions(int numberOfDrones) {
		SpaceObject root = fakeStar(0,0);
		var b = new Spaceshuttle.Builder("TestCarrier", root).distance_to_parent(100);
		
		for(int i = 0;i<numberOfDrones;i++) {
			b.addDroneMount(DroneFactory::standardLaserDrone);
		}
		var carrier = b.build();
		var drones = carrier.getDrones();
		// We get the drones positions and filter them for distinctiveness, the resulting size should be equal to the number of drones
		var dronePositions = drones.stream().map(d -> d.getCenter()).distinct().collect(Collectors.toSet());
		
		boolean samePositionFound = false;
		for(var p1 : dronePositions)       // Compare Every Drones Position
			for(var p2: dronePositions)    // To every other Drones Position
				if(! p1.equals(p2))        // If they are not the same
					samePositionFound = samePositionFound || (p1.getX() == p2.getX() && p1.getY() == p2.getY());
		assertFalse(samePositionFound);
	}
	
	@ParameterizedTest
	@ValueSource(ints = { 0,1,3,15})
	void testConstructor_checkDrones_DronesShouldHaveDifferentPositionFromCarrier(int numberOfDrones) {
		SpaceObject root = fakeStar(0,0);
		var b = new Spaceshuttle.Builder("TestCarrier", root).distance_to_parent(100);
		for(int i = 0;i<numberOfDrones;i++) {
			b.addDroneMount(DroneFactory::standardLaserDrone);
		}
		var carrier = b.build();
		var drones = carrier.getDrones();
		var dronePositions = drones.stream().map(d -> d.getCenter()).distinct().collect(Collectors.toSet());
		
		boolean samePositionFound = false;
		for(var p1 : dronePositions)
					samePositionFound = samePositionFound || (p1.getX() == carrier.getCenter().getX() && p1.getY() == carrier.getCenter().getY());
		assertFalse(samePositionFound);
	}
	
	@Test
	void testEquality_selfReflexivity_ShouldBeTrue() {
		Spaceshuttle testObject = makeBattleCarrier();
		
		assertEquals(testObject,testObject);
	}
	
	@Test
	void testIsMyDrone_isDroneOfThisCarrier_shouldBeTrue() {
		Spaceshuttle testCarrier = makeBattleCarrier();
		
		Spaceshuttle drone = (Spaceshuttle) testCarrier.getDrones().get(0);
		
		assertTrue(testCarrier.isMyDrone(drone));
	}
	
	@Test
	void testIsMyDrone_testNull_shouldBeFalse() {
		Spaceshuttle testCarrier = makeBattleCarrier();
		
		assertFalse(testCarrier.isMyDrone(null));
	}
	
	@Test
	void testIsMyDrone_isDroneOfOtherCarrier_shouldBeFalse() {
		Spaceshuttle testCarrier = makeBattleCarrier();
		Spaceshuttle otherCarrier = makeBattleCarrier();
		
		Spaceshuttle drone = (Spaceshuttle) otherCarrier.getDrones().get(0);
		
		assertFalse(testCarrier.isMyDrone(drone));
	}
	
	@Test
	void testIsMyDrone_otherIsCarrierButNotDrone_shouldBeFalse() {
		Spaceshuttle testCarrier = makeBattleCarrier();
		Spaceshuttle otherCarrier = makeBattleCarrier();
		
		assertFalse(testCarrier.isMyDrone(otherCarrier));
	}
	
	@Test
	void testIsMyDrone_otherIsSpaceshuttle_butNeitherCarrierNotDrone_shouldBeFalse() {
		SpaceObject root = fakeStar(0,0);
		var b = new Spaceshuttle.Builder("TestShuttle", root).distance_to_parent(100);
		var shuttle = b.build();
		
		Spaceshuttle testCarrier = makeBattleCarrier();
		
		assertFalse(testCarrier.isMyDrone(shuttle));
	}	
	
	@Test
	void testIsMyDrone_IAmNotACarrier_shouldBeFalse() {
		SpaceObject root = fakeStar(0,0);
		var b = new Spaceshuttle.Builder("TestShuttle", root).distance_to_parent(100);
		var shuttle = b.build();
		
		Spaceshuttle testCarrier = makeBattleCarrier();
		
		assertFalse(shuttle.isMyDrone(shuttle));
	}
	
	@Test
	void testIsMyCarrier_IAmNotADrone_shouldBeFalse() {
		SpaceObject root = fakeStar(0,0);
		var b = new Spaceshuttle.Builder("TestShuttle", root).distance_to_parent(100);
		var shuttle = b.build();
		
		Spaceshuttle testCarrier = makeBattleCarrier();
		
		assertFalse(shuttle.isMyCarrier(testCarrier));
	}
	
	@Test
	void testIsMyCarrier_IAmHisDrone_shouldBeTrue() {
		Spaceshuttle testCarrier = makeBattleCarrier();
		
		Spaceshuttle drone = (Spaceshuttle) testCarrier.getDrones().get(0);
		
		assertTrue(drone.isMyCarrier(testCarrier));
	}
	

	@Test
	void testIsMyCarrier_isCarrierButNotMyCarrier_shouldBeFalse() {
		Spaceshuttle testCarrier = makeBattleCarrier();
		Spaceshuttle otherCarrier = makeBattleCarrier();
		
		Spaceshuttle drone = (Spaceshuttle) testCarrier.getDrones().get(0);
		
		assertFalse(drone.isMyCarrier(otherCarrier));
	}
	
	@Test
	void testIsMyCarrier_isNull_shouldBeFalse() {
		Spaceshuttle testCarrier = makeBattleCarrier();
		
		Spaceshuttle drone = (Spaceshuttle) testCarrier.getDrones().get(0);
		
		assertFalse(drone.isMyCarrier(null));
	}
	
	@Test
	void testIsDroneOfSameCarrier_bothDronesFromSameCarrier_shouldBeTrue() {
		Spaceshuttle testCarrier = makeBattleCarrier();
		
		Spaceshuttle drone = (Spaceshuttle) testCarrier.getDrones().get(0);
		Spaceshuttle otherDrone = (Spaceshuttle) testCarrier.getDrones().get(1);
		
		assertTrue(drone.isDroneWithSameCarrier(otherDrone));
	}
	
	@Test
	void testIsDroneOfSameCarrier_dronesOfDifferentCarriers_shouldBeFalse() {
		Spaceshuttle testCarrier = makeBattleCarrier();
		Spaceshuttle otherCarrier = makeBattleCarrier();
		
		Spaceshuttle drone = (Spaceshuttle) testCarrier.getDrones().get(0);
		Spaceshuttle otherDrone = (Spaceshuttle) otherCarrier.getDrones().get(0);
		
		boolean result = drone.isDroneWithSameCarrier(otherDrone);
		
		assertFalse(result);
	}
	
	@Test
	void testIsDroneOfSameCarrier_IamNotACarrier_shouldBeFalse() {
		SpaceObject root = fakeStar(0,0);
		var b = new Spaceshuttle.Builder("TestShuttle", root).distance_to_parent(100);
		var shuttle = b.build();
		
		Spaceshuttle testCarrier = makeBattleCarrier();
		Spaceshuttle drone = (Spaceshuttle) testCarrier.getDrones().get(0);
		
		assertFalse(shuttle.isMyCarrier(drone));
	}
	
	@Test
	void testIsDroneOfSameCarrier_isNull_shouldBeFalse() {
		Spaceshuttle testCarrier = makeBattleCarrier();
		
		Spaceshuttle drone = (Spaceshuttle) testCarrier.getDrones().get(0);
		
		assertFalse(drone.isDroneWithSameCarrier(null));
	}
	
	
	@Test
	public void testIsDrone_checkDrone_shouldBeTrue() {
		Spaceshuttle testCarrier = makeBattleCarrier();
		Spaceshuttle drone = testCarrier.getDrones().get(0);
		
		var items = ManagerRegistry.getUpdateManager().getRegisteredItems();
		
		assertTrue(drone.isDrone());
	}
	
	@Test
	public void testIsDrone_checkAllDrones_shouldAllBeDrones() {
		Spaceshuttle testCarrier = makeBattleCarrier();
		
		testCarrier.getDrones().stream().forEach(d -> assertTrue(d.isDrone()));
	}
	
	@Test
	public void testIsDrone_checkingOnDrone_otherItemsAndOtherCarriersInManagers_shouldBeTrue() {
		Spaceshuttle noiseCarrier = makeBattleCarrier();
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle noiseShuttle = new Spaceshuttle.Builder("TestShuttle", root).distance_to_parent(100).build();
		
		Spaceshuttle testCarrier = makeBattleCarrier();
		Spaceshuttle drone = (Spaceshuttle) testCarrier.getDrones().get(0);
	
		assertTrue(drone.isDrone());
		
	}
	
	@Test
	public void testIsDrone_IsCarrier_shouldBeFalse() {
		Spaceshuttle testCarrier = makeBattleCarrier();
		
		assertFalse(testCarrier.isDrone());
	}
	
	@Test
	public void testIsDrone_NormalSpaceShip_shouldBeFalse() {
		SpaceObject root = fakeStar(0,0);
		var b = new Spaceshuttle.Builder("TestShuttle", root).distance_to_parent(100);
		var shuttle = b.build();
		
		assertFalse(shuttle.isDrone());
	}
	
	public static Spaceshuttle makeBattleCarrier() {
		SpaceObject carrierRoot = fakeStar(0,0);
		int randomSeed = (int) (Math.random()*10000);
		var b = new Spaceshuttle.Builder("TestCarrier"+randomSeed, carrierRoot);
		for(int i = 0;i<4;i++) {
			b.addDroneMount(DroneFactory::standardLaserDrone);
		}

		int randomPos = (int)(Math.random()*100);
		return b.color(Color.ALICEBLUE)
				 .size(4)
				 .distance_to_parent(randomPos)
				 .rotationSpeed(Math.PI)
				 .speed(-Math.PI)
				 .levelOfDetail(3)
				 .addMountedWeapon(WeaponFactory::standardRocketLauncher)
				 .addMountedWeapon(WeaponFactory::standardRocketLauncher)
				 .addMountedWeapon(WeaponFactory::standardLaserCannon)
				 .build();
	}
	
}
