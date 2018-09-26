package core;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import geom.Point;
import space.core.Star;

class CoverTest {

	static Star anker;
	static int xHit,yHit,xNoHit,yNoHit;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		anker= new Star("anker1", null, new Point(250, 250),250);
		xHit=250;
		yHit=250;
		xNoHit=12500;
		yNoHit=12500;
	}

	@Test
	void Hit() {
		assertTrue(anker.isCovered(xHit, yHit));
	}
	
	@Test 
	void notHit() {
		assertFalse(anker.isCovered(xNoHit, yNoHit));
	}
	
	@Test
	void oneNoHit() {
		assertFalse(anker.isCovered(xNoHit, yHit));
		assertFalse(anker.isCovered(xHit, yNoHit));
	}

}
