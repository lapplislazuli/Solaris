package shuttle;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import geom.AbsolutePoint;
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
		star = new Star("star",null,new AbsolutePoint(250,250), 25);
		planet = (new Planet.Builder("planet", star))
				.size(10)
				.distance(100)
				.build();
		shuttle= new ArmedSpaceShuttle("shuttle",planet,5,10,0);
		
		shuttle.shootLaser(planet);
		missile=(Missile)shuttle.getAllChildrenFlat().get(0);
	}
	
	@Test
	public void testMissileRotation() {
		double oldRotation=missile.rotation;
		for(int i=0;i<10;i++)
			missile.rotate(); //Missiles do not Rotate
		
		assertTrue(missile.rotation==oldRotation);
	}
	
	@Test
	public void testMissileInitialisation() {
		assertTrue(missile.center.getX()==shuttle.center.getX() && missile.center.getY()==shuttle.center.getY());
		assertTrue(missile.distanceTo(planet)==planet.distanceTo(shuttle));
		assertTrue(missile.rotation==shuttle.degreeTo(planet));
		
	}
	
	@Test
	public void testMissileMovement() {
		missile.move(new AbsolutePoint(0,0));
		
		assertEquals(true,missile.distanceTo(planet)>planet.distanceTo(shuttle));
		
		while(true) {
			missile.move(new AbsolutePoint(0,0));
			if(missile.distanceTo(planet)==0) {
				assertEquals(true,true);
				return;
			}
		}
	}
	
	@Test
	public void testMissileCollision() {
		assertTrue(shuttle.collides(missile));
		
		while(true) {
			missile.move(new AbsolutePoint(0,0));
			if(missile.distanceTo(planet)==0) {
				assertTrue(missile.collides(planet));
				assertTrue(planet.collides(missile));
				return;
			}
		}
	}
}
