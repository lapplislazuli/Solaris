/**
 * @Author Leonhard Applis
 * @Created 03.10.2018
 * @Package core
 */
package core;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import geom.AbsolutePoint;
import space.core.Planet;
import space.core.SpaceObject;
import space.core.Star;

class SpaceObjectEqualityTests {

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
	void testTransitity() {
		assertTrue(aOne.equals(aTwo)==aTwo.equals(aThree));
	}
	
	@Test
	void testEqualityAfterMove() {
		cOne.speed = 0;
		
		aOne.update(); // c#s moved
		
		assertFalse(cOne.equals(cTwo));
	}
	
	@Test
	void testAddSameItemToSet() {
		Set<SpaceObject> sOs= new HashSet<SpaceObject>();
		sOs.add(aOne); 
		sOs.add(aOne);
		assertEquals(1,sOs.size());
	}
	
	@Test
	void testAddSimiliarItemToSet() {
		Set<SpaceObject> sOs= new HashSet<SpaceObject>();
		sOs.add(aOne); 
		sOs.add(aTwo);
		assertEquals(1,sOs.size());
	}
	
	@Test
	void testAddDifferentItemsToSet() {
		Set<SpaceObject> sOs= new HashSet<SpaceObject>();
		sOs.add(aOne); 
		sOs.add(cOne);
		assertEquals(2,sOs.size());
	}
}
