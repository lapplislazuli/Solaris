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
		SpaceObject anchor= fakeStar(0,0,25);
		
		Planet planet = (new Planet.Builder("NoCollider", anchor))
				.size(15)
				.distance(1000)
				.levelOfDetail(20)
				.speed(0)
				.build();
		
		planet.updateHitbox();
		
		assertFalse(anchor.collides(planet));
	}
	
	@Test
	void testCollision_noCollision_shouldBeSymmetric() {
		SpaceObject anchor= fakeStar(0,0,25);
		SpaceObject otheranchor= fakeStar("otheranchor",100,0,25);
		
		Planet planet = (new Planet.Builder("NoCollider", anchor))
				.size(15)
				.distance(1000)
				.levelOfDetail(20)
				.build();
		
		planet.updateHitbox();
		
		assertTrue(anchor.collides(planet)==planet.collides(anchor));
		assertTrue(anchor.collides(otheranchor)==otheranchor.collides(anchor));
	}
	
	@Test
	void testCollision_collision_shouldCollide() {
		SpaceObject anchor= fakeStar(0,0,25);
		Planet planet = (new Planet.Builder("NoCollider", anchor))
				.size(150)
				.distance(10)
				.levelOfDetail(20)
				.build();
		
		planet.updateHitbox();
		
		assertTrue(anchor.collides(planet));
	}
	
	@Test
	void testCollision_ElementsAreInEachOther_shouldCollide() {
		SpaceObject anchor= fakeStar(0,0,25);
		Planet planet = (new Planet.Builder("Collider", anchor))
				.size(25)
				.distance(0)
				.levelOfDetail(20)
				.build();
		
		planet.updateHitbox();
		
		assertTrue(anchor.collides(planet));
	}
	
	@Test
	void testCollision_selfCollision_shouldNotCollide() {
		SpaceObject anchor= fakeStar(0,0,25);
		
		assertFalse(anchor.collides(anchor));
	}
	
	@Test
	void testCollision_ElementsAreTangenting_shouldCollide() {
		SpaceObject anchor= fakeStar(0,0,25);
		Planet planet = (new Planet.Builder("Collider", anchor))
				.size(50)
				.distance(50)
				.levelOfDetail(20)
				.build();
		
		planet.updateHitbox();
		
		assertTrue(anchor.collides(planet));
	}
	
	@Test
	void testCollision_multipleItemsCollide_allCollide() {
		SpaceObject anchor= fakeStar(0,0,25);
		SpaceObject otheranchor= fakeStar("otheranchor",100,0,25);
		Star collidingStar = new Star("Collider",null,new AbsolutePoint(50,0),150);
		
		collidingStar.updateHitbox();
		
		assertTrue(anchor.collides(collidingStar));
		assertTrue(collidingStar.collides(anchor)&&collidingStar.collides(otheranchor));
		assertTrue(otheranchor.collides(collidingStar));
	}
	
	@Test
	void testCollision_NotCollideableItem_shouldAllCollide() {
		SpaceObject anchor= fakeStar(0,0,25);
		FixStar uncollidableFixStar = new FixStar("a", 1, 1, 1);
		
		uncollidableFixStar.shape.updateOrInitOutline();
		
		assertFalse(uncollidableFixStar.collides(anchor));
		assertTrue(anchor.collides(uncollidableFixStar));
	}
	
	private static Star fakeStar(int xpos,int ypos,int size) {
		Star anchor = new Star("Anker", null, new AbsolutePoint(xpos,ypos),size);
		anchor.shape.updateOrInitOutline();
		return anchor;
	}
	
	private static SpaceObject fakeStar(String name,int xpos,int ypos,int size) {
		Star anchor = new Star(name, null, new AbsolutePoint(xpos,ypos),size);
		anchor.shape.updateOrInitOutline();
		return anchor;
	}
	
}
