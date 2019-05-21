package spaceobjects.spacecrafts;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import interfaces.spacecraft.CarrierDrone;
import logic.manager.ManagerRegistry;
import space.core.SpaceObject;
import space.spacecrafts.ships.BattleCarrier;
import space.spacecrafts.ships.Carrier;

import static helpers.FakeSpaceObjectFactory.*;

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
	void testConstructor_shouldHaveSetValues() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new BattleCarrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
	
		assertEquals(Math.PI/2,carrier.getSpeed());
		assertEquals("TestCarrier",carrier.getName());
	}
	
	@Test
	void testConstructor_hasFullDrones() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new BattleCarrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
		
		assertTrue(carrier.hasFullDrones());
	}
	
	@Test
	void testConstructor_isBuildWith3Ships() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new BattleCarrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
	
		assertEquals(3,carrier.getCurrentDroneCount());
	}
	
	@Test
	void testConstructor_isNotBuildingNewShips() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new BattleCarrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
	
		assertFalse(carrier.isBuilding());
	}
	
	@Test
	void testConstructor_hasFullShipsIsTrue() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new BattleCarrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
	
		assertTrue(carrier.hasFullDrones());
	}
	
	@Test
	void testLaunchDrones_checkTarget_shouldHaveShipsInTrabants() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new BattleCarrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
	
		SpaceObject droneTarget = fakeStar(1000,1000);
		carrier.launchDrones(droneTarget);
		
		assertEquals(3, droneTarget.getTrabants().size()); // The ships should be children of targetTwo	
	}
	
	@Test
	void testLaunchDrones_inspectDrones_shouldHaveTargetAsParent() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new BattleCarrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
	
		SpaceObject droneTarget = fakeStar(1000,1000);
		carrier.launchDrones(droneTarget);
		
		for(CarrierDrone s : carrier.getDrones())
			assertEquals(droneTarget,s.getParent());
	}
	
	@Test
	void testLaunchDrones_shouldStillbeInCarriersKader() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new BattleCarrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
		
		SpaceObject droneTarget = fakeStar(1000,1000);
		carrier.launchDrones(droneTarget);
		
		assertEquals(3, carrier.getCurrentDroneCount()); // There should still be 4 Ships for the Carrier
	}
	
	@Test
	void testLaunchDrones_inspectCarrierTrabants_shouldBeEmpty() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new BattleCarrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
		
		SpaceObject droneTarget = fakeStar(1000,1000);
		carrier.launchDrones(droneTarget);
		
		assertTrue(carrier.getTrabants().isEmpty());
	}
	
	@Test
	void testRevoke_inspectCarrierTrabants_shouldBeShipsCount() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new BattleCarrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
		
		SpaceObject droneTarget = fakeStar(1000,1000);
		carrier.launchDrones(droneTarget);
		
		carrier.revokeDrones();
		
		assertEquals(3,carrier.getCurrentDroneCount());
	}
	
	@Test
	void testRevoke_inspectDrones_shouldHaveCarrierAsParent() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new BattleCarrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
	
		SpaceObject droneTarget = fakeStar(1000,1000);
		carrier.launchDrones(droneTarget);
		
		carrier.revokeDrones();
		
		for(CarrierDrone s : carrier.getDrones())
			assertEquals(carrier,s.getParent());
	}
	
	@Test
	void testRevoke_inspectTarget_shouldHaveNoTrabants() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new BattleCarrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
		
		SpaceObject droneTarget = fakeStar(1000,1000);
		carrier.launchDrones(droneTarget);
		
		carrier.revokeDrones();
		
		assertTrue(droneTarget.getTrabants().isEmpty());
	}
	
	@Test
	void testRespawn_RemoveDroneAndUpdate_shouldHaveANewShip() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new BattleCarrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
	
		carrier.removeFirstDroneWithoutTrash(); //Remove a ship!
		carrier.update(); //Update
		//Update should have rebuild, as fresh carriers dont have cooldown
		assertTrue(carrier.hasFullDrones());
	}
	
	@Test
	void testRespawn_RemoveDroneAndUpdate_shouldSetBuildingTrue() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new BattleCarrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
	
		carrier.removeFirstDroneWithoutTrash();
		carrier.update();
		assertTrue(carrier.isBuilding());
	}
	
	@Test
	void testFullDrones_DestroyAShipAndTest_shouldBeFalse(){
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new BattleCarrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
	
		carrier.removeFirstDroneWithoutTrash();
		
		assertFalse(carrier.hasFullDrones());
	}
	
	@Test
	void testReduceMaxDrones_reduceAndUpdate_shouldHaveLessShips() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new BattleCarrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
	
		carrier.setMaxDrones(2); //One Less
		carrier.update();
		
		assertEquals(2,carrier.getCurrentDroneCount());
		assertEquals(carrier.getCurrentDroneCount(),carrier.getMaxDrones());
		
	}
	
	@Test
	void testReduceMaxDrones_reduceAndUpdate_shouldHaveFullShips() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new BattleCarrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
		
		carrier.setMaxDrones(2); //One Less
		carrier.update();
		
		assertTrue(carrier.hasFullDrones());
	}
	
	@Test
	void testExtendMaxDrones_checkHasFullShips_shouldBeFalse() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new BattleCarrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
		

		carrier.setMaxDrones(10);
		carrier.update();

		assertFalse(carrier.hasFullDrones());
	}
	
	@Test
	void testExtendMaxDrones_shouldSpawnANewShipAfterUpdate() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new BattleCarrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
		
		carrier.setMaxDrones(4);
		carrier.update();
		
		assertEquals(4,carrier.getCurrentDroneCount());
	}
	
	@Test
	void testExtendMaxDrones_AndUpdate_shouldBeBuilding() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new BattleCarrier("TestCarrier",carrierRoot,5,6,Math.PI/2);

		carrier.setMaxDrones(5); //One More
		carrier.update();
		
		assertTrue(carrier.isBuilding());
	}
	
	@Test
	void testExtendMaxDrones_DontUpdate_shouldHaveOldShipCount() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new BattleCarrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
			
		carrier.setMaxDrones(5);
		assertEquals(3,carrier.getCurrentDroneCount());
	}
	
	@Test
	void testSetMaxDrones_insertNegativeValue_shouldThrowError() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new BattleCarrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
	
		Executable wrongArgument = () -> carrier.setMaxDrones(-1);

	    assertThrows(IllegalArgumentException.class, wrongArgument, "MaxShips cannot be smaller than 0!");		
	}
	
	@Test
	void testCollision_inspectCarrier_doesNotCollideDrones() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new BattleCarrier("TestCarrier",carrierRoot,5,6,Math.PI/2);

		carrier.update();
		
		carrier.getDrones()
			.forEach(s -> assertFalse(carrier.collides(s)));
	}
	
	@Test
	void testCollision_inspectDrones_DoNotCollideCarrier() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new BattleCarrier("TestCarrier",carrierRoot,5,6,Math.PI/2);

		carrier.update();
		
		carrier.getDrones().forEach(s -> assertFalse(s.collides(carrier)));
	}
	
	@Test
	void testCollision_inspectDrones_shouldNotCollideEachOther() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new BattleCarrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
		
		carrier.update();
		
		for(CarrierDrone d : carrier.getDrones())
			for (CarrierDrone u : carrier.getDrones())
				assertFalse(d.collides(u));
	}
	
}
