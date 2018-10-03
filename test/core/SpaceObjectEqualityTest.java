/**
 * @Author Leonhard Applis
 * @Created 03.10.2018
 * @Package core
 */
package core;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import geom.AbsolutePoint;
import space.core.Planet;
import space.core.Star;

class SpaceObjectEqualityTest {

	static Star aOne, aTwo, aThree, b;
	Planet cOne,cTwo;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		aOne = (new Star.Builder("a"))
				.center(new AbsolutePoint(100,100))
				.radious(10)
				.build();
		aTwo = (new Star.Builder("a"))
				.center(new AbsolutePoint(100,100))
				.radious(10)
				.build();
		aThree = (new Star.Builder("a"))
				.center(new AbsolutePoint(100,100))
				.radious(10)
				.build();
		
		b = (new Star.Builder("b"))
				.center(new AbsolutePoint(120,110))
				.radious(10)
				.build();
	}
	
	@BeforeEach
	void setupPlanetsAndMove() {
		cOne= (new Planet.Builder("c", aOne))
				.size(1)
				.distance(100)
				.speed(Math.PI)
				.build();
		cTwo= (new Planet.Builder("c", aOne))
				.size(1)
				.distance(100)
				.speed(Math.PI)
				.build(); 
	}

	@Test
	void testPositiveEqualsObject() {
		assertTrue(aOne.equals(aTwo));
		
		assertTrue(cOne.equals(cTwo));
	}

	
	@Test
	void testNegativeEquality() {
		assertFalse(aOne.equals(cOne));
		assertFalse(aOne.equals(b));
		assertFalse(cOne.equals(b));
	}
	
	@Test
	void testReflexivity() {
		assertTrue(aOne.equals(aOne));
	}
	
	@Test
	void testSymetry() {
		assertTrue(aOne.equals(aTwo)==aTwo.equals(aOne));
		assertTrue(cOne.equals(cTwo)==cTwo.equals(cOne));
	}
	
	@Test
	void TestTransitity() {
		assertTrue(aOne.equals(aTwo)==aTwo.equals(aThree));
	}
	
	@Test
	void TestEqualityAfterMove() {
		cOne.speed = 0;
		
		aOne.update(); // c#s moved
		
		assertFalse(cOne.equals(cTwo));
	}
}
