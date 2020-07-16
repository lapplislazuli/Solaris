package junit.logic;



import static junit.testhelpers.FakeSpaceObjectFactory.fakeStar;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import interfaces.spacecraft.Spacecraft;
import junit.fakes.FakeArmedNavigator;
import junit.fakes.FakeSpacecraft;
import logic.manager.ManagerRegistry;
import logic.manager.PlayerManager;
import space.core.SpaceObject;
import space.spacecrafts.ships.Spaceshuttle;

class PlayerManagerTests {

	@BeforeEach
	void initManagerRegistry() {
		ManagerRegistry.getInstance();
	}
	@AfterEach
	void resteManagerRegistry() {
		ManagerRegistry.reset();
	}
	
	@Test
	void testConstructor_shouldHaveNullItems() {
		PlayerManager mng = new PlayerManager();
		
		assertEquals(null,mng.getPlayerNavigator());
		assertEquals(null,mng.getPlayerShuttle());
		assertEquals(0,mng.getPlayerDeaths());
	}
	
	@Test
	void testRegisterPlayerShuttle_shouldBeTheSetValue() {
		PlayerManager mng = new PlayerManager();
		
		FakeSpacecraft stub = new FakeSpacecraft();
		
		mng.registerPlayerShuttle(stub);
		assertEquals(stub,mng.getPlayerShuttle());
	}

	@Test
	void testRegisterShuttle_doubleRegister_shouldBeOverwritten() {
		PlayerManager mng = new PlayerManager();
		

		FakeSpacecraft stub = new FakeSpacecraft();
		FakeSpacecraft replacement = new FakeSpacecraft();
		
		mng.registerPlayerShuttle(stub);
		mng.registerPlayerShuttle(replacement);
		
		assertEquals(replacement,mng.getPlayerShuttle());
	}
	
	@Test
	void testRegisterPlayerNavigator_shouldBeTheSetValue() {
		PlayerManager mng = new PlayerManager();
		
		FakeArmedNavigator stub = new FakeArmedNavigator();
		
		mng.registerPlayerNavigator(stub);
		
		assertEquals(stub,mng.getPlayerNavigator());
	}
	
	@Test
	void testRegisterPlayerNavigator_doubleRegister_shouldBeOverwritten() {
		PlayerManager mng = new PlayerManager();
		
		FakeArmedNavigator stub = new FakeArmedNavigator();
		FakeArmedNavigator replacement = new FakeArmedNavigator();
		
		mng.registerPlayerNavigator(stub);
		mng.registerPlayerNavigator(replacement);
		
		assertEquals(replacement,mng.getPlayerNavigator());
	}
	
	@Test
	void testBuildPlayerShuttle_shouldBeRegisteredAutomaticly() {
		PlayerManager mng = new PlayerManager();
		ManagerRegistry.getInstance().setPlayerManager(mng);

		SpaceObject root = fakeStar(0,0);
		Spacecraft testObject = new Spaceshuttle.Builder("test", root).isPlayer().build();
		
		assertEquals(testObject,mng.getPlayerShuttle());
	}
	
	@Test
	void testReset_afterSetSomeValues_shouldBeStandard() {
		PlayerManager mng = new PlayerManager();
		
		FakeArmedNavigator navStub = new FakeArmedNavigator();
		mng.registerPlayerNavigator(navStub);
		FakeSpacecraft shuttleStub = new FakeSpacecraft();
		mng.registerPlayerShuttle(shuttleStub);
		
		mng.reset();
		
		assertEquals(null,mng.getPlayerNavigator());
		assertEquals(null,mng.getPlayerShuttle());
		assertEquals(0,mng.getPlayerDeaths());
	}
	
	@Test
	void testForceRespawn_nullShuttle_shouldNotThrowError() {
		PlayerManager mng = new PlayerManager();
		
		mng.forceRespawn();
		
		return; //Return means autopass
	}
	
	@Test
	void testForceRespawn_checkShuttle_shouldBeDead() {
		PlayerManager mng = new PlayerManager();
		FakeSpacecraft stub = new FakeSpacecraft();
		mng.registerPlayerShuttle(stub);
		
		mng.forceRespawn();
		
		assertTrue(stub.isDead());
	}
	
	@Test
	void testForceDestruct_checkPlayerDeaths_shouldBeOne() {
		PlayerManager mng = new PlayerManager();
		ManagerRegistry.getInstance().setPlayerManager(mng);

		SpaceObject root = fakeStar(0,0);
		Spacecraft testObject = new Spaceshuttle.Builder("test", root).isPlayer().build();
		
		mng.forceRespawn();
		
		assertEquals(1,mng.getPlayerDeaths());
	}
	
	@Test
	void testSpeedUp_nullShuttle_shouldNotThrowError() {
		PlayerManager mng = new PlayerManager();
		
		mng.speedUp();
		
		return; //Return means autopass
	}
	
	@Test
	void testSpeedUp_checkShuttleSpeed_shouldBe10PercentHigher() {
		PlayerManager mng = new PlayerManager();
		FakeSpacecraft stub = new FakeSpacecraft();
		mng.registerPlayerShuttle(stub);
		
		stub.speed=100;
		mng.speedUp();
		
		assertEquals(110,stub.speed,0.001);
	}
	
	@Test
	void testSpeedUp_checkShuttleSpeedChanged_shouldbeChanged() {
		PlayerManager mng = new PlayerManager();
		FakeSpacecraft stub = new FakeSpacecraft();
		mng.registerPlayerShuttle(stub);
		
		mng.speedUp();
		
		assertTrue(stub.speedSet);	
	}
	
	
	@Test
	void testSlowDown_nullShuttle_shouldNotThrowError() {
		PlayerManager mng = new PlayerManager();
		
		mng.slowDown();
		
		return; //Return means autopass
	}
	
	@Test
	void testSlowDown_checkShuttleSpeed_shouldBe10PercentLower() {
		PlayerManager mng = new PlayerManager();
		FakeSpacecraft stub = new FakeSpacecraft();
		mng.registerPlayerShuttle(stub);
		
		stub.speed=100;
		mng.slowDown();
		
		assertEquals(90,stub.speed,0.001);
	}
	
	@Test
	void testSlowDown_checkShuttleSpeedChanged_shouldbeChanged() {
		PlayerManager mng = new PlayerManager();
		FakeSpacecraft stub = new FakeSpacecraft();
		mng.registerPlayerShuttle(stub);
		
		mng.slowDown();
		
		assertTrue(stub.speedSet);	
	}
}
