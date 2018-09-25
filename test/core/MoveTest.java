package core;

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
		Planet a = new Planet("A",sun,null,50,250,Math.PI/2);
		Satellite b= new Satellite("B",a,50,250,-Math.PI/2);
		Planet c= new Planet("C",a,null,50,250,0);
		//Check X-Y Koords
		assertEquals(250,a.center.x);
		assertEquals(500,a.center.y);
		assertEquals(250,b.center.x);
		assertEquals(750,b.center.y);
		assertEquals(250,c.center.x);
		assertEquals(750,c.center.y);
		//Check DegreeTo EachOther
		assertEquals(3*Math.PI/2, a.degreeTo(sun));
		assertEquals(3*Math.PI/2, b.degreeTo(sun));
		assertEquals(3*Math.PI/2, c.degreeTo(sun));
		//assertEquals(3*Math.PI/2, a.degreeTo(b));
		
	}
	
	@Test
	void SimpleMove() {
		sun.update();
		assertEquals(250,sun.center.x);assertEquals(250,sun.center.y);
		//Add a Planet to move with 90° per update
		//Add a Satellite to Planet to move with -90° per update
		Planet planet = new Planet("A",sun,null,50,250,Math.PI/2);
		Satellite satellite= new Satellite("B",planet,50,250,-Math.PI/2);
		
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
		Planet a = new Planet("A",sun,null,50,250,0);
		//Add a Satellite to Planet to move with -90° per update
		Satellite b= new Satellite("B",a,50,250,0);
		
		for(int i =0;i<10;i++) {
			sun.update();
			assertEquals(250,sun.center.x);
			assertEquals(250,sun.center.y);
			assertEquals(500,a.center.x);
			assertEquals(250,a.center.y);
			assertEquals(750,b.center.x);
			assertEquals(250,b.center.y);
		}
	}
	
	@Test
	public void RelativePosition() {
		//Add a Planet to move with 90° per update
		Planet planet = new Planet("A",sun,null,50,250,Math.PI/2);
		//Add a Satellite to Planet to move with -90° per update
		Satellite satellite= new Satellite("B",planet,50,50,-Math.PI/2);
		
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
