package junit.spaceobjects;


import static junit.testhelpers.FakeGeometryFactory.fakeAbsolutePoint;
import static junit.testhelpers.FakeManagerFactory.freshNewCollisionManager;
import static junit.testhelpers.FakeSpaceObjectFactory.fakeStar;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import interfaces.geom.Point;
import interfaces.logical.MovingUpdatingObject;
import junit.fakes.FakeMovingSpaceObject;
import junit.fakes.interfaces.FakeDestructibleObject;
import logic.manager.CollisionManager;
import space.advanced.Asteroid;
import space.advanced.AsteroidBelt;
import space.core.Star;

public class AsteroidBeltTests {

	@Test
	public void testBuilder_EverythingIsFine_ShouldBeBuild() {
		Star anchor = fakeStar(0,0);
		
		AsteroidBelt testObject = (new AsteroidBelt.Builder("Test",anchor))
				.distance(50)
				.asteroids(10)
				.speed(Math.PI)
				.build();
		
		assertEquals(50,testObject.getDistance());
		assertEquals(10,testObject.getAllChildren().size());
	}
	
	@Test
	public void testInit_IsBuildOnParent_shouldBeTrue() {
		Star anchor = fakeStar(0,0);
		
		AsteroidBelt testObject = (new AsteroidBelt.Builder("Test",anchor)
				.distance(50)
				.asteroids(100)
				.speed(Math.PI).build());
		
		assertEquals(0,testObject.distanceTo(anchor),0);
	}
	
	@Test
	public void testInit_CheckTrabants_ShouldAllBeAsteroids() {
		Star anchor = fakeStar(0,0);
		
		AsteroidBelt testObject = (new AsteroidBelt.Builder("Test",anchor))
				.distance(50)
				.asteroids(20)
				.build();
		
		for(MovingUpdatingObject r : testObject.getTrabants())
			assertTrue(r instanceof Asteroid);
	}
	
	@Test
	public void testBuilder_NegativeAsteroids_shouldThrowError() {
		Star anchor = fakeStar(0,0);
		
		assertThrows(IllegalArgumentException.class,
				() ->  (new AsteroidBelt.Builder("Test",anchor))
				.asteroids(-50));	
	}
	

	@Test
	public void testBuilder_NegativeDistance_shouldThrowError() {
		Star anchor = fakeStar(0,0);
		
		assertThrows(IllegalArgumentException.class,
				() ->  (new AsteroidBelt.Builder("Test",anchor))
				.distance(-5));
	}
	
	@Test
	public void testBuilder_NoParent_shouldThrowError() {
		assertThrows(
				IllegalArgumentException.class,
				(() ->  new AsteroidBelt.Builder("Error", null)));
	}
	
	@Test
	public void testBuilder_NoName_shouldThrowError() {
		Star anchor = fakeStar(0,0);
		
		assertThrows(IllegalArgumentException.class,
				() ->  new AsteroidBelt.Builder("",anchor));		
	}
	
	@Test
	public void testBuilder_NullName_shouldThrowError() {
		Star anchor = fakeStar(0,0);
		
		assertThrows(IllegalArgumentException.class,
				() ->  new AsteroidBelt.Builder(null,anchor));		
	}
	
	@ParameterizedTest
	@ValueSource(ints = {-1000,-100,0,100,1000})
	public void testMove_parentMoved_shouldBeOnParent(int offset) {
		Star anchor = fakeStar(0,0);
		AsteroidBelt testObject = (new AsteroidBelt.Builder("Test",anchor))
				.build();
		
		Point newCenter = fakeAbsolutePoint(offset,offset);
		anchor.setCenter(newCenter);
		
		testObject.move(newCenter);
		
		assertEquals(newCenter,testObject.getCenter());
	}
	
	@Test
	public void testCollision_doesNotCollide() {
		CollisionManager mnger = freshNewCollisionManager();
		FakeDestructibleObject fakeDestructibleObject = new FakeDestructibleObject();
		Star anchor = fakeStar(0,0);
		AsteroidBelt testObject = (new AsteroidBelt.Builder("Test",anchor))
				.build();
		
		mnger.registerItem(fakeDestructibleObject);
		mnger.registerItem(testObject);
		
		mnger.doCollisions();
		
		assertFalse(fakeDestructibleObject.destroyed);
	}
	
	@Test
	public void testMove_childrenMoved_shouldBeMoved() {
		Star anchor = fakeStar(0,0);
		AsteroidBelt testObject = (new AsteroidBelt.Builder("Test",anchor))
				.build();
		
		FakeMovingSpaceObject fake = new FakeMovingSpaceObject();
		testObject.getTrabants().add(fake);
		
		testObject.move(anchor.getCenter());
		
		assertTrue(fake.moved);
	}
	
}
