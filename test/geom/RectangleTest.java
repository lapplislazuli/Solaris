package geom;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import interfaces.geom.Point;

class RectangleTest {
	
	private static Rectangle testObject;
	private static AbsolutePoint centerTestObject;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		centerTestObject= new AbsolutePoint(100,100);
		testObject = new Rectangle(centerTestObject,200,200);
	}
	
	//TODO: Write Better Test!
	@Test
	void testInitOutline() {
		testObject.levelOfDetail=2; //4 OutlinePoints!
		testObject.updateOrInitOutline(); 
		//System.out.println(testObject.outLine.toString());
		/*for (for Point ) {
			Point testOutlinePoint=testObject.outLine.get(i);
			assertTrue(
				testOutlinePoint.distanceTo(new AbsolutePoint(0,200))==0
				|| testOutlinePoint.distanceTo(new AbsolutePoint(200,200))==0
				|| testOutlinePoint.distanceTo(new AbsolutePoint(200,0))==0
				|| testOutlinePoint.distanceTo(new AbsolutePoint(0,0))==0
			);
		}*/
	}

	@Test
	void testPositiveContains() {
		List<AbsolutePoint> inPoints = new LinkedList<AbsolutePoint>();
		inPoints.add(new AbsolutePoint(65,50));
		inPoints.add(new AbsolutePoint(125,100));
		inPoints.add(centerTestObject);
		for(AbsolutePoint inPoint : inPoints)
			assertTrue(testObject.contains(inPoint));
	}
	@Test
	void testNegativeContains() {
		List<AbsolutePoint> outPoints = new LinkedList<AbsolutePoint>();
		outPoints.add(new AbsolutePoint(0,300));  
		outPoints.add(new AbsolutePoint(0,-10)); 
		//outPoints.add(new Point(0,0,400)); 
		for(AbsolutePoint outPoint : outPoints)
			assertFalse(testObject.contains(outPoint));
	}
	
	@Test
	void testTangentialContains() {
		Point edgePoint=new AbsolutePoint(200,200); 
		assertTrue(testObject.contains(edgePoint));
	}
}