package geom;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import interfaces.geom.Point;

import static helpers.GeometryFakeFactory.*;

class CircleTests implements ShapeTests{
	
	@ParameterizedTest
	@ValueSource(ints = {200,300,100000})
	public void testContains_yposOut_shouldFail(int ypos) {
		Circle testCircle = fakeCircle(100);
		Point outPoint = fakeAbsolutePoint(0,ypos);
		
		assertFalse(testCircle.contains(outPoint));
	}
	
	@ParameterizedTest
	@ValueSource(ints = {200,300,100000})
	public void testContains_xposOut_shouldFail(int xpos) {
		Circle testCircle = fakeCircle(100);
		Point outPoint = fakeAbsolutePoint(xpos,0);
		
		assertFalse(testCircle.contains(outPoint));
	}
	
	@ParameterizedTest
	@ValueSource(ints = {200,300,100000})
	public void testContains_bothAttributesOut_shouldFail(int distance) {
		Circle testCircle = fakeCircle(100);
		Point outPoint = fakeAbsolutePoint(distance,distance);
		
		assertFalse(testCircle.contains(outPoint));
	}
	
	@ParameterizedTest
	@ValueSource(ints = {0,10,100,1000})
	public void testContains_PointTangents_shouldSucceed(int distance) {
		Circle testCircle = fakeCircle(distance);
		
		Point verticalTangenter = fakeAbsolutePoint(distance,0);
		Point horizontalTangenter = fakeAbsolutePoint(0,distance);
		Point diagonalTangenter = fakeAbsolutePoint(0,0);
		
		assertTrue(testCircle.contains(verticalTangenter));
		assertTrue(testCircle.contains(horizontalTangenter));
		assertTrue(testCircle.contains(diagonalTangenter));
	}
	
	@ParameterizedTest
	@ValueSource(ints = {0,10,100,1000})
	public void testContains_EveryPointOfOutline_shouldSucceed(int distance) {
		Point fakeCenter = fakeAbsolutePoint();
		
		Circle testObject = fakeCircle(fakeCenter,distance);
		
		for(Point p : testObject.outLine)
			testObject.contains(p);
	}
	
	@ParameterizedTest
	@ValueSource(ints = {0,10,30,75,100})
	public void testContains_pointIsInShape_shouldSucceed(int dist) {
		Circle testCircle = fakeCircle(200);
		Point inPoint = fakeAbsolutePoint(dist,dist);
		
		assertTrue(testCircle.contains(inPoint));
	}

	@ParameterizedTest
	@ValueSource(ints = {0,10,100,1000})
	public void testContains_ContainsAnotherSmallerShape_shouldSucced(int size) {
		Point fakeCenter = fakeAbsolutePoint();
		
		Circle smallerCircle = fakeCircle(fakeCenter,size);
		Circle biggerCircle = fakeCircle(fakeCenter,size+5);
		
		for(Point p : smallerCircle.outLine)
			assertTrue(biggerCircle.contains(p));
	}

	@ParameterizedTest
	@ValueSource(ints = {0,10,100,1000})
	public void testContains_ContainsAnotherBiggerShape_shouldFail(int size) {
		Point fakeCenter = fakeAbsolutePoint();
		
		Circle smallerCircle = fakeCircle(fakeCenter,size);
		Circle biggerCircle = fakeCircle(fakeCenter,size+5);
		
		for(Point p : biggerCircle.outLine) 
			assertFalse(smallerCircle.contains(p));
	}
}
