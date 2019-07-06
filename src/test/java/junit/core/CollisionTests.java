package junit.core;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import space.core.Planet;
import space.core.SpaceObject;

import static junit.testhelpers.FakeSpaceObjectFactory.*;

class CollisionTests {

	@Tag("core")
	@Tag("logic")
	@RepeatedTest(10)
	@Test
	void testCollision_noCollision_shouldNotCollide() {
		SpaceObject anchor= fakeStar(0,0,25);
		
		Planet planet = fakePlanet(anchor,1000,15);
		
		assertFalse(anchor.collides(planet));
	}

	@Tag("core")
	@Tag("logic")
	@RepeatedTest(10)
	@Test
	void testCollision_noCollision_shouldBeSymmetric() {
		SpaceObject anchor= fakeStar(0,0,25);
		
		Planet planet = fakePlanet(anchor,1000,15);
		
		assertTrue(anchor.collides(planet)==planet.collides(anchor));
	}

	@Tag("core")
	@Tag("logic")
	@RepeatedTest(10)
	@Test
	void testCollision_collision_shouldCollide() {
		SpaceObject anchor= fakeStar(0,0,25);
		Planet collider = fakePlanet(anchor,10,150);
		
		assertTrue(anchor.collides(collider));
	}
	
	@Tag("core")
	@Tag("logic")
	@RepeatedTest(10)
	@Test
	void testCollision_ElementsAreInEachOther_innerShouldCollideOuter() {
		SpaceObject anchor= fakeStar(0,0,25);
		Planet inAnchor = fakePlanet(anchor,0,25);
		
		assertTrue(anchor.collides(inAnchor));
	}
	@Tag("core")
	@Tag("logic")
	@RepeatedTest(10)
	@Test
	void testCollision_ElementsAreInEachOther_outerShouldCollideInner() {
		SpaceObject anchor= fakeStar(0,0,25);
		Planet inAnchor = fakePlanet(anchor,0,25);
		
		assertTrue(inAnchor.collides(anchor));
	}
	
	@Tag("core")
	@Tag("logic")
	@RepeatedTest(10)
	@Test
	void testCollision_selfCollision_shouldNotCollide() {
		SpaceObject anchor= fakeStar(0,0,25);
		
		assertFalse(anchor.collides(anchor));
	}
	
	@Tag("core")
	@Tag("logic")
	@RepeatedTest(10)
	@Test
	void testCollision_ElementsAreTangenting_shouldCollide() {
		SpaceObject anchor= fakeStar(0,0,25);
		Planet toucher =fakePlanet (anchor,50,50);

		assertTrue(anchor.collides(toucher));
	}
	
	@Tag("core")
	@Tag("logic")
	@RepeatedTest(10)
	@Test
	void testCollision_multipleItemsCollide_allCollide() {
		SpaceObject anchor= fakeStar(0,0,25);
		SpaceObject otheranchor= fakeStar("otheranchor",100,0,25);
		SpaceObject thirdCollider = fakeStar("otheranchor",50,0,150);
		
		assertTrue(anchor.collides(thirdCollider));
		assertTrue(thirdCollider.collides(anchor)&&thirdCollider.collides(otheranchor));
		assertTrue(otheranchor.collides(thirdCollider));
	}
	
	@Tag("core")
	@Tag("logic")
	@RepeatedTest(10)
	@Test
	void testCollision_NotCollideableItem_colliderShouldCollide() {
		SpaceObject anchor= fakeStar(0,0,25);
		
		SpaceObject uncollider = fakeFixStar(1,1);
		
		uncollider.getShape().updateOrInitOutline();
		
		assertTrue(anchor.collides(uncollider));
	}
	
	@Tag("core")
	@Tag("logic")
	@RepeatedTest(10)
	@Test
	void testCollision_NotCollideableItem_uncolliderShouldNotCollide() {
		SpaceObject anchor= fakeStar(0,0,25);
		
		SpaceObject uncollider = fakeFixStar(1,1);
		
		assertFalse(uncollider.collides(anchor));
	}
	
	
}
