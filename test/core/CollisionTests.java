package core;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import space.core.Planet;
import space.core.SpaceObject;

import static helpers.FakeSpaceObjectFactory.*;

class CollisionTests {
	
	@Test
	void testCollision_noCollision_shouldNotCollide() {
		SpaceObject anchor= fakeStar(0,0,25);
		
		Planet planet = fakePlanet(anchor,1000,15);
		
		assertFalse(anchor.collides(planet));
	}
	
	@Test
	void testCollision_noCollision_shouldBeSymmetric() {
		SpaceObject anchor= fakeStar(0,0,25);
		
		Planet planet = fakePlanet(anchor,1000,15);
		
		assertTrue(anchor.collides(planet)==planet.collides(anchor));
	}
	
	@Test
	void testCollision_collision_shouldCollide() {
		SpaceObject anchor= fakeStar(0,0,25);
		Planet collider = fakePlanet(anchor,10,150);
		
		assertTrue(anchor.collides(collider));
	}
	
	@Test
	void testCollision_ElementsAreInEachOther_innerShouldCollideOuter() {
		SpaceObject anchor= fakeStar(0,0,25);
		Planet inAnchor = fakePlanet(anchor,0,25);
		
		assertTrue(anchor.collides(inAnchor));
	}
	
	@Test
	void testCollision_ElementsAreInEachOther_outerShouldCollideInner() {
		SpaceObject anchor= fakeStar(0,0,25);
		Planet inAnchor = fakePlanet(anchor,0,25);
		
		assertTrue(inAnchor.collides(anchor));
	}
	
	@Test
	void testCollision_selfCollision_shouldNotCollide() {
		SpaceObject anchor= fakeStar(0,0,25);
		
		assertFalse(anchor.collides(anchor));
	}
	
	@Test
	void testCollision_ElementsAreTangenting_shouldCollide() {
		SpaceObject anchor= fakeStar(0,0,25);
		Planet toucher =fakePlanet (anchor,50,50);

		assertTrue(anchor.collides(toucher));
	}
	
	@Test
	void testCollision_multipleItemsCollide_allCollide() {
		SpaceObject anchor= fakeStar(0,0,25);
		SpaceObject otheranchor= fakeStar("otheranchor",100,0,25);
		SpaceObject thirdCollider = fakeStar("otheranchor",50,0,150);
		
		assertTrue(anchor.collides(thirdCollider));
		assertTrue(thirdCollider.collides(anchor)&&thirdCollider.collides(otheranchor));
		assertTrue(otheranchor.collides(thirdCollider));
	}
	
	@Test
	void testCollision_NotCollideableItem_colliderShouldCollide() {
		SpaceObject anchor= fakeStar(0,0,25);
		
		SpaceObject uncollider = fakeFixStar(1,1);
		
		uncollider.getShape().updateOrInitOutline();
		
		assertTrue(anchor.collides(uncollider));
	}
	
	@Test
	void testCollision_NotCollideableItem_uncolliderShouldNotCollide() {
		SpaceObject anchor= fakeStar(0,0,25);
		
		SpaceObject uncollider = fakeFixStar(1,1);
		
		assertFalse(uncollider.collides(anchor));
	}
	
	
}
