/**
 * @Author Leonhard Applis
 * @Created 12.09.2018
 * @Package space.core
 */
package space.core;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RotationTest {
	
	static Star anker;
	static Planet slow, fast, reverse;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		anker=new Star("AnkerPoint",null,250,250,250);
		slow= new Planet("SlowPlanet",anker,null,250,250,0);
		fast= new Planet("FastPlanet",anker,null,2500,250,0);
		reverse= new Planet("FastPlanet",anker,null,2500,250,0);
		slow.rotationSpeed=Math.PI/360; //1 Degree per rotate
		fast.rotationSpeed=Math.PI/2;
		reverse.rotationSpeed=-Math.PI/2;
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		slow.rotation=0;
		fast.rotation=0;
		reverse.rotation=0;
	}

	/**
	 * Test method for {@link space.core.MovingSpaceObject#rotate()}.
	 */
	@Test
	void rotate() {
		slow.rotate(); 
		assertEquals(slow.rotationSpeed, slow.rotation);
		fast.rotate(); 
		assertEquals(fast.rotationSpeed, fast.rotation);
	}
	@Test
	void rotateReset() {
		//Rotate for more than 2 PI
		for(int i=0;i<5;i++)
			fast.rotate(); 
		assertEquals(Math.PI/2, fast.rotation);
		
		for(int i=0;i<721;i++)
			slow.rotate();
		
		assertEquals(Math.round(Math.PI/360), Math.round(slow.rotation));
	}
	
	@Test
	void reverseRotation() {
		reverse.rotate(); 
		reverse.rotate();
		assertEquals(-Math.PI, reverse.rotation);
		for(int i=0;i<4;i++)
			reverse.rotate();
		assertEquals(-Math.PI, reverse.rotation);
	}
}
