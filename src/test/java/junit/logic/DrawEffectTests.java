package junit.logic;


import junit.fakes.FakeEffect;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import logic.manager.DrawingManager;
import logic.manager.EffectManager;
import logic.manager.ManagerRegistry;

import static junit.testhelpers.FakeManagerFactory.fakeEfxManager;
import static junit.testhelpers.FakeManagerFactory.fakeDrawingManager;

public class DrawEffectTests {
	
	@AfterEach
	void resetManagers() {
		ManagerRegistry.reset();
	}
	
	@Test
	public void testInit_InitEffectManager_shouldBeInDrawingManager() {
		ManagerRegistry top = ManagerRegistry.getInstance();
		DrawingManager drMngr = fakeDrawingManager();
		top.setDrawingManager(drMngr);
		
		EffectManager efxMngr = fakeEfxManager();

		assertTrue(drMngr.getRegisteredItems().contains(efxMngr));
	}
	
	@Test
	public void testDrawEffects_everythingOk_shouldBeDrawn(){
		ManagerRegistry top = ManagerRegistry.getInstance();
		DrawingManager drMngr = fakeDrawingManager();
		ManagerRegistry.getInstance().setDrawingManager(drMngr);
		EffectManager efxMngr = fakeEfxManager();
		ManagerRegistry.getInstance().setEffectManager(efxMngr);
		
		FakeEffect fake = new FakeEffect();
		efxMngr.registerItem(fake);
		ManagerRegistry.getInstance().getUpdateManager().registerItem(fake);
		
		
		drMngr.update();

		
		assertTrue(fake.drawn);
	}
	
	@Test
	public void testDrawEffects_drawingManagerPaused_shouldNotBeDrawn(){
		ManagerRegistry top = ManagerRegistry.getInstance();
		DrawingManager drMngr = fakeDrawingManager();
		ManagerRegistry.getInstance().setDrawingManager(drMngr);
		EffectManager efxMngr = fakeEfxManager();
		ManagerRegistry.getInstance().setEffectManager(efxMngr);
	

		FakeEffect fake = new FakeEffect();
		efxMngr.registerItem(fake);
		
		drMngr.toggleUpdate();
		drMngr.update();
		
		assertFalse(fake.drawn);
	}
	
	@Test
	public void testDrawEffects_effectManagerPaused_shouldBeDrawn(){
		ManagerRegistry top = ManagerRegistry.getInstance();
		DrawingManager drMngr = fakeDrawingManager();
		ManagerRegistry.getInstance().setDrawingManager(drMngr);
		EffectManager efxMngr = fakeEfxManager();
		ManagerRegistry.getInstance().setEffectManager(efxMngr);

		FakeEffect fake = new FakeEffect();
		efxMngr.registerItem(fake);
		efxMngr.toggleUpdate();
		
		ManagerRegistry.getInstance().getUpdateManager().registerItem(efxMngr);
		
		drMngr.update();
		
		assertTrue(fake.drawn);
	}
	
}
