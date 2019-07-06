package junit.spaceobjects.spacecrafts;



import static junit.testhelpers.FakeSpaceObjectFactory.fakeStar;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import interfaces.spacecraft.SpacecraftState;
import junit.fakes.FakeSpacecraft;
import logic.manager.ManagerRegistry;
import space.core.SpaceObject;
import space.spacecrafts.navigators.BaseNavigator;

class BaseNavigatorTests {

	@AfterEach
	public void resetManagers() {
		ManagerRegistry.reset();
	}
	
	@Tag("Shuttle")
	@Tag("fast")
	@Test
	void testConstructor_shouldBeBuild() {
		FakeSpacecraft stub = new FakeSpacecraft();
		BaseNavigator testObject = new BaseNavigator("Test",stub,true);
		
		return;
	}

	@Tag("Shuttle")
	@Tag("fast")
	@RepeatedTest(2)
	@Test
	void testConstructor_NullShip_shouldThrowException() {
		assertThrows(IllegalArgumentException.class, 
				() -> new BaseNavigator("Test",null,true)
		);
	}

	@Tag("Shuttle")
	@Tag("fast")
	@Test
	void testConstructor_NullName_shouldThrowException() {
		FakeSpacecraft stub = new FakeSpacecraft();
		assertThrows(IllegalArgumentException.class, 
				() -> new BaseNavigator(null,stub,true)
		);
	}
	

	@Tag("Shuttle")
	@Tag("fast")
	@Test
	void testConstructor_EmptyName_shouldThrowException() {
		FakeSpacecraft stub = new FakeSpacecraft();
		assertThrows(IllegalArgumentException.class, 
				() -> new BaseNavigator("",stub,true)
		);
	}
	

	@Tag("Shuttle")
	@Tag("fast")
	@Test
	void testConstructor_shouldNotBeOrphaned() {
		ManagerRegistry.getInstance();
		
		FakeSpacecraft stub = new FakeSpacecraft();
		BaseNavigator testObject = new BaseNavigator("Test",stub,true);
		
		assertFalse(testObject.isOrphan());
	}
	

	@Tag("Shuttle")
	@Tag("fast")
	@Test
	void testConstructor_shouldHaveShipsParentInRoute() {
		FakeSpacecraft stub = new FakeSpacecraft();
		SpaceObject stubRoot = fakeStar(0,0);
		stub.parent=stubRoot;
		BaseNavigator testObject = new BaseNavigator("Test",stub,true);
		
		assertTrue(testObject.getRoute().contains(stubRoot));
		assertEquals(1,testObject.getRoute().size());
	}
	

	@Tag("Shuttle")
	@Tag("fast")
	@Test
	void testClearRoute_shouldNotHaveEmptyRoute() {
		FakeSpacecraft stub = new FakeSpacecraft();
		SpaceObject stubRoot = fakeStar(0,0);
		stub.parent=stubRoot;
		BaseNavigator testObject = new BaseNavigator("Test",stub,true);
		
		testObject.clearRoute();
		
		assertTrue(testObject.getRoute().contains(stubRoot));
		assertEquals(1,testObject.getRoute().size());
	}
	

	@Tag("Shuttle")
	@Tag("fast")
	@Test
	void testGetNextWayPoint_currentPointerIsFirst_shouldBeSecondElement() {
		FakeSpacecraft stub = new FakeSpacecraft();
		SpaceObject stubRoot = fakeStar(0,0);
		SpaceObject stubTarget = fakeStar(0,0);
		stub.parent=stubRoot;
		BaseNavigator testObject = new BaseNavigator("Test",stub,true);
		
		testObject.getRoute().add(stubTarget);
		
		assertEquals(stubTarget,testObject.getNextWayPoint());
	}
	

	@Tag("Shuttle")
	@Tag("fast")
	@Test
	void testGetNextWayPoint_currentPointerIsLastWayPoint_shouldBeFirstElement() {
		FakeSpacecraft stub = new FakeSpacecraft();
		SpaceObject stubRoot = fakeStar(0,0);
		SpaceObject stubTarget = fakeStar(0,0);
		stub.parent=stubRoot;
		BaseNavigator testObject = new BaseNavigator("Test",stub,true);
		
		testObject.getRoute().add(stubTarget);
		testObject.commandLaunch(); // Move the Pointer 
		
		assertEquals(stubRoot,testObject.getNextWayPoint());
	}
	

	@Tag("Shuttle")
	@Tag("fast")
	@Test
	void testRebuildShuttle_shouldBeRebuild() {
		FakeSpacecraft stub = new FakeSpacecraft();
		BaseNavigator testObject = new BaseNavigator("Test",stub,true);
		
		testObject.rebuildShuttle();
		
		assertTrue(stub.rebuild);
	}
	

	@Tag("Shuttle")
	@Tag("fast")
	@Test
	void testIsInGoodLaunchPosition_shuttleIsDead_shouldBeFalse() {
		FakeSpacecraft stub = new FakeSpacecraft();
		SpaceObject stubTarget = fakeStar(0,0);
		BaseNavigator testObject = new BaseNavigator("Test",stub,true);

		stub.dead=true;
		
		assertFalse(testObject.isInGoodLaunchPosition(stubTarget));
	}
	

	@Tag("Shuttle")
	@Tag("fast")
	@Test
	void testRemove_shouldBeOrphaned() {
		FakeSpacecraft stub = new FakeSpacecraft();
		BaseNavigator testObject = new BaseNavigator("Test",stub,true);
		
		testObject.remove();
		
		assertTrue(testObject.isOrphan());
	}
	

	@Tag("Shuttle")
	@Tag("fast")
	@Test
	void testCommandLaunch_allGood_ShuttleShouldBeLaunched() {
		FakeSpacecraft stub = new FakeSpacecraft();
		SpaceObject stubRoot = fakeStar(0,0);
		SpaceObject stubTarget = fakeStar(0,0);
		stub.parent=stubRoot;
		BaseNavigator testObject = new BaseNavigator("Test",stub,true);
		
		testObject.getRoute().add(stubTarget);
		testObject.commandLaunch(); // Move the Pointer 
		
		assertTrue(stub.launched);
	}
	

	@Tag("Shuttle")
	@Tag("fast")
	@Test
	void testCommandLaunch_threeWayPoints_shouldHaveNewWayPoint() {
		FakeSpacecraft stub = new FakeSpacecraft();
		SpaceObject stubRoot = fakeStar(0,0);
		SpaceObject stubTarget = fakeStar(0,0);
		SpaceObject finalTarget = fakeStar(0,0);
		stub.parent=stubRoot;
		BaseNavigator testObject = new BaseNavigator("Test",stub,true);
		
		testObject.getRoute().add(stubTarget);
		testObject.getRoute().add(finalTarget);
		testObject.commandLaunch(); // Move the Pointer 
		
		assertEquals(finalTarget,testObject.getNextWayPoint());
	}
	
	
	@Tag("Shuttle")
	@Tag("fast")
	@Test
	void testUpdate_shuttleIsDeadAndRespawns_shuttleIsRebuild() {
		FakeSpacecraft stub = new FakeSpacecraft();
		BaseNavigator testObject = new BaseNavigator("Test",stub,true);
		
		stub.dead=true;
		
		testObject.update();
		
		assertTrue(stub.rebuild);
	}

	@Tag("Shuttle")
	@Tag("fast")
	@Test
	void testUpdate_shuttleIsDeadAndDoesNotRespawn_shuttleStaysDead() {
		FakeSpacecraft stub = new FakeSpacecraft();
		BaseNavigator testObject = new BaseNavigator("Test",stub,false);
		
		stub.dead=true;
		
		testObject.update();
		
		assertFalse(stub.rebuild);
	}

	@Tag("Shuttle")
	@Tag("fast")
	@Test
	void testUpdate_shuttleIsAlive_shuttleIsNotBeRebuild() {
		FakeSpacecraft stub = new FakeSpacecraft();
		BaseNavigator testObject = new BaseNavigator("Test",stub,true);
		
		stub.dead=false;
		
		testObject.update();
		
		assertFalse(stub.rebuild);
	}

	@Tag("Shuttle")
	@Tag("fast")
	@Test
	void testUpdate_shuttleIsFlying_NothingHappens() {
		FakeSpacecraft stub = new FakeSpacecraft();
		SpaceObject stubRoot = fakeStar(0,0);
		SpaceObject stubTarget = fakeStar(0,0);
		stub.parent=stubRoot;
		BaseNavigator testObject = new BaseNavigator("Test",stub,true);
		testObject.getRoute().add(stubTarget);
		
		stub.state=SpacecraftState.FLYING;
		
		testObject.update();
		
		assertEquals(SpacecraftState.FLYING,stub.getState());
		assertEquals(stubTarget,testObject.getNextWayPoint());
		assertFalse(stub.launched);
	}

	@Tag("Shuttle")
	@Tag("fast")
	@Test
	void testUpdate_shuttleIsOrbiting_OrbitingForLongTime_ButIsNotInGoodLaunchPosition_shouldNotBeLaunched() {
		FakeSpacecraft stub = new FakeSpacecraft();
		SpaceObject stubRoot = fakeStar(0,0);
		SpaceObject stubTarget = fakeStar(0,0);
		stub.parent=stubRoot;
		BaseNavigator testObject = new BaseNavigator("Test",stub,true);
		testObject.getRoute().add(stubTarget);
		
		stub.state=SpacecraftState.ORBITING;
		for(int i=0;i<5000;i++)
			testObject.update();
		
		assertFalse(stub.launched);
	}
	
}
