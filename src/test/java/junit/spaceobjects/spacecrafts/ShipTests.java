package junit.spaceobjects.spacecrafts;


import static junit.testhelpers.FakeSpaceObjectFactory.fakeStar;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import geom.AbsolutePoint;
import interfaces.geom.Point;
import interfaces.logical.MovingUpdatingObject;
import interfaces.spacecraft.SpacecraftState;
import junit.fakes.FakeSensor;
import junit.spaceobjects.RemovableTests;
import logic.manager.ManagerRegistry;
import space.advanced.Asteroid;
import space.core.MovingSpaceObject;
import space.core.Planet;
import space.core.SpaceObject;
import space.core.Star;
import space.spacecrafts.ships.ArmedSpaceShuttle;

public class ShipTests implements RemovableTests {

	@BeforeEach
	void initManagerRegistry() {
		ManagerRegistry.getInstance();
	}
	@AfterEach
	void resteManagerRegistry() {
		ManagerRegistry.reset();
	}

	@Test
	void testConstructor_shouldBePlayer() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle testObject= new ArmedSpaceShuttle("shuttleOne",root,0,50,Math.PI);
		
		assertFalse(testObject.isPlayer());
	}
	@Test
	void testConstructor_ValuesShouldBeAsExpected() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle shuttle= new ArmedSpaceShuttle("shuttleOne",root,0,50,Math.PI);
		
		
		assertEquals(SpacecraftState.ORBITING,shuttle.getState());
		assertEquals(50,shuttle.getOrbitingDistance());
		assertEquals(Math.PI,shuttle.getSpeed());
	}
	
	@Test
	void testSetTarget_targetShouldBeSet() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle shuttle= new ArmedSpaceShuttle("shuttleOne",root,0,50,Math.PI);
		SpaceObject target = fakeStar(100,100);
		
		shuttle.setTarget(target);
		
		assertEquals(target,shuttle.getTarget());
	}
	
	@Test
	void testSetTarget_doubleSet_targetBeShouldNewer() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle shuttle= new ArmedSpaceShuttle("shuttleOne",root,0,50,Math.PI);
		SpaceObject target = fakeStar(100,100);
		SpaceObject newertarget = fakeStar(250,250);
		
		shuttle.setTarget(target);
		shuttle.setTarget(newertarget);
		
		assertEquals(newertarget,shuttle.getTarget());
	}
	
	@Test
	void testLaunch_InspectParentAfterLaunch_ParentShouldBeOldTarget() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle shuttle= new ArmedSpaceShuttle("shuttleOne",root,0,50,Math.PI);
		SpaceObject target = fakeStar(100,100);
		
		shuttle.setTarget(target);
		shuttle.launch();
		
		assertEquals(target,shuttle.getParent());
	}
	
	@Test
	void testLaunch_InspectTargetAfterLaunch_TargetShouldBeNull() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle shuttle= new ArmedSpaceShuttle("shuttleOne",root,0,50,Math.PI);
		SpaceObject target = fakeStar(100,100);
		
		shuttle.setTarget(target);
		shuttle.launch();
		
		assertEquals(null, shuttle.getTarget());
	}
	
	@Test
	void testLaunch_shouldHaveNewState_shouldBeFlying() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle shuttle= new ArmedSpaceShuttle("shuttleOne",root,0,50,Math.PI);
		SpaceObject target = fakeStar(100,100);
		
		shuttle.setTarget(target);
		shuttle.launch();
		
		assertTrue(shuttle.getState()==SpacecraftState.FLYING);
	}
	
	@Test
	void testLaunch_InspectDistance_shouldBeDistanceToTarget() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle shuttle= new ArmedSpaceShuttle("shuttleOne",root,0,50,Math.PI);
		SpaceObject target = fakeStar(100,100);
		
		shuttle.setTarget(target);
		shuttle.launch();

		assertEquals(shuttle.getDistance(),(int)shuttle.distanceTo(target));
	}
	
	
	@Test
	void testLaunch_InspectRelativePos_shouldBeDegreeToTarget() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle shuttle= new ArmedSpaceShuttle("shuttleOne",root,0,50,Math.PI);
		SpaceObject target = fakeStar(100,100);
		
		shuttle.setTarget(target);
		shuttle.launch();

		assertEquals(target.degreeTo(shuttle),shuttle.getRelativePos());
	}

	@Test
	void testLaunch_NoTarget_shouldNotChangeState() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle shuttle= new ArmedSpaceShuttle("shuttleOne",root,0,50,Math.PI);
		
		shuttle.setTarget(null);
		shuttle.launch();
		
		assertEquals(SpacecraftState.ORBITING,shuttle.getState());
	}
	
	@Test
	void testLaunch_NoTarget_shouldHaveOldParent() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle shuttle= new ArmedSpaceShuttle("shuttleOne",root,0,50,Math.PI);
		
		shuttle.setTarget(null);
		shuttle.launch();

		assertEquals(root,shuttle.getParent());
	}
	
	@Test 
	void testLaunch_CheckForTeleport_shouldBeOnSamePosition() {
		/*
		 * I had a very special problem that the ships do teleport after
		 * launching and doing an update. This was probably related to relativePos
		 * But i want to check it separetly 
		 * It didn�t occur with 0 speed
		 */
		
		SpaceObject root = fakeStar(0,0);
		double speed= 0.001; //Minimal Speed
		ArmedSpaceShuttle shuttle= new ArmedSpaceShuttle("shuttleOne",root,0,50,speed);
		SpaceObject target = fakeStar(1000,1000);
		
		Point oldPos= shuttle.getCenter().clone();
		
		shuttle.setTarget(target);
		shuttle.launch();
		
		target.update();

		assertTrue(oldPos.distanceTo(shuttle.getCenter())<5);
	}
	

	@Test
	public void testConstructors_IsInParentsChildren(){
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle testObject= new ArmedSpaceShuttle("shuttleOne",root,0,50,0);
		
		assertTrue(root.getTrabants().contains(testObject));
	}
	
	@Test
	public void testConstructors_isNotOrphaned(){
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle testObject= new ArmedSpaceShuttle("shuttleOne",root,0,50,0);
		
		assertFalse(testObject.isOrphan());
	}
	
	@Test
	public void testRemove_isNotInParentsChildren() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle testObject= new ArmedSpaceShuttle("shuttleOne",root,0,50,0);
		
		testObject.remove();
		
		assertFalse(root.getTrabants().contains(testObject));
	}
	
	@Test
	public void testRemove_isOrphaned() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle testObject= new ArmedSpaceShuttle("shuttleOne",root,0,50,0);
		
		testObject.remove();
		
		assertTrue(testObject.isOrphan());
	}
	
	@Test
	public void testRemove_inspectTarget_shouldBeNull() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle testObject= new ArmedSpaceShuttle("shuttleOne",root,0,50,0);
		SpaceObject target = fakeStar(1000,1000);
		testObject.setTarget(target);
		
		testObject.remove();
		
		assertEquals(null,testObject.getTarget());
	}
	
	@Test
	public void testRemove_doubleRemove_shouldThrowNullpoint() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle testObject= new ArmedSpaceShuttle("shuttleOne",root,0,50,0);
		
		testObject.remove();
		
		assertThrows(NullPointerException.class,
				() -> testObject.remove());
	}
	
	@Test
	void testLaunch_flipSpeedIfnecessary_shouldNotBeFlipped() {
		SpaceObject origin = new Star("star",null,new AbsolutePoint(250,250),250);
		MovingSpaceObject launchedTowards=new Planet.Builder("Forward", origin).speed(Math.PI/2000).distance(300).build();
		
		ArmedSpaceShuttle shuttle= new ArmedSpaceShuttle("shuttleOne",origin,0,50,-Math.PI/400);
		double oldSpeed=shuttle.getSpeed();
		origin.update();//To move Planets
		
		shuttle.setTarget(launchedTowards);		
		shuttle.launch();
		
		assertEquals(oldSpeed,shuttle.getSpeed());
	}
	
	@Test
	void testLaunch_flipSpeedIfnecessary_shouldBeFlipped() {
		SpaceObject origin = new Star("star",null,new AbsolutePoint(250,250),250);
		MovingSpaceObject launchedAway = new Planet.Builder("Flip", origin).speed(Math.PI/80).distance(250).build();
		
		ArmedSpaceShuttle shuttle= new ArmedSpaceShuttle("shuttleOne",origin,0,50,-Math.PI/400);
		double oldSpeed=shuttle.getSpeed();
		origin.update();//To move Planets
		
		shuttle.setTarget(launchedAway);		
		shuttle.launch();
		
		assertEquals(oldSpeed,-shuttle.getSpeed());
	}
	
	@Test
	void testMove_isFlying_comesCloserToTarget_shouldBeCloser() {
		SpaceObject root = fakeStar(0,0);
		double speed= 1; //Minimal Speed
		ArmedSpaceShuttle shuttle= new ArmedSpaceShuttle("shuttleOne",root,0,50,speed);
		SpaceObject target = fakeStar(1000,1000);
		
		double oldDistance= shuttle.getDistance();
		
		shuttle.setTarget(target);
		shuttle.launch();
		
		shuttle.move(target.getCenter());
		
		assertTrue(shuttle.getDistance()>oldDistance);
	}
	
	@Test
	void testMove_isOrbiting_distanceStaysSame() {
		SpaceObject root = fakeStar(0,0);
		double speed= 0.001; //Minimal Speed
		ArmedSpaceShuttle shuttle= new ArmedSpaceShuttle("shuttleOne",root,0,50,speed);
		
		double oldDistance= shuttle.getDistance();
		
		
		shuttle.move(root.getCenter());
		
		assertEquals(oldDistance,shuttle.getDistance());
	}
	
	@Test
	void testMove_isFlying_CloseEnoughToStay_shouldGetOrbiting() {
		SpaceObject root = fakeStar(0,0);
		double speed= 0.001; //Minimal Speed
		ArmedSpaceShuttle shuttle= new ArmedSpaceShuttle("shuttleOne",root,0,150,speed);
		SpaceObject target = fakeStar(0,0);
		
		shuttle.setTarget(target);
		shuttle.launch();
		
		shuttle.move(target.getCenter());
		
		assertEquals(SpacecraftState.ORBITING ,shuttle.getState());
	}
	
	@Test
	void testRebuildAt_shouldHaveSameValues() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle shuttle= new ArmedSpaceShuttle("shuttleOne",root,0,150,1);
		
		ArmedSpaceShuttle copy = shuttle.rebuildAt("copy", root);
		
		assertEquals(150,copy.getOrbitingDistance());
		assertEquals(1, copy.getSpeed());
		assertEquals(shuttle.getParent(),copy.getParent());
		assertEquals(shuttle.getState(),copy.getState());
	}
	
	@Test
	void testRebuildAt_loosesTarget() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle shuttle= new ArmedSpaceShuttle("shuttleOne",root,0,150,0);
		SpaceObject target = fakeStar(600,600);
		
		shuttle.setTarget(target);
		
		ArmedSpaceShuttle copy = shuttle.rebuildAt("new", root);
		
		assertEquals(null ,copy.getTarget());
	}
	
	@Test
	void testRebuildAt_spawnsAtParent() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle shuttle= new ArmedSpaceShuttle("shuttleOne",root,0,150,0);
		
		ArmedSpaceShuttle copy = shuttle.rebuildAt("new", root);
		
		assertEquals(root,copy.getParent());
	}
	
	@Test
	void testRebuildAt_spawnsOrbiting() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle shuttle= new ArmedSpaceShuttle("shuttleOne",root,0,150,0);
		SpaceObject target = fakeStar(0,0);
		
		shuttle.setTarget(target);
		shuttle.launch();
		ArmedSpaceShuttle copy = shuttle.rebuildAt("new", root);
		
		assertEquals(SpacecraftState.ORBITING,copy.getState());
	}
	
	@Test
	void testRebuildAt_OriginalShipWasDead_CopyShouldBeAlive() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle shuttle= new ArmedSpaceShuttle("shuttleOne",root,0,150,0);
		
		shuttle.destruct();
		
		ArmedSpaceShuttle copy = shuttle.rebuildAt("new", root);
		
		assertEquals(SpacecraftState.ORBITING,copy.getState());
	}
	
	@Test
	void testDestruct_stateShouldBeDead() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle shuttle= new ArmedSpaceShuttle("shuttleOne",root,0,150,0);
		
		shuttle.destruct();
		
		assertEquals(SpacecraftState.DEAD,shuttle.getState());
	}
	
	@Test
	void testDestruct_checkPlayerManagerDeathCount_shouldBeZero() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle shuttle= new ArmedSpaceShuttle("shuttleOne",root,0,150,0);
		
		shuttle.destruct();
		//The ship is not the player, therefore the PlayerManager should not Care
		assertEquals(0,ManagerRegistry.getPlayerManager().getPlayerDeaths());
	}
	
	@Test
	void testDestruct_doubleDestruct_shouldNotDoAnything() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle shuttle= new ArmedSpaceShuttle("shuttleOne",root,0,150,0);
		
		shuttle.destruct();
		shuttle.destruct();
		
		assertTrue(shuttle.isDead());
	}
	
	@Test
	void testDestruct_shouldSpawnTrash() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle shuttle= new ArmedSpaceShuttle("shuttleOne",root,0,50,0);
		
		shuttle.destruct();
		
		MovingUpdatingObject test = root.getTrabants().get(0);
		assertTrue(test instanceof Asteroid);
		Asteroid castedTest = (Asteroid) test;
		assertEquals(Asteroid.Type.TRASH,castedTest.getType());
	}
	
	@Test
	void testDestruct_shouldBeDead() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle shuttle= new ArmedSpaceShuttle("shuttleOne",root,0,50,0);
		
		shuttle.destruct();
		
		assertTrue(shuttle.isDead());
	}

	@Test
	void testDetectedItems_SensorIsEmpty_ShouldBeEmpty() {
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle shuttle= new ArmedSpaceShuttle("shuttleOne",root,0,50,0);
		
		FakeSensor stub = new FakeSensor();
		shuttle.setSensor(stub);
		
		assertTrue(shuttle.getDetectedItems().isEmpty());
	}
	
	@Test
	void testDetectedItems_SensorHasItems_shouldBeTheSameAsFakeSensor(){
		SpaceObject root = fakeStar(0,0);
		ArmedSpaceShuttle shuttle= new ArmedSpaceShuttle("shuttleOne",root,0,50,0);
		
		FakeSensor stub = new FakeSensor();
		shuttle.setSensor(stub);
		stub.detectedItems.add(root);
		
		assertTrue(shuttle.getDetectedItems().contains(root));
		assertEquals(1,shuttle.getDetectedItems().size());		
	}
}
