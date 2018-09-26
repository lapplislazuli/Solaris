package shuttle;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import geom.Point;
import space.core.Planet;
import space.core.Star;
import space.shuttle.ArmedSpaceShuttle;
import space.shuttle.missiles.Missile;

public class MissileTest {
	Star star;
	Planet planet;
	ArmedSpaceShuttle shuttle;
	Missile missile;
	
	@BeforeEach
	public void resetEnv() {
		star = new Star("star",null,new Point(250,250), 25);
		planet = (new Planet.Builder("planet", star))
				.size(10)
				.distance(100)
				.build();
		shuttle= new ArmedSpaceShuttle("shuttle",planet,5,10,0);
	}
	
	@Test
	public void MissileRotation() {
		shuttle.shootLaser(planet);
		missile=(Missile)shuttle.getAllChildrenFlat().get(0);
		
		assertEquals(true, missile.rotation==shuttle.degreeTo(planet));
		missile.rotate();
		assertEquals(true, missile.rotation==shuttle.degreeTo(planet));
	}
	
	@Test
	public void MissileMovement() {
		shuttle.shootLaser(planet);
		missile=(Missile)shuttle.getAllChildrenFlat().get(0);
		
		assertEquals(true,missile.center.x==shuttle.center.x && missile.center.y==shuttle.center.y);
		assertEquals(true,missile.distanceTo(planet)==planet.distanceTo(shuttle));
		
		missile.move(new Point(0,0));
		assertEquals(true,missile.distanceTo(planet)>planet.distanceTo(shuttle));
		
		while(true) {
			missile.move(new Point(0,0));
			if(missile.distanceTo(planet)==0) {
				assertEquals(true,true);
				return;
			}
		}
	}
	
	@Test
	public void MissileCollision() {
		shuttle.shootLaser(planet);
		missile=(Missile)shuttle.getAllChildrenFlat().get(0);
		
		assertTrue(shuttle.collides(missile));
		assertFalse(missile.collides(missile));
		
		while(true) {
			missile.move(new Point(0,0));
			if(missile.distanceTo(planet)==0) {
				assertEquals(true,missile.collides(planet));
				assertEquals(true,planet.collides(missile));
				return;
			}
		}
	}
}
