package logic;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.jupiter.api.AfterAll;

import logic.manager.EffectManager;
import logic.manager.ManagerRegistry;

public class ManagerRegistryTests {
	
	@AfterAll
	public static void resetManagerRegistry() {
		ManagerRegistry.reset();
	}
	
	@Test
	public void testGetInstance_shouldNotBeNull() {
		assertFalse(null==ManagerRegistry.getInstance());
	}
	
	@Test
	public void testInit_EfxManager_shouldBeInUpdateManager() {
		EffectManager toCheck = new EffectManager();
		ManagerRegistry.setEffectManager(toCheck);
		
		ManagerRegistry.getInstance().init(null);
		
		boolean result = ManagerRegistry.getUpdateManager().getRegisteredItems().contains(toCheck);
		assertTrue(result);
	}
	
	@Test
	public void testInit_EfxManager_shouldBeInDrawingManager() {
		EffectManager toCheck = new EffectManager();
		ManagerRegistry.setEffectManager(toCheck);
		
		ManagerRegistry.getInstance().init(null);
		
		boolean result = ManagerRegistry.getDrawingManager().getRegisteredItems().contains(toCheck);
		assertTrue(result);
	}
}
