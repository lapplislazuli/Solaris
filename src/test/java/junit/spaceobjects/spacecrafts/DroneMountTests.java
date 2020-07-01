package junit.spaceobjects.spacecrafts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import geom.AbsolutePoint;
import interfaces.geom.Point;
import interfaces.spacecraft.CarrierDrone;
import logic.manager.ManagerRegistry;
import space.core.SpaceObject;
import space.spacecraft.ships.devices.DroneMount;
import space.spacecrafts.ships.ArmedSpaceShuttle;
import space.spacecrafts.ships.LaserDrone;

import static junit.testhelpers.FakeSpaceObjectFactory.*;

public class DroneMountTests {
	
	@BeforeEach
	void initManagerRegistry() {
		ManagerRegistry.getInstance();
	}
	@AfterEach
	void resetManagerRegistry() {
		ManagerRegistry.reset();
	}
	
	@Test
	void testConstructor_droneIsAlive_shouldBeReady() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle emitter= new ArmedSpaceShuttle("Army",root,0,50,0);
		CarrierDrone testDrone = new LaserDrone("testDrone", emitter, 2, 10, Math.PI/2);

		DroneMount testObject = new DroneMount(emitter,testDrone);

		assertTrue(testObject.isReady());
	}
	
	@Test
	void testIsReady_droneIsDead_shouldBeReady() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle emitter= new ArmedSpaceShuttle("Army",root,0,50,0);
		CarrierDrone testDrone = new LaserDrone("testDrone", emitter, 2, 10, Math.PI/2);

		DroneMount testObject = new DroneMount(emitter,testDrone);

		testDrone.destruct();
		
		assertFalse(testObject.isReady());
	}
	
	@Test
	void testGetParent_shouldBeEmitter() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle emitter= new ArmedSpaceShuttle("Army",root,0,50,0);
		CarrierDrone testDrone = new LaserDrone("testDrone", emitter, 2, 10, Math.PI/2);

		DroneMount testObject = new DroneMount(emitter,testDrone);

		assertEquals(emitter,testObject.getParent());
	}
	
	@Test
	void testConstructor_nullEmitter_shouldThrowError() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle emitter= new ArmedSpaceShuttle("Army",root,0,50,0);
		CarrierDrone testDrone = new LaserDrone("testDrone", emitter, 2, 10, Math.PI/2);

		assertThrows(IllegalArgumentException.class, () -> new DroneMount(null,testDrone));
	}
	@Test
	void testConstructor_negativeCooldown_shouldThrowError() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle emitter= new ArmedSpaceShuttle("Army",root,0,50,0);
		
		assertThrows(IllegalArgumentException.class, () -> new DroneMount(emitter,null));	
	}
	
	@Test
	void testSetTarget_targetIsNull_shouldThrowError() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle emitter= new ArmedSpaceShuttle("Army",root,0,50,0);
		CarrierDrone testDrone = new LaserDrone("testDrone", emitter, 2, 10, Math.PI/2);

		DroneMount testObject = new DroneMount(emitter,testDrone);
		
		SpaceObject target = null;
		
		assertThrows(IllegalArgumentException.class, () ->testObject.setTarget(target));	
	}
	
	
	@Test
	void testSetTarget_targetIsNotNull_shouldBeSet() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle emitter= new ArmedSpaceShuttle("Army",root,0,50,0);
		CarrierDrone testDrone = new LaserDrone("testDrone", emitter, 2, 10, Math.PI/2);

		DroneMount testObject = new DroneMount(emitter,testDrone);
		
		testObject.setTarget(root);	
		return;
	}
	
	@Test
	void testLaunchDrone_DroneCanBeLaunched_DroneIsInTargetsTrabants() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle emitter= new ArmedSpaceShuttle("Army",root,0,50,0);
		CarrierDrone testDrone = new LaserDrone("testDrone", emitter, 2, 10, Math.PI/2);

		DroneMount testObject = new DroneMount(emitter,testDrone);
		SpaceObject target = fakeStar(250,250);
		testObject.setTarget(target);
		
		testObject.launch_drone();
		
		assertEquals(1,target.getTrabants().size());
	}
	
	@Test
	void testLaunchDrone_DroneIsDead_TargetHasNoTrabants() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle emitter= new ArmedSpaceShuttle("Army",root,0,50,0);
		CarrierDrone testDrone = new LaserDrone("testDrone", emitter, 2, 10, Math.PI/2);

		DroneMount testObject = new DroneMount(emitter,testDrone);
		SpaceObject target = fakeStar(250,250);
		testObject.setTarget(target);
		
		testDrone.destruct();
		
		testObject.launch_drone();
		
		assertEquals(0,target.getTrabants().size());
	}
	
	@Test
	void testActivate_DroneCanBeLaunched_DroneIsInTargetsTrabants() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle emitter= new ArmedSpaceShuttle("Army",root,0,50,0);
		CarrierDrone testDrone = new LaserDrone("testDrone", emitter, 2, 10, Math.PI/2);

		DroneMount testObject = new DroneMount(emitter,testDrone);
		SpaceObject target = fakeStar(250,250);
		testObject.setTarget(target);
		
		testObject.activate();
		
		assertEquals(1,target.getTrabants().size());
	}
	
	@Test
	void testActivateTwice_DroneCanBeLaunched_DroneIsNotInTargetsTrabants() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle emitter= new ArmedSpaceShuttle("Army",root,0,50,0);
		CarrierDrone testDrone = new LaserDrone("testDrone", emitter, 2, 10, Math.PI/2);

		DroneMount testObject = new DroneMount(emitter,testDrone);
		SpaceObject target = fakeStar(250,250);
		testObject.setTarget(target);
		
		testObject.activate();
		testObject.activate();
		
		assertEquals(0,target.getTrabants().size());
	}	
	
	@Test
	void testActivateTwice_DroneCanBeLaunched_DroneIsNotInEmittersTrabants() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle emitter= new ArmedSpaceShuttle("Army",root,0,50,0);
		CarrierDrone testDrone = new LaserDrone("testDrone", emitter, 2, 10, Math.PI/2);

		DroneMount testObject = new DroneMount(emitter,testDrone);
		SpaceObject target = fakeStar(250,250);
		testObject.setTarget(target);
		
		testObject.activate();
		testObject.activate();
		
		assertEquals(1,emitter.getTrabants().size());
	}
	
	@Test
	void testLaunch_TargetIsSetViaPoint_notLaunched() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle emitter= new ArmedSpaceShuttle("Army",root,0,50,0);
		CarrierDrone testDrone = new LaserDrone("testDrone", emitter, 2, 10, Math.PI/2);

		DroneMount testObject = new DroneMount(emitter,testDrone);
		Point target = new AbsolutePoint(250,250);
		testObject.setTarget(target);
		
		testObject.launch_drone();
		
		assertEquals(1,emitter.getTrabants().size());
	}
	@Test
	void testLaunch_TargetIsSetViaRadiant_notLaunched() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle emitter= new ArmedSpaceShuttle("Army",root,0,50,0);
		CarrierDrone testDrone = new LaserDrone("testDrone", emitter, 2, 10, Math.PI/2);
		DroneMount testObject = new DroneMount(emitter,testDrone);
		
		testObject.setTarget(Math.PI);
		
		testObject.launch_drone();
		
		assertEquals(1,emitter.getTrabants().size());
	}
	
	@Test
	void testUpdateTwice_DroneWasDead_DroneShouldNotBeRebuild() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle emitter= new ArmedSpaceShuttle("Army",root,0,50,0);
		CarrierDrone testDrone = new LaserDrone("testDrone", emitter, 2, 10, Math.PI/2);

		DroneMount testObject = new DroneMount(emitter,testDrone);
		
		testDrone.destruct();
		
		testObject.update();
		testObject.update();
		
		assertFalse(testObject.isReady());
	}
	
	@Test
	void testUpdate_DroneWasDead_UpdateManyTimes_DroneIsRebuild() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle emitter= new ArmedSpaceShuttle("Army",root,0,50,0);
		CarrierDrone testDrone = new LaserDrone("testDrone", emitter, 2, 10, Math.PI/2);

		DroneMount testObject = new DroneMount(emitter,testDrone);
		
		testDrone.destruct();
		
		for(int i = 0;i<5000;i++)
			testObject.update();
		
		assertTrue(testObject.isReady());
	}
}
