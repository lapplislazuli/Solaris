package core;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import geom.Circle;
import geom.Point;
import space.core.Star;

public class DegreeTest {
	@Test
	void BasicDegree() {
		Star anker1= new Star("anker1", null, new Point(0, 0), new Circle(new Point(0, 0), 0));

		Star anker2= new Star("anker1", null, new Point(100, 0), new Circle(new Point(100, 0), 0));
		
		//Expected: 180°
		assertEquals(Math.PI,anker1.degreeTo(anker2));
		//Expected: 360°=0°
		assertEquals(2*Math.PI,anker2.degreeTo(anker1));

		Star anker3= new Star("anker1", null, new Point(100, -50), new Circle(new Point(100, -50), 0));
		//Expected: 90°
		assertEquals(Math.PI/2, anker3.degreeTo(anker2));
		//Expected: 270°
		assertEquals(3*Math.PI/2, anker2.degreeTo(anker3));
	}

	@Test
	void DegreeSymetry() {
		Star anker1= new Star("anker1", null, new Point(0, 0), new Circle(new Point(0, 0), 0));
		Star anker2= new Star("anker1", null, new Point(100, 0), new Circle(new Point(100, 0), 0));
		//Expected: One==Other+PI
		assertEquals(true, anker1.degreeTo(anker2)+Math.PI==anker2.degreeTo(anker1));
	}	
}
