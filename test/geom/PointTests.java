package geom;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import interfaces.geom.Point;

class PointTests {
	
	
	private static AbsolutePoint first2D,second2D; 
	private static AbsolutePoint first3D; //3 Dimensional Point

	@BeforeEach
	void setUp() throws Exception {
		first2D= new AbsolutePoint(0,0);
		second2D= new AbsolutePoint(50,50);
		first3D= new AbsolutePoint(0,0,100);
	}

	@Test
	void testDegreeTo() {
		second2D.move(-50, 0);
		assertEquals(Math.PI/2,first2D.degreeTo(second2D));
		assertEquals(3*Math.PI/2,second2D.degreeTo(first2D));
		
		second2D.move(50, -50);
		assertEquals(0,first2D.degreeTo(second2D));
		assertEquals(Math.PI, second2D.degreeTo(first2D));
	}

	@Test
	void testDistance2D() {
		//Check correctness of formula
		assertEquals(100, first2D.distanceTo(first3D));
		assertEquals(Math.sqrt(2)*50,first2D.distanceTo(second2D));
	}
	@Test
	void testDistance3D() {
		assertEquals(Math.sqrt(15000),first3D.distanceTo(second2D));
	}
	
	@Test
	void testNegativeDistance() {
		//x and y would be negative, but distance has to be positive all the time
		first2D.move(100, 100);
		assertEquals(Math.sqrt(2)*50,first2D.distanceTo(second2D));
	}
	
	@Test
	void testDistanceToSymmetry() {
		assertTrue(first2D.distanceTo(second2D)==second2D.distanceTo(first2D));
		assertTrue(first2D.distanceTo(first3D)==first3D.distanceTo(first2D));
		assertTrue(first3D.distanceTo(second2D)==second2D.distanceTo(first3D));
	}
	
	@Test
	void testMoveIntInt() {
		first2D.move(50, 50);
		assertEquals(50,first2D.x);
		assertEquals(50,first2D.y);
		assertEquals(0,first2D.distanceTo(second2D));
		
		first2D.move(-50, -50);
		assertEquals(0,first2D.x);
		assertEquals(0,first2D.y);
		assertEquals(Math.sqrt(2)*50,first2D.distanceTo(second2D));
	}

	@Test
	void testMovePositive3D() {
		first2D.move(0, 0,100);
		assertEquals(0,first2D.x);
		assertEquals(0,first2D.y);
		assertEquals(100,first2D.z);
	}
	
	@Test
	void testMoveNegative3D() {
		first2D.move(0, 0, -100);
		assertEquals(0,first2D.x);
		assertEquals(0,first2D.y);
		assertEquals(-100,first2D.z);
	}
	@Test
	void testClone() {
		AbsolutePoint clone= first2D.clone();
		first2D.move(50, 50);
		assertEquals(0,clone.x);
		assertEquals(0,clone.y);
	}
	
	@Test 
	void testPositiveSamePosition() {
		Point samePoint = first2D.clone();
		
		assertTrue(samePoint.samePosition(first2D));
	}
	@Test
	void testNegativeSamePosition() {
		assertFalse(second2D.samePosition(first2D));
	}
	
	@Test 
	void testPositiveHashCode() {
		Point samePoint = first2D.clone();
		
		assertEquals(first2D.hashCode(),samePoint.hashCode());
	}
	@Test
	void testNegativeHashCode() {
		assertFalse(second2D.hashCode()==first2D.hashCode());
	}
	
	@Test
	void testSamePointSymetry() {
		Point samePoint = first2D.clone();
		
		assertTrue(samePoint.samePosition(first2D)== first2D.samePosition(samePoint));
		
		assertTrue(first2D.samePosition(second2D) == second2D.samePosition(first2D));
	}

}
