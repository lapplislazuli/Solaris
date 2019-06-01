package junit.geom;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;

import static helpers.FakeGeometryFactory.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import geom.RelativePoint;
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
		RelativePoint testy = new RelativePoint(anker,offset,offset);
		
		assertEquals(offset,testy.getX());
		assertEquals(offset,testy.getY());
	}
	
	@ParameterizedTest
	@ValueSource(ints = {-100,-10,-5,0,10,30,75,100})
	void testConstructor_moveRelativePoint_shouldBeOffsetAway(int offset){
		Point anker = fakeAbsolutePoint();
		RelativePoint testy = new RelativePoint(anker,offset,offset,offset);
		
		assertEquals(offset,testy.getX());
		assertEquals(offset,testy.getY());
		assertEquals(offset,testy.getZ());
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
		testy.setZ(dest);

		assertEquals(dest,testy.getX());
		assertEquals(dest,testy.getY());
		assertEquals(dest,testy.getZ());
	}
	
	@ParameterizedTest
	@ValueSource(ints = {-100,-10,-5,0,10,30,75,100})
	void testSetXDiffRelative_PointShouldBeOnNewValues(int dest){
		Point oldAnker = fakeAbsolutePoint();
		RelativePoint testy = fakeRelativePoint(oldAnker);
		
		testy.setXDif(dest);
		testy.setYDif(dest);
		testy.setZDif(dest);

		assertEquals(dest,testy.getX());
		assertEquals(dest,testy.getY());
		assertEquals(dest,testy.getZ());
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
	
	@Test
	void testClone_compareToClone_shouldBeEqual() {
		Point anchor = fakeAbsolutePoint();
		RelativePoint raw = fakeRelativePoint(anchor,10,10);
		RelativePoint clone= raw.clone();
		
		assertEquals(raw,clone);
	}
	
	@Test
	void testClone_compareToClone_shouldHaveSameKoords() {
		Point anchor = fakeAbsolutePoint();
		RelativePoint raw = fakeRelativePoint(anchor,10,10);
		RelativePoint clone= raw.clone();
		
		assertEquals(raw.getY(),clone.getY());
		assertEquals(raw.getX(),clone.getX());
		assertEquals(raw.getZ(),clone.getZ());
	}
	
	@Test
	void testToString_shouldBeCoordsInBrackets() {
		Point anchor = fakeAbsolutePoint();
		RelativePoint raw = fakeRelativePoint(anchor,0,0);
		
		assertEquals("[0|0|0]",raw.toString());
	}
	
	@Test
	void testClone_toString_shouldBeEqual() {
		Point anchor = fakeAbsolutePoint();
		RelativePoint raw = fakeRelativePoint(anchor,10,10);
		RelativePoint clone= raw.clone();
		
		assertEquals(raw.toString(),clone.toString());
	}
	
	@Test
	void testToString_NoOffset_shouldBeEqualToAnchor() {
		Point anchor = fakeAbsolutePoint();
		RelativePoint raw = fakeRelativePoint(anchor,0,0);
		
		assertEquals(anchor.toString(),raw.toString());
	}
	
	@Test
	void testEquals_noPointToCompare_shouldbeFalse() {
		Point anchor = fakeAbsolutePoint();
		RelativePoint raw = fakeRelativePoint(anchor,0,0);
		
		assertNotEquals(new LinkedList(),raw);
	}
	
	@Test
	void testEquals_AbsolutePointOnSamePosition_shouldBeTrue() {
		Point anchor = fakeAbsolutePoint();
		RelativePoint raw = fakeRelativePoint(anchor,0,0);
		
		assertNotEquals(anchor,raw);
	}
}
