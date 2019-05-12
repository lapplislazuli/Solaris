package geom;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;

import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import interfaces.geom.Point;
import static helpers.FakeGeometryFactory.*;

class RectangleTests implements ShapeTests{
	
	@ParameterizedTest
	@ValueSource(ints = {-35,-10,0,10,30})
	public void testContains_pointIsInShape_shouldSucceed(int shift) {
		Rectangle testItem = fakeRectangle(100);
		Point inPoint = fakeAbsolutePoint(shift,shift);
		
		assertTrue(testItem.contains(inPoint));
	}

	@ParameterizedTest
	@ValueSource(ints = {-3000,-500,200,300,10000})
	public void testContains_yposOut_shouldFail(int ypos) {
		Rectangle testItem = fakeRectangle(100);
		Point outPoint = fakeAbsolutePoint(0,ypos);
		
		assertFalse(testItem.contains(outPoint));
	}

	@ParameterizedTest
	@ValueSource(ints = {-3000,-500,200,300,10000})
	public void testContains_xposOut_shouldFail(int xpos) {
		Rectangle testItem = fakeRectangle(100);
		Point outPoint = fakeAbsolutePoint(xpos,0);
		
		assertFalse(testItem.contains(outPoint));
	}

	@ParameterizedTest
	@ValueSource(ints = {-3000,-500,200,300,10000})
	public void testContains_bothAttributesOut_shouldFail(int shift) {
		Rectangle testItem = fakeRectangle(100);
		Point outPoint = fakeAbsolutePoint(shift,shift);
		
		assertFalse(testItem.contains(outPoint));
	}

	@ParameterizedTest
	@ValueSource(ints = {10,50,100})
	public void testContains_PointTangents_shouldSucceed(int size) {
		Rectangle testItem = fakeRectangle(size);
		Point tangenterOne = fakeAbsolutePoint(size/2,0);
		Point tangenterTwo = fakeAbsolutePoint(0,size/2);
		
		assertTrue(testItem.contains(tangenterOne));
		assertTrue(testItem.contains(tangenterTwo));
	}

	@ParameterizedTest
	@ValueSource(ints = {10,50,100})
	public void testContains_EveryPointOfOutline_shouldSucceed(int size) {
		Rectangle testItem = fakeRectangle(size);
		
		for(Point p : testItem.outLine)
			testItem.contains(p);
	}

	@ParameterizedTest
	@ValueSource(ints = {10,50,100,250})
	public void testContains_ContainsAnotherSmallerShape_shouldSucced(int size) {
		Rectangle smallerItem = fakeRectangle(size);
		Rectangle biggerItem = fakeRectangle(size+5);
		
		smallerItem.setLevelOfDetail(5);
		biggerItem.setLevelOfDetail(5);
		
		smallerItem.updateOrInitOutline();
		biggerItem.updateOrInitOutline();
		
		for(Point p : smallerItem.outLine)
			assertTrue(biggerItem.contains(p));
	}

	@ParameterizedTest
	@ValueSource(ints = {10,50,100,250})
	public void testContains_ContainsAnotherBiggerShape_shouldFail(int size) {
		Rectangle smallerItem = fakeRectangle(size);
		Rectangle biggerItem = fakeRectangle(size+5);
		
		smallerItem.setLevelOfDetail(5);
		biggerItem.setLevelOfDetail(5);
		
		smallerItem.updateOrInitOutline();
		biggerItem.updateOrInitOutline();
		
		for(Point p : biggerItem.outLine) 
			assertFalse(smallerItem.contains(p));
	}
	

	@ParameterizedTest
	@ValueSource(ints = {-10,-100,-1000})
	public void testShape_NegativeSizeInput_ShouldThrowError(int size) {
		assertThrows(Exception.class,() -> fakeRectangle(size));
	}


	@ParameterizedTest
	@ValueSource(ints = {0,-0})
	public void testShape_ZeroSizeInput_ShouldSucceed(int zeros) {
		Rectangle testItem = fakeRectangle(zeros);
		assertTrue(true,"Rectangle was build with size 0 - No Exception");
	}
	
	@ParameterizedTest
	@ValueSource(ints = {0,10,100,1000})
	public void testEquality_SameValuesAndSameCenter_shouldBeTrue(int size) {
		Point sharedCenter = fakeAbsolutePoint();
		
		Rectangle testObject = fakeRectangle(sharedCenter,size);
		Rectangle sameObject = fakeRectangle(sharedCenter,size);
		
		assertTrue(testObject.equals(sameObject));
	}
	
	@ParameterizedTest
	@ValueSource(ints = {0,10,100,1000})
	public void testEquality_SameValuesDifferentCenterOnSamePoint_shouldBeFalse(int size) {
		Rectangle testObject = fakeRectangle(size);
		Rectangle sameObject = fakeRectangle(size);
		
		assertFalse(testObject.equals(sameObject));
	}
	
	@ParameterizedTest
	@ValueSource(ints = {0,10,100,1000})
	public void testEquality_differentValues_shouldBeFalse(int size) {
		Rectangle testObject = fakeRectangle(size);
		Rectangle otherObject = fakeRectangle(size+1);
		
		assertFalse(testObject.equals(otherObject));
	}

	@ParameterizedTest
	@ValueSource(ints = {0,10,100,1000})
	public void testEquality_checkSymmetryOfSameValue_shouldBeTrue(int size) {
		Rectangle testObject = fakeRectangle(size);
		Rectangle sameObject = fakeRectangle(size);
		
		assertTrue(testObject.equals(sameObject)==sameObject.equals(testObject));
	}

	@ParameterizedTest
	@ValueSource(ints = {0,10,100,1000})
	public void testEquality_checkSymmetryOfDifferentValue_shouldBeTrue(int size) {
		Rectangle testObject = fakeRectangle(size);
		Rectangle otherObject = fakeRectangle(size+1);
		
		assertTrue(testObject.equals(otherObject)==otherObject.equals(testObject));
	}
	
	@ParameterizedTest
	@ValueSource(ints = {-10,0,10,})
	public void testEquality_sameSizeDifferentCenter_shouldbeFalse(int offset) {
		Point firstCenter = fakeAbsolutePoint();
		Point secondCenter = fakeAbsolutePoint(offset,offset);
		Rectangle testObject = fakeRectangle(firstCenter,100);
		Rectangle otherObject = fakeRectangle(secondCenter,100);
		
		assertFalse(testObject.equals(otherObject));
	}
	

	@ParameterizedTest
	@ValueSource(ints = {0})
	public void testEquality_completelyDifferentObject_shouldNotBeEqual(int dummy) {
		Point firstCenter = fakeAbsolutePoint();
		Rectangle testObject =  fakeRectangle(firstCenter,100);
		
		assertNotEquals(new LinkedList<Object>(),testObject);
	}
}