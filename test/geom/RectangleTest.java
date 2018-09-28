package geom;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RectangleTest {
	
	private static Rectangle testObject;
	private static AbsolutePoint centerTestObject;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		centerTestObject= new AbsolutePoint(100,100);
		testObject = new Rectangle(centerTestObject,200,200);
	}

	
	//TODO: Check this why its not working?
	@Test
	void testInitOutline() {
		testObject.levelOfDetail=2; //4 OutlinePoints!
		testObject.initOutline(); 
		for (int i=0;i<4;i++) {
			Point testOutlinePoint=testObject.outLine.get(i);
			assertTrue(
				testOutlinePoint.distanceTo(new AbsolutePoint(0,200))==0
				|| testOutlinePoint.distanceTo(new AbsolutePoint(200,200))==0
				|| testOutlinePoint.distanceTo(new AbsolutePoint(200,0))==0
				|| testOutlinePoint.distanceTo(new AbsolutePoint(0,0))==0
			);
		}
		testObject.levelOfDetail=100;
		testObject.initOutline();
		assertEquals(100,testObject.outLine.size());
	}

	@Test
	void testContains() {
		List<AbsolutePoint> inPoints = new LinkedList<AbsolutePoint>();
		inPoints.add(new AbsolutePoint(65,50)); //definitive in Circle 
		inPoints.add(new AbsolutePoint(125,100)); //definitive in Circle
		inPoints.add(new AbsolutePoint(200,200)); //Edgepoint
		inPoints.add(centerTestObject);
		for(AbsolutePoint inPoint : inPoints)
			assertTrue(testObject.contains(inPoint));
		List<AbsolutePoint> outPoints = new LinkedList<AbsolutePoint>();
		outPoints.add(new AbsolutePoint(0,300));  
		outPoints.add(new AbsolutePoint(0,-10)); 
		//outPoints.add(new Point(0,0,400)); 
		for(AbsolutePoint outPoint : outPoints)
			assertFalse(testObject.contains(outPoint));
		
	}

}