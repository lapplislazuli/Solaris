package geom;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RectangleTest {
	
	private static Rectangle testObject;
	private static Point centerTestObject;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		centerTestObject= new Point(100,100);
		testObject = new Rectangle(centerTestObject,200,200);
	}

	@Test
	void testInitOutline() {
		testObject.levelOfDetail=2; //4 OutlinePoints!
		testObject.initOutline(); 
		for (int i=0;i<4;i++) {
			Point testOutlinePoint=testObject.outLine.get(i);
			assertTrue(
				testOutlinePoint.distanceTo(new Point(0,200))==0
				|| testOutlinePoint.distanceTo(new Point(200,200))==0
				|| testOutlinePoint.distanceTo(new Point(200,0))==0
				|| testOutlinePoint.distanceTo(new Point(0,0))==0
			);
		}
		testObject.levelOfDetail=100;
		testObject.initOutline();
		assertEquals(100,testObject.outLine.size());
	}

	@Test
	void testContains() {
		List<Point> inPoints = new LinkedList<Point>();
		inPoints.add(new Point(65,50)); //definitive in Circle 
		inPoints.add(new Point(125,100)); //definitive in Circle
		inPoints.add(new Point(200,200)); //Edgepoint
		inPoints.add(centerTestObject);
		for(Point inPoint : inPoints)
			assertTrue(testObject.contains(inPoint));
		List<Point> outPoints = new LinkedList<Point>();
		outPoints.add(new Point(0,300));  
		outPoints.add(new Point(0,-10)); 
		//outPoints.add(new Point(0,0,400)); 
		for(Point outPoint : outPoints)
			assertFalse(testObject.contains(outPoint));
		
	}

}