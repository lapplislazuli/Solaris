package junit.spaceobjects;



import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;

import geom.AbsolutePoint;
import space.core.Star;
import static junit.testhelpers.FakeGeometryFactory.fakeAbsolutePoint;

public class StarBuilderTests implements CommonSpaceObjectBuilderTests {

	@Test
	public void testBuilder_EverythingIsFine_ShouldBeBuild() {
		AbsolutePoint center = fakeAbsolutePoint();
				
		Star testObject = (new Star.Builder("Test"))
				.radious(50)
				.center(center)
				.reCentering(true)
				.levelOfDetail(50)
				.build();

		assertEquals(center,testObject.getCenter());
	}
	
	@Test
	public void testBuilder_BuildWithXY_ShouldBeBuild() {
		AbsolutePoint center = fakeAbsolutePoint();
		
		Star testObject = (new Star.Builder("Test"))
				.radious(50)
				.center(0,0)
				.build();
		assertEquals(center.getX(),testObject.getCenter().getX());
		assertEquals(center.getY(),testObject.getCenter().getY());
	}
	
	@Test
	public void testBuilder_NegativeSize_shouldThrowError() {
			assertThrows(IllegalArgumentException.class,
					() ->  new Star.Builder("Test").radious(-10)
				);		
	}

	@Test
	public void testBuilder_NegativeLevelOfDetail_shouldThrowError() {
		assertThrows(IllegalArgumentException.class,
				() ->  new Star.Builder("Test").levelOfDetail(-10)
			);		
	}

	@Test
	public void testBuilder_NoName_shouldThrowError() {
		assertThrows(IllegalArgumentException.class,
				() ->  new Star.Builder("")
			);		
	}

	@Test
	public void testBuilder_NullName_shouldThrowError() {
		assertThrows(IllegalArgumentException.class,
				() ->  new Star.Builder(null)
		);
	}

}
