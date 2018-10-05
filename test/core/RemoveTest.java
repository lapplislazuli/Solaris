package core;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import geom.AbsolutePoint;
import space.core.Planet;
import space.core.Satellite;
import space.core.Star;
import space.shuttle.ArmedSpaceShuttle;
import space.shuttle.missiles.Rocket;

class RemoveTest {
	
	static Star sun;
	static Planet earth;
	static ArmedSpaceShuttle shuttleOne, shuttleTwo;
	static Satellite astra;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	
	}

	@BeforeEach
	void setUp() throws Exception {
		sun= (new Star.Builder("sun"))
				.center(new AbsolutePoint(400,400))
				.build();
		
		earth = (new Planet.Builder("Earth", sun))
				.size(15)
				.distance(1000)
				.levelOfDetail(20)
				.speed(0)
				.build();
		
		astra = (new Satellite.Builder("Earth", earth))
				.size(3, 4)
				.distance(100)
				.levelOfDetail(10)
				.speed(0)
				.build();
		
		shuttleOne= new ArmedSpaceShuttle("shuttle",sun,5,110,5);
		
		shuttleTwo= new ArmedSpaceShuttle("shuttle",earth,5,120,5);
	}

	@AfterEach
	void tearDown() throws Exception {
		shuttleOne.trabants.clear();
		shuttleTwo.trabants.clear();
	}

	@Test
	void testInitialChildrenCount() {
		assertEquals(5,sun.getAllChildrenFlat().size());
	}
	@Test
	void testChildrenCountAfterShootingMissiles() {
		shuttleOne.shootLaser(sun);
		shuttleTwo.shootLaser(sun);
		
		assertEquals(7,sun.getAllChildrenFlat().size());
	}
	
	@Test
	void testRemoveSatellite() {
		astra.remove();
		
		assertEquals(4, sun.getAllChildrenFlat().size());
	}
	
	@Test
	void testDestructSatellite() {
		astra.destruct();
		
		assertEquals(4, sun.getAllChildrenFlat().size());
	}
	
	@Test
	void testRemoveShuttle() {
		shuttleOne.remove();
		
		assertEquals(4, sun.getAllChildrenFlat().size());
	}
	
	@Test
	void testDestructShuttle() {
		shuttleOne.destruct();
		
		assertEquals(4, sun.getAllChildrenFlat().size());
	}
	

	@Test
	void testRemoveMissile() {
		shuttleOne.shootRocket(sun);
		Rocket r = (Rocket)(shuttleOne.trabants.get(0));
		
		r.remove();
		
		assertEquals(4, sun.getAllChildrenFlat().size());
	}
	
	@Test
	void testDestructMissile() {
		shuttleOne.shootRocket(sun);
		Rocket r = (Rocket)(shuttleOne.trabants.get(0));
		
		r.destruct();
		
		assertEquals(4, sun.getAllChildrenFlat().size());
	}
	
	@Test
	void testAddAndRemoveSatellite() {
		Star tmpSun= new Star.Builder("tmp").build();
		
		Satellite tmp = (new Satellite.Builder("Earth", tmpSun))
			.build();
		assertEquals(1, tmpSun.trabants.size());
		
		tmp.remove();
		assertEquals(0, tmpSun.trabants.size());
	}
}
