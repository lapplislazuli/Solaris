package junit.effects;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import drawing.EmptyJFXDrawingInformation;
import geom.AbsolutePoint;
import geom.Circle;
import logic.manager.ManagerRegistry;
import space.effect.Explosion;

class ExplosionTests implements SharedEffectTests {
	
	@BeforeEach
	public void initManagers() {
		ManagerRegistry.getInstance();
	}
	
	@AfterEach
	public void resetManagers() {
		ManagerRegistry.reset();
	}
	
	@Test
	public void testConstructor_shouldBeInEffectManager() {
		Explosion testObject = new Explosion( "Test", new AbsolutePoint(0,0),10, 100,new EmptyJFXDrawingInformation());
		
		assertTrue(ManagerRegistry.getEffectManager().getRegisteredItems().contains(testObject));		
	}
	
	@Test
	public void testConstructor_shouldNotBeInUpdateManager() {
		Explosion testObject = new Explosion( "Test", new AbsolutePoint(0,0),10, 100,new EmptyJFXDrawingInformation());
		
		assertFalse(ManagerRegistry.getUpdateManager().getRegisteredItems().contains(testObject));	
	}
	
	@Test
	public void testConstructor_isNotOrphaned() {
		Explosion testObject = new Explosion( "Test", new AbsolutePoint(0,0),10, 100,new EmptyJFXDrawingInformation());
		
		assertFalse(testObject.isOrphan());
	}
	
	@Test
	public void testUpdate_standardGrowthRate_shouldBeALittleBigger() {
		Explosion testObject = new Explosion( "Test", new AbsolutePoint(0,0),10, 100,new EmptyJFXDrawingInformation());
		
		Circle c = (Circle)testObject.getShape();
		double oldSize = c.radious;
		
		testObject.update();
		double newSize = c.radious;
		
		assertTrue(newSize>oldSize);
	}

	@Test
	public void testUpdate_doubleGrowthRate_shouldBeTwiceAsBig() {
		Explosion testObject = new Explosion( "Test", new AbsolutePoint(0,0),10, 100,2.0,new EmptyJFXDrawingInformation());
		Circle c = (Circle)testObject.getShape();
		double oldSize = c.radious;
		
		testObject.update();
		double newSize = c.radious;
		
		assertEquals(oldSize*2,newSize);
	}
	
	@Test
	public void testRemove_isOrphaned() {
		Explosion testObject = new Explosion( "Test", new AbsolutePoint(0,0),10, 100,new EmptyJFXDrawingInformation());
		
		testObject.remove();
		
		assertTrue(testObject.isOrphan());
	}
	
	@Test
	public void testRemove_doubleRemove_isOrphaned_noErrors() {
		Explosion testObject = new Explosion( "Test", new AbsolutePoint(0,0),10, 100,new EmptyJFXDrawingInformation());
		
		testObject.remove();
		testObject.remove();
		
		assertTrue(testObject.isOrphan());
	}
	
	@Test
	public void testIsCovered_pointIsIn_shouldBeTrue() {
		Explosion testObject = new Explosion( "Test", new AbsolutePoint(0,0),10, 100,new EmptyJFXDrawingInformation());
		
		assertTrue(testObject.isCovered(5, 5));
	}
	
	@Test
	public void testIsCovered_pointIsOut_shouldBeFalse() {
		Explosion testObject = new Explosion( "Test", new AbsolutePoint(0,0),10, 100,new EmptyJFXDrawingInformation());
		
		assertFalse(testObject.isCovered(5, 100));
	}

	@Test
	public void testIsCovered_effectHasNoSize_PointIsCenter_shouldBeTrue() {
		Explosion testObject = new Explosion( "Test", new AbsolutePoint(0,0),0, 100,new EmptyJFXDrawingInformation());
		
		assertTrue(testObject.isCovered(0,0));
	}
	
	@Test
	public void testToString_isNameAtPoint() {
		Explosion testObject = new Explosion( "Test", new AbsolutePoint(0,0),0, 100,new EmptyJFXDrawingInformation());
		
		assertEquals("Test@[0|0|0]",testObject.toString());
	}

}
