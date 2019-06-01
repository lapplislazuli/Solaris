package junit.spaceobjects.spacecrafts;


import static junit.testhelpers.FakeSpaceObjectFactory.fakeStar;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import geom.AbsolutePoint;
import interfaces.geom.Point;
import junit.fakes.FakeSensor;
import junit.fakes.interfaces.FakeCollidingObject;
import space.advanced.Asteroid;
import space.core.SpaceObject;
import space.spacecrafts.ships.BattleCarrier;
import space.spacecrafts.ships.Carrier;

class BattleCarrierTest {

	@Test
	void testConstructor_shouldBeBuild() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new BattleCarrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
	
		assertEquals(Math.PI/2,carrier.getSpeed(),00000001);
		assertEquals("TestCarrier",carrier.getName());
	}
	
	@Test
	void testAttackTargetSpaceObject_shouldEqualLaunchShips() {
		//For more Input on Launching Ships check Carrier Tests
		SpaceObject carrierRoot = fakeStar(0,0);
		BattleCarrier carrier = new BattleCarrier("TestCarrier",carrierRoot,5,6,Math.PI/2);

		SpaceObject droneTarget = fakeStar(1000,1000);
		carrier.attack(droneTarget);
		assertEquals(3, droneTarget.getTrabants().size());
	}
	
	@Test
	void testAttackTargetPoint_shouldNotLaunchShips() {
		//For more Input on Launching Ships check Carrier Tests
		SpaceObject carrierRoot = fakeStar(0,0);
		BattleCarrier carrier = new BattleCarrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
		
		Point target = new AbsolutePoint(1000,100);
		carrier.attack(target);
		
		assertEquals(3,carrier.getTrabants().size());
	}
	
	@Test
	void rebuildAt_shouldBeInstanceOfBattleCarrier() {
		SpaceObject carrierRoot = fakeStar(0,0);
		BattleCarrier carrier = new BattleCarrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
		
		Carrier copy = carrier.rebuildAt("copy",carrierRoot);
		
		assertTrue(copy instanceof BattleCarrier);
	}
	

	
	@Test
	void testGetNearestPossibleTarget_noItemsDetected_shouldBeEmptyOptional() {
		SpaceObject root = fakeStar(0,0);
		BattleCarrier testObject = new BattleCarrier("TestCarrier",root,5,6,Math.PI/2);
			
		FakeSensor stubSensor = new FakeSensor();
		testObject.setSensor(stubSensor);
		
		assertFalse(testObject.getNearestPossibleTarget().isPresent());
	}
	
	@Test
	void testGetNearestPossibleTarget_notDestructible_shouldBeEmptyOptional(){
		SpaceObject root = fakeStar(0,0);
		BattleCarrier testObject = new BattleCarrier("TestCarrier",root,5,6,Math.PI/2);
		
		FakeSensor stubSensor = new FakeSensor();
		testObject.setSensor(stubSensor);
		stubSensor.detectedItems.add(root);
		
		assertFalse(testObject.getNearestPossibleTarget().isPresent());
	}
	
	@Test
	void testGetNearestPossibleTarget_SensorNonEmpty_noSpaceObjectInSensor_shouldBeEmptyOptional() {
		SpaceObject root = fakeStar(0,0);
		BattleCarrier testObject = new BattleCarrier("TestCarrier",root,5,6,Math.PI/2);
		
		FakeSensor stubSensor = new FakeSensor();
		testObject.setSensor(stubSensor);
		stubSensor.detectedItems.add(new FakeCollidingObject());
		
		assertFalse(testObject.getNearestPossibleTarget().isPresent());
	}
	
	@Test
	void testGetNearestPossibleTarget_DestructibleItemInSensor_shouldBeNonEmptyOptional() {
		SpaceObject root = fakeStar(0,0);
		BattleCarrier testObject = new BattleCarrier("TestCarrier",root,5,6,Math.PI/2);
		
		FakeSensor stubSensor = new FakeSensor();
		testObject.setSensor(stubSensor);
		
		Asteroid a = new Asteroid("stubDestructible",root,0,0);
		stubSensor.detectedItems.add(a);
		
		assertTrue(testObject.getNearestPossibleTarget().isPresent());
	}
	
	@Test
	void testGetNearestPossibleTarget_DestructibleItemInSensor_shouldReturnTheDestructibleInOptional() {
		SpaceObject root = fakeStar(0,0);
		BattleCarrier testObject = new BattleCarrier("TestCarrier",root,5,6,Math.PI/2);
		
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
		BattleCarrier testObject = new BattleCarrier("TestCarrier",root,5,6,Math.PI/2);
		
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
		BattleCarrier testObject = new BattleCarrier("TestCarrier",root,5,6,Math.PI/2);
		
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
		BattleCarrier testObject = new BattleCarrier("TestCarrier",root,5,6,Math.PI/2);
		
		FakeSensor stubSensor = new FakeSensor();
		testObject.setSensor(stubSensor);
		
		stubSensor.detectedItems.addAll(testObject.getDrones());

		assertFalse(testObject.getNearestPossibleTarget().isPresent());
	}
	
	@Test
	void testGetNearestPossibleTarget_CarriesIsInSensor_shouldBeEmptyOptional() {
		SpaceObject root = fakeStar(0,0);
		BattleCarrier testObject = new BattleCarrier("TestCarrier",root,5,6,Math.PI/2);
		
		FakeSensor stubSensor = new FakeSensor();
		testObject.setSensor(stubSensor);
		
		stubSensor.detectedItems.add(testObject);

		assertFalse(testObject.getNearestPossibleTarget().isPresent());
	}
}
