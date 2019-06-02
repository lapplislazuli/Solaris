package junit.spaceobjects;


public interface CommonSpaceObjectBuilderTests {

	void testBuilder_EverythingIsFine_ShouldBeBuild();

	void testBuilder_NegativeSize_shouldThrowError();

	void testBuilder_NegativeLevelOfDetail_shouldThrowError();

	void testBuilder_NoName_shouldThrowError();

	void testBuilder_NullName_shouldThrowError();

}