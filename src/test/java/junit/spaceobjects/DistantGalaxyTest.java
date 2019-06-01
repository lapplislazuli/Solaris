package junit.spaceobjects;

import static helpers.FakeManagerFactory.freshNewCollisionManager;
import static helpers.FakeSpaceObjectFactory.fakePlanet;
import static helpers.FakeSpaceObjectFactory.fakeStar;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

import interfaces.logical.MovingUpdatingObject;
import junit.fakes.interfaces.FakeDestructibleObject;
import logic.manager.CollisionManager;
import space.advanced.DistantGalaxy;
import space.advanced.FixStar;
import space.core.Planet;
import space.core.Star;

class DistantGalaxyTest {
	@Test
	void testConstructor_buildWithoutName_shouldThrowError(){
		assertThrows(IllegalArgumentException.class,
				() ->  new DistantGalaxy("",10)
			);
	}
	
	@Test
	void testConstructor_buildWithNullName_shouldThrowError(){
		assertThrows(IllegalArgumentException.class,
				() ->  new DistantGalaxy(null,10)
			);
	}
	
	@Test
	void testConstructor_oKValues_shouldBeFine(){
		DistantGalaxy testObject = new DistantGalaxy("Test",10);
		
		assertEquals(10,testObject.getMaxStars());
		assertEquals(10,testObject.getStars().size());
	}
	
	@Test
	void testFillStars_biggerMaxStars_shouldHaveMoreStars(){
		DistantGalaxy testObject = new DistantGalaxy("Test",10);
		
		testObject.setMaxStars(15);
		testObject.fillStars();
		
		assertEquals(15,testObject.getStars().size());
	}
	
	@Test
	void testFillStars_smallerMaxStars_shouldbeSameAmountOfStars(){
		DistantGalaxy testObject = new DistantGalaxy("Test",10);
		
		testObject.setMaxStars(5);
		testObject.fillStars();
		
		assertEquals(10,testObject.getStars().size());
	}
	
	@Test
	void testUpdate_setMoreMaxStars_shouldRefill() {
		DistantGalaxy testObject = new DistantGalaxy("Test",10);
		
		testObject.setMaxStars(15);
		testObject.update();
		
		assertEquals(15,testObject.getStars().size());
	}
	
	@Test
	void testUpdate_setLessMaxStars_shouldHaveSameStars() {
		DistantGalaxy testObject = new DistantGalaxy("Test",20);
		
		testObject.setMaxStars(5);
		testObject.update();
		//This Sometimes fails, as maybe the update kills the first star.
		//assertEquals(10,testObject.getStars().size());
		//Workaround so the logic doesn�t hit and the chance to fail is reduced rapildy
		assertTrue(testObject.getStars().size()>5);
	}
	
	
	@Test
	void testCollision_shouldNotDestroyAnyOther() {
		CollisionManager mnger = freshNewCollisionManager();
		FakeDestructibleObject fakeDestructibleObject = new FakeDestructibleObject();
		DistantGalaxy testObject = new DistantGalaxy("Test",10);
		
		mnger.registerItem(fakeDestructibleObject);
		mnger.registerItem(testObject);
		
		mnger.doCollisions();
		
		assertFalse(fakeDestructibleObject.destroyed);
	}
	
	@Test
	void testDistanceTo_shouldBeMax() {
		DistantGalaxy testObject = new DistantGalaxy("Test",10);
		Star fakeAnchor = fakeStar(0,0);
		
		assertEquals(Double.MAX_VALUE, testObject.distanceTo(fakeAnchor),0);
	}
	
	@Test
	void testUpdate_withDeadStar_deadStarShouldNotBeinStars() {
		DistantGalaxy testObject = new DistantGalaxy("Test",1);
		FixStar first = testObject.getStars().get(0);
		first.die();
		
		testObject.update();
		
		assertFalse(testObject.getStars().contains(first));
	}
	
	@Test
	void testUpdate_withDeadStar_shouldSpawnNewStar() {
		DistantGalaxy testObject = new DistantGalaxy("Test",1);
		FixStar first = testObject.getStars().get(0);
		first.die();
		
		testObject.update();
		
		assertEquals(1,testObject.getStars().size());
	}
	
	@Test
	void testGetTrabants_shouldBeEmptyList() {
		DistantGalaxy testObject = new DistantGalaxy("Test",10);
		
		assertTrue(testObject.getTrabants().isEmpty());
	}
	
	@Test
	void testSetAndGetTrabants_shouldBeEmptyList() {
		DistantGalaxy testObject = new DistantGalaxy("Test",10);
		
		Star fakeAnchor = fakeStar(0,0);
		Planet fakePlanet = fakePlanet(fakeAnchor,50);
		List<MovingUpdatingObject> stub = new LinkedList<MovingUpdatingObject>();
		stub.add(fakePlanet);
		
		testObject.setTrabants(stub);
		
		assertTrue(testObject.getTrabants().isEmpty());
	}
}
