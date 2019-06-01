package junit.logic;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;

import config.interfaces.Config;
import helpers.FakeConfigFactory;
import logic.interaction.KeyBoardManager;
import logic.interaction.MouseManager;
import logic.manager.CollisionManager;
import logic.manager.DrawingManager;
import logic.manager.EffectManager;
import logic.manager.ManagerRegistry;
import logic.manager.UpdateManager;

public class ManagerRegistryTests {
	
	@AfterEach
	public void resetManagerRegistry() {
		ManagerRegistry.reset();
	}
	
	@Test
	public void testGetInstance_shouldNotBeNull() {
		assertFalse(ManagerRegistry.getInstance()==null);
	}
	
	@Test
	public void testInit_EfxManager_shouldBeInUpdateManager() {
		UpdateManager toMock = new UpdateManager();
		EffectManager toCheck = new EffectManager();
		ManagerRegistry.setEffectManager(toCheck);
		ManagerRegistry.setUpdateManager(toMock);
		
		Config fakeConfig = FakeConfigFactory.standardConfig();
		
		ManagerRegistry.getInstance().init(fakeConfig);
		
		assertTrue(toMock.getRegisteredItems().contains(toCheck));
	}
	
	@Test
	public void testInit_DrwManager_shouldBeInUpdateManager() {
		UpdateManager toMock = new UpdateManager();
		DrawingManager toCheck = new DrawingManager();
		ManagerRegistry.setDrawingManager(toCheck);
		ManagerRegistry.setUpdateManager(toMock);
		
		Config fakeConfig = FakeConfigFactory.standardConfig();
		
		ManagerRegistry.getInstance().init(fakeConfig);
		
		assertTrue(toMock.getRegisteredItems().contains(toCheck));
	}
	
	@Test
	public void testInit_ColManager_shouldBeInUpdateManager() {
		ManagerRegistry.getInstance();
		
		UpdateManager toMock = new UpdateManager();
		CollisionManager toCheck = new CollisionManager();
		ManagerRegistry.setCollisionManager(toCheck);
		ManagerRegistry.setUpdateManager(toMock);
		
		Config fakeConfig = FakeConfigFactory.standardConfig();
		
		ManagerRegistry.getInstance()
			.init(fakeConfig);
		
		assertTrue(toMock.getRegisteredItems().contains(toCheck));
	}
	
	@Test
	public void testInit_EfxManager_shouldBeInDrawingManager() {
		EffectManager toCheck = new EffectManager();
		DrawingManager toMock = new DrawingManager();
		ManagerRegistry.setEffectManager(toCheck);
		ManagerRegistry.setDrawingManager(toMock);
		
		Config fakeConfig = FakeConfigFactory.standardConfig();
		
		ManagerRegistry.getInstance().init(fakeConfig);
		
		assertTrue(toMock.getRegisteredItems().contains(toCheck));
	}
	
	@Test
	public void testReset_RegisterManagersAndReset_ShouldAllBeGone() {
		UpdateManager upt = new UpdateManager();
		CollisionManager col = new CollisionManager();
		EffectManager efx = new EffectManager();
		DrawingManager drw = new DrawingManager();
		MouseManager mou = new MouseManager();
		KeyBoardManager key = new KeyBoardManager();
		
		ManagerRegistry.setEffectManager(efx);
		ManagerRegistry.setDrawingManager(drw);
		ManagerRegistry.setCollisionManager(col);
		ManagerRegistry.setUpdateManager(upt);
		ManagerRegistry.setMouseManager(mou);
		ManagerRegistry.setKeyboardManager(key);
		
		ManagerRegistry.reset();
		
		assertNotEquals(efx,ManagerRegistry.getEffectManager());
		assertNotEquals(upt,ManagerRegistry.getUpdateManager());
		assertNotEquals(drw,ManagerRegistry.getDrawingManager());
		assertNotEquals(col,ManagerRegistry.getCollisionManager());
		assertNotEquals(mou,ManagerRegistry.getMouseManager());
		assertNotEquals(key,ManagerRegistry.getKeyBoardManager());
	}
	
}
