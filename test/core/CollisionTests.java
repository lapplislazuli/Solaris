package core;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import geom.AbsolutePoint;
import space.advanced.FixStar;
import space.core.Planet;
import space.core.Star;

class CollisionTests {
	
	
	static Star starOne, starTwo;
	
	@BeforeAll
	static void initAnkers() {
		starOne= new Star("anker1", null, new AbsolutePoint(0, 0),25);
		starTwo= new Star("anker2", null,new AbsolutePoint(100, 0),25);
		starOne.shape.updateOrInitOutline();
		starTwo.shape.updateOrInitOutline();
	}
	
	@Test
	void testCollision_noCollision_shouldNotCollide() {
		assertFalse(starOne.collides(starTwo));
		Planet planet = (new Planet.Builder("NoCollider", starOne))
				.size(15)
				.distance(1000)
				.levelOfDetail(20)
				.speed(0)
				.build();
		
		planet.updateHitbox();
		
		assertFalse(starOne.collides(planet));
	}
	
	@Test
	void testCollision_noCollision_shouldBeSymmetric() {
		Planet planet = (new Planet.Builder("NoCollider", starOne))
				.size(15)
				.distance(1000)
				.levelOfDetail(20)
				.build();
		
		planet.updateHitbox();
		
		assertTrue(starOne.collides(planet)==planet.collides(starOne));
		assertTrue(starOne.collides(starTwo)==starTwo.collides(starOne));
	}
	
	@Test
	void testCollision_collision_shouldCollide() {
		Planet planet = (new Planet.Builder("NoCollider", starOne))
				.size(150)
				.distance(10)
				.levelOfDetail(20)
				.build();
		
		planet.updateHitbox();
		
		assertTrue(starOne.collides(planet));
	}
	
	@Test
	void testCollision_ElementsAreInEachOther_shouldCollide() {
		Planet planet = (new Planet.Builder("Collider", starOne))
				.size(25)
				.distance(0)
				.levelOfDetail(20)
				.build();
		
		planet.updateHitbox();
		
		assertTrue(starOne.collides(planet));
	}
	
	@Test
	void testCollision_selfCollision_shouldNotCollide() {
		assertFalse(starOne.collides(starOne));
	}
	
	@Test
	void testCollision_ElementsAreTangenting_shouldCollide() {
		Planet planet = (new Planet.Builder("Collider", starOne))
				.size(50)
				.distance(50)
				.levelOfDetail(20)
				.build();
		
		planet.updateHitbox();
		
		assertTrue(starOne.collides(planet));
	}
	
	@Test
	void testCollision_multipleItemsCollide_allCollide() {
		Star collidingStar = new Star("Collider",null,new AbsolutePoint(50,0),150);
		
		collidingStar.updateHitbox();
		
		assertTrue(starOne.collides(collidingStar));
		assertTrue(collidingStar.collides(starOne)&&collidingStar.collides(starTwo));
		assertTrue(starTwo.collides(collidingStar));
	}
	
	@Test
	void testCollision_NotCollideableItem_shouldAllCollide() {
		FixStar uncollidableFixStar = new FixStar("a", 1, 1, 1);
		
		uncollidableFixStar.shape.updateOrInitOutline();
		
		assertFalse(uncollidableFixStar.collides(starOne));
		assertTrue(starOne.collides(uncollidableFixStar));
	}
}
