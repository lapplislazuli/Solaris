package geom;

import static org.junit.jupiter.api.Assertions.*;
import static helpers.GeometryFakeFactory.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import interfaces.geom.Point;

class PointTests {
	
	@Test 
	void testDegreeTo_verticalPointAbove_shouldBe90Degrees(){
		Point firstPoint= fakeAbsolutePoint();
		Point secondPoint= fakeAbsolutePoint(0,100);
		
		assertEquals(Math.PI/2,firstPoint.degreeTo(secondPoint));
	}
	
	@Test 
	void testDegreeTo_verticalPointBeyond_shouldBe270Degrees(){
		Point firstPoint= fakeAbsolutePoint();
		Point secondPoint= fakeAbsolutePoint(0,-100);
		
		assertEquals(3*Math.PI/2,firstPoint.degreeTo(secondPoint));
	}
	
	@Test 
	void testDegreeTo_horizontalPointRight_shouldBe0Degrees(){
		Point firstPoint= fakeAbsolutePoint();
		Point secondPoint= fakeAbsolutePoint(100,0);
		
		assertEquals(0,firstPoint.degreeTo(secondPoint));
	}
	
	@Test 
	void testDegreeTo_horizontalPointLeft_shouldBe180Degrees(){
		Point firstPoint= fakeAbsolutePoint();
		Point secondPoint= fakeAbsolutePoint(-100,0);
		
		assertEquals(Math.PI,firstPoint.degreeTo(secondPoint));
	}
	
	@Test
	void testDegreeTo_comparePointsToEachother_shouldBeSymmetric() {
		Point firstPoint= fakeAbsolutePoint();
		Point secondPoint= fakeAbsolutePoint(-100,0);
		
		assertTrue(firstPoint.degreeTo(secondPoint)==secondPoint.degreeTo(firstPoint)+Math.PI);
	}
	
	@ParameterizedTest
	@ValueSource(ints = {0,10,30,75,100})
	void testDistance_pointIsInXNAway_shouldEqualN(int dist) {
		Point fakeCenter = fakeAbsolutePoint();
		Point xcomparer = fakeAbsolutePoint(0,dist);
		
		assertEquals(dist,xcomparer.distanceTo(fakeCenter));
	}
	
	@ParameterizedTest
	@ValueSource(ints = {0,10,30,75,100})
	void testDistance_pointIsInYNAway_shouldEqualN(int dist) {
		Point fakeCenter = fakeAbsolutePoint();
		Point ycomparer = fakeAbsolutePoint(0,dist);
		
		assertEquals(dist,ycomparer.distanceTo(fakeCenter));
	}
	
	@ParameterizedTest
	@ValueSource(ints = {0,10,30,75,100})
	void testDistance_pointIsInXNegativeAway_shouldEqualHavePositiveN(int dist) {
		Point fakeCenter = fakeAbsolutePoint();
		Point xcomparer = fakeAbsolutePoint(-dist,0);
		
		assertEquals(dist,xcomparer.distanceTo(fakeCenter));
	}
	
	@ParameterizedTest
	@ValueSource(ints = {-100000,-100,-10,0,10,100,10000})
	void testDistance_symmetry_shouldBeSymmetric(int dist) {
		Point fakeCenter = fakeAbsolutePoint();
		Point xcomparer = fakeAbsolutePoint(dist,0);
		
		assertTrue(xcomparer.distanceTo(fakeCenter)==fakeCenter.distanceTo(xcomparer));
	}
	
	@ParameterizedTest
	@ValueSource(ints = {-100000,-100,-10,0,10,100,10000})
	void testDistance_movePointOntoOther_shouldOnOther(int dist) {
		AbsolutePoint movingPoint = fakeAbsolutePoint();
		Point center = fakeAbsolutePoint(dist,dist);
		
		movingPoint.move(dist, dist);

		assertEquals(0,movingPoint.distanceTo(center));
	}
	
	@ParameterizedTest
	@ValueSource(ints = {-100000,-100,-10,0,10,100,10000})
	void testClone_PointShouldHaveSamePosition_shouldBeTrue(int variableCord) {
		AbsolutePoint test = fakeAbsolutePoint(variableCord,variableCord);
		Point clone = test.clone();
		
		assertTrue(test.samePosition(clone));
	}
	
	@ParameterizedTest
	@ValueSource(ints = {-100000,-100,-10,10,100,10000})
	void testClone_DifferentPoints_shouldBeFalse(int variableCord) {
		Point center = fakeAbsolutePoint();
		AbsolutePoint test = fakeAbsolutePoint(variableCord,variableCord);
		
		assertFalse(test.samePosition(center));
	}
	
	@ParameterizedTest
	@ValueSource(ints = {-100000,-100,-10,10,100,10000})
	void testClone_checkHashcode_shouldNotBeEqual(int variableCord) {
		Point center = fakeAbsolutePoint();
		AbsolutePoint test = fakeAbsolutePoint(variableCord,variableCord);
		
		assertFalse(test.hashCode() == center.hashCode());
	}
	
	@ParameterizedTest
	@ValueSource(ints = {-100000,-100,-10,10,100,10000})
	void testClone_cloneSamePositionSymmetry_shouldBeEqual(int variableCord) {
		AbsolutePoint test = fakeAbsolutePoint(variableCord,variableCord);
		Point clone = test.clone();
		
		assertTrue(test.samePosition(clone)==clone.samePosition(test));
	}
}
