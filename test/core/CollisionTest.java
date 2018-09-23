package core;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import geom.Circle;
import geom.Point;
import space.advanced.DistantGalaxy;
import space.advanced.FixStar;
import space.core.Planet;
import space.core.Star;

class CollisionTest {
	
	
	static Star starOne, starTwo;
	@BeforeAll
	static void initAnkers() {
		starOne= new Star("anker1", null, new Point(0, 0),new Circle( new Point(0, 0), 25));
		starTwo= new Star("anker2", null,new Point(100, 0),new Circle( new Point(100, 0),25));
		starOne.area.initOutline();
		starTwo.area.initOutline();
	}
	
	
	@Test
	void negativeCollision() {
		assertFalse(starOne.collides(starTwo));
		Planet a = new Planet("NoCollider",starOne,null,5,25,0);
		assertFalse(starOne.collides(a));
		assertEquals(false, starTwo.collides(a));
		//SymetryCheck
		assertTrue(starOne.collides(a)==a.collides(starOne));
		assertTrue(starOne.collides(starTwo)==starTwo.collides(starOne));
	}
	
	@Test
	void positiveCollision() {
		Planet a = new Planet("Collider",starOne,null,150,10,0);
		a.area.initOutline();
		assertTrue(starOne.collides(a));
		assertTrue(starTwo.collides(a));
	}
	
	@Test
	void pointBlankCollision() {
		Planet a = new Planet("Collider",starOne,null,25,0,0);
		a.area.initOutline();
		assertTrue(starOne.collides(a));
		assertFalse(starTwo.collides(a));
	}
	
	@Test
	void noSelfCollision() {
		assertFalse(starOne.collides(starOne));
		assertFalse(starTwo.collides(starTwo));
	}
	
	@Test
	void exactTangenting() {
		Planet a = new Planet("Collider",starOne,null,50,50,0);
		a.area.initOutline();
		assertTrue(starOne.collides(a));
		assertTrue(starTwo.collides(a));
	}
	
	@Test
	void multipleCollisions() {
		Star a = new Star("Collider",null,new Point(50,0), new Circle(new Point(50,0),150));
		a.area.initOutline();
		assertTrue(starOne.collides(a));
		assertTrue(a.collides(starOne)&&a.collides(starTwo));
		assertTrue(starTwo.collides(a));
	}
	
	//Check Fixstars and Distant Galaxy
	@Test
	void uncollidables() {
		FixStar a = new FixStar("a", 1, 1, 1);
		assertEquals(false,a.collides(starOne));
		assertEquals(true, starOne.collides(a));
	}
	
}
