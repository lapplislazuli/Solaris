package geom;

import static org.junit.jupiter.api.Assertions.*;
import static helpers.GeometryFakeFactory.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import interfaces.geom.Point;

class RelativePointTests {

	@ParameterizedTest
	@ValueSource(ints = {0,10,30,75,100})
	void testInitialValues_shouldBeOnAnker(int value){
		Point anker = fakeAbsolutePoint(value,value);
		RelativePoint testy = fakeRelativePoint(anker);
		
		assertEquals(anker.getX(),testy.getX());
		assertEquals(anker.getY(),testy.getY());
	}
	
	@ParameterizedTest
	@ValueSource(ints = {-100,-10,-5,0,10,30,75,100})
	void testInitialValues_moveRelativePoint_shouldOffsetAway(int offset){
		Point anker = fakeAbsolutePoint();
		RelativePoint testy = fakeRelativePoint(anker,offset,offset);
		
		assertEquals(offset,testy.getX());
		assertEquals(offset,testy.getY());
	}
	
	
	@ParameterizedTest
	@ValueSource(ints = {0,10,30,75,100})
	void testAnkerChanged_AnkerIsOnewPos_ShouldHaveNewPosition(int moved){
		Point oldAnker = fakeAbsolutePoint();
		Point newAnker = fakeAbsolutePoint(moved,moved);
		RelativePoint testy = fakeRelativePoint(oldAnker);
		
		testy.anker=newAnker;
		
		assertEquals(newAnker.getX(),testy.getX());
		assertEquals(newAnker.getY(),testy.getY());
	}
	
	@ParameterizedTest
	@ValueSource(ints = {-100,-10,-5,0,10,30,75,100})
	void testSetRelative_PointShouldBeOnNewValues(int dest){
		Point oldAnker = fakeAbsolutePoint();
		RelativePoint testy = fakeRelativePoint(oldAnker);
		
		testy.setX(dest);
		testy.setY(dest);

		assertEquals(dest,testy.getX());
		assertEquals(dest,testy.getY());
	}
	
	@ParameterizedTest
	@ValueSource(ints = {-100,-10,-5,0,10,30,75,100})
	void testSetXDiffRelative_PointShouldBeOnNewValues(int dest){
		Point oldAnker = fakeAbsolutePoint();
		RelativePoint testy = fakeRelativePoint(oldAnker);
		
		testy.setXDif(dest);
		testy.setYDif(dest);

		assertEquals(dest,testy.getX());
		assertEquals(dest,testy.getY());
	}
	
	
	@Test
	void testRelativePoint_SamePosition_shouldBeTrue() {
		Point anchor = fakeAbsolutePoint();
		RelativePoint testy = fakeRelativePoint(anchor);
		
		assertTrue(testy.samePosition(anchor));
	}
	
	@Test
	void testRelativePoint_SamePosition_moved_shouldBeFalse() {
		Point anchor = fakeAbsolutePoint();
		RelativePoint testy = fakeRelativePoint(anchor,10,10);
		
		assertFalse(testy.samePosition(anchor));
	}
}
