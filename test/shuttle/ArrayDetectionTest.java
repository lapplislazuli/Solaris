package shuttle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import geom.AbsolutePoint;
import logic.CollisionManager;
import space.core.Star;
import space.shuttle.SensorArray;
import space.shuttle.SpaceShuttle;

public class ArrayDetectionTest {
	
	static Star hitStar,notHitStar, notHittableStar;
	static SensorArray sensor;
	static CollisionManager cm; //Sensor Array only works with CM
	
	@BeforeAll
	public static void initVariables() {
		hitStar = new Star("hit",null,new AbsolutePoint(250,250),250);
		notHitStar = new Star("noHit",null,new AbsolutePoint(1250,250),250);
		notHittableStar = new Star("noHitEver",null,new AbsolutePoint(250,1250),0); //no Size!
		
		SpaceShuttle shuttle= new SpaceShuttle("shuttle",hitStar,0,0,0);
		
		sensor= new SensorArray(shuttle,50);
		
		cm=CollisionManager.getInstance();
		cm.addCollidable(hitStar); 
		cm.addCollidable(notHitStar);
		cm.addCollidable(notHittableStar);
		cm.addCollidable(shuttle);
	}
	
	@Test
	public void testDetectHit() {
		sensor.update();
		
		assertTrue(sensor.detectedItems.contains(hitStar));	
		assertEquals(1, sensor.detectedItems.size());
	}
	
}
