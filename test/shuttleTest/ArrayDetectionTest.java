package shuttleTest;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import geom.Circle;
import geom.Point;
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
		hitStar = new Star("hit",null,new Point(250,250),250);
		notHitStar = new Star("noHit",null,new Point(1250,250),250);
		notHittableStar = new Star("noHitEver",null,new Point(250,1250),0); //no Size!
		
		SpaceShuttle shuttle= new SpaceShuttle("shuttle",hitStar,0,0,0);
		
		sensor= new SensorArray(shuttle,50);
		
		cm=CollisionManager.getInstance();
		cm.addCollidable(hitStar); 
		cm.addCollidable(notHitStar);
		cm.addCollidable(notHittableStar);
		cm.addCollidable(shuttle);
	}
	
	@Test
	public void detectHit() {
		assertEquals(true, sensor.detectedItems.isEmpty());
		
		sensor.update();
		
		assertEquals(true, sensor.detectedItems.contains(hitStar));
		
		assertEquals(1, sensor.detectedItems.size());
		sensor.update();
		assertEquals(true, sensor.detectedItems.contains(hitStar));
		assertEquals(1, sensor.detectedItems.size());
	}
	@Ignore //Sensor cannot move like that anymore
	public void notHitable() {
		sensor.update();
		assertEquals(true,sensor.detectedItems.isEmpty());
	}
}
