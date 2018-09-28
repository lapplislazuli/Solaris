package geom;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import interfaces.geom.Point;

class CircleTest {
	
	private static Circle testObject;
	private static Point centerTestObject;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		centerTestObject= new AbsolutePoint(100,100);
		testObject = new Circle(centerTestObject,100);
	}

	@Test
	void testInitOutline() {
		testObject.levelOfDetail=4; //4 OutlinePoints!
		testObject.updateOrInitOutline(); 
		for (int i=0;i<4;i++) {
			Point testOutlinePoint=testObject.outLine.get(i);
			assertTrue(
					testOutlinePoint.distanceTo(new AbsolutePoint(0,100))==0
				|| testOutlinePoint.distanceTo(new AbsolutePoint(200,100))==0
				|| testOutlinePoint.distanceTo(new AbsolutePoint(100,0))==0
				|| testOutlinePoint.distanceTo(new AbsolutePoint(100,200))==0
			);
		}
		testObject.levelOfDetail=100;
		testObject.updateOrInitOutline();
		assertEquals(100,testObject.outLine.size());
	}

	@Test
	void testContains() {
		List<Point> inPoints = new LinkedList<Point>();
		inPoints.add(new AbsolutePoint(65,50)); //definitive in Circle 
		inPoints.add(new AbsolutePoint(125,100)); //definitive in Circle
		inPoints.add(new AbsolutePoint(200,100)); //Edgepoint
		inPoints.add(centerTestObject);
		for(Point inPoint : inPoints)
			assertTrue(testObject.contains(inPoint));
		List<Point> outPoints = new LinkedList<Point>();
		outPoints.add(new AbsolutePoint(0,0));  
		outPoints.add(new AbsolutePoint(0,120)); 
		outPoints.add(new AbsolutePoint(0,0,200)); 
		for(Point outPoint : outPoints)
			assertFalse(testObject.contains(outPoint));
		
	}

}
