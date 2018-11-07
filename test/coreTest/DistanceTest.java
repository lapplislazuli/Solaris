package coreTest;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import geom.Point;
import space.core.Planet;
import space.core.Star;

class DistanceTest {
	
	@Test
	void positiveDistance() {
		Star anker= new Star("anker", null, new Point( 0, 0),0);
		Planet closeStar = (new Planet.Builder("a", anker))
				.size(0)
				.distance(0)
				.build();
		Planet mediumStar =(new Planet.Builder("b", anker))
				.size(0)
				.distance(50)
				.build();
		Planet farStar = (new Planet.Builder("c", anker))
				.size(0)
				.distance(100)
				.build();

		assertEquals(50, closeStar.distanceTo(mediumStar));
		assertEquals(50, mediumStar.distanceTo(farStar));
		assertEquals(true, closeStar.distanceTo(mediumStar)==mediumStar.distanceTo(closeStar));
		assertEquals(true, farStar.distanceTo(mediumStar)==mediumStar.distanceTo(farStar));
	}
	
	@Test
	void negativeDistance() {
		Star anker= new Star("anker", null, new Point(150, 0),0);
		Planet closeStar = (new Planet.Builder("a", anker))
				.size(0)
				.distance(0)
				.build();
		Planet mediumStar =(new Planet.Builder("b", anker))
				.size(0)
				.distance(-50)
				.build();
		Planet farStar = (new Planet.Builder("c", anker))
				.size(0)
				.distance(-100)
				.build();

		assertEquals(50, closeStar.distanceTo(mediumStar));
		assertEquals(50, mediumStar.distanceTo(farStar));
		assertEquals(true, closeStar.distanceTo(mediumStar)==mediumStar.distanceTo(closeStar));
		assertEquals(true, farStar.distanceTo(mediumStar)==mediumStar.distanceTo(farStar));
	}
	
	@Test
	void nullDistance() {
		Star anker1= new Star("anker", null, new Point( 0, 0),0);
		Star anker2= new Star("anker", null, new Point( 0, 0),0);
		assertEquals(0,anker1.distanceTo(anker2));
		assertEquals(true, anker1.distanceTo(anker2)==anker2.distanceTo(anker1));
	}
}
