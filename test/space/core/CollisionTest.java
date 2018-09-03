/**
 * @Author Leonhard Applis
 * @Created 03.09.2018
 * @Package space.core
 */
package space.core;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import space.advanced.DistantGalaxy;
import space.advanced.FixStar;

class CollisionTest {
	
	
	Star anker1= new Star("anker1", null, 0, 0, 25);
	Star anker2= new Star("anker2", null, 100, 0, 25);
	
	
	@Test
	void negativeCollision() {
		assertEquals(false, anker1.collides(anker2));
		Planet a = new Planet("NoCollider",anker1,null,25,25,0);
		assertEquals(false, anker1.collides(a));
		assertEquals(false, anker2.collides(a));
		//SymetryCheck
		assertEquals(true, anker1.collides(a)==a.collides(anker1));
		assertEquals(true, anker1.collides(anker2)==anker2.collides(anker1));
	}
	
	@Test
	void positiveCollision() {
		Planet a = new Planet("Collider",anker1,null,25,5,0);
		assertEquals(true, anker1.collides(a));
		assertEquals(false, anker2.collides(a));
	}
	
	@Test
	void pointBlankCollision() {
		Planet a = new Planet("Collider",anker1,null,25,0,0);
		assertEquals(true, anker1.collides(a));
		assertEquals(false, anker2.collides(a));
	}
	
	@Test
	void noSelfCollision() {
		assertEquals(false, anker1.collides(anker1));
		assertEquals(false, anker2.collides(anker2));
	}
	
	@Test
	void exactTangenting() {
		Planet a = new Planet("Collider",anker1,null,50,50,0);
		assertEquals(false, anker1.collides(a));
		assertEquals(false, anker2.collides(a));
	}
	
	@Test
	void multipleCollisions() {
		Star a = new Star("Collider",null,50,0,150);
		assertEquals(true, anker1.collides(a));
		assertEquals(true, a.collides(anker1)&&a.collides(anker2));
		assertEquals(true, anker2.collides(a));
	}
	
	//Check Fixstars and Distant Galaxy
	@Test
	void uncollidables() {
		FixStar a = new FixStar("a", 1, 1, 1);
		assertEquals(false,a.collides(anker1));
		assertEquals(false, anker1.collides(a));
	}
	
}
