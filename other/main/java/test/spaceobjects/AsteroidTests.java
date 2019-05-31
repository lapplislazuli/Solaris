package spaceobjects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;

import fakes.interfaces.FakeDestructibleObject;
import logic.manager.CollisionManager;
import space.advanced.Asteroid;
import space.core.SpaceObject;

import static helpers.FakeManagerFactory.freshNewCollisionManager;
import static helpers.FakeSpaceObjectFactory.*;

public class AsteroidTests {
	
	@Test
	public void testConstructors_buildAsTrash_ShouldBeTrash(){
		SpaceObject root = fakeStar(0,0);
		
		Asteroid testObject = new Asteroid("test",root,30,10,Asteroid.Type.TRASH);
		
		assertEquals(Asteroid.Type.TRASH,testObject.getType());
	}
	
	@Test
	public void testConstructors_buildWithoutType_shouldBeRandomAndNotTrash(){
		SpaceObject root = fakeStar(0,0);
		
		Asteroid testObject = new Asteroid("test",root,30,10);
		
		assertNotEquals(Asteroid.Type.TRASH,testObject.getType());
		assertNotEquals(null,testObject.getType());
	}
	
	@Test
	public void testConstructors_NullType_ShouldThrowNullPointer(){
		SpaceObject root = fakeStar(0,0);
		
		assertThrows(NullPointerException.class,
				() -> new Asteroid("test",root,30,10,null));
	}
	
	@Test
	public void testConstructors_IsInParentsChildren(){
		SpaceObject root = fakeStar(0,0);
		
		Asteroid testObject = new Asteroid("test",root,30,10);
		
		assertTrue(root.getTrabants().contains(testObject));
	}
	
	@Test
	public void testConstructors_isNotOrphaned(){
		SpaceObject root = fakeStar(0,0);
		
		Asteroid testObject = new Asteroid("test",root,30,10);
		
		assertFalse(testObject.isOrphan());
	}
	
	@Test
	public void testRemove_isNotInParentsChildren() {
		SpaceObject root = fakeStar(0,0);
		Asteroid testObject = new Asteroid("test",root,30,10);
		
		testObject.remove();
		
		assertFalse(root.getTrabants().contains(testObject));
	}
	
	@Test
	public void testRemove_isOrphaned() {
		SpaceObject root = fakeStar(0,0);
		Asteroid testObject = new Asteroid("test",root,30,10);
		
		testObject.remove();
		
		assertTrue(testObject.isOrphan());
	}
	
	@Test
	public void testRemove_doubleRemove_shouldThrowNullpoint() {
		SpaceObject root = fakeStar(0,0);
		Asteroid testObject = new Asteroid("test",root,30,10);
		
		testObject.remove();
		
		assertThrows(NullPointerException.class,
				() -> testObject.remove());
	}
	
	@Test
	public void testCollision_shouldBeRemoved() {
		CollisionManager mnger = freshNewCollisionManager();
		FakeDestructibleObject fakeDestructibleObject = new FakeDestructibleObject();
		
		SpaceObject root = fakeStar(0,0);
		Asteroid testObject = new Asteroid("test",root,30,10);
		
		mnger.registerItem(fakeDestructibleObject);
		mnger.registerItem(testObject);
		
		mnger.doCollisions();
		
		assertFalse(root.getTrabants().contains(testObject));
	}
	
	@Test 
	public void testDestruct_isNotInParentsChildren() {
		SpaceObject root = fakeStar(0,0);
		Asteroid testObject = new Asteroid("test",root,30,10);
		
		testObject.destruct();
		
		assertFalse(root.getTrabants().contains(testObject));
	}
	
	@Test
	public void testDestruct_doubleDestruct_shouldSucceed() {
		SpaceObject root = fakeStar(0,0);
		Asteroid testObject = new Asteroid("test",root,30,10);
		
		testObject.destruct();
		testObject.destruct();
		
		assertFalse(root.getTrabants().contains(testObject));
	}
	
	@Test
	public void testDestruct_isOrphaned() {
		SpaceObject root = fakeStar(0,0);
		Asteroid testObject = new Asteroid("test",root,30,10);
		
		testObject.destruct();
		
		assertTrue(testObject.isOrphan());
	}
}
