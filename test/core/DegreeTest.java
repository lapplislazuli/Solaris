package core;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import geom.AbsolutePoint;
import space.core.Star;

public class DegreeTest {
	static Star starOne,starTwo,starAboveTwo, starFarAway;
	
	@BeforeAll
	static void initStars() {
		starOne= new Star("anker1", null, new AbsolutePoint(0, 0),0);
		starTwo= new Star("anker2", null, new AbsolutePoint(100, 0),0);
		starAboveTwo = new Star("anker3", null, new AbsolutePoint(100, -50),0);
		starFarAway= new Star("Anker4", null, new AbsolutePoint(1000,1000),0);
	}
	
	@Test
	void testBasicDegree() {		
		//Expected: 180°
		assertEquals(0,starOne.degreeTo(starTwo));
		//Expected: 360°=0°
		assertEquals(Math.PI,starTwo.degreeTo(starOne));
	}
	
	@Test
	void testDegreesForUpperItems() {
		//Expected: 90°
		assertEquals(Math.PI/2, starAboveTwo.degreeTo(starTwo));
		//Expected: 270°
		assertEquals(3*Math.PI/2, starTwo.degreeTo(starAboveTwo));
	}
	@Test
	void testDegreesFarAwayItem() {
		//Expected: 45°
		assertEquals(Math.PI/4+0.0000000000000001, starOne.degreeTo(starFarAway));
		//Expected: 215°
		assertEquals(5*Math.PI/4+0.0000000000000001, starFarAway.degreeTo(starOne));
	}
	
	@Test
	void testDegreeSymetry() {
		//Expected: One==Other+PI
		assertTrue(starOne.degreeTo(starTwo)+Math.PI==starTwo.degreeTo(starOne));
	}
}
