package geom;

import static org.junit.jupiter.api.Assertions.*;
import static helpers.FakeGeometryFactory.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import interfaces.geom.Point;

class PointTests {
	

	@ParameterizedTest
	@ValueSource(ints = {10,75,1000})
	void testDegreeTo_verticalPointAbove_shouldBe90Degrees(int offset){
		Point firstPoint= fakeAbsolutePoint();
		Point secondPoint= fakeAbsolutePoint(0,offset);
		
		assertEquals(Math.PI/2,firstPoint.degreeTo(secondPoint));
	}
	

	@ParameterizedTest
	@ValueSource(ints = {-10,-75,-1000})
	void testDegreeTo_verticalPointBeyond_shouldBe270Degrees(int offset){
		Point firstPoint= fakeAbsolutePoint();
		Point secondPoint= fakeAbsolutePoint(0,offset);
		
		assertEquals(3*Math.PI/2,firstPoint.degreeTo(secondPoint));
	}

	@ParameterizedTest
	@ValueSource(ints = {10,75,1000})
	void testDegreeTo_horizontalPointRight_shouldBe0Degrees(int offset){
		Point firstPoint= fakeAbsolutePoint();
		Point secondPoint= fakeAbsolutePoint(offset,0);
		
		assertEquals(0,firstPoint.degreeTo(secondPoint));
	}
	

	@ParameterizedTest
	@ValueSource(ints = {-10,-75,-1000})
	void testDegreeTo_horizontalPointLeft_shouldBe180Degrees(int offset){
		Point firstPoint= fakeAbsolutePoint();
		Point secondPoint= fakeAbsolutePoint(offset,0);
		
		assertEquals(Math.PI,firstPoint.degreeTo(secondPoint));
	}
	
	@ParameterizedTest
	@ValueSource(ints = {-10,-75,-1000})
	void testDegreeTo_comparePointsToEachother_shouldBeSymmetric(int offset) {
		Point firstPoint= fakeAbsolutePoint();
		Point secondPoint= fakeAbsolutePoint(offset,0);
		
		assertEquals(firstPoint.degreeTo(secondPoint),secondPoint.degreeTo(firstPoint)+Math.PI);
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
		
		assertEquals(xcomparer.distanceTo(fakeCenter),fakeCenter.distanceTo(xcomparer));
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
		
		assertNotEquals(test.hashCode(),center.hashCode());
	}
	
	@ParameterizedTest
	@ValueSource(ints = {-100000,-100,-10,10,100,10000})
	void testClone_cloneSamePositionSymmetry_shouldBeEqual(int variableCord) {
		AbsolutePoint test = fakeAbsolutePoint(variableCord,variableCord);
		Point clone = test.clone();
		
		assertEquals(test.samePosition(clone),clone.samePosition(test));
	}
}
