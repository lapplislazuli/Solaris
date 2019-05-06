package core;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import geom.AbsolutePoint;
import space.advanced.FixStar;
import space.core.Planet;
import space.core.SpaceObject;
import space.core.Star;

class CollisionTests {
	
	@Test
	void testCollision_noCollision_shouldNotCollide() {
		SpaceObject ancor= fakeStar(0,0,25);
		
		Planet planet = (new Planet.Builder("NoCollider", ancor))
				.size(15)
				.distance(1000)
				.levelOfDetail(20)
				.speed(0)
				.build();
		
		planet.updateHitbox();
		
		assertFalse(ancor.collides(planet));
	}
	
	@Test
	void testCollision_noCollision_shouldBeSymmetric() {
		SpaceObject ancor= fakeStar(0,0,25);
		SpaceObject otherAncor= fakeStar("otherAncor",100,0,25);
		
		Planet planet = (new Planet.Builder("NoCollider", ancor))
				.size(15)
				.distance(1000)
				.levelOfDetail(20)
				.build();
		
		planet.updateHitbox();
		
		assertTrue(ancor.collides(planet)==planet.collides(ancor));
		assertTrue(ancor.collides(otherAncor)==otherAncor.collides(ancor));
	}
	
	@Test
	void testCollision_collision_shouldCollide() {
		SpaceObject ancor= fakeStar(0,0,25);
		Planet planet = (new Planet.Builder("NoCollider", ancor))
				.size(150)
				.distance(10)
				.levelOfDetail(20)
				.build();
		
		planet.updateHitbox();
		
		assertTrue(ancor.collides(planet));
	}
	
	@Test
	void testCollision_ElementsAreInEachOther_shouldCollide() {
		SpaceObject ancor= fakeStar(0,0,25);
		Planet planet = (new Planet.Builder("Collider", ancor))
				.size(25)
				.distance(0)
				.levelOfDetail(20)
				.build();
		
		planet.updateHitbox();
		
		assertTrue(ancor.collides(planet));
	}
	
	@Test
	void testCollision_selfCollision_shouldNotCollide() {
		SpaceObject ancor= fakeStar(0,0,25);
		
		assertFalse(ancor.collides(ancor));
	}
	
	@Test
	void testCollision_ElementsAreTangenting_shouldCollide() {
		SpaceObject ancor= fakeStar(0,0,25);
		Planet planet = (new Planet.Builder("Collider", ancor))
				.size(50)
				.distance(50)
				.levelOfDetail(20)
				.build();
		
		planet.updateHitbox();
		
		assertTrue(ancor.collides(planet));
	}
	
	@Test
	void testCollision_multipleItemsCollide_allCollide() {
		SpaceObject ancor= fakeStar(0,0,25);
		SpaceObject otherAncor= fakeStar("otherAncor",100,0,25);
		Star collidingStar = new Star("Collider",null,new AbsolutePoint(50,0),150);
		
		collidingStar.updateHitbox();
		
		assertTrue(ancor.collides(collidingStar));
		assertTrue(collidingStar.collides(ancor)&&collidingStar.collides(otherAncor));
		assertTrue(otherAncor.collides(collidingStar));
	}
	
	@Test
	void testCollision_NotCollideableItem_shouldAllCollide() {
		SpaceObject ancor= fakeStar(0,0,25);
		FixStar uncollidableFixStar = new FixStar("a", 1, 1, 1);
		
		uncollidableFixStar.shape.updateOrInitOutline();
		
		assertFalse(uncollidableFixStar.collides(ancor));
		assertTrue(ancor.collides(uncollidableFixStar));
	}
	
	private static Star fakeStar(int xpos,int ypos,int size) {
		Star ancor = new Star("Anker", null, new AbsolutePoint(xpos,ypos),size);
		ancor.shape.updateOrInitOutline();
		return ancor;
	}
	
	private static SpaceObject fakeStar(String name,int xpos,int ypos,int size) {
		Star ancor = new Star(name, null, new AbsolutePoint(xpos,ypos),size);
		ancor.shape.updateOrInitOutline();
		return ancor;
	}
	
}
