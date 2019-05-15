package spaceobjects;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import geom.AbsolutePoint;
import interfaces.geom.Point;
import interfaces.spacecraft.SpacecraftState;
import space.core.MovingSpaceObject;
import space.core.Planet;
import space.core.SpaceObject;
import space.core.Star;
import space.spacecrafts.ships.Ship;

import static helpers.FakeSpaceObjectFactory.*;

public class ShipTests {

	
/*
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		origin = new Star("star",null,new AbsolutePoint(250,250),250);
		targetOne = new Star("One",null,new AbsolutePoint(1250,250),250);
		targetTwo = new Star("Two",null,new AbsolutePoint(699,394),250);
		targetAboveShuttleOne = new Star("Three",null,new AbsolutePoint(300,500),250);
		
		launchedTowards=new Planet.Builder("Forward", origin).speed(Math.PI/2000).distance(300).build();
		launchedAway = new Planet.Builder("Flip", origin).speed(Math.PI/80).distance(250).build();
	}
	*/
	@Test
	void testConstructor_ValuesShouldBeAsExpected() {
		SpaceObject root = fakeStar(0,0);
		Ship shuttle= new Ship("shuttleOne",root,0,50,Math.PI);
		
		
		assertEquals(SpacecraftState.ORBITING,shuttle.getState());
		assertEquals(50,shuttle.getOrbitingDistance());
		assertEquals(Math.PI,shuttle.getSpeed());
	}
	
	@Test
	void testSetTarget_targetShouldBeSet() {
		SpaceObject root = fakeStar(0,0);
		Ship shuttle= new Ship("shuttleOne",root,0,50,Math.PI);
		SpaceObject target = fakeStar(100,100);
		
		shuttle.setTarget(target);
		
		assertEquals(target,shuttle.target);
	}
	
	@Test
	void testSetTarget_doubleSet_targetBeShouldNewer() {
		SpaceObject root = fakeStar(0,0);
		Ship shuttle= new Ship("shuttleOne",root,0,50,Math.PI);
		SpaceObject target = fakeStar(100,100);
		SpaceObject newertarget = fakeStar(250,250);
		
		shuttle.setTarget(target);
		shuttle.setTarget(newertarget);
		
		assertEquals(newertarget,shuttle.target);
	}
	
	@Test
	void testLaunch_InspectParentAfterLaunch_ParentShouldBeOldTarget() {
		SpaceObject root = fakeStar(0,0);
		Ship shuttle= new Ship("shuttleOne",root,0,50,Math.PI);
		SpaceObject target = fakeStar(100,100);
		
		shuttle.setTarget(target);
		shuttle.launch();
		
		assertEquals(target,shuttle.getParent());
	}
	
	@Test
	void testLaunch_InspectTargetAfterLaunch_TargetShouldBeNull() {
		SpaceObject root = fakeStar(0,0);
		Ship shuttle= new Ship("shuttleOne",root,0,50,Math.PI);
		SpaceObject target = fakeStar(100,100);
		
		shuttle.setTarget(target);
		shuttle.launch();
		
		assertEquals(null, shuttle.target);
	}
	
	@Test
	void testLaunch_shouldHaveNewState_shouldBeFlying() {
		SpaceObject root = fakeStar(0,0);
		Ship shuttle= new Ship("shuttleOne",root,0,50,Math.PI);
		SpaceObject target = fakeStar(100,100);
		
		shuttle.setTarget(target);
		shuttle.launch();
		
		assertTrue(shuttle.getState()==SpacecraftState.FLYING);
	}
	
	void testLaunch_InspectDistance_shouldBeDistanceToTarget() {
		SpaceObject root = fakeStar(0,0);
		Ship shuttle= new Ship("shuttleOne",root,0,50,Math.PI);
		SpaceObject target = fakeStar(100,100);
		
		shuttle.setTarget(target);
		shuttle.launch();

		assertEquals(shuttle.distance,(int)shuttle.distanceTo(target));
	}

	void testLaunch_InspectRelativePos_shouldBeDegreeToTarget() {
		SpaceObject root = fakeStar(0,0);
		Ship shuttle= new Ship("shuttleOne",root,0,50,Math.PI);
		SpaceObject target = fakeStar(100,100);
		
		shuttle.setTarget(target);
		shuttle.launch();

		assertEquals(target.degreeTo(shuttle),shuttle.relativePos);
	}

	@Test
	void testLaunch_NoTarget_shouldNotChangeState() {
		SpaceObject root = fakeStar(0,0);
		Ship shuttle= new Ship("shuttleOne",root,0,50,Math.PI);
		
		shuttle.setTarget(null);
		shuttle.launch();
		
		assertEquals(SpacecraftState.ORBITING,shuttle.getState());
	}
	
	@Test
	void testLaunch_NoTarget_shouldHaveOldParent() {
		SpaceObject root = fakeStar(0,0);
		Ship shuttle= new Ship("shuttleOne",root,0,50,Math.PI);
		
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
		 * It didn´t occur with 0 speed
		 */
		
		SpaceObject root = fakeStar(0,0);
		double speed= 0.001; //Minimal Speed
		Ship shuttle= new Ship("shuttleOne",root,0,50,speed);
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
		Ship testObject= new Ship("shuttleOne",root,0,50,0);
		
		assertTrue(root.getTrabants().contains(testObject));
	}
	
	@Test
	public void testConstructors_isNotOrphaned(){
		SpaceObject root = fakeStar(0,0);
		Ship testObject= new Ship("shuttleOne",root,0,50,0);
		
		assertFalse(testObject.isOrphan());
	}
	
	@Test
	public void testRemove_isNotInParentsChildren() {
		SpaceObject root = fakeStar(0,0);
		Ship testObject= new Ship("shuttleOne",root,0,50,0);
		
		testObject.remove();
		
		assertFalse(root.getTrabants().contains(testObject));
	}
	
	@Test
	public void testRemove_isOrphaned() {
		SpaceObject root = fakeStar(0,0);
		Ship testObject= new Ship("shuttleOne",root,0,50,0);
		
		testObject.remove();
		
		assertTrue(testObject.isOrphan());
	}
	
	@Test
	public void testRemove_doubleRemove_shouldThrowNullpoint() {
		SpaceObject root = fakeStar(0,0);
		Ship testObject= new Ship("shuttleOne",root,0,50,0);
		
		testObject.remove();
		
		assertThrows(NullPointerException.class,
				() -> testObject.remove());
	}
	
	@Test
	void testLaunch_flipSpeedIfnecessary_shouldNotBeFlipped() {
		SpaceObject origin = new Star("star",null,new AbsolutePoint(250,250),250);
		MovingSpaceObject launchedTowards=new Planet.Builder("Forward", origin).speed(Math.PI/2000).distance(300).build();
		
		Ship shuttle= new Ship("shuttleOne",origin,0,50,-Math.PI/400);
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
		
		Ship shuttle= new Ship("shuttleOne",origin,0,50,-Math.PI/400);
		double oldSpeed=shuttle.getSpeed();
		origin.update();//To move Planets
		
		shuttle.setTarget(launchedAway);		
		shuttle.launch();
		
		assertEquals(oldSpeed,-shuttle.getSpeed());
	}
}
