package junit.spaceobjects.spacecrafts;



import static junit.testhelpers.FakeSpaceObjectFactory.fakeStar;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import drawing.EmptyJFXDrawingInformation;
import geom.AbsolutePoint;
import junit.fakes.FakeSpacecraft;
import logic.manager.ManagerRegistry;
import space.advanced.FixStar;
import space.core.SpaceObject;
import space.effect.Explosion;
import space.spacecrafts.ships.Sensor;
import space.spacecrafts.ships.SensorArray;
import space.spacecrafts.ships.Ship;

public class SensorArrayTests {
	
	@BeforeEach
	public void initManagers() {
		ManagerRegistry.getInstance();
	}
	
	@AfterEach
	public void resetManagers() {
		ManagerRegistry.reset();
	}
	
	@Test
	public void testConstructor_shouldHaveNoCollisions() {
		FakeSpacecraft root = new FakeSpacecraft();
		Sensor testObject = new SensorArray (root,50);
		
		assertTrue(testObject.getDetectedItems().isEmpty());
	}
	
	@Test
	public void testConstructor_shouldHaveEmittersSpeed() {
		FakeSpacecraft root = new FakeSpacecraft();
		root.speed=100;
		SensorArray testObject = new SensorArray (root,50);
		
		assertEquals(100,testObject.getSpeed(),00000000001);
	}
	
	@Test
	public void testSetSpeed_doesNothing() {
		FakeSpacecraft root = new FakeSpacecraft();
		root.speed=100;
		SensorArray testObject = new SensorArray (root,50);
		
		testObject.setSpeed(2000);
	
		assertEquals(100,testObject.getSpeed(),00000000001);		
	}
	
	@Test
	public void testGetSpeed_EmitterGotFasterSinceConstructor_shouldHaveNewSpeed() {
		FakeSpacecraft root = new FakeSpacecraft();
		root.speed=100;
		SensorArray testObject = new SensorArray (root,50);
		
		root.speed=200;
		
		assertEquals(200,testObject.getSpeed(),00000000001);		
		
	}
	
	@Test
	public void testDetection_shouldNotDetectEmitter() {
		FakeSpacecraft root = new FakeSpacecraft();
		root.colliding=true;
		SensorArray testObject = new SensorArray (root,50);
		
		testObject.update();
		
		assertTrue(testObject.getDetectedItems().isEmpty());
	}
	
	@Test
	public void testDetection_shouldNotDetectFixstars() {
		FakeSpacecraft root = new FakeSpacecraft();
		SensorArray testObject = new SensorArray (root,50);
		
		FixStar nonDetectable = new FixStar("fixstar",0,0,1);
		
		testObject.update();
		
		assertTrue(testObject.getDetectedItems().isEmpty());
	}
	
	@Test
	public void testDetection_shouldNotDetectExplosion() {
		FakeSpacecraft root = new FakeSpacecraft();
		SensorArray testObject = new SensorArray (root,50);
		
		Explosion exp = new Explosion("exp",new AbsolutePoint(0,0),0,0, new EmptyJFXDrawingInformation());
		
		testObject.update();
		
		assertTrue(testObject.getDetectedItems().isEmpty());
	}
	
	@Test
	public void testDetection_addFakeStar_shouldDetectFakeStar() {
		SpaceObject shipRoot = fakeStar(0,0);
		Ship sensorRoot = new Ship("SensorRoot",shipRoot,0,50,0);
		SensorArray testObject = new SensorArray (sensorRoot,501);
		ManagerRegistry.getCollisionManager().registerItem(shipRoot);
		
		testObject.update();
		
		assertEquals(1,testObject.getDetectedItems().size());
		assertTrue(testObject.getDetectedItems().contains(shipRoot));
	}
	
	@Test
	public void testDetection_addTwoFakeStars_shouldDetectBothFakeStars() {
		SpaceObject shipRoot = fakeStar(0,0);
		SpaceObject fakeSecondItem = fakeStar(25,25);
		Ship sensorRoot = new Ship("SensorRoot",shipRoot,0,50,0);
		SensorArray testObject = new SensorArray (sensorRoot,500);
		
		ManagerRegistry.getCollisionManager().registerItem(shipRoot);
		ManagerRegistry.getCollisionManager().registerItem(fakeSecondItem);
		
		testObject.update();
		
		assertEquals(2,testObject.getDetectedItems().size());
		assertTrue(testObject.getDetectedItems().contains(shipRoot));
		assertTrue(testObject.getDetectedItems().contains(fakeSecondItem));
	}
	

	@Test
	public void testDetection_CollidedObjectIsNotFullyCovered_shouldNotDetectIt() {
		SpaceObject shipRoot = fakeStar(0,0);
		Ship sensorRoot = new Ship("SensorRoot",shipRoot,0,50,0);
		//With this size, the Sensor only intersects the object. it is not detected
		SensorArray testObject = new SensorArray (sensorRoot,50);
		
		ManagerRegistry.getCollisionManager().registerItem(shipRoot);
		
		testObject.update();
		
		assertEquals(0,testObject.getDetectedItems().size());
	}
	
}
