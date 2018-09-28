package geom;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BaseGeomCollisionTest {
	
	static Rectangle rect,distantRect;
	static Circle smallCircle,bigCircle;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		smallCircle=new Circle(new AbsolutePoint(100,100),25);
		bigCircle=new Circle(new AbsolutePoint(100,100),50);
		rect=new Rectangle(new AbsolutePoint(50,100),150,100);
		distantRect=new Rectangle(new AbsolutePoint(1500,1000),10,10);
		
		smallCircle.levelOfDetail=10;
		bigCircle.levelOfDetail=10;
		rect.levelOfDetail=10;

		distantRect.levelOfDetail=10;
	}

	@BeforeEach
	void setUp() throws Exception {
		smallCircle.initOutline();
		bigCircle.initOutline();
		rect.initOutline();
		distantRect.initOutline();
	}

	@Test
	void testIntersects() {
		assertTrue(bigCircle.intersects(smallCircle));
		assertTrue(smallCircle.intersects(bigCircle));
		
		assertTrue(rect.intersects(bigCircle));
		assertTrue(bigCircle.intersects(rect));
		assertTrue(rect.intersects(smallCircle));
		assertTrue(smallCircle.intersects(rect));
		
		assertFalse(rect.intersects(distantRect));
		assertFalse(distantRect.intersects(rect));
		assertFalse(bigCircle.intersects(distantRect));
		assertFalse(distantRect.intersects(bigCircle));
	}

	@Test
	void testCovers() {
		assertTrue(bigCircle.covers(smallCircle));
		assertFalse(smallCircle.covers(bigCircle));
		
		assertTrue(rect.covers(smallCircle));
		assertFalse(rect.covers(bigCircle));
	}
}
