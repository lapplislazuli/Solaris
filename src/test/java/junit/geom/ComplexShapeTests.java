package junit.geom;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import geom.AbsolutePoint;
import geom.HShape;
import interfaces.geom.Shape;

public class ComplexShapeTests {
	
	
	@Test
	void testIntersect_TwoHShapesAtSamePosition_shouldIntersect() {
		Shape a = new HShape(2, 2, 1);
		a.setCenter(new AbsolutePoint(0,0));
		a.setLevelOfDetail(3);
		a.updateOrInitOutline();
		
		Shape b = new HShape(1, 2, 1);
		b.setCenter(new AbsolutePoint(0,0));
		b.setLevelOfDetail(3);
		b.updateOrInitOutline();
		
		assertTrue(a.intersects(b));
		assertTrue(b.intersects(a));
	}

	@Test
	void testIntersect_TwoHShapesAtClosePosition_shouldIntersect() {
		Shape a = new HShape(2, 2, 1);
		a.setCenter(new AbsolutePoint(0,0));
		a.setLevelOfDetail(3);
		a.updateOrInitOutline();
		
		Shape b = new HShape(1, 2, 1);
		b.setCenter(new AbsolutePoint(1,1));
		b.setLevelOfDetail(3);
		b.updateOrInitOutline();
		
		assertTrue(a.intersects(b));
		assertTrue(b.intersects(a));
	}
	
	@Test
	void testIntersect_HShapesDoNotIntersect_shouldNotIntersect() {
		Shape a = new HShape(2, 2, 1);
		a.setCenter(new AbsolutePoint(0,0));
		a.setLevelOfDetail(3);
		a.updateOrInitOutline();
		
		Shape b = new HShape(1, 2, 1);
		b.setCenter(new AbsolutePoint(100,0));
		b.setLevelOfDetail(3);
		b.updateOrInitOutline();
		
		assertFalse(a.intersects(b));
		assertFalse(b.intersects(a));		
	}
	
	@Test
	void testIntersect_sameShape_reflexiveTest_shouldIntersect() {
		Shape a = new HShape(2, 2, 1);
		a.setCenter(new AbsolutePoint(0,0));
		a.setLevelOfDetail(3);
		a.updateOrInitOutline();
		
		assertTrue(a.intersects(a));	
	}
}
