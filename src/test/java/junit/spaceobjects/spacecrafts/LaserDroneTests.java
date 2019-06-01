package junit.spaceobjects.spacecrafts;

import static helpers.FakeSpaceObjectFactory.fakeStar;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import junit.fakes.FakeSensor;
import junit.fakes.interfaces.FakeCollidingObject;
import space.advanced.Asteroid;
import space.core.SpaceObject;
import space.spacecrafts.ships.ArmedSpaceShuttle;
import space.spacecrafts.ships.BattleCarrier;
import space.spacecrafts.ships.LaserDrone;

class LaserDroneTests {

	@Test
	public void testConstructor_shouldNotFail() {
		SpaceObject root = fakeStar(0,0);
		new LaserDrone("TestObject", root,0,10, 0);
		
		return;
	}

	@Test
	public void testShootLaser_shouldSpawnLaser() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle testObject= new LaserDrone("TestObject", root,0,10, 0);
		
		testObject.shootLaser(root);
		
		assertFalse(testObject.getTrabants().isEmpty());
		assertEquals(1,testObject.getTrabants().size());
	}
	
	@Test
	public void testShootLaser_shouldSetCooldown() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle testObject= new LaserDrone("TestObject", root,0,10, 0);
		
		testObject.shootLaser(root);
		
		assertTrue(testObject.laserCoolDown>0);
	}
	

	@Test
	void testShootLaser_hasCooldown_shouldNotSpawnLaser() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle testObject= new LaserDrone("TestObject", root,0,10, 0);
		
		testObject.laserCoolDown=1000;
		
		testObject.shootLaser(root);
		
		assertTrue(testObject.getTrabants().isEmpty());
	}
	
	@Test
	void testGetNearestPossibleTarget_noItemsDetected_shouldBeEmptyOptional() {
		SpaceObject root = fakeStar(0,0);
		BattleCarrier testObjectSource = new BattleCarrier("TestCarrier",root,5,6,Math.PI/2);
			
		LaserDrone testObject = (LaserDrone) testObjectSource.getDrones().get(0);
		
		FakeSensor stubSensor = new FakeSensor();
		testObject.setSensor(stubSensor);
		
		assertFalse(testObject.getNearestPossibleTarget().isPresent());
	}
	
	@Test
	void testGetNearestPossibleTarget_notDestructible_shouldBeEmptyOptional(){
		SpaceObject root = fakeStar(0,0);
		BattleCarrier testObjectSource = new BattleCarrier("TestCarrier",root,5,6,Math.PI/2);
		
		LaserDrone testObject = (LaserDrone) testObjectSource.getDrones().get(0);
		
		FakeSensor stubSensor = new FakeSensor();
		testObject.setSensor(stubSensor);
		stubSensor.detectedItems.add(root);
		
		assertFalse(testObject.getNearestPossibleTarget().isPresent());
	}
	
	@Test
	void testGetNearestPossibleTarget_SensorNonEmpty_noSpaceObjectInSensor_shouldBeEmptyOptional() {
		SpaceObject root = fakeStar(0,0);
		BattleCarrier testObjectSource = new BattleCarrier("TestCarrier",root,5,6,Math.PI/2);
		
		LaserDrone testObject = (LaserDrone) testObjectSource.getDrones().get(0);
		
		FakeSensor stubSensor = new FakeSensor();
		testObject.setSensor(stubSensor);
		stubSensor.detectedItems.add(new FakeCollidingObject());
		
		assertFalse(testObject.getNearestPossibleTarget().isPresent());
	}
	
	@Test
	void testGetNearestPossibleTarget_DestructibleItemInSensor_shouldBeNonEmptyOptional() {
		SpaceObject root = fakeStar(0,0);
		BattleCarrier testObjectSource = new BattleCarrier("TestCarrier",root,5,6,Math.PI/2);
		
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
		BattleCarrier testObjectSource = new BattleCarrier("TestCarrier",root,5,6,Math.PI/2);
		
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
		BattleCarrier testObjectSource = new BattleCarrier("TestCarrier",root,5,6,Math.PI/2);
		
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
		BattleCarrier testObjectSource = new BattleCarrier("TestCarrier",root,5,6,Math.PI/2);
		
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
		BattleCarrier testObjectSource = new BattleCarrier("TestCarrier",root,5,6,Math.PI/2);
		
		LaserDrone testObject = (LaserDrone) testObjectSource.getDrones().get(0);
		
		FakeSensor stubSensor = new FakeSensor();
		testObject.setSensor(stubSensor);
		
		stubSensor.detectedItems.addAll(testObjectSource.getDrones());

		assertFalse(testObject.getNearestPossibleTarget().isPresent());
	}
	
	@Test
	void testGetNearestPossibleTarget_CarrierIsInSensor_shouldBeEmptyOptional() {
		SpaceObject root = fakeStar(0,0);
		BattleCarrier testObjectSource = new BattleCarrier("TestCarrier",root,5,6,Math.PI/2);
		
		LaserDrone testObject = (LaserDrone) testObjectSource.getDrones().get(0);
		
		FakeSensor stubSensor = new FakeSensor();
		testObject.setSensor(stubSensor);
		
		stubSensor.detectedItems.add(testObjectSource);

		assertFalse(testObject.getNearestPossibleTarget().isPresent());
	}
}
