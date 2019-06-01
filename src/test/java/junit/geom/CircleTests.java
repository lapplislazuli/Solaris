package junit.geom;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;

import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import geom.Circle;
import interfaces.geom.Point;

import static helpers.FakeGeometryFactory.*;

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
		
		smallerCircle.setLevelOfDetail(10);
		biggerCircle.setLevelOfDetail(10);
		smallerCircle.updateOrInitOutline();
		biggerCircle.updateOrInitOutline();

		for(Point p : smallerCircle.outLine)
			assertTrue(biggerCircle.contains(p));
	}

	@ParameterizedTest
	@ValueSource(ints = {0,10,100,1000})
	public void testContains_ContainsAnotherBiggerShape_shouldFail(int size) {
		Point fakeCenter = fakeAbsolutePoint();
		
		Circle smallerCircle = fakeCircle(fakeCenter,size);
		Circle biggerCircle = fakeCircle(fakeCenter,size+5);

		smallerCircle.setLevelOfDetail(10);
		biggerCircle.setLevelOfDetail(10);
		smallerCircle.updateOrInitOutline();
		biggerCircle.updateOrInitOutline();

		for(Point p : biggerCircle.outLine) 
			assertFalse(smallerCircle.contains(p));
	}

	@ParameterizedTest
	@ValueSource(ints = {-10,-100})
	public void testShape_NegativeSizeInput_ShouldThrowError(int size) {
		assertThrows(Exception.class,() -> fakeCircle(size));
	}

	@ParameterizedTest
	@ValueSource(ints = {0,-0})
	public void testShape_ZeroSizeInput_ShouldSucceed(int size) {
		Circle testItem = fakeCircle(size);
		assertTrue(true,"Circle was build with size 0 - No Exception");
	}
	
	@ParameterizedTest
	@ValueSource(ints = {0,10,100,1000})
	public void testEquality_SameValuesAndSameCenter_shouldBeTrue(int size) {
		Point sharedCenter = fakeAbsolutePoint();
		
		Circle testObject = fakeCircle(sharedCenter,size);
		Circle sameObject = fakeCircle(sharedCenter,size);
		
		assertTrue(testObject.equals(sameObject));
	}
	
	@ParameterizedTest
	@ValueSource(ints = {0,10,100,1000})
	public void testEquality_SameValuesDifferentCenterOnSamePoint_shouldBeFalse(int size) {
		Circle testObject = fakeCircle(size);
		Circle sameObject = fakeCircle(size);
		
		assertFalse(testObject.equals(sameObject));
	}
	
	@ParameterizedTest
	@ValueSource(ints = {0,10,100,1000})
	public void testEquality_differentValues_shouldBeFalse(int size) {
		Circle testObject = fakeCircle(size);
		Circle otherObject = fakeCircle(size+1);
		
		assertFalse(testObject.equals(otherObject));
	}

	@ParameterizedTest
	@ValueSource(ints = {0,10,100,1000})
	public void testEquality_checkSymmetryOfSameValue_shouldBeTrue(int size) {
		Circle testObject = fakeCircle(size);
		Circle sameObject = fakeCircle(size);
		
		assertEquals(testObject.equals(sameObject),sameObject.equals(testObject));
	}

	@ParameterizedTest
	@ValueSource(ints = {0,10,100,1000})
	public void testEquality_checkSymmetryOfDifferentValue_shouldBeTrue(int size) {
		Circle testObject = fakeCircle(size);
		Circle otherObject = fakeCircle(size+1);
		
		assertEquals(testObject.equals(otherObject),otherObject.equals(testObject));
	}

	@ParameterizedTest
	@ValueSource(ints = {-10,0,10,})
	public void testEquality_sameSizeDifferentCenter_shouldbeFalse(int offset) {
		Point firstCenter = fakeAbsolutePoint();
		Point secondCenter = fakeAbsolutePoint(offset,offset);
		Circle testObject = fakeCircle(firstCenter,100);
		Circle otherObject = fakeCircle(secondCenter,100);
		
		assertFalse(testObject.equals(otherObject));
	}
	
	@ParameterizedTest
	@ValueSource(ints = {0})
	public void testEquality_completelyDifferentObject_shouldNotBeEqual(int dummy) {
		Point firstCenter = fakeAbsolutePoint();
		Circle testObject = fakeCircle(firstCenter,100);
		
		assertNotEquals(new LinkedList<Object>(),testObject);
	}

}
