package shuttle;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import drawing.JavaFXDrawingInformation;
import geom.AbsolutePoint;
import interfaces.spacecraft.ArmedSpacecraft;
import javafx.scene.paint.Color;
import space.core.Planet;
import space.core.SpaceObject;
import space.core.Star;
import space.spacecrafts.navigators.ArmedShuttleNavigator;
import space.spacecrafts.ships.ArmedSpaceShuttle;

@SuppressWarnings("restriction")
class NavigatorTests {
	
	ArmedShuttleNavigator nav;
	ArmedSpaceShuttle shuttle;
	
	SpaceObject sun;
	SpaceObject earth;

	@BeforeEach
	void setUp() throws Exception {
		sun = new Star("Sun",new JavaFXDrawingInformation(Color.ORANGE),new AbsolutePoint(600,400),30);
		earth = (new Planet.Builder("Earth", sun))
				.size(13)
				.distance(150)
				.levelOfDetail(10)
				.speed(Math.PI/2000)
				.rotationSpeed(Math.PI*2/365)
				.color(Color.DARKCYAN)
				.build();
		
		shuttle = new ArmedSpaceShuttle("Martians",sun,3,50,Math.PI/100);
		
		nav = new ArmedShuttleNavigator("Overlord",shuttle,true);
		nav.getRoute().add(earth);
	}

	@Test
	void testRebuildShuttle() {
		shuttle.destruct();
		assertTrue(shuttle.isDead());
		
		nav.update(); //Should rebuild shuttle
		
		assertFalse(nav.getShuttle().isDead());
		assertNotEquals(shuttle,nav.getShuttle());
		assertTrue(nav.getShuttle() instanceof ArmedSpacecraft);
	}

	@Test
	void testDoesAutoAttack() {
		assertFalse(nav.doesAutoAttack());
	}

	@Test
	void testSetAutoAttack() {
		nav.setAutoAttack(true);
		assertTrue(nav.doesAutoAttack());
	}

	@Test
	void testToggleAutoAttack() {
		nav.toggleAutoAttack();
		assertTrue(nav.doesAutoAttack());
		nav.toggleAutoAttack();
		assertFalse(nav.doesAutoAttack());
	}

	@Test
	void testClearRoute() {
		nav.clearRoute();
		
		assertFalse(nav.getRoute().isEmpty());
		assertEquals(1,nav.getRoute().size());
		assertEquals(sun,nav.getRoute().get(0));
	}

	@Test
	void testGetNextWayPoint() {
		assertEquals(earth,nav.getNextWayPoint());
	}

	@Test
	void testGetShuttle() {
		assertEquals(shuttle,nav.getShuttle());
	}

	@Test
	void testGetRoute() {
		List<SpaceObject> simRoute=new LinkedList<SpaceObject>();
		simRoute.add(sun);
		simRoute.add(earth);
		
		assertEquals(simRoute,nav.getRoute());
	}

}
