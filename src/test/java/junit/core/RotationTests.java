package junit.core;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import space.core.Planet;
import space.core.SpaceObject;

import static junit.testhelpers.FakeSpaceObjectFactory.fakeStar;

class RotationTests {
	
	@Tag("fast")
	@Tag("core")
	@ParameterizedTest
	@ValueSource(ints = {0,1,50,360 })
	void testRotate_itemHasRotation_shouldRotateByItsRotationSpeed(int degree) {
		SpaceObject anker = fakeStar(250,250);
		Planet rotater= (new Planet.Builder("RotatingPlanet", anker))
				.size(250)
				.distance(250)
				.rotationSpeed(Math.PI/degree)
				.build();
		
		rotater.setRotation(0);
		
		rotater.rotate(); 
		assertEquals(rotater.getRotationSpeed(), rotater.getRotation());
	}
	
	@Tag("fast")
	@Tag("core")
	@RepeatedTest(2)
	@Test
	void testRotate_rotateForMoreThan360Degree_shouldBeReset() {
		SpaceObject anker = fakeStar(250,250);
		Planet rotater= (new Planet.Builder("SlowPlanet", anker))
				.size(10)
				.distance(250)
				.rotationSpeed(Math.PI/2) //90 Degree per rotate()
				.build();
		
		rotater.setRotation(0);
		
		//Rotate for more than 2 PI
		for(int i=0;i<5;i++)
			rotater.rotate(); 
		
		assertEquals(Math.PI/2, rotater.getRotation());
	}
	
	@Tag("fast")
	@Tag("core")
	@Test
	void testRotate_negativeRotationSpeed_shouldBePositiveRotationDegree() {
		SpaceObject anker = fakeStar(250,250);
		Planet rotater= (new Planet.Builder("SlowPlanet", anker))
				.size(10)
				.distance(250)
				.rotationSpeed(-Math.PI/2) //-90 Degree per rotate()
				.build();
		
		rotater.setRotation(0);
		
		rotater.rotate(); 
		
		assertTrue(rotater.getRotation()>0);
	}
	
	@Tag("fast")
	@Tag("core")
	@RepeatedTest(2)
	@Test
	void testRotate_negativeRotationSpeedOf90Degree_rotateTwice_shouldBeMathPi() {
		SpaceObject anker = fakeStar(250,250);
		Planet rotater= (new Planet.Builder("SlowPlanet", anker))
				.size(10)
				.distance(250)
				.rotationSpeed(-Math.PI/2) //-90 Degree per rotate()
				.build();
		
		rotater.setRotation(0);
		
		rotater.rotate(); 
		rotater.rotate();
		
		assertEquals(Math.PI, rotater.getRotation());
	}
	
	@Tag("fast")
	@Tag("core")
	@RepeatedTest(2)
	@Test
	void testRotate_negativeRotation_rotateOver2Pi_shouldBeReset() {
		SpaceObject anker = fakeStar(250,250);
		Planet rotater= (new Planet.Builder("SlowPlanet", anker))
				.size(10)
				.distance(250)
				.rotationSpeed(-Math.PI/2) //-90 Degree per rotate()
				.build();
		
		rotater.setRotation(0);
		
		for(int i = 0; i<6;i++)
			rotater.rotate(); // Rotate 6 times for 90 Degree each
		
		assertEquals(Math.PI, rotater.getRotation());
	}
}
