package core;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import geom.AbsolutePoint;
import space.core.Star;

public class DegreeTest {
	static Star starOne,starTwo,starAboveTwo;
	
	@BeforeAll
	static void initStars() {
		starOne= new Star("anker1", null, new AbsolutePoint(0, 0),0);
		starTwo= new Star("anker2", null, new AbsolutePoint(100, 0),0);
		starAboveTwo = new Star("anker3", null, new AbsolutePoint(100, -50),0);
	}
	
	@Test
	void testBasicDegree() {		
		//Expected: 180°
		assertEquals(Math.PI,starOne.degreeTo(starTwo));
		//Expected: 360°=0°
		assertEquals(2*Math.PI,starTwo.degreeTo(starOne));
	}
	
	@Test
	void testDegreesForUpperItems() {
		//Expected: 90°
		assertEquals(Math.PI/2, starAboveTwo.degreeTo(starTwo));
		//Expected: 270°
		assertEquals(3*Math.PI/2, starTwo.degreeTo(starAboveTwo));
	}
	
	@Test
	void testDegreeSymetry() {
		//Expected: One==Other+PI
		assertTrue(starOne.degreeTo(starTwo)+Math.PI==starTwo.degreeTo(starOne));
	}	
}
