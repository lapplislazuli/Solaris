/**
 * @Author Leonhard Applis
 * @Created 10.09.2018
 * @Package space.shuttle
 */
package space.shuttle;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import logic.CollisionManager;
import space.core.Star;

public class ArrayDetectionTest {
	
	static Star hitStar,notHitStar, notHittableStar;
	static SensorArray sensor;
	static CollisionManager cm; //Sensor Array only works with CM
	
	@BeforeAll
	public static void initVariables() {
		hitStar = new Star("star",null,250,250,250);
		notHitStar = new Star("star",null,1250,250,250);
		notHittableStar = new Star("star",null,250,1250,0); //no Size!
		
		SpaceShuttle shuttle= new SpaceShuttle("shuttle",hitStar,0,0,0);
		
		sensor= new SensorArray(shuttle,50);
		sensor.x=250;
		sensor.y=250;
		cm=CollisionManager.getInstance();
		cm.addCollidable(hitStar); cm.addCollidable(notHitStar);cm.addCollidable(notHittableStar);cm.addCollidable(shuttle);
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
	
	public void notHitable() {
		sensor.move(250, 1250);
		sensor.update();
		assertEquals(true,sensor.detectedItems.isEmpty());
	}
}
