/**
 * @Author Leonhard Applis
 * @Created 04.09.2018
 * @Package space.core
 */
package space.core;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class DegreeTest {
	@Test
	void BasicDegree() {
		Star anker1= new Star("anker1", null, 0, 0, 0);
		Star anker2= new Star("anker2", null, 100, 0, 0);
		
		//Expected: 180°
		assertEquals(Math.PI,anker1.degreeTo(anker2));
		//Expected: 360°=0°
		assertEquals(2*Math.PI,anker2.degreeTo(anker1));
		Star anker3= new Star("anker3",null, 100,-50,0);
		
		//Expected: 90°
		assertEquals(Math.PI/2, anker3.degreeTo(anker2));
		//Expected: 270°
		assertEquals(3*Math.PI/2, anker2.degreeTo(anker3));
	}

	@Test
	void DegreeSymetry() {
		Star anker1= new Star("anker1", null, 0, 0, 0);
		Star anker2= new Star("anker2", null, 100, 0, 0);
		//Expected: One==Other+PI
		assertEquals(true, anker1.degreeTo(anker2)+Math.PI==anker2.degreeTo(anker1));
	}
	
}
