package junit.logic;

public interface SharedManagerTests {
	
	public void testRegisterItems_registeredObjectShouldContainItem_shouldBeTrue();
	public void testDoubleRegisterItems_CheckSize_shouldBeOne();
	
	public void testTogglePause_toggleOff_shouldBeOff();
	public void testTogglePause_toggleOffOn_shouldBeOn();
	
	public void testReset_togglePauseAndReset_shouldBeOn();
	public void testReset_addItemAndReset_shouldBeEmpty();
	
	public void testState_UpdateWhenPaused_shouldNotDoAnything();
}
