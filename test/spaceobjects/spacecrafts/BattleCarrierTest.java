package spaceobjects.spacecrafts;

import static helpers.FakeSpaceObjectFactory.fakeStar;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import geom.AbsolutePoint;
import interfaces.geom.Point;
import space.core.SpaceObject;
import space.spacecrafts.ships.BattleCarrier;
import space.spacecrafts.ships.Carrier;

class BattleCarrierTest {

	@Test
	void testConstructor_shouldBeBuild() {
		SpaceObject carrierRoot = fakeStar(0,0);
		Carrier carrier = new BattleCarrier("TestCarrier",carrierRoot,5,6,Math.PI/2);
	
		assertEquals(Math.PI/2,carrier.getSpeed());
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
}
