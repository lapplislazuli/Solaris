package shuttle;

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
	void testConstructor_hasFullShips() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new BattleCarrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
		
		assertTrue(carrier.hasFullShips());
	}
	
	@Test
	void testConstructor_isBuildWith3Ships() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new BattleCarrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
	
		assertEquals(3,carrier.getCurrentShipCount());
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
	
		assertTrue(carrier.hasFullShips());
	}
	
	@Test
	void testConstructor_hasNoShips_isFalse() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new BattleCarrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
	
		assertFalse(carrier.hasNoShips());
	}
	
	@Test
	void testLaunchShips_checkTarget_shouldHaveShipsInTrabants() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new BattleCarrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
	
		SpaceObject droneTarget = fakeStar(1000,1000);
		carrier.launchShips(droneTarget);
		
		assertEquals(3, droneTarget.getTrabants().size()); // The ships should be children of targetTwo	
	}
	
	@Test
	void testLaunchShips_inspectDrones_shouldHaveTargetAsParent() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new BattleCarrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
	
		SpaceObject droneTarget = fakeStar(1000,1000);
		carrier.launchShips(droneTarget);
		
		for(CarrierDrone s : carrier.getCurrentShips())
			assertEquals(droneTarget,s.getParent());
	}
	
	@Test
	void testLaunchShips_shouldStillbeInCarriersKader() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new BattleCarrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
		
		SpaceObject droneTarget = fakeStar(1000,1000);
		carrier.launchShips(droneTarget);
		
		assertEquals(3, carrier.getCurrentShipCount()); // There should still be 4 Ships for the Carrier
	}
	
	@Test
	void testLaunchShips_inspectCarrierTrabants_shouldBeEmpty() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new BattleCarrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
		
		SpaceObject droneTarget = fakeStar(1000,1000);
		carrier.launchShips(droneTarget);
		
		assertTrue(carrier.getTrabants().isEmpty());
	}
	
	@Test
	void testRevoke_inspectCarrierTrabants_shouldBeShipsCount() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new BattleCarrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
		
		SpaceObject droneTarget = fakeStar(1000,1000);
		carrier.launchShips(droneTarget);
		
		carrier.revokeShips();
		
		assertEquals(3,carrier.getCurrentShipCount());
	}
	
	@Test
	void testRevoke_inspectDrones_shouldHaveCarrierAsParent() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new BattleCarrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
	
		SpaceObject droneTarget = fakeStar(1000,1000);
		carrier.launchShips(droneTarget);
		
		carrier.revokeShips();
		
		for(CarrierDrone s : carrier.getCurrentShips())
			assertEquals(carrier,s.getParent());
	}
	
	@Test
	void testRevoke_inspectTarget_shouldHaveNoTrabants() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new BattleCarrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
		
		SpaceObject droneTarget = fakeStar(1000,1000);
		carrier.launchShips(droneTarget);
		
		carrier.revokeShips();
		
		assertTrue(droneTarget.getTrabants().isEmpty());
	}
	
	@Test
	void testRespawn_RemoveShipAndUpdate_shouldHaveANewShip() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new BattleCarrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
	
		carrier.removeFirstShipWithoutTrash(); //Remove a ship!
		carrier.update(); //Update
		//Update should have rebuild, as fresh carriers dont have cooldown
		assertTrue(carrier.hasFullShips());
	}
	
	@Test
	void testRespawn_RemoveShipAndUpdate_shouldSetBuildingTrue() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new BattleCarrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
	
		carrier.removeFirstShipWithoutTrash();
		carrier.update();
		assertTrue(carrier.isBuilding());
	}
	
	@Test
	void testFullShips_DestroyAShipAndTest_shouldBeFalse(){
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new BattleCarrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
	
		carrier.removeFirstShipWithoutTrash();
		
		assertFalse(carrier.hasFullShips());
	}
	
	@Test
	void testHasNoShips_killAllShipsBefore_shouldBeTrue() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new BattleCarrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
		
		for(int i=0;i<carrier.getCurrentShipCount();i++)
			carrier.removeFirstShipWithoutTrash();
		
		assertTrue(carrier.hasNoShips());
		assertEquals(0,carrier.getCurrentShipCount());	
	}
	
	@Test
	void testReduceMaxShips_reduceAndUpdate_shouldHaveLessShips() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new BattleCarrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
	
		carrier.setMaxShips(2); //One Less
		carrier.update();
		
		assertEquals(2,carrier.getCurrentShipCount());
		assertEquals(carrier.getCurrentShipCount(),carrier.getMaxShips());
		
	}
	
	@Test
	void testReduceMaxShips_reduceAndUpdate_shouldHaveFullShips() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new BattleCarrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
		
		carrier.setMaxShips(2); //One Less
		carrier.update();
		
		assertTrue(carrier.hasFullShips());
	}
	
	@Test
	void testExtendMaxShips_checkHasFullShips_shouldBeFalse() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new BattleCarrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
		

		carrier.setMaxShips(10);
		carrier.update();

		assertFalse(carrier.hasFullShips());
	}
	
	@Test
	void testExtendMaxShips_shouldSpawnANewShipAfterUpdate() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new BattleCarrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
		
		carrier.setMaxShips(4);
		carrier.update();
		
		assertEquals(4,carrier.getCurrentShipCount());
	}
	
	@Test
	void testExtendMaxShips_AndUpdate_shouldBeBuilding() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new BattleCarrier("TestCarrier",carrierRoot,5,6,Math.PI/2);

		carrier.setMaxShips(5); //One More
		carrier.update();
		
		assertTrue(carrier.isBuilding());
	}
	
	@Test
	void testExtendMaxShips_DontUpdate_shouldHaveOldShipCount() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new BattleCarrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
			
		carrier.setMaxShips(5);
		assertEquals(3,carrier.getCurrentShipCount());
	}
	
	@Test
	void testSetMaxShips_insertNegativeValue_shouldThrowError() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new BattleCarrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
	
		Executable wrongArgument = () -> carrier.setMaxShips(-1);

	    assertThrows(IllegalArgumentException.class, wrongArgument, "MaxShips cannot be smaller than 0!");		
	}
	
	@Test
	void noChildCollision() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new BattleCarrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
		
		carrier.getCurrentShips().forEach(s -> assertFalse(carrier.collides(s)));
		carrier.update();
		carrier.getCurrentShips().forEach(s -> assertFalse(carrier.collides(s)));
	}
	
	
}
