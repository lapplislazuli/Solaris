package coreTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import geom.AbsolutePoint;
import space.core.Planet;
import space.core.Star;

class RotationTest {
	
	static Star anker;
	static Planet slow, fast, reverse;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		anker= new Star("anker", null, new AbsolutePoint(250, 250), 250);
		slow= (new Planet.Builder("SlowPlanet", anker))
				.size(250)
				.distance(250)
				.rotationSpeed(Math.PI/360) //1 Degree per rotate()
				.build();
				
		fast= (new Planet.Builder("FastPlanet", anker))
				.size(250)
				.distance(250)
				.rotationSpeed(Math.PI/2) //90 Degree per rotate()
				.build();
		reverse= (new Planet.Builder("ReversePlanet", anker))
				.size(250)
				.distance(250)
				.rotationSpeed(-Math.PI/2) //-90 Degree per rotate()
				.build();
	}

	@BeforeEach
	void setUp() throws Exception {
		slow.rotation=0;
		fast.rotation=0;
		reverse.rotation=0;
	}

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
		assertEquals(Math.PI, reverse.rotation);
		for(int i=0;i<4;i++)
			reverse.rotate();
		assertEquals(Math.PI, reverse.rotation);
	}
}
