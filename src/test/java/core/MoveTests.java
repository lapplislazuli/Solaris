package core;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import space.core.MovingSpaceObject;
import space.core.Planet;
import space.core.SpaceObject;

import static helpers.FakeSpaceObjectFactory.*;

class MoveTests {
	
	@Test
	void testConstructor_makePlanet_planetXandDistanceCorrellate() {		
		SpaceObject anchor = fakeStar(250,250);
		SpaceObject planet = fakePlanetWithSpeed(anchor,250,Math.PI/2);
		
		//Check X-Y Koords
		assertEquals(250,planet.getCenter().getX());
		assertEquals(500,planet.getCenter().getY());
		
		//Check DegreeTo EachOther
		assertEquals(3*Math.PI/2, planet.degreeTo(anchor));
	}
	
	@Test
	void testConstructor_makePlanet_planetDegreeDiffersInX() {		
		SpaceObject anchor = fakeStar(250,250);
		SpaceObject planet = fakePlanetWithSpeed(anchor,250,Math.PI/2);
		
		assertEquals(3*Math.PI/2, planet.degreeTo(anchor));
	}
	
	@Test
	void testMovement_starMove_shouldNotMove() {
		SpaceObject anchor = fakeStar(250,250);
		
		int expectedX=250;
		int expectedY=250;
		
		anchor.update();

		assertEquals(expectedX,anchor.getCenter().getX());
		assertEquals(expectedY,anchor.getCenter().getY());
	}
	
	@Test
	void testMovement_singlePlanetsingleMove_shouldMove() {
		SpaceObject anchor = fakeStar(250,250);
		SpaceObject planet = fakePlanetWithSpeed(anchor,250,Math.PI/2);
		
		int expectedX=250;
		int expectedY=500;
		
		anchor.update();

		assertEquals(expectedX,planet.getCenter().getX());
		assertEquals(expectedY,planet.getCenter().getY());
	}
	
	@Test
	void testMovement_singlePlanetdoubleMove_shouldMove() {
		SpaceObject anchor = fakeStar(250,250);
		SpaceObject planet = fakePlanetWithSpeed(anchor,250,Math.PI/2);
		
		int expectedX=0;
		int expectedY=250;
		
		anchor.update();
		anchor.update();
		
		assertEquals(expectedX,planet.getCenter().getX());
		assertEquals(expectedY,planet.getCenter().getY());
	}
	
	@Test
	void testMovement_negativeSpeed_shouldMoveBackwards() {
		SpaceObject anchor = fakeStar(250,250);
		SpaceObject planet = fakePlanetWithSpeed(anchor,250,Math.PI/2);
		SpaceObject satellite = fakeSatelliteWithSpeed(planet,250,-Math.PI/2);
		
		anchor.update();
		
		assertEquals(250,satellite.getCenter().getX());
		assertEquals(250,satellite.getCenter().getY());
	}
	
	
	@Test
	void testMovement_zeroSpeed_shouldNotMove() {
		SpaceObject anchor = fakeStar(250,250);
		SpaceObject planet = fakePlanetWithSpeed(anchor,250,0);
		
		anchor.update();
		
		assertEquals(500,planet.getCenter().getX());
		assertEquals(250,planet.getCenter().getY());
	}
	
	@Test
	public void testMovement_checkRelativePositionAfterUpdate_shouldChange() {
		SpaceObject anchor = fakeStar(250,250);
		MovingSpaceObject planet = fakePlanetWithSpeed(anchor,250,Math.PI/2);
		
		anchor.update();
		
		assertEquals(3*Math.PI/2,planet.degreeTo(anchor));
		assertEquals(Math.PI/2,planet.getRelativePos());
		assertEquals(3*Math.PI/2,planet.degreeTo(anchor));
	}
	
	@Test
	public void testMovement_doubleUpdate_checkRelativePositionAfterUpdate_shouldChange() {
		SpaceObject anchor = fakeStar(250,250);
		MovingSpaceObject planet = fakePlanetWithSpeed(anchor,250,Math.PI/2);
		
		anchor.update();
		anchor.update();
		
		assertEquals(0,planet.degreeTo(anchor));
		assertEquals(Math.PI,planet.getRelativePos());
		assertEquals(Math.PI, anchor.degreeTo(planet));
	}
	
	
	public void testFasterThanMe_ComparisonIsFaster_ShouldBeTrue() {
		SpaceObject anchor = fakeStar(250,250);
		
		MovingSpaceObject planet = fakePlanetWithSpeed(anchor,250,Math.PI/2);
		Planet fasterPlanet = fakePlanetWithSpeed(anchor,250,Math.PI);
		
		assertTrue(planet.isFasterThanMe(fasterPlanet));
	}
	
	public void testFasterThanMe_ComparisonIsSlower_ShouldBeFalse() {
		SpaceObject anchor = fakeStar(250,250);
		
		MovingSpaceObject planet = fakePlanetWithSpeed(anchor,250,Math.PI/2);
		Planet slowerPlanet = fakePlanetWithSpeed(anchor,250,Math.PI/4);
		
		assertTrue(planet.isFasterThanMe(slowerPlanet));
	}
	
	@Test
	public void testFasterThanMe_ComparisonIsNotMoving_ShouldBeFalse() {
		SpaceObject anchor = fakeStar(250,250);
		MovingSpaceObject planet = fakePlanetWithSpeed(anchor,250,Math.PI/2);
		
		assertFalse(planet.isFasterThanMe(anchor));
	}
	

	@Test
	public void testSameDirection_ComparisonMovesSameDirection_ShouldBeTrue() {
		SpaceObject anchor = fakeStar(250,250);
		
		MovingSpaceObject planet = fakePlanetWithSpeed(anchor,250,Math.PI/2);
		MovingSpaceObject sameDirectionPlanet = fakePlanetWithSpeed(anchor,250,Math.PI);
		
		assertTrue(planet.movesInSameDirection(sameDirectionPlanet));
	}
	
	@Test
	public void testSameDirection_MovesSameDirection_symmetry_ShouldBeTrue() {
		SpaceObject anchor = fakeStar(250,250);
		
		MovingSpaceObject planet = fakePlanetWithSpeed(anchor,250,Math.PI/2);
		MovingSpaceObject sameDirectionPlanet = fakePlanetWithSpeed(anchor,250,Math.PI);
		
		assertTrue(planet.movesInSameDirection(sameDirectionPlanet)==sameDirectionPlanet.movesInSameDirection(planet));
	}
	
	@Test
	public void testSameDirection_ComparisonMovesOtherDirection_ShouldBeTrue() {
		SpaceObject anchor = fakeStar(250,250);
		MovingSpaceObject planet = fakePlanetWithSpeed(anchor,250,Math.PI/2);
		MovingSpaceObject satellite = fakeSatelliteWithSpeed(planet,250,-Math.PI/2);
		
		assertFalse(planet.movesInSameDirection(satellite));
	}
	
	@Test
	public void testSameDirection_MovesOtherDirection_symmetry_ShouldBeTrue() {
		SpaceObject anchor = fakeStar(250,250);
		MovingSpaceObject planet = fakePlanetWithSpeed(anchor,250,Math.PI/2);
		MovingSpaceObject satellite = fakeSatelliteWithSpeed(planet,250,-Math.PI/2);
		
		assertTrue(planet.movesInSameDirection(satellite)==satellite.movesInSameDirection(planet));
	}
	
	@Test
	public void testSameDirection_ComparisonDoesNotMove_ShouldBeFalse() {
		SpaceObject anchor = fakeStar(250,250);
		MovingSpaceObject planet = fakePlanetWithSpeed(anchor,250,Math.PI/2);
		
		assertFalse(planet.movesInSameDirection(anchor));
	}	
	
}
