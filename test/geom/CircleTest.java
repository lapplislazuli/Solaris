package geom;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import interfaces.geom.Point;

class CircleTest {
	
	private Circle testObject;
	private Point centerTestObject;
	
	@BeforeEach
	void setUp() throws Exception {
		centerTestObject= new AbsolutePoint(100,100);
		testObject = new Circle(centerTestObject,100);
	}
	
	@Test
	void testPositiveContains() {
		List<Point> inPoints = new LinkedList<Point>();
		inPoints.add(new AbsolutePoint(65,50)); 
		inPoints.add(new AbsolutePoint(125,100));
		inPoints.add(centerTestObject);
		for(Point inPoint : inPoints)
			assertTrue(testObject.contains(inPoint));
	}
	
	@Test 
	void testNegativeContains() {
		List<Point> outPoints = new LinkedList<Point>();
		outPoints.add(new AbsolutePoint(0,0));  
		outPoints.add(new AbsolutePoint(0,120)); 
		outPoints.add(new AbsolutePoint(0,0,200)); 
		for(Point outPoint : outPoints)
			assertFalse(testObject.contains(outPoint));
	}
	
	@Test
	void testTangentialContains() {
		Point edgePoint = new AbsolutePoint(200,100); //On the Edge of Circle
		assertTrue(testObject.contains(edgePoint));
	}
	
	/*
	 * Idea: Make a little smaller Circle and a little bigger circle around the testObject
	 * Check whether all Points are in one and not in the other. 
	 * This leaves only to check whether outline distance and outlinepoint-degree are correct
	 */
	@Test
	void testInitOutlineWithRelativeCircles() {
		Circle biggerCircle = new Circle(centerTestObject,102);
		Circle smallerCircle = new Circle(centerTestObject,98);
		testObject.setLevelOfDetail(1000);
		testObject.initOutline();
		
		for(Point p : testObject.outLine) {
			assertTrue(biggerCircle.contains(p));
			assertFalse(smallerCircle.contains(p));
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
		testObject.levelOfDetail=100;
		testObject.updateOrInitOutline();
		assertEquals(100,testObject.outLine.size());
	}
}
