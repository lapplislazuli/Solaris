package shuttle;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import geom.AbsolutePoint;
import interfaces.geom.Point;
import interfaces.spacecraft.SpacecraftState;
import space.core.Planet;
import space.core.Star;
import space.spacecrafts.ships.Ship;

class LaunchTest {
	
	static Star origin,targetOne, targetTwo, targetAboveShuttleOne;
	static Ship shuttleOne, shuttleTwo;
	static Planet launchedTowards,launchedAway;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		origin = new Star("star",null,new AbsolutePoint(250,250),250);
		targetOne = new Star("One",null,new AbsolutePoint(1250,250),250);
		targetTwo = new Star("Two",null,new AbsolutePoint(699,394),250);
		targetAboveShuttleOne = new Star("Three",null,new AbsolutePoint(300,500),250);
		
		launchedTowards=new Planet.Builder("Forward", origin).speed(Math.PI/2000).distance(300).build();
		launchedAway = new Planet.Builder("Flip", origin).speed(Math.PI/80).distance(250).build();
	}

	@BeforeEach
	void setUp() throws Exception {
		shuttleOne= new Ship("shuttleOne",origin,0,50,-Math.PI/400);
		shuttleOne.target=targetOne;
		
		shuttleTwo= new Ship("shuttleTwo",origin,0,50,Math.PI/880);
		shuttleTwo.target=targetTwo; 
		
		shuttleTwo.relativePos=Math.PI/35;
		
		origin.update();
	}
	
	@AfterEach
	void emptyOriginsShuttles() throws Exception {
		origin.trabants.clear();
	}
	
	@Test
	void testConstructor() {
		assertTrue(origin.degreeTo(shuttleOne)-shuttleOne.relativePos <0.2);
		assertEquals(SpacecraftState.ORBITING,shuttleOne.getState());
	}
	
	@Test
	void testParentAndTargetAfterLaunch() {
		shuttleOne.launch();
		
		assertEquals(shuttleOne.parent,targetOne);
		assertEquals(null, shuttleOne.target);
		assertFalse(shuttleOne.getState()==SpacecraftState.ORBITING);
	}
	
	@Test
	void testDistanceAfterLaunch() {
		shuttleOne.launch();
			
		assertEquals(shuttleOne.distance,(int)shuttleOne.distanceTo(targetOne));
	}
	
	@Test
	void testDegreeAfterLaunch() {
		shuttleOne.launch();
		
		assertEquals(targetOne.degreeTo(shuttleOne),shuttleOne.relativePos);
	}
	
	@Test
	void TestFixDegree() {
		shuttleOne.target=targetAboveShuttleOne;
		
		shuttleOne.launch();
		
		assertTrue(3*Math.PI/2-shuttleOne.relativePos<0.2);
	}
	
	@Test
	void testCorrectLaunchShuttleTwo() {
		shuttleTwo.launch();
		
		assertEquals(shuttleTwo.parent,targetTwo);
		assertEquals(null, shuttleTwo.target);
		
		assertEquals(targetTwo.degreeTo(shuttleTwo),shuttleTwo.relativePos);

		assertEquals(SpacecraftState.ORBITING,shuttleOne.getState());
		assertEquals(shuttleTwo.distance,(int)shuttleTwo.distanceTo(targetTwo));
	}
	
	@Test
	void testNoTargetLaunch() {
		shuttleOne.target=null;
		
		shuttleOne.launch(); // does nothing!assertEquals(shuttle.parent,target);
		assertEquals(null, shuttleOne.target);
		
		assertEquals(SpacecraftState.ORBITING,shuttleOne.getState());
	}
	
	@Test
	void testNoTeleport() {
		Point oldPos= shuttleOne.center.clone();

		shuttleOne.launch();
		targetOne.update();
		
		assertTrue(oldPos.distanceTo(shuttleOne.center)<=20);
	}
	
	@Test
	void testRemoval() {
		shuttleOne.remove();
		assertEquals(1,origin.trabants.size());
		
		shuttleTwo.remove();
		assertEquals(0,origin.trabants.size());
	}
	@Test
	void testNoTeleportShuttleTwo() {
		Point oldPos= shuttleTwo.center.clone();

		shuttleTwo.launch();
		targetTwo.update();
		
		assertTrue(oldPos.distanceTo(shuttleTwo.center)<=10);
	}
	
	@Test
	void testNoSpeedFlip() {
		origin.update();//To move Planets
		double oldSpeed=shuttleOne.speed;
		
		shuttleOne.target=launchedTowards;		
		shuttleOne.launch();
		
		assertEquals(oldSpeed,shuttleOne.speed);
	}
	
	@Test
	void testSpeedFlip() {
		origin.update();//To move Planets
		double oldSpeed=shuttleOne.speed;
		
		shuttleOne.target=launchedAway;		
		shuttleOne.launch();
		
		assertEquals(oldSpeed,-shuttleOne.speed);
	}
}
