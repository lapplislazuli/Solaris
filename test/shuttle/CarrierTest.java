package shuttle;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import geom.AbsolutePoint;
import space.core.Planet;
import space.core.Star;
import space.shuttle.Carrier;

class CarrierTest {
	
	Carrier carrier;
	
	Star star;
	Planet targetOne,targetTwo;
	
	@BeforeEach
	void resetPlanets() {
		star = new Star("Anker",null,new AbsolutePoint(250,250), 25);
		
		targetOne = (new Planet.Builder("One", star))
				.size(10)
				.distance(100)
				.speed(0)
				.build();
		targetTwo = (new Planet.Builder("Two", star))
				.size(10)
				.distance(-100)
				.speed(0)
				.build();
		
		targetTwo.relativePos=Math.PI/4.5; //Move it
	}
	
	@BeforeEach
	void buildCarrier() {
		carrier = new Carrier("TestCarrier",targetOne,5,6,Math.PI/2);
	}
	
	@Test
	void ConstructorTest() {
		assertTrue(carrier.hasFullShips());
		assertEquals(4,carrier.getCurrentShipCount());
		assertFalse(carrier.isBuilding());
		
		assertEquals(5,carrier.getAllChildrenFlat().size()); //Itself and 4 children
	}
	
	@Test
	void CarrierLaunchTest() {
		carrier.launchShips(targetTwo);
		// Update Everything 
		star.update();
		
		assertEquals(4, carrier.getCurrentShipCount()); // There should still be 4 Ships for the Carrier
		assertEquals(1,carrier.getAllChildrenFlat().size()); // There should be nothing cycling around the carrier
		
		assertEquals(5, targetTwo.getAllChildrenFlat().size()); // The ships should be children of targetTwo
	}
	
	@Test
	void ReducedCarrierLaunchTest() {
		carrier.removeFirstShipWithoutTrash();
		carrier.removeFirstShipWithoutTrash();
		star.update();
		
		carrier.launchShips(targetTwo);
		// Update Everything 
		star.update();
		
		assertEquals(3, carrier.getCurrentShipCount()); // There should still be 3 Ships for the Carrier
		assertEquals(1,carrier.getAllChildrenFlat().size()); // There should be nothing cycling around the carrier
		
		assertEquals(4, targetTwo.getAllChildrenFlat().size()); // The ships should be children of targetTwo
	}
	
	@Test
	void CarrierRevokeTest() {
		carrier.launchShips(targetTwo);
		star.update();
		
		carrier.revokeShips();
		star.update();

		assertEquals(4, carrier.getCurrentShipCount()); // There should still be 4 Ships for the Carrier
		assertEquals(5,carrier.getAllChildrenFlat().size()); // Ships should be back at carrier
		
		assertEquals(1, targetTwo.getAllChildrenFlat().size()); // TargetTwo should be free
	}
	
	@Test
	void RespawnTest() {
		carrier.removeFirstShipWithoutTrash(); //Remove a ship!
		carrier.update(); //Update
		//Update should have rebuild, as fresh carriers dont have cooldown
		assertTrue(carrier.hasFullShips());
	}
	
	@Test
	void RespawnTimerTest() {
		carrier.getCurrentShips().get(0).destruct();
		carrier.update();
		assertTrue(carrier.isBuilding());
	}
	
	@Test
	void negativeHasFullShipsTest() {
		carrier.getCurrentShips().get(0).destruct();
		carrier.getCurrentShips().get(1).destruct();
		carrier.update();
		assertFalse(carrier.hasFullShips());
		assertFalse(carrier.hasNoShips());
	}
	
	@Test
	void testAllDead() {
		carrier.getCurrentShips().forEach(t -> t.destruct());
		carrier.update();
		carrier.getCurrentShips().get(0).destruct();
		carrier.update();
		assertTrue(carrier.hasNoShips());
		assertFalse(carrier.hasFullShips());
		assertEquals(0,carrier.getCurrentShipCount());
	}
	
	@Test
	void reduceMaxShipsTest() {
		assertTrue(carrier.hasFullShips());
		carrier.update();
		carrier.setMaxShips(2); //One Less
		carrier.update();
		assertEquals(2,carrier.getCurrentShipCount());
		assertEquals(carrier.getCurrentShipCount(),carrier.getMaxShips());
		assertTrue(carrier.hasFullShips());
	}
	@Test
	void extendMaxShipsTest() {
		assertTrue(carrier.hasFullShips());
		
		carrier.setMaxShips(5); //One More
		assertEquals(4,carrier.getCurrentShipCount());
		assertFalse(carrier.hasFullShips());
	}
	
	@Test
	void negativeMaxShipsTest() {
		Executable wrongArgument = () -> carrier.setMaxShips(-1);

	    assertThrows(IllegalArgumentException.class, wrongArgument, "MaxShips cannot be smaller than 0!");		
	}
	
	
}
