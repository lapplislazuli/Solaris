package core;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import geom.AbsolutePoint;
import space.core.Planet;
import space.core.Star;

class DistanceTest {
	
	static Star starOne;
	static Planet closeStar,mediumStar,farStar;
	
	@BeforeEach
	void initSpaceObjects(){
		starOne= new Star("anker", null, new AbsolutePoint( 0, 0),0);
		closeStar = (new Planet.Builder("a", starOne))
				.size(0)
				.distance(0)
				.build();
		mediumStar =(new Planet.Builder("b", starOne))
				.size(0)
				.distance(50)
				.build();
		farStar = (new Planet.Builder("c", starOne))
				.size(0)
				.distance(100)
				.build();
	}
	
	@Test
	void positiveDistance() {
		assertEquals(50, closeStar.distanceTo(mediumStar));
		assertEquals(50, mediumStar.distanceTo(farStar));
		assertEquals(100,closeStar.distanceTo(farStar));
	}
	
	@Test
	void negativeDistance() {
		assertEquals(50, closeStar.distanceTo(mediumStar));
		assertEquals(50, mediumStar.distanceTo(farStar));
	}
	
	@Test
	void testDistanceSymmetry() {
		assertTrue(closeStar.distanceTo(mediumStar)==mediumStar.distanceTo(closeStar));
		assertTrue(farStar.distanceTo(mediumStar)==mediumStar.distanceTo(farStar));
	}
	
	@Test
	void nullDistance() {
		Star starTwo= new Star("anker", null, new AbsolutePoint( 0, 0),0);
		assertEquals(0,starOne.distanceTo(starTwo));
	}
}
