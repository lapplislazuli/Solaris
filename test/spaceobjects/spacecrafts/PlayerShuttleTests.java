package spaceobjects.spacecrafts;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;

import static helpers.FakeSpaceObjectFactory.*;

import org.junit.jupiter.api.Test;

import logic.manager.ManagerRegistry;
import logic.manager.PlayerManager;
import space.core.SpaceObject;
import space.spacecrafts.ships.PlayerSpaceShuttle;
import space.spacecrafts.ships.Ship;

class PlayerShuttleTests {

	@AfterEach
	void resetPlayerMng() {
		ManagerRegistry.getPlayerManager();
	}
	
	@Test
	void testConstructor_shouldBeBuild() {
		SpaceObject root = fakeStar(0,0);
		PlayerSpaceShuttle testObject = new PlayerSpaceShuttle("test",root,3,50,Math.PI);
		
		assertEquals(50,testObject.getOrbitingDistance());
	}
	
	@Test
	void testConstructor_shouldBeRegisteredInPlayerManager() {
		SpaceObject root = fakeStar(0,0);
		PlayerManager mng = ManagerRegistry.getPlayerManager();
		PlayerSpaceShuttle testObject = new PlayerSpaceShuttle("test",root,3,50,Math.PI);
		
		assertEquals(mng.getPlayerShuttle(),testObject);
	}
	
	@Test
	void testRebuildAt_shouldBePlayerShuttle() {
		SpaceObject root = fakeStar(0,0);
		PlayerSpaceShuttle testObject = new PlayerSpaceShuttle("test",root,3,50,Math.PI);
		
		PlayerSpaceShuttle copy = testObject.rebuildAt("Copy", root);
		
		assertTrue(copy instanceof PlayerSpaceShuttle);
		assertTrue(copy instanceof Ship);
	}
	
	@Test
	void testRebuildAt_checkPlayerManager_shouldHaveRebuildAsPlayer() {
		SpaceObject root = fakeStar(0,0);
		PlayerManager mng = ManagerRegistry.getPlayerManager();
		PlayerSpaceShuttle testObject = new PlayerSpaceShuttle("test",root,3,50,Math.PI);
		
		PlayerSpaceShuttle copy = testObject.rebuildAt("Copy", root);
		
		assertEquals(copy,mng.getPlayerShuttle());
	}
	
	@Test
	void testDestruct_shouldBeDead() {
		SpaceObject root = fakeStar(0,0);
		PlayerSpaceShuttle testObject = new PlayerSpaceShuttle("test",root,3,50,Math.PI);
	
		testObject.destruct();
		
		assertTrue(testObject.isDead());
	}
	
	@Test
	void testDestruct_shouldIncreasePlayerDeaths() {
		SpaceObject root = fakeStar(0,0);
		PlayerManager mng = ManagerRegistry.getPlayerManager();
		PlayerSpaceShuttle testObject = new PlayerSpaceShuttle("test",root,3,50,Math.PI);
		
		testObject.destruct();
		
		assertEquals(1,mng.getPlayerDeaths());
	}
	
	@Test
	void testDestruct_doubleDestruct_shouldIncreasePlayerDeathsOnlyOnce() {
		SpaceObject root = fakeStar(0,0);
		PlayerManager mng = ManagerRegistry.getPlayerManager();
		PlayerSpaceShuttle testObject = new PlayerSpaceShuttle("test",root,3,50,Math.PI);
		
		testObject.destruct();
		testObject.destruct();
		
		assertEquals(1,mng.getPlayerDeaths());
	}
	
}
