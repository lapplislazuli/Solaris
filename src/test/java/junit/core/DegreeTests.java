package junit.core;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import space.core.SpaceObject;

import static helpers.FakeSpaceObjectFactory.*;

public class DegreeTests {
	
	@Test
	void testDegree_twoObjectOnLine_ShouldEqual0Degree() {
		SpaceObject one = fakeStar(0,0,0);
		SpaceObject two = fakeStar(100,0,0);
		
		assertEquals(0,one.degreeTo(two));
	}
	
	@Test
	void testDegree_twoObjectOnLine_otherWayRound_ShouldEqual180Degree() {
		SpaceObject one = fakeStar(0,0,0);
		SpaceObject two = fakeStar(100,0,0);
		
		assertEquals(Math.PI,two.degreeTo(one));
	}
	
	@Test
	void testDegree_itemVerticalBeyondOther_shouldEqual270Degree() {
		SpaceObject base = fakeStar(100,0,0);
		SpaceObject aboveBase = fakeStar(100,-50,0);
		
		assertEquals(3*Math.PI/2, base.degreeTo(aboveBase));
	}
	
	@Test
	void testDegree_itemVerticalAboveOther_shouldEqual90Degree() {
		SpaceObject base = fakeStar(100,0,0);
		SpaceObject aboveBase = fakeStar(100,-50,0);
	
		assertEquals(Math.PI/2, aboveBase.degreeTo(base));
	}
	
	@Test
	void testDegree_itemFarAwayDiagonal() {
		SpaceObject one = fakeStar(0,0,0);
		SpaceObject farAway = fakeStar(1000,1000,0);
 
		assertEquals(Math.PI/4+0.0000000000000001, one.degreeTo(farAway));
		assertEquals(5*Math.PI/4+0.0000000000000001, farAway.degreeTo(one));
	}
	
	@Test
	void testDegree_compareDegrees_shouldVaryByPi() {
		SpaceObject one = fakeStar(0,0,0);
		SpaceObject two = fakeStar(100,0,0);
		//Expected: One==Other+PI
		assertTrue(one.degreeTo(two)+Math.PI==two.degreeTo(one));
	}
}
