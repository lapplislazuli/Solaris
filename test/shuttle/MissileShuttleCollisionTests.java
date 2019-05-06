package shuttle;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import geom.AbsolutePoint;
import space.core.Star;
import space.spacecrafts.ships.ArmedSpaceShuttle;
import space.spacecrafts.ships.missiles.Missile;
class MissileShuttleCollisionTests {
	Star star;
	ArmedSpaceShuttle shuttleOne, shuttleTwo;
	Missile missileOne,missileTwo;
	
	@BeforeEach
	public void resetEnv() {
		star = new Star("Anker",null,new AbsolutePoint(250,250), 25);
		shuttleOne= new ArmedSpaceShuttle("shuttle",star,5,10,5);
		
		shuttleTwo= new ArmedSpaceShuttle("shuttle",star,5,10,5);
		shuttleTwo.relativePos=Math.PI/2;
		star.update();
		
		initMissiles();
		
		initHitboxes();
	}
	private void initHitboxes() {
		shuttleOne.updateHitbox();
		shuttleTwo.updateHitbox();
		missileOne.updateHitbox();
		missileTwo.updateHitbox();
	}
	void initMissiles() {
		shuttleOne.shootLaser(shuttleTwo);
		missileOne=(Missile)shuttleOne.getAllChildrenFlat().get(0);
		
		shuttleTwo.shootLaser(shuttleOne);
		missileTwo=(Missile)shuttleTwo.getAllChildrenFlat().get(0);
	}
	
	@Test
	void testShuttleCollidesMissile() {
		assertFalse(shuttleOne.collides(missileOne));
		
		assertFalse(shuttleTwo.collides(missileTwo));
	}
	@Test
	void testMissileCollidesShuttle() {
		assertFalse(missileOne.collides(shuttleOne));
		
		assertFalse(missileTwo.collides(shuttleTwo));
	}
}