/**
 * @Author Leonhard Applis
 * @Created 05.09.2018
 * @Package space.shuttle
 */
package space.shuttle;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import space.core.Planet;
import space.core.Star;

public class MissileTest {
	
	Star star;
	Planet planet;
	ArmedSpaceShuttle shuttle;
	Missile missile;
	
	@BeforeEach
	public void resetEnv() {
		star = new Star("star",null,250,250,25);
		planet = new Planet("planet", star,null,10,100,0);
		shuttle= new ArmedSpaceShuttle("shuttle",planet,5,10,0);
	}
	
	@Test
	public void MissileRotation() {
		shuttle.shootLaser(planet);
		missile=(Missile)shuttle.getChildren().get(0);
		
		assertEquals(true, missile.getRotation()==shuttle.degreeTo(planet));
		missile.rotate();
		assertEquals(true, missile.getRotation()==shuttle.degreeTo(planet));
	}
	
	@Test
	public void MissileMovement() {
		shuttle.shootLaser(planet);
		missile=(Missile)shuttle.getChildren().get(0);
		
		assertEquals(true,missile.getX()==shuttle.getX() && missile.getY()==shuttle.getY());
		assertEquals(true,missile.distanceTo(planet)==planet.distanceTo(shuttle));
		
		missile.move(0, 0);
		assertEquals(true,missile.distanceTo(planet)>planet.distanceTo(shuttle));
		
		while(true) {
			missile.move(0,0);
			if(missile.distanceTo(planet)==0) {
				assertEquals(true,true);
				return;
			}
		}
	}
	
	@Test
	public void MissileCollision() {
		shuttle.shootLaser(planet);
		missile=(Missile)shuttle.getChildren().get(0);
		
		assertTrue(shuttle.collides(missile));
		assertFalse(missile.collides(missile));
		
		while(true) {
			missile.move(0,0);
			if(missile.distanceTo(planet)==0) {
				assertEquals(true,missile.collides(planet));
				assertEquals(true,planet.collides(missile));
				return;
			}
		}
	}
}
