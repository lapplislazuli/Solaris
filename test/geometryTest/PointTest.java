package geometryTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import geom.Point;

class PointTest {
	
	
	private static Point a,b; 
	private static Point c; //3 Dimensional Point

	@BeforeEach
	void setUp() throws Exception {
		a= new Point(0,0);
		b= new Point(50,50);
		c= new Point(0,0,100);
	}

	@Test
	void testDegreeTo() {
		b.move(-50, 0);
		assertEquals(Math.PI/2,a.degreeTo(b));
		assertEquals(3*Math.PI/2,b.degreeTo(a));
		
		b.move(50, -50);
		assertEquals(Math.PI,a.degreeTo(b));
		assertEquals(2*Math.PI, b.degreeTo(a));
	}

	@Test
	void testDistanceTo() {
		//Check correctness of formula
		assertEquals(100, a.distanceTo(c));
		assertEquals(Math.sqrt(2)*50,a.distanceTo(b));
		assertEquals(Math.sqrt(15000),c.distanceTo(b));
		//Check Symetrie
		assertTrue(a.distanceTo(b)==b.distanceTo(a));
		assertTrue(a.distanceTo(c)==c.distanceTo(a));
		assertTrue(c.distanceTo(b)==b.distanceTo(c));
		//check negative Distance for positiveness
		a.move(100, 100);
		assertEquals(Math.sqrt(2)*50,a.distanceTo(b));
	}

	@Test
	void testMoveIntInt() {
		a.move(50, 50);
		assertEquals(50,a.x);
		assertEquals(50,a.y);
		assertEquals(0,a.distanceTo(b));
		
		a.move(-50, -50);
		assertEquals(0,a.x);
		assertEquals(0,a.y);
		assertEquals(Math.sqrt(2)*50,a.distanceTo(b));
	}

	@Test
	void testMoveIntIntInt() {
		a.move(0, 0,100);
		assertEquals(0,a.x);
		assertEquals(0,a.y);
		assertEquals(100,a.z);
		assertEquals(0,a.distanceTo(c));
		
		a.move(0, 0, -100);
		assertEquals(0,a.x);
		assertEquals(0,a.y);
		assertEquals(0,a.z);
		assertEquals(100,a.distanceTo(c));
	}

	@Test
	void testClone() {
		Point clone= a.clone();
		a.move(50, 50);
		assertEquals(0,clone.x);
		assertEquals(0,clone.y);
	}

}
