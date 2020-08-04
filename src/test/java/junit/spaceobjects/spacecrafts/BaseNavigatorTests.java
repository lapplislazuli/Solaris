package junit.spaceobjects.spacecrafts;



import static junit.testhelpers.FakeSpaceObjectFactory.fakeStar;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import geom.AbsolutePoint;
import interfaces.spacecraft.Spacecraft;
import interfaces.spacecraft.SpacecraftState;
import junit.fakes.FakeSpacecraft;
import junit.testhelpers.FakeSpaceObjectFactory;
import logic.manager.ManagerRegistry;
import space.core.MovingSpaceObject;
import space.core.Planet;
import space.core.SpaceObject;
import space.core.Star;
import space.spacecrafts.navigators.BaseNavigator;
import space.spacecrafts.ships.Spaceshuttle;

class BaseNavigatorTests {

	@BeforeEach
	void initManagerRegistry() {
		ManagerRegistry.getInstance();
	}
	@AfterEach
	void resteManagerRegistry() {
		ManagerRegistry.reset();
	}
	
	@Tag("Shuttle")
	@Tag("fast")
	@Test
	void testConstructor_shouldBeBuild() {
		FakeSpacecraft stub = new FakeSpacecraft();
		BaseNavigator testObject = new BaseNavigator("Test",stub,true);
		
		testObject.toString();
		
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
		stub.parent = fakeStar(0,0);
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
	@Tag("Approximation")
	@Test
	void testUpdate_shuttleIsDeadAndRespawns_shuttleIsRebuild() {
		FakeSpacecraft stub = new FakeSpacecraft();
		
		stub.parent = fakeStar(0,0);
		
		BaseNavigator testObject = new BaseNavigator("Test",stub,true,500);

		stub.dead=true;


		testObject.update();
		
		for(int i=0;i<1000;i++)
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

	@Tag("Shuttle")
	@Tag("fast")
	@Tag("Integration")
	@Tag("Approximation")
	@Test
	void testUpdate_shuttleIsOrbiting_OrbitingForLongTime_shouldBeLaunched() {
		SpaceObject root = new Star("star",null,new AbsolutePoint(250,250),250);
		
		MovingSpaceObject target = new Planet.Builder("Flip", root).speed(Math.PI/80).distance(250).build();
	
		Spaceshuttle shuttle= new Spaceshuttle("shuttleOne",root,0,50,-Math.PI/400);
		root.update();//To move Planets
		
		
		BaseNavigator testObject = new BaseNavigator("Test",shuttle,true);
		testObject.getRoute().add(target);
		
		for(int i=0;i<5000;i++) {
			//Move planets and ship
			root.update();
			//Run Navigator Logic
			testObject.update();
		}
		
		assertEquals(target, shuttle.getParent());
	}
	

	@Tag("Shuttle")
	@Tag("fast")
	@Tag("UnitTest")
	@Test
	void testConstructor_isNotPlayerByDefault() {
		Spacecraft stub = new FakeSpacecraft();
		
		BaseNavigator testObject = new BaseNavigator("Test",stub,true);
		
		assertFalse(testObject.isActivePlayerNavigator());
	}
	
	@Tag("Shuttle")
	@Tag("fast")
	@Tag("UnitTest")
	@Test
	void testConstructor_doesNotAutoAttackByDefault() {
		Spacecraft stub = new FakeSpacecraft();
		
		BaseNavigator testObject = new BaseNavigator("Test",stub,true);
		
		assertFalse(testObject.doesAutoAttack());
	}
	
	@Tag("Shuttle")
	@Tag("fast")
	@Tag("UnitTest")
	@Tag("SideEffect")
	@Test
	void testRegisterManuallyInPlayerManager_isActivePlayer() {
		Spacecraft stub = new FakeSpacecraft();
		
		BaseNavigator testObject = 
				new BaseNavigator("Test",stub,true);
		
		ManagerRegistry.getPlayerManager().registerPlayerNavigator(testObject);
		
		assertTrue(testObject.isActivePlayerNavigator());
	}
	
	@Tag("Shuttle")
	@Tag("fast")
	@Tag("UnitTest")
	@Test
	void testAttackPoint_shouldAttack() {
		FakeSpacecraft stub = new FakeSpacecraft();
		BaseNavigator testObject = 
				new BaseNavigator("Test",stub,true);
		
		testObject.attack(new AbsolutePoint(0,0));
		
		assertTrue(stub.attacked);
	}
	@Tag("Shuttle")
	@Tag("fast")
	@Tag("UnitTest")
	@Test
	void testAttackSpaceObject_shouldAttack() {
		FakeSpacecraft stub = new FakeSpacecraft();
		BaseNavigator testObject = 
				new BaseNavigator("Test",stub,true);
		
		testObject.attack(fakeStar(0,0));
		
		assertTrue(stub.attacked);
	}
	
	@Tag("Shuttle")
	@Tag("fast")
	@Tag("Integration")
	@Tag("SideEffect")
	@Test
	void testRebuild_rebuildShuttleShouldBeArmedSpacecraft() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle stubShip = new Spaceshuttle("Stub",root,2,10,Math.PI/20);
		BaseNavigator testObject = 
				new BaseNavigator("Test",stubShip,true);
		
		testObject.rebuildShuttle();
		
		assertTrue(testObject.getShuttle() instanceof Spacecraft);
	}

	@Tag("Shuttle")
	@Tag("fast")
	@Test
	void testAutoAttack_NothingToAttack_doesNotAttack() {
		FakeSpacecraft stub = new FakeSpacecraft();
		BaseNavigator testObject = 
				new BaseNavigator("Test",stub,true);
		
		stub.nearestTarget=Optional.empty();
		
		testObject.autoAttack();
		
		assertFalse(stub.attacked);
	}

	@Tag("Shuttle")
	@Tag("fast")
	@Tag("Integration")
	@Tag("SideEffect")
	@Test
	void testAutoAttack_hasTarget_shouldAttack() {
		FakeSpacecraft stub = new FakeSpacecraft();
		BaseNavigator testObject = 
				new BaseNavigator("Test",stub,true);
		
		stub.nearestTarget=Optional.of(fakeStar(0,0));
		
		testObject.autoAttack();
		
		assertTrue(stub.attacked);
	}

	@Tag("Shuttle")
	@Tag("fast")
	@Tag("Integration")
	@Test
	void testUpdate_isDead_doesNotAttack() {
		FakeSpacecraft stub = new FakeSpacecraft();
		stub.parent = fakeStar(0,0);
		BaseNavigator testObject = 
				new BaseNavigator("Test",stub,true);
		
		stub.dead=true;

		SpaceObject routePoint = FakeSpaceObjectFactory.fakeStar(0,0);
		testObject.getRoute().add(routePoint);
		
		testObject.update();
		
		assertFalse(stub.attacked);
	}

	@Tag("Shuttle")
	@Tag("fast")
	@Test
	void testUpdate_doesNotAutoAttack_doesNotAttack() {
		FakeSpacecraft stub = new FakeSpacecraft();
		BaseNavigator testObject = 
				new BaseNavigator("Test",stub,false);
		
		testObject.update();
		
		assertFalse(stub.attacked);
	}

	@Tag("Shuttle")
	@Tag("fast")
	@Tag("Integration")
	@Tag("SideEffect")
	@Test
	void testUpdate_isAliveAndAutoAttacks_shouldAttack() {
		FakeSpacecraft stub = new FakeSpacecraft();
		stub.isArmed = true;
		stub.nearestTarget=Optional.of(fakeStar(0,0));
		
		BaseNavigator testObject = 
				new BaseNavigator("Test",stub,true);
		testObject.setAutoAttack(true);
		
		testObject.update();
		
		assertTrue(stub.attacked);
	}
	

	@Tag("Shuttle")
	@Tag("fast")
	@Tag("UnitTest")
	@Test
	void testToggleAutoAttack_wasOn_shouldBeOff() {
		FakeSpacecraft stub = new FakeSpacecraft();
		BaseNavigator testObject = 
				new BaseNavigator("Test",stub,false);

		testObject.setAutoAttack(true);
		
		testObject.toggleAutoAttack();
		
		assertFalse(testObject.doesAutoAttack());		
	}

	@Tag("Shuttle")
	@Tag("fast")
	@Tag("UnitTest")
	@Test
	void testToggleAutoAttack_wasOff_shouldBeOn() {
		FakeSpacecraft stub = new FakeSpacecraft();
		BaseNavigator testObject = 
				new BaseNavigator("Test",stub,true);
		
		testObject.setAutoAttack(false);
		
		testObject.toggleAutoAttack();
		
		assertTrue(testObject.doesAutoAttack());
	}
}
