package junit.spaceobjects.spacecrafts;


import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import geom.AbsolutePoint;
import interfaces.geom.Point;
import interfaces.spacecraft.CarrierDrone;
import javafx.scene.paint.Color;
import junit.fakes.FakeSensor;
import junit.fakes.interfaces.FakeCollidingObject;
import logic.manager.ManagerRegistry;
import space.advanced.Asteroid;
import space.core.SpaceObject;
import space.spacecraft.ships.devices.WeaponFactory;
import space.spacecrafts.ships.Carrier;
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
		var b = new Carrier.Builder("TestCarrier", carrierRoot);
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
		var carrier = new Carrier.Builder("TestCarrier", carrierRoot).build();
		
		assertEquals(0,carrier.getTrabants().size());
	}
	
	
	@Test
	void testBuilder_negativeSize_shouldThrowError() {
		SpaceObject carrierRoot = fakeStar(0,0);
		var b = new Carrier.Builder("TestCarrier", carrierRoot);
		
		assertThrows(IllegalArgumentException.class, () -> b.size(-1));
	}
	@Test
	void testBuilder_negativeDistance_shouldThrowError() {
		SpaceObject carrierRoot = fakeStar(0,0);
		var b = new Carrier.Builder("TestCarrier", carrierRoot);
		
		assertThrows(IllegalArgumentException.class, () -> b.distance_to_parent(-1));
	}
	@Test
	void testBuilder_negativeLevelOfDetail_shouldThrowError() {
		SpaceObject carrierRoot = fakeStar(0,0);
		var b = new Carrier.Builder("TestCarrier", carrierRoot);
		
		assertThrows(IllegalArgumentException.class, () -> b.levelOfDetail(-1));
	}
	@Test
	void testBuilder_LevelOfDetailZero_shouldThrowError() {
		SpaceObject carrierRoot = fakeStar(0,0);
		var b = new Carrier.Builder("TestCarrier", carrierRoot);
		
		assertThrows(IllegalArgumentException.class, () -> b.levelOfDetail(0));
	}
	

	@Test
	void testBuilder_emptyName_shouldThrowError() {
		SpaceObject carrierRoot = fakeStar(0,0);
		assertThrows(IllegalArgumentException.class, () ->new Carrier.Builder("", carrierRoot));
	}
	@Test
	void testBuilder_nullName_shouldThrowError() {
		SpaceObject carrierRoot = fakeStar(0,0);
		assertThrows(IllegalArgumentException.class, () ->new Carrier.Builder(null, carrierRoot));
	}	
	@Test
	void testBuilder_nullRoot_shouldThrowError() {
		assertThrows(IllegalArgumentException.class, () ->new Carrier.Builder("TestCarrier", null));
	}
	
	/*
	 * Legacy Tests, might be deprecated
	 */
	
	@Test
	void testConstructor_shouldHaveSetValues() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new Carrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
	
		assertEquals(Math.PI/2,carrier.getSpeed());
		assertEquals("TestCarrier",carrier.getName());
	}
	
	@Test
	void testConstructor_hasFullDrones() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new Carrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
		
		assertTrue(carrier.hasFullDrones());
	}
	
	@Test
	void testConstructor_isBuildWith3Ships() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new Carrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
	
		assertEquals(3,carrier.getCurrentDroneCount());
	}
	
	@Test
	void testConstructor_isNotBuildingNewShips() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new Carrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
	
		assertFalse(carrier.isBuilding());
	}
	
	@Test
	void testConstructor_hasFullShipsIsTrue() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new Carrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
	
		assertTrue(carrier.hasFullDrones());
	}
	
	@Test
	void testLaunchDrones_checkTarget_shouldHaveShipsInTrabants() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new Carrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
	
		SpaceObject droneTarget = fakeStar(1000,1000);
		carrier.launchDrones(droneTarget);
		
		assertEquals(3, droneTarget.getTrabants().size()); // The ships should be children of targetTwo	
	}
	
	@Test
	void testLaunchDrones_inspectDrones_shouldHaveTargetAsParent() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new Carrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
	
		SpaceObject droneTarget = fakeStar(1000,1000);
		carrier.launchDrones(droneTarget);
		
		for(CarrierDrone s : carrier.getDrones())
			assertEquals(droneTarget,s.getParent());
	}
	
	@Test
	void testLaunchDrones_shouldStillbeInCarriersKader() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new Carrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
		
		SpaceObject droneTarget = fakeStar(1000,1000);
		carrier.launchDrones(droneTarget);
		
		assertEquals(3, carrier.getCurrentDroneCount()); // There should still be 4 Ships for the Carrier
	}
	
	@Test
	void testLaunchDrones_inspectCarrierTrabants_shouldBeEmpty() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new Carrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
		
		SpaceObject droneTarget = fakeStar(1000,1000);
		carrier.launchDrones(droneTarget);
		
		assertTrue(carrier.getTrabants().isEmpty());
	}
	
	@Test
	void testRevoke_inspectCarrierTrabants_shouldBeShipsCount() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new Carrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
		
		SpaceObject droneTarget = fakeStar(1000,1000);
		carrier.launchDrones(droneTarget);
		
		carrier.revokeDrones();
		
		assertEquals(3,carrier.getCurrentDroneCount());
	}
	
	@Test
	void testRevoke_inspectDrones_shouldHaveCarrierAsParent() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new Carrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
	
		SpaceObject droneTarget = fakeStar(1000,1000);
		carrier.launchDrones(droneTarget);
		
		carrier.revokeDrones();
		
		for(CarrierDrone s : carrier.getDrones())
			assertEquals(carrier,s.getParent());
	}
	
	@Test
	void testRevoke_inspectTarget_shouldHaveNoTrabants() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new Carrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
		
		SpaceObject droneTarget = fakeStar(1000,1000);
		carrier.launchDrones(droneTarget);
		
		carrier.revokeDrones();
		
		assertTrue(droneTarget.getTrabants().isEmpty());
	}
	
	@Test
	void testRespawn_RemoveDroneAndUpdate_shouldHaveANewShip() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new Carrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
	
		carrier.removeFirstDroneWithoutTrash(); //Remove a ship!
		carrier.update(); //Update
		//Update should have rebuild, as fresh carriers dont have cooldown
		assertTrue(carrier.hasFullDrones());
	}
	
	@Test
	void testRespawn_RemoveDroneAndUpdate_shouldSetBuildingTrue() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new Carrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
	
		carrier.removeFirstDroneWithoutTrash();
		carrier.update();
		assertTrue(carrier.isBuilding());
	}
	
	@Test
	void testFullDrones_DestroyAShipAndTest_shouldBeFalse(){
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new Carrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
	
		carrier.removeFirstDroneWithoutTrash();
		
		assertFalse(carrier.hasFullDrones());
	}
	
	@Test
	void testReduceMaxDrones_reduceAndUpdate_shouldHaveLessShips() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new Carrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
	
		carrier.setMaxDrones(2); //One Less
		carrier.update();
		
		assertEquals(2,carrier.getCurrentDroneCount());
		assertEquals(carrier.getCurrentDroneCount(),carrier.getMaxDrones());
		
	}
	
	@Test
	void testReduceMaxDrones_reduceAndUpdate_shouldHaveFullShips() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new Carrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
		
		carrier.setMaxDrones(2); //One Less
		carrier.update();
		
		assertTrue(carrier.hasFullDrones());
	}
	
	@Test
	void testExtendMaxDrones_checkHasFullShips_shouldBeFalse() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new Carrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
		

		carrier.setMaxDrones(10);
		carrier.update();

		assertFalse(carrier.hasFullDrones());
	}
	
	@Test
	void testExtendMaxDrones_shouldSpawnANewShipAfterUpdate() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new Carrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
		
		carrier.setMaxDrones(4);
		carrier.update();
		
		assertEquals(4,carrier.getCurrentDroneCount());
	}
	
	@Test
	void testExtendMaxDrones_AndUpdate_shouldBeBuilding() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new Carrier("TestCarrier",carrierRoot,5,6,Math.PI/2);

		carrier.setMaxDrones(5); //One More
		carrier.update();
		
		assertTrue(carrier.isBuilding());
	}
	
	@Test
	void testExtendMaxDrones_DontUpdate_shouldHaveOldShipCount() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new Carrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
			
		carrier.setMaxDrones(5);
		assertEquals(3,carrier.getCurrentDroneCount());
	}
	
	@Test
	void testSetMaxDrones_insertNegativeValue_shouldThrowError() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new Carrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
	
		Executable wrongArgument = () -> carrier.setMaxDrones(-1);

	    assertThrows(IllegalArgumentException.class, wrongArgument, "MaxShips cannot be smaller than 0!");		
	}
	
	@Test
	void testCollision_inspectCarrier_doesNotCollideDrones() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new Carrier("TestCarrier",carrierRoot,5,6,Math.PI/2);

		carrier.update();
		
		carrier.getDrones()
			.forEach(s -> assertFalse(carrier.collides(s)));
	}
	
	@Test
	void testCollision_inspectDrones_DoNotCollideCarrier() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new Carrier("TestCarrier",carrierRoot,5,6,Math.PI/2);

		carrier.update();
		
		carrier.getDrones().forEach(s -> assertFalse(s.collides(carrier)));
	}
	
	@Test
	void testCollision_inspectDrones_shouldNotCollideEachOther() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new Carrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
		
		carrier.update();
		
		for(CarrierDrone d : carrier.getDrones())
			for (CarrierDrone u : carrier.getDrones())
				assertFalse(d.collides(u));
	}
	
	/*
	 * Battle Carrier Tests
	 */
	
	@Test
	void testConstructor_shouldBeBuild() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new Carrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
	
		assertEquals(Math.PI/2,carrier.getSpeed(),00000001);
		assertEquals("TestCarrier",carrier.getName());
	}
	
	@Test
	void testAttackTargetSpaceObject_shouldEqualLaunchShips() {
		//For more Input on Launching Ships check Carrier Tests
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new Carrier("TestCarrier",carrierRoot,5,6,Math.PI/2);

		SpaceObject droneTarget = fakeStar(1000,1000);
		carrier.attack(droneTarget);
		assertEquals(3, droneTarget.getTrabants().size());
	}
	
	@Test
	void testAttackTargetPoint_shouldNotLaunchShips() {
		//For more Input on Launching Ships check Carrier Tests
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new Carrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
		
		Point target = new AbsolutePoint(1000,100);
		carrier.attack(target);
		
		assertEquals(3,carrier.getTrabants().size());
	}
	
	@Test
	void rebuildAt_shouldBeInstanceOfBattleCarrier() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new Carrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
		
		Carrier copy = carrier.rebuildAt("copy",carrierRoot);
		
		assertTrue(copy instanceof Carrier);
	}
	

	
	@Test
	void testGetNearestPossibleTarget_noItemsDetected_shouldBeEmptyOptional() {
		SpaceObject root = fakeStar(0,0);
		Carrier testObject = new Carrier("TestCarrier",root,5,6,Math.PI/2);
			
		FakeSensor stubSensor = new FakeSensor();
		testObject.setSensor(stubSensor);
		
		assertFalse(testObject.getNearestPossibleTarget().isPresent());
	}
	
	@Test
	void testGetNearestPossibleTarget_notDestructible_shouldBeEmptyOptional(){
		SpaceObject root = fakeStar(0,0);
		Carrier testObject = new Carrier("TestCarrier",root,5,6,Math.PI/2);
		
		FakeSensor stubSensor = new FakeSensor();
		testObject.setSensor(stubSensor);
		stubSensor.detectedItems.add(root);
		
		assertFalse(testObject.getNearestPossibleTarget().isPresent());
	}
	
	@Test
	void testGetNearestPossibleTarget_SensorNonEmpty_noSpaceObjectInSensor_shouldBeEmptyOptional() {
		SpaceObject root = fakeStar(0,0);
		Carrier testObject = new Carrier("TestCarrier",root,5,6,Math.PI/2);
		
		FakeSensor stubSensor = new FakeSensor();
		testObject.setSensor(stubSensor);
		stubSensor.detectedItems.add(new FakeCollidingObject());
		
		assertFalse(testObject.getNearestPossibleTarget().isPresent());
	}
	
	@Test
	void testGetNearestPossibleTarget_DestructibleItemInSensor_shouldBeNonEmptyOptional() {
		SpaceObject root = fakeStar(0,0);
		Carrier testObject = new Carrier("TestCarrier",root,5,6,Math.PI/2);
		
		FakeSensor stubSensor = new FakeSensor();
		testObject.setSensor(stubSensor);
		
		Asteroid a = new Asteroid("stubDestructible",root,0,0);
		stubSensor.detectedItems.add(a);
		
		assertTrue(testObject.getNearestPossibleTarget().isPresent());
	}
	
	@Test
	void testGetNearestPossibleTarget_DestructibleItemInSensor_shouldReturnTheDestructibleInOptional() {
		SpaceObject root = fakeStar(0,0);
		Carrier testObject = new Carrier("TestCarrier",root,5,6,Math.PI/2);
		
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
		Carrier testObject = new Carrier("TestCarrier",root,5,6,Math.PI/2);
		
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
		Carrier testObject = new Carrier("TestCarrier",root,5,6,Math.PI/2);
		
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
		Carrier testObject = new Carrier("TestCarrier",root,5,6,Math.PI/2);
		
		FakeSensor stubSensor = new FakeSensor();
		testObject.setSensor(stubSensor);
		
		stubSensor.detectedItems.addAll(testObject.getDrones());

		assertFalse(testObject.getNearestPossibleTarget().isPresent());
	}
	
	@Test
	void testGetNearestPossibleTarget_CarriesIsInSensor_shouldBeEmptyOptional() {
		SpaceObject root = fakeStar(0,0);
		Carrier testObject = new Carrier("TestCarrier",root,5,6,Math.PI/2);
		
		FakeSensor stubSensor = new FakeSensor();
		testObject.setSensor(stubSensor);
		
		stubSensor.detectedItems.add(testObject);

		assertFalse(testObject.getNearestPossibleTarget().isPresent());
	}
	
}
