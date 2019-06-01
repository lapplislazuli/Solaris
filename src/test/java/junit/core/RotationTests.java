package junit.core;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import geom.AbsolutePoint;
import space.core.Planet;
import space.core.Star;

class RotationTests {
	
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
		slow.setRotation(0);
		fast.setRotation(0);
		reverse.setRotation(0);
	}

	@Test
	void rotate() {
		slow.rotate(); 
		assertEquals(slow.getRotationSpeed(), slow.getRotation());
		fast.rotate(); 
		assertEquals(fast.getRotationSpeed(), fast.getRotation());
	}
	@Test
	void rotateReset() {
		//Rotate for more than 2 PI
		for(int i=0;i<5;i++)
			fast.rotate(); 
		assertEquals(Math.PI/2, fast.getRotation());
		
		for(int i=0;i<721;i++)
			slow.rotate();
		
		assertEquals(Math.round(Math.PI/360), Math.round(slow.getRotation()));
	}
	
	@Test
	void reverseRotation() {
		reverse.rotate(); 
		reverse.rotate();
		assertEquals(Math.PI, reverse.getRotation());
		for(int i=0;i<4;i++)
			reverse.rotate();
		assertEquals(Math.PI, reverse.getRotation());
	}
}
