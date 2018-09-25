package core;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import geom.Point;
import space.core.Planet;
import space.core.Star;

class DistanceTest {
	
	@Test
	void positiveDistance() {
		Star anker= new Star("anker", null, new Point( 0, 0),0);
		Planet closeStar = new Planet("a", anker, null, 0, 0, 0);
		Planet mediumStar = new Planet("b", anker, null, 0, 50, 0);
		Planet farStar = new Planet("c", anker, null, 0, 100, 0);
		
		assertEquals(50, closeStar.distanceTo(mediumStar));
		assertEquals(50, mediumStar.distanceTo(farStar));
		assertEquals(true, closeStar.distanceTo(mediumStar)==mediumStar.distanceTo(closeStar));
		assertEquals(true, farStar.distanceTo(mediumStar)==mediumStar.distanceTo(farStar));
	}
	
	@Test
	void negativeDistance() {
		Star anker= new Star("anker", null, new Point(150, 0),0);
		Planet closeStar = new Planet("a", anker, null, 0, 0, 0);
		Planet mediumStar = new Planet("b", anker, null, 0, -50, 0);
		Planet farStar = new Planet("c", anker, null, 0, -100, 0);
		
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
