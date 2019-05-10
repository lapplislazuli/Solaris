package geom;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import interfaces.geom.Point;
import static helpers.GeometryFakeFactory.*;

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
		
		for(Point p : smallerItem.outLine)
			assertTrue(biggerItem.contains(p));
	}

	@Override
	public void testContains_ContainsAnotherBiggerShape_shouldFail(int size) {
		Rectangle smallerItem = fakeRectangle(size);
		Rectangle biggerItem = fakeRectangle(size+5);
		
		for(Point p : biggerItem.outLine) 
			assertFalse(smallerItem.contains(p));
	}
	
	@Test
	public void testShape_NegativeSizeInput_ShouldThrowError() {
		assertThrows(Exception.class,() -> fakeRectangle(-100));
	}

	@Test
	public void testShape_ZeroSizeInput_ShouldSucceed() {
		Rectangle testItem = fakeRectangle(0);
		assertTrue(true,"Rectangle was build with size 0 - No Exception");
	}
}