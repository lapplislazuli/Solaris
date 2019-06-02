package junit.core;



import static junit.testhelpers.FakeSpaceObjectFactory.fakePlanet;
import static junit.testhelpers.FakeSpaceObjectFactory.fakeStar;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import space.core.SpaceObject;

class SpaceObjectEqualityTests {

	@Test 
	public void testEquality_selfEquality_shouldBeTrue() {
		SpaceObject self = fakeStar(0,0);
		
		assertTrue(self.equals(self));
	}
	
	@Test 
	public void testEquality_sameAttributes_shouldBeTrue() {
		SpaceObject obj = fakeStar(0,0);
		SpaceObject sameObj=fakeStar(0,0);
		
		assertTrue(obj.equals(sameObj));
	}
	
	@Test 
	public void testEquality_differentKoords_shouldBeFalse() {
		SpaceObject obj = fakeStar(0,0);
		SpaceObject diff=fakeStar(20,0);
		
		assertFalse(obj.equals(diff));	
	}
	
	@Test 
	public void testEquality_sameKoordsDifferentClass_shouldBeFalse() {
		SpaceObject obj = fakeStar(0,0);
		SpaceObject diff=fakePlanet(obj,0);
		
		assertFalse(obj.equals(diff));	
	}
	
	@Test 
	public void testEquality_symmetry_shouldBeTrue() {
		SpaceObject obj = fakeStar(0,0);
		SpaceObject sameObj=fakeStar(0,0);
		
		assertTrue(obj.equals(sameObj)==sameObj.equals(obj));
	}
	
	@Test 
	public void testEquality_transity_shouldBeTrue() {
		SpaceObject first = fakeStar(0,0);
		SpaceObject second=fakeStar(0,0);
		SpaceObject third=fakeStar(0,0);
		
		assertTrue(first.equals(second)==second.equals(third));
	}
	
	
	@Test
	public void testEquality_addSameItemToSet_shouldDisappear() {
		SpaceObject obj = fakeStar(0,0);
		Set<SpaceObject> sOs= new HashSet<SpaceObject>();
		sOs.add(obj); 
		sOs.add(obj);
		
		assertEquals(1,sOs.size());
	}
	
	
	@Test
	public void testEquality_addOtherItemToSet_shouldDisappear() {
		SpaceObject obj = fakeStar(0,0);
		SpaceObject other = fakeStar(0,10);
		Set<SpaceObject> sOs= new HashSet<SpaceObject>();
		
		sOs.add(obj); 
		sOs.add(other);
		
		assertEquals(2,sOs.size());
	}
}
