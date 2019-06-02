package junit.geom;



import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import geom.AbsolutePoint;
import geom.Circle;
import geom.Rectangle;

public class BaseGeomCollisionTests {
	
	static Rectangle rect,distantRect;
	static Circle smallCircle,bigCircle;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		smallCircle=new Circle(new AbsolutePoint(100,100),25);
		bigCircle=new Circle(new AbsolutePoint(100,100),50);
		rect=new Rectangle(new AbsolutePoint(50,100),150,100);
		distantRect=new Rectangle(new AbsolutePoint(1500,1000),10,10);
		
		smallCircle.setLevelOfDetail(10);
		bigCircle.setLevelOfDetail(10);
		rect.setLevelOfDetail(10);

		distantRect.setLevelOfDetail(10);
	}

	@BeforeEach
	void setUp() throws Exception {
		smallCircle.updateOrInitOutline();
		bigCircle.updateOrInitOutline();
		rect.updateOrInitOutline();
		distantRect.updateOrInitOutline();
	}

	@Test
	void testNegativeIntersection() {
		
		assertFalse(rect.intersects(distantRect));
		assertFalse(distantRect.intersects(rect));
		assertFalse(bigCircle.intersects(distantRect));
		assertFalse(distantRect.intersects(bigCircle));
	}
	
	@Test
	void testPostiveIntersection() {
		assertTrue(bigCircle.intersects(smallCircle));
		assertTrue(smallCircle.intersects(bigCircle));
		
		assertTrue(rect.intersects(bigCircle));
		assertTrue(bigCircle.intersects(rect));
		assertTrue(rect.intersects(smallCircle));
		assertTrue(smallCircle.intersects(rect));
	}
	
	@Test
	void testSelfIntersection() {
		assertFalse(smallCircle.intersects(smallCircle));

		assertFalse(rect.intersects(rect));
	}
	@Test
	void testSelfCoverage() {
		assertTrue(smallCircle.covers(smallCircle));

		assertTrue(rect.covers(rect));
	}
	@Test
	void testPositiveCovers() {
		assertFalse(smallCircle.covers(bigCircle));
		assertTrue(rect.covers(smallCircle));
	}
	
	@Test
	void testNegativeCovers() {
		assertTrue(bigCircle.covers(smallCircle));
		assertFalse(rect.covers(bigCircle));
	}
}
