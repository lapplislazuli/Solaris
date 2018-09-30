package shuttle;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import geom.AbsolutePoint;
import space.core.Star;
import space.shuttle.SpaceShuttle;

class LaunchTest {
	
	static Star origin,target;
	static SpaceShuttle shuttle;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		origin = new Star("star",null,new AbsolutePoint(250,250),250);
		target = new Star("star",null,new AbsolutePoint(1250,250),250);
	}

	@BeforeEach
	void setUp() throws Exception {
		shuttle= new SpaceShuttle("shuttle",origin,0,0,0);
		shuttle.target=target;
	}

	@Test
	void ConstructorTest() {
		assertEquals(shuttle.degreeTo(origin),shuttle.relativePos);
		assertTrue(shuttle.orbiting);
	}

	@Test
	void correctLaunchTest() {
		shuttle.launch();
		
		assertEquals(shuttle.parent,target);
		assertEquals(null, shuttle.target);
		
		assertEquals(shuttle.degreeTo(target),shuttle.relativePos);
		assertFalse(shuttle.orbiting);
		assertEquals(shuttle.distance,shuttle.distanceTo(target));
	}
	
	@Test
	void faultyLaunchTest() {
		shuttle.target=null;
		
		shuttle.launch(); // does nothing!assertEquals(shuttle.parent,target);
		assertEquals(null, shuttle.target);
		
		assertFalse(shuttle.orbiting);
		assertEquals(shuttle.distance,shuttle.distanceTo(target));
	}
	
}
