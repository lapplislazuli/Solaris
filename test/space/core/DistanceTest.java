/**
 * @Author Leonhard Applis
 * @Created 03.09.2018
 * @Package space.core
 */
package space.core;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DistanceTest {
	
	@Test
	void positiveDistance() {
		Star anker= new Star("anker", null, 0, 0, 0);
		Planet a = new Planet("a", anker, null, 0, 0, 0);
		Planet b = new Planet("b", anker, null, 0, 50, 0);
		Planet c = new Planet("c", anker, null, 0, 100, 0);
		
		assertEquals(50, a.distanceTo(b));
		assertEquals(50, b.distanceTo(c));
		assertEquals(true, a.distanceTo(b)==b.distanceTo(a));
		assertEquals(true, c.distanceTo(b)==b.distanceTo(c));
	}
	
	@Test
	void negativeDistance() {
		Star anker= new Star("anker", null, 150, 0, 0);
		Planet a = new Planet("a", anker, null, 0, 0, 0);
		Planet b = new Planet("b", anker, null, 0, -50, 0);
		Planet c = new Planet("c", anker, null, 0, -100, 0);
		
		assertEquals(50, a.distanceTo(b));
		assertEquals(50, b.distanceTo(c));
		assertEquals(true, a.distanceTo(b)==b.distanceTo(a));
		assertEquals(true, c.distanceTo(b)==b.distanceTo(c));
	}
	
	@Test
	void nullDistance() {
		Star anker1= new Star("anker1", null, 0, 0, 0);
		Star anker2= new Star("anker2", null, 0, 0, 0);
		
		assertEquals(0,anker1.distanceTo(anker2));
		assertEquals(true, anker1.distanceTo(anker2)==anker2.distanceTo(anker1));
	}
}
