package spaceobjects.spacecrafts;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;

import static helpers.FakeSpaceObjectFactory.*;

import org.junit.jupiter.api.Test;

import interfaces.spacecraft.ArmedSpacecraft;
import logic.manager.ManagerRegistry;
import logic.manager.PlayerManager;
import space.core.SpaceObject;
import space.spacecrafts.ships.ArmedSpaceShuttle;
import space.spacecrafts.ships.Ship;

class PlayerShuttleTests {

	@AfterEach
	void resetPlayerMng() {
		ManagerRegistry.getPlayerManager().reset();
	}
	
	@Test
	void testConstructor_shouldBeBuild() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpacecraft testObject = ArmedSpaceShuttle.PlayerSpaceShuttle("test",root,3,50,Math.PI);
		
		assertEquals(50,((Ship) testObject).getOrbitingDistance());
	}
	
	@Test
	void testConstructor_shouldBeRegisteredInPlayerManager() {
		SpaceObject root = fakeStar(0,0);
		PlayerManager mng = ManagerRegistry.getPlayerManager();
		ArmedSpacecraft testObject = ArmedSpaceShuttle.PlayerSpaceShuttle("test",root,3,50,Math.PI);
		
		assertEquals(mng.getPlayerShuttle(),testObject);
	}
	
	@Test
	void testConstructor_shouldBePlayer() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpacecraft testObject = ArmedSpaceShuttle.PlayerSpaceShuttle("test",root,3,50,Math.PI);
		
		assertTrue(testObject.isPlayer());
	}
	
	@Test
	void testRebuildAt_shouldBePlayerShuttle() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpacecraft testObject = ArmedSpaceShuttle.PlayerSpaceShuttle("test",root,3,50,Math.PI);
		
		ArmedSpacecraft copy = (ArmedSpacecraft) testObject.rebuildAt("Copy", root);
		
		assertTrue(copy instanceof ArmedSpacecraft);
		assertTrue(copy instanceof Ship);
		assertTrue(copy.isPlayer());
	}
	
	@Test
	void testRebuildAt_checkPlayerManager_shouldHaveRebuildAsPlayer() {
		SpaceObject root = fakeStar(0,0);
		PlayerManager mng = ManagerRegistry.getPlayerManager();
		ArmedSpacecraft testObject = ArmedSpaceShuttle.PlayerSpaceShuttle("test",root,3,50,Math.PI);
		
		ArmedSpacecraft copy = (ArmedSpacecraft) testObject.rebuildAt("Copy", root);
		
		assertEquals(copy,mng.getPlayerShuttle());
	}
	
	@Test
	void testDestruct_shouldBeDead() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpacecraft testObject = ArmedSpaceShuttle.PlayerSpaceShuttle("test",root,3,50,Math.PI);
	
		testObject.destruct();
		
		assertTrue(testObject.isDead());
	}
	
	@Test
	void testDestruct_shouldIncreasePlayerDeaths() {
		SpaceObject root = fakeStar(0,0);
		PlayerManager mng = ManagerRegistry.getPlayerManager();
		ArmedSpacecraft testObject = ArmedSpaceShuttle.PlayerSpaceShuttle("test",root,3,50,Math.PI);
		
		testObject.destruct();
		
		assertEquals(1,mng.getPlayerDeaths());
	}
	
	@Test
	void testDestruct_doubleDestruct_shouldIncreasePlayerDeathsOnlyOnce() {
		SpaceObject root = fakeStar(0,0);
		PlayerManager mng = ManagerRegistry.getPlayerManager();
		ArmedSpacecraft testObject = ArmedSpaceShuttle.PlayerSpaceShuttle("test",root,3,50,Math.PI);
		
		testObject.destruct();
		testObject.destruct();
		
		assertEquals(1,mng.getPlayerDeaths());
	}
	
	
}
