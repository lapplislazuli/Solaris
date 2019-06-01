package junit.geom;

public interface ShapeTests {

	void testContains_pointIsInShape_shouldSucceed(int dist);
	
	void testContains_yposOut_shouldFail(int ypos);
	void testContains_xposOut_shouldFail(int xpos);
	
	void testContains_bothAttributesOut_shouldFail(int distance);
	
	void testContains_PointTangents_shouldSucceed(int distance);
	
	void testContains_EveryPointOfOutline_shouldSucceed(int distance);
	
	void testContains_ContainsAnotherSmallerShape_shouldSucced(int size);
	void testContains_ContainsAnotherBiggerShape_shouldFail(int size);
	
	void testShape_NegativeSizeInput_ShouldThrowError(int size);
	void testShape_ZeroSizeInput_ShouldSucceed(int zeros);
	

	void testEquality_SameValuesAndSameCenter_shouldBeTrue(int size);
	void testEquality_SameValuesDifferentCenterOnSamePoint_shouldBeFalse(int size);
	void testEquality_differentValues_shouldBeFalse(int size);
	void testEquality_checkSymmetryOfSameValue_shouldBeTrue(int size);
	void testEquality_checkSymmetryOfDifferentValue_shouldBeTrue(int size);
	void testEquality_sameSizeDifferentCenter_shouldbeFalse(int offset);
	void testEquality_completelyDifferentObject_shouldNotBeEqual(int dummy);
}
