package junit.logic;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import logic.loader.GalaxyFactory;
import logic.manager.ManagerRegistry;

public class GalaxyFactoryTests {
	
	@AfterEach
	void resetManagers() {
		ManagerRegistry.reset();
	}
	
	@Test
	@Tag("Integration")
	@Tag("SideEffect")
	public void runInitialisation_updateManagerShouldHaveItems() {
		ManagerRegistry.getInstance();
		
		GalaxyFactory.defaultGalaxy();
		
		assertFalse(ManagerRegistry.getUpdateManager().getRegisteredItems().isEmpty());
	}
	
	@Test
	@Tag("Integration")
	@Tag("SideEffect")
	public void runInitialisation_drawingManagerIsUpdated_drawingManagerShouldHaveItems() {
		ManagerRegistry.getInstance();
		
		GalaxyFactory.defaultGalaxy();
		
		ManagerRegistry.getDrawingManager().update();
		
		assertFalse(ManagerRegistry.getDrawingManager().getRegisteredItems().isEmpty());
	}
	
	@Test
	@Tag("Integration")
	@Tag("SideEffect")
	public void runInitialisation_drawingManagerIsNotUpdated_drawingManagerShouldHaveNoItems() {
		// The DrawingManager needs an Update to get its item from the UpdateManager - hence it is empty before update. 
		ManagerRegistry.getInstance();
		
		GalaxyFactory.defaultGalaxy();
		
		assertTrue(ManagerRegistry.getDrawingManager().getRegisteredItems().isEmpty());
	}
	
	@Test
	@Tag("Integration")
	@Tag("SideEffect")
	public void runInitialisation_collisionManagerShouldHaveItems() {
		ManagerRegistry.getInstance();
		
		GalaxyFactory.defaultGalaxy();
		
		assertFalse(ManagerRegistry.getCollisionManager().getRegisteredItems().isEmpty());
	}
}
