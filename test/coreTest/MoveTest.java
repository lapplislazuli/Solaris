package coreTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import geom.Point;
import space.core.Planet;
import space.core.Satellite;
import space.core.Star;

class MoveTest {

	static Star sun;
	
	@BeforeAll
	static void initSun() {
		sun=new Star("Sun",null,new Point(250,250),50);
	}
	
	@Test
	void InitSpaceObjects() {
		
		//Stars should not move!
		sun.update();
		assertEquals(250,sun.center.x);assertEquals(250,sun.center.y);
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
		assertEquals(250,planet.center.x);
		assertEquals(500,planet.center.y);
		assertEquals(250,satellite.center.x);
		assertEquals(750,satellite.center.y);
		assertEquals(250,noSpeedPlanet.center.x);
		assertEquals(500,noSpeedPlanet.center.y);
		//Check DegreeTo EachOther
		assertEquals(3*Math.PI/2, planet.degreeTo(sun));
		assertEquals(3*Math.PI/2, satellite.degreeTo(sun));
		assertEquals(3*Math.PI/2, noSpeedPlanet.degreeTo(sun));
		//assertEquals(3*Math.PI/2, a.degreeTo(b));
		
	}
	
	@Test
	void SimpleMove() {
		sun.update();
		assertEquals(250,sun.center.x);assertEquals(250,sun.center.y);
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
		assertEquals(250,sun.center.x);
		assertEquals(250,sun.center.y);
		assertEquals(250,planet.center.x);
		assertEquals(0,planet.center.y);
		assertEquals(250,satellite.center.x);
		assertEquals(250,satellite.center.y);
		
		sun.update();
		//Star not moved, Planet beyond Star, Satellite beyond Planet
		assertEquals(250,sun.center.x);
		assertEquals(250,sun.center.y);
		assertEquals(0,planet.center.x);
		assertEquals(250,planet.center.y);
		assertEquals(-250,satellite.center.x);
		assertEquals(250,satellite.center.y);
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
			assertEquals(250,sun.center.x);
			assertEquals(250,sun.center.y);
			assertEquals(500,planet.center.x);
			assertEquals(250,planet.center.y);
			assertEquals(750,b.center.x);
			assertEquals(250,b.center.y);
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
