package junit.core;



import static junit.testhelpers.FakeSpaceObjectFactory.fakeStar;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import space.core.SpaceObject;

class DistanceTests {

	@Tag("fast")
	@Tag("core")
	@Tag("logic")
	@RepeatedTest(3)
	@Test
	void testDistance_closeItem_shouldBeDifferenceOfCenters() {
		int ancorscenter=0;
		int wantedDifference=50;
		SpaceObject ancor = fakeStar(ancorscenter,0);
		SpaceObject close = fakeStar(ancorscenter+wantedDifference,0);
		
		assertEquals(wantedDifference, close.distanceTo(ancor));
	}
	
	@Tag("fast")
	@Tag("core")
	@Tag("logic")
	@RepeatedTest(3)
	@Test
	void testDistance_farItem_shouldBeDifferenceOfCenters() { 
		int ancorscenter=0;
		int wantedDifference=5000;
		SpaceObject ancor = fakeStar(ancorscenter,0);
		SpaceObject close = fakeStar(ancorscenter+wantedDifference,0);
		
		assertEquals(wantedDifference, close.distanceTo(ancor));
	}
	
	@Tag("fast")
	@Tag("core")
	@Tag("logic")
	@RepeatedTest(3)
	@Test
	void testDistance_negativeXDif_shouldBePositiveDistance() {
		int ancorscenter=0;
		int wantedDifference=50;
		SpaceObject ancor = fakeStar(ancorscenter,0);
		SpaceObject negativer = fakeStar(ancorscenter-wantedDifference,0);
		
		assertEquals(wantedDifference, negativer.distanceTo(ancor));
	}
	
	@Tag("fast")
	@Tag("core")
	@Tag("logic")
	@RepeatedTest(3)
	@Test
	void testDistance_compareTwoItems_shouldHaveSameDistance() {
		SpaceObject ancor = fakeStar(0,0);
		SpaceObject other = fakeStar(100,-50);
		
		assertTrue(ancor.distanceTo(other)==other.distanceTo(ancor));
	}
	
	@Tag("fast")
	@Tag("core")
	@Tag("logic")
	@RepeatedTest(3)
	@Test
	void testDistance_compareItemToItself_shouldHaveZeroDistance() {
		SpaceObject singleton = fakeStar(0,0);
		
		assertEquals(0,singleton.distanceTo(singleton));
	}
}
