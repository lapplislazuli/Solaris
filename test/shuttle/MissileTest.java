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
	Planet targetOne,targetTwo;
	Star targetThree, targetFour;
	ArmedSpaceShuttle shuttle;
	Missile missileOne,missileTwo, missileThree, missileFour;
	
	@BeforeEach
	public void resetEnv() {
		star = new Star("Anker",null,new AbsolutePoint(250,250), 25);
		
		initTargets();

		shuttle= new ArmedSpaceShuttle("shuttle",targetOne,5,10,5);
		
		star.update(); //to Move everything like expected
		
		initMissiles();
	}

	void initTargets() {
		targetOne = (new Planet.Builder("One", star))
				.size(10)
				.distance(100)
				.speed(0)
				.build();
		targetTwo = (new Planet.Builder("Two", star))
				.size(10)
				.distance(-100)
				.speed(0)
				.build();
		
		targetTwo.relativePos=Math.PI/4.5; //Move it
		
		targetThree = new Star("Three",null, new AbsolutePoint(400,1200),100);

		targetFour = new Star("Four",null, new AbsolutePoint(100,100),30);
	}

	void initMissiles() {
		shuttle.shootLaser(targetOne);
		missileOne=(Missile)shuttle.getAllChildrenFlat().get(0);
		
		shuttle.laserCoolDown=0;
		shuttle.shootLaser(targetTwo);
		missileTwo=(Missile)shuttle.getAllChildrenFlat().get(1);
		
		shuttle.laserCoolDown=0;
		shuttle.shootLaser(targetThree);
		missileThree=(Missile)shuttle.getAllChildrenFlat().get(2);
		
		shuttle.laserCoolDown=0;
		shuttle.shootLaser(targetFour);
		missileFour=(Missile)shuttle.getAllChildrenFlat().get(3);
	}
	
	@Test
	public void testMissileRotation() {
		double oldRotation=missileOne.rotation;
		for(int i=0;i<10;i++)
			missileOne.rotate(); //Missiles do not Rotate
		
		assertTrue(missileOne.rotation==oldRotation);
	}
	
	@Test
	public void testMissileInitialisation() {
		assertTrue(missileOne.center.getX()==shuttle.center.getX() && missileOne.center.getY()==shuttle.center.getY());
		assertTrue(missileOne.distanceTo(targetOne)==targetOne.distanceTo(shuttle));
		assertTrue(missileOne.rotation==shuttle.degreeTo(targetOne));
		
	}
	
	@Test
	public void testMissileMovementFirstTarget() {
		missileOne.move(new AbsolutePoint(0,0));

		assertTrue(missileOne.distanceTo(targetOne) < targetOne.distanceTo(shuttle));
	}
	
	@Test
	void testMissileMovementSecondTarget() {
		missileTwo.move(new AbsolutePoint(0,0));
		System.out.println(missileTwo.toString()+ "2 d: " + missileTwo.distanceTo(targetTwo));
		missileTwo.move(new AbsolutePoint(0,0));
		assertTrue(missileTwo.distanceTo(targetTwo)<targetTwo.distanceTo(shuttle));
	}
	@Test
	void testMissileMovementThirdTarget() {
		missileThree.move(new AbsolutePoint(0,0));
		assertTrue( missileThree.distanceTo(targetThree)<targetThree.distanceTo(shuttle));
	}
	@Test
	void testMissileMovementFourthTarget() {
		missileFour.move(new AbsolutePoint(0,0));
		assertTrue( missileFour.distanceTo(targetFour)<targetFour.distanceTo(shuttle));
	}
	@Test
	public void testMissileCollision() {
		assertTrue(shuttle.collides(missileOne));
		
		for(int i=0;i<10000;i++){
			missileOne.move(new AbsolutePoint(0,0));
			if(missileOne.distanceTo(targetOne)==0) {
				assertTrue(missileOne.collides(targetOne));
				assertTrue(targetOne.collides(missileOne));
				return;
			}
		}
	}
}
