package geom;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import interfaces.geom.Point;

class RectangleTests {
	
	private Rectangle testObject;
	private AbsolutePoint centerTestObject;
	
	@BeforeEach
	void setUpBeforeClass() throws Exception {
		centerTestObject= new AbsolutePoint(100,100);
		testObject = new Rectangle(centerTestObject,200,200);
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
		
		for(AbsolutePoint outPoint : outPoints)
			assertFalse(testObject.contains(outPoint));
	}
	
	@Test
	void testTangentialContains() {
		Point edgePoint=new AbsolutePoint(200,200); 
		assertTrue(testObject.contains(edgePoint));
	}

	@Test
	void testInitOutlineWithRelativeRectangles() {
		Rectangle biggerRectangle = new Rectangle(centerTestObject, 202,202);
		Rectangle smallerRectangle = new Rectangle(centerTestObject, 190, 190);
		
		testObject.setLevelOfDetail(100);
		testObject.initOutline();
		
		for(Point p : testObject.outLine) {
			assertTrue(biggerRectangle.contains(p));
			assertFalse(smallerRectangle.contains(p));
		}
	}
	
	@Test
	void testOutlineIsTangenting() {
		testObject.setLevelOfDetail(100);
		testObject.initOutline();
		
		for(Point p : testObject.outLine)
			testObject.contains(p);
	}
	@Test
	void testOutlineCount() {
		testObject.levelOfDetail=25;
		testObject.updateOrInitOutline();
		assertEquals(100,testObject.outLine.size());
	}
}