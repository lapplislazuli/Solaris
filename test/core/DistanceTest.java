package core;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import drawing.EmptyJFXDrawingInformation;
import geom.AbsolutePoint;
import space.core.Planet;
import space.core.Star;

import javafx.scene.paint.Color;

@SuppressWarnings("restriction")
class DistanceTest {
	
	static Star starOne;
	static Planet closeStar,mediumStar,farStar;
	
	@BeforeEach
	void initSpaceObjects(){
		starOne= new Star("anker", new EmptyJFXDrawingInformation(), new AbsolutePoint( 0, 0),0);
		closeStar = (new Planet.Builder("a", starOne))
				.color(Color.INDIANRED)
				.size(0)
				.distance(0)
				.build();
		mediumStar =(new Planet.Builder("b", starOne))
				.size(0)
				.color(Color.INDIANRED)
				.distance(50)
				.build();
		farStar = (new Planet.Builder("c", starOne))
				.size(0)
				.color(Color.INDIANRED)
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
		Star starTwo= new Star("anker", new EmptyJFXDrawingInformation(), new AbsolutePoint( 0, 0),0);
		assertEquals(0,starOne.distanceTo(starTwo));
	}
}
