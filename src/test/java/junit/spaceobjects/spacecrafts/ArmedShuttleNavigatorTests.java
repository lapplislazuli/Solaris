package junit.spaceobjects.spacecrafts;


import static junit.testhelpers.FakeSpaceObjectFactory.fakeStar;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import geom.AbsolutePoint;
import interfaces.spacecraft.Spacecraft;
import junit.fakes.FakeSpacecraft;
import junit.testhelpers.FakeSpaceObjectFactory;
import logic.manager.ManagerRegistry;
import space.core.SpaceObject;
import space.spacecrafts.navigators.ArmedShuttleNavigator;
import space.spacecrafts.ships.Spaceshuttle;

class ArmedShuttleNavigatorTests {

	@BeforeEach
	void initManagers() {
		ManagerRegistry.getInstance();
	}
	
	@AfterEach
	void resetManagers() {
		ManagerRegistry.reset();
	}
	
	@Test
	void testConstructor_isNotPlayer() {
		Spacecraft stub = new FakeSpacecraft();
		
		ArmedShuttleNavigator testObject = new ArmedShuttleNavigator("Test",stub,true);
		
		assertFalse(testObject.isPlayer());
	}

	@Test
	void testConstructor_doesNotAutoAttack() {
		Spacecraft stub = new FakeSpacecraft();
		
		ArmedShuttleNavigator testObject = new ArmedShuttleNavigator("Test",stub,true);
		
		assertFalse(testObject.doesAutoAttack());
	}
	
	@Test
	void testPlayerConstructor_isPlayer() {
		Spacecraft stub = new FakeSpacecraft();
		
		ArmedShuttleNavigator testObject = ArmedShuttleNavigator.PlayerNavigator("Test",stub);

		assertTrue(testObject.isPlayer());
	}
	
	@Test
	void testPlayerConstructor_doesNotAutoAttack() {
		Spacecraft stub = new FakeSpacecraft();
		
		ArmedShuttleNavigator testObject = ArmedShuttleNavigator.PlayerNavigator("Test",stub);

		assertFalse(testObject.doesAutoAttack());
	}
	
	@Test
	void testRegisterManuallyInPlayerManager_isPlayer() {
		Spacecraft stub = new FakeSpacecraft();
		
		ArmedShuttleNavigator testObject = 
				new ArmedShuttleNavigator("Test",stub,true);
		
		ManagerRegistry.getPlayerManager().registerPlayerNavigator(testObject);
		
		assertTrue(testObject.isPlayer());
	}
	
	@Test
	void testAttackPoint_shouldAttack() {
		FakeSpacecraft stub = new FakeSpacecraft();
		ArmedShuttleNavigator testObject = 
				new ArmedShuttleNavigator("Test",stub,true);
		
		testObject.attack(new AbsolutePoint(0,0));
		
		assertTrue(stub.attacked);
	}
	
	@Test
	void testAttackSpaceObject_shouldAttack() {
		FakeSpacecraft stub = new FakeSpacecraft();
		ArmedShuttleNavigator testObject = 
				new ArmedShuttleNavigator("Test",stub,true);
		
		testObject.attack(fakeStar(0,0));
		
		assertTrue(stub.attacked);
	}
	
	@Test
	void testRebuild_rebuildShuttleShouldBeArmedSpacecraft() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle stubShip = new Spaceshuttle("Stub",root,2,10,Math.PI/20);
		ArmedShuttleNavigator testObject = 
				new ArmedShuttleNavigator("Test",stubShip,true);
		
		testObject.rebuildShuttle();
		
		assertTrue(testObject.getShuttle() instanceof Spacecraft);
	}
	
	@Test
	void testAutoAttack_NothingToAttack_doesNotAttack() {
		FakeSpacecraft stub = new FakeSpacecraft();
		ArmedShuttleNavigator testObject = 
				new ArmedShuttleNavigator("Test",stub,true);
		
		stub.nearestTarget=Optional.empty();
		
		testObject.autoAttack();
		
		assertFalse(stub.attacked);
	}
	
	@Test
	void testAutoAttack_hasTarget_shouldAttack() {
		FakeSpacecraft stub = new FakeSpacecraft();
		ArmedShuttleNavigator testObject = 
				new ArmedShuttleNavigator("Test",stub,true);
		
		stub.nearestTarget=Optional.of(fakeStar(0,0));
		
		testObject.autoAttack();
		
		assertTrue(stub.attacked);
	}
	
	@Test
	void testUpdate_isDead_doesNotAttack() {
		FakeSpacecraft stub = new FakeSpacecraft();
		stub.parent = fakeStar(0,0);
		ArmedShuttleNavigator testObject = 
				new ArmedShuttleNavigator("Test",stub,true);
		
		stub.dead=true;

		SpaceObject routePoint = FakeSpaceObjectFactory.fakeStar(0,0);
		testObject.getRoute().add(routePoint);
		
		testObject.update();
		
		assertFalse(stub.attacked);
	}
	
	@Test
	void testUpdate_doesNotAutoAttack_doesNotAttack() {
		FakeSpacecraft stub = new FakeSpacecraft();
		ArmedShuttleNavigator testObject = 
				new ArmedShuttleNavigator("Test",stub,false);
		
		testObject.update();
		
		assertFalse(stub.attacked);
	}
	
	@Test
	void testUpdate_isAliveAndAutoAttacks_shouldAttack() {
		FakeSpacecraft stub = new FakeSpacecraft();
		stub.nearestTarget=Optional.of(fakeStar(0,0));
		
		ArmedShuttleNavigator testObject = 
				new ArmedShuttleNavigator("Test",stub,true);
		testObject.setAutoAttack(true);
		
		testObject.update();
		
		assertTrue(stub.attacked);
	}
	
	
	@Test
	void testToggleAutoAttack_wasOn_shouldBeOff() {
		FakeSpacecraft stub = new FakeSpacecraft();
		ArmedShuttleNavigator testObject = 
				new ArmedShuttleNavigator("Test",stub,false);

		testObject.setAutoAttack(true);
		
		testObject.toggleAutoAttack();
		
		assertFalse(testObject.doesAutoAttack());		
	}
	
	@Test
	void testToggleAutoAttack_wasOff_shouldBeOn() {
		FakeSpacecraft stub = new FakeSpacecraft();
		ArmedShuttleNavigator testObject = 
				new ArmedShuttleNavigator("Test",stub,true);
		
		testObject.setAutoAttack(false);
		
		testObject.toggleAutoAttack();
		
		assertTrue(testObject.doesAutoAttack());
	}
}
