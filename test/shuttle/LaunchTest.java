/**
 * @Author Leonhard Applis
 * @Created 12.09.2018
 * @Package space.shuttle
 */
package space.shuttle;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import space.core.Star;

class LaunchTest {
	
	static Star origin,target;
	static SpaceShuttle shuttle;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		origin = new Star("star",null,250,250,250);
		target = new Star("star",null,1250,250,250);
	}

	@BeforeEach
	void setUp() throws Exception {
		System.out.println("Reset Shuttle!");
		shuttle= new SpaceShuttle("shuttle",origin,0,0,0);
		shuttle.target=target;
	}

	/**
	 * Test method for {@link space.shuttle.SpaceShuttle#SpaceShuttle(java.lang.String, space.core.SpaceObject, int, int, double)}.
	 */
	@Test
	void Constructor() {
		assertEquals(shuttle.degreeTo(origin),shuttle.relativePos);
		assertTrue(shuttle.orbiting);
		assertEquals(shuttle.distance,origin.size/2);
	}

	/**
	 * Test method for {@link space.shuttle.SpaceShuttle#launch()}.
	 */
	@Test
	void Launch() {
		shuttle.launch();
		
		assertEquals(shuttle.parent,target);
		assertEquals(null, shuttle.target);
		
		assertEquals(shuttle.degreeTo(target),shuttle.relativePos);
		assertFalse(shuttle.orbiting);
		assertEquals(shuttle.distance,shuttle.distanceTo(target));
	}
	
	@Test
	void faultyLaunch() {
		shuttle.launch(); //Now its like in TestCase Launch()
		
		shuttle.launch(); // does nothing!assertEquals(shuttle.parent,target);
		assertEquals(null, shuttle.target);
		
		assertEquals(shuttle.degreeTo(target),shuttle.relativePos);
		assertFalse(shuttle.orbiting);
		assertEquals(shuttle.distance,shuttle.distanceTo(target));
	}
	
}
