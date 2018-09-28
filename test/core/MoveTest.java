package core;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import geom.AbsolutePoint;
import space.core.Planet;
import space.core.Satellite;
import space.core.Star;

class MoveTest {

	static Star sun;
	
	@BeforeAll
	static void initSun() {
		sun=new Star("Sun",null,new AbsolutePoint(250,250),50);
	}
	
	@Test
	void InitSpaceObjects() {
		
		//Stars should not move!
		sun.update();
		assertEquals(250,sun.center.getX());assertEquals(250,sun.center.getY());
		//Add a Planet to move with 90° per update
		//Add a Satellite to Planet to move with -90° per update
		Planet planet = (new Planet.Builder("A", sun))
				.size(50)
				.distance(250)
				.speed(Math.PI/2)
				.build();
		Satellite satellite= (new Satellite.Builder("B", planet))
				.size(50,50)
				.distance(250)
				.speed(-Math.PI/2)
				.build();
		
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
	void SimpleMove() {
		sun.update();
		assertEquals(250,sun.center.getX());assertEquals(250,sun.center.getY());
		//Add a Planet to move with 90° per update
		//Add a Satellite to Planet to move with -90° per update
		Planet planet = (new Planet.Builder("A", sun))
				.size(50)
				.distance(250)
				.speed(Math.PI/2)
				.build();
		
		Satellite satellite= (new Satellite.Builder("B", planet))
				.size(50,50)
				.distance(250)
				.speed(-Math.PI/2)
				.build();
		
		//Move all Items, if they are movable
		sun.update();
		//Star not moved, Planet right to Star, Satellite Left to Planet in Star
		assertEquals(250,sun.center.getX());
		assertEquals(250,sun.center.getY());
		assertEquals(250,planet.center.getX());
		assertEquals(0,planet.center.getY());
		assertEquals(250,satellite.center.getX());
		assertEquals(250,satellite.center.getY());
		
		sun.update();
		//Star not moved, Planet beyond Star, Satellite beyond Planet
		assertEquals(250,sun.center.getX());
		assertEquals(250,sun.center.getY());
		assertEquals(0,planet.center.getX());
		assertEquals(250,planet.center.getY());
		assertEquals(-250,satellite.center.getX());
		assertEquals(250,satellite.center.getY());
	}
	@Test
	void NoSpeed() {
		//Add a Planet to move with 90° per update
		Planet planet = (new Planet.Builder("A", sun))
				.size(50)
				.distance(250)
				.speed(0)
				.build();
		//Add a Satellite to Planet to move with -90° per update
		Satellite b= (new Satellite.Builder("B", planet))
				.size(50,50)
				.distance(250)
				.speed(0)
				.build();
		
		for(int i =0;i<10;i++) {
			sun.update();
			assertEquals(250,sun.center.getX());
			assertEquals(250,sun.center.getY());
			assertEquals(500,planet.center.getX());
			assertEquals(250,planet.center.getY());
			assertEquals(750,b.center.getX());
			assertEquals(250,b.center.getY());
		}
	}
	
	@Test
	public void RelativePosition() {
		//Add a Planet to move with 90° per update
		Planet planet = (new Planet.Builder("A", sun))
				.size(50)
				.distance(250)
				.speed(Math.PI/2)
				.build();
		//Add a Satellite to Planet to move with -90° per update
		Satellite satellite=(new Satellite.Builder("B", planet))
				.size(50,50)
				.distance(250)
				.speed(-Math.PI/2)
				.build();
		
		//Move all Items, if they are movable
		sun.update();
		
		assertEquals(Math.PI/2,planet.degreeTo(sun));
		assertEquals(5*Math.PI/2,planet.relativePos);
		assertEquals(Math.PI/2,planet.degreeTo(sun));
		assertEquals(3*Math.PI/2,satellite.degreeTo(planet));
		assertEquals(3*Math.PI/2,satellite.relativePos);
		
		sun.update();
		assertEquals(Math.PI,planet.degreeTo(sun));
		assertEquals(Math.PI,planet.relativePos);
		
		assertEquals(Math.PI,satellite.degreeTo(planet));
		assertEquals(3*Math.PI,satellite.relativePos);
	}
}
