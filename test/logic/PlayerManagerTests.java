package logic;

import static helpers.FakeSpaceObjectFactory.fakeStar;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import fakes.FakePlayerSpaceshuttle;
import interfaces.spacecraft.ArmedSpacecraft;
import logic.manager.ManagerRegistry;
import logic.manager.PlayerManager;
import space.core.SpaceObject;
import space.spacecrafts.ships.ArmedSpaceShuttle;

class PlayerManagerTests {

	@Test
	void testConstructor_shouldHaveNullItems() {
		PlayerManager mng = new PlayerManager();
		
		assertEquals(null,mng.getPlayerNavigator());
		assertEquals(null,mng.getPlayerShuttle());
		assertEquals(0,mng.getPlayerDeaths());
	}
	
	@Test
	void testSetPlayerShuttle_shouldBeTheSetValue() {
		PlayerManager mng = new PlayerManager();
		
		FakePlayerSpaceshuttle stub = new FakePlayerSpaceshuttle();
		
		//mng.registerPlayerShuttle(stub);
		fail("Not Yet Implemented");
	}
	
	@Test
	void testSetPlayerNavigator_shouldBeTheSetValue() {
		PlayerManager mng = new PlayerManager();
		
		fail("Not Yet Implemented");
	}
	
	@Test
	void testBuildPlayerShuttle_shouldBeRegisteredAutomaticly() {
		PlayerManager mng = new PlayerManager();
		ManagerRegistry.getInstance().setPlayerManager(mng);

		SpaceObject root = fakeStar(0,0);
		ArmedSpacecraft testObject = ArmedSpaceShuttle.PlayerSpaceShuttle("test",root,3,50,Math.PI);
		
		assertEquals(testObject,mng.getPlayerShuttle());
	}
	
	@Test
	void testReset_afterSetSomeValues_shouldBeStandard() {
		PlayerManager mng = new PlayerManager();
		
		//ADD SOME STUFF
		
		mng.reset();
		
		assertEquals(null,mng.getPlayerNavigator());
		assertEquals(null,mng.getPlayerShuttle());
		assertEquals(0,mng.getPlayerDeaths());
	}
	
	@Test
	void testForceDestruct_nullShuttle_shouldNotThrowError() {

		fail("Not Yet Implemented");
	}
	
	@Test
	void testForceDestruct_checkShuttle_shouldBeDead() {

		fail("Not Yet Implemented");
	}
	
	@Test
	void testForceDestruct_checkPlayerDeaths_shouldBeOne() {

		fail("Not Yet Implemented");
	}
	
	@Test
	void testSpeedUp_nullShuttle_shouldNotThrowError() {

		fail("Not Yet Implemented");
	}
	
	@Test
	void testSpeedUp_checkShuttleSpeed_shouldBe10PercentHigher() {

		fail("Not Yet Implemented");
	}
	
	@Test
	void testSlowDown_nullShuttle_shouldNotThrowError() {

		fail("Not Yet Implemented");
	}
	
	@Test
	void testSlowDown_checkShuttleSpeed_shouldBe10PercentLower() {

		fail("Not Yet Implemented");
	}
}
