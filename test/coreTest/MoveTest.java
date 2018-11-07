package coreTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import geom.AbsolutePoint;
import space.core.Planet;
import space.core.Satellite;
import space.core.Star;

class MoveTest {

	static Star sun;
	static Planet planet;
	static Satellite satellite;
	
	@BeforeAll
	static void initSun() {
		sun=new Star("Sun",null,new AbsolutePoint(250,250),50);
	}
	
	@BeforeEach
	void resetSatelliteAndPlanet(){
		planet = (new Planet.Builder("A", sun))
				.size(50)
				.distance(250)
				.speed(Math.PI/2)
				.build();
		satellite= (new Satellite.Builder("B", planet))
				.size(50,50)
				.distance(250)
				.speed(-Math.PI/2)
				.build();
		
	}
	
	@Test
	void testInitSpaceObjects() {
		assertEquals(250,sun.center.getX());assertEquals(250,sun.center.getY());
		
		Planet noSpeedPlanet= (new Planet.Builder("A", sun))
				.size(50)
				.distance(250)
				.speed(0)
				.build();
		//Check X-Y Koords
		assertEquals(250,planet.center.getX());
		assertEquals(500,planet.center.getY());
		assertEquals(250,satellite.center.getX());
		assertEquals(750,satellite.center.getY());
		assertEquals(250,noSpeedPlanet.center.getX());
		assertEquals(500,noSpeedPlanet.center.getY());
		//Check DegreeTo EachOther
		assertEquals(3*Math.PI/2, planet.degreeTo(sun));
		assertEquals(3*Math.PI/2, satellite.degreeTo(sun));
		assertEquals(3*Math.PI/2, noSpeedPlanet.degreeTo(sun));
		//assertEquals(3*Math.PI/2, a.degreeTo(b));
	}
	
	@Test
	void testSimpleMove() {
		//Move all Items, if they are movable
		sun.update();
		//Star not moved, Planet right to Star, Satellite Left to Planet in Star
		assertEquals(250,sun.center.getX());
		assertEquals(250,sun.center.getY());
		assertEquals(250,planet.center.getX());
		assertEquals(500,planet.center.getY());
		
		sun.update();
		//Star not moved, Planet beyond Star
		assertEquals(250,sun.center.getX());
		assertEquals(250,sun.center.getY());
		assertEquals(0,planet.center.getX());
		assertEquals(250,planet.center.getY());
	}
	@Test
	void testNegativeSpeedMovement() {
		sun.update();
		assertEquals(250,satellite.center.getX());
		assertEquals(250,satellite.center.getY());
		sun.update();
		assertEquals(-250,satellite.center.getX());
		assertEquals(250,satellite.center.getY());
	}
	
	@Test
	void testNoSpeed() {
		//Add a Planet to move with 90° per update
		Planet planet = (new Planet.Builder("A", sun))
				.size(50)
				.distance(250)
				.speed(0)
				.build();
		
		for(int i =0;i<10;i++) {
			sun.update();
			assertEquals(500,planet.center.getX());
			assertEquals(250,planet.center.getY());
		}
	}
	
	@Test
	public void testRelativePosition() {
		sun.update();
		
		assertEquals(3*Math.PI/2,planet.degreeTo(sun));
		assertEquals(Math.PI/2,planet.relativePos);
		assertEquals(3*Math.PI/2,planet.degreeTo(sun));
		
		sun.update();
		assertEquals(0,planet.degreeTo(sun));
		assertEquals(Math.PI,planet.relativePos);
		assertEquals(Math.PI, sun.degreeTo(planet));
	}
	
	@Test
	void testNegativeRelativePosition() {		
		sun.update();
		assertEquals(Math.PI/2,satellite.degreeTo(planet));
		assertEquals(3*Math.PI/2,satellite.relativePos);
		sun.update();
		assertEquals(0,satellite.degreeTo(planet));
		assertEquals(Math.PI,satellite.relativePos);
	}
	
	@Test
	void testPositiveIsFasterThanMe() {
		Planet fasterPlanet = (new Planet.Builder("Faster", sun))
				.size(50)
				.distance(250)
				.speed(Math.PI)
				.build();
		
		assertTrue(planet.isFasterThanMe(fasterPlanet));
	}
	
	@Test
	void testNegativeIsFasterThanMe() {
		Planet slowerPlanet = (new Planet.Builder("Slower", sun))
				.size(50)
				.distance(250)
				.speed(Math.PI/4)
				.build();
		
		assertFalse(planet.isFasterThanMe(slowerPlanet));
	}
	
	@Test
	void testIsFasterThanMeWithSpaceObject() {
		assertFalse(planet.isFasterThanMe(sun));
		assertFalse(satellite.isFasterThanMe(sun));
	}
	
	@Test
	void testPositivemovesInSameDirection() {
		Planet sameDirectionPlanet = (new Planet.Builder("Faster", sun))
				.size(50)
				.distance(250)
				.speed(Math.PI)
				.build();
		
		assertTrue(planet.movesInSameDirection(sameDirectionPlanet));
	}
	
	@Test
	void testNegativeMovesInSameDirection() {
		assertFalse(planet.movesInSameDirection(satellite));
	}
	
	@Test
	void testMovesInSameDirectionWithSpaceObject() {
		assertFalse(planet.movesInSameDirection(sun));
		assertFalse(satellite.movesInSameDirection(sun));
	}
	
	
	
}
