package geom;

public interface ShapeTests {

	void testContains_pointIsInShape_shouldSucceed(int dist);
	
	void testContains_yposOut_shouldFail(int ypos);
	void testContains_xposOut_shouldFail(int xpos);
	
	void testContains_bothAttributesOut_shouldFail(int distance);
	
	void testContains_PointTangents_shouldSucceed(int distance);
	
	void testContains_EveryPointOfOutline_shouldSucceed(int distance);
	
	void testContains_ContainsAnotherSmallerShape_shouldSucced(int size);
	void testContains_ContainsAnotherBiggerShape_shouldFail(int size);
	
}
