package views;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import config.json.JSONConfig;
import config.interfaces.Config;
import drawing.JavaFXDrawingContext;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import logic.loader.GalaxyFactory;
import logic.manager.ManagerRegistry;

public class GalaxyView {
	private static Logger logger = LogManager.getLogger(GalaxyView.class);

	JSONConfig config;
	
	public GalaxyView (JSONConfig config,Stage primaryStage) {
		
		Group center = new Group();

		Scene scene=initScene(config,center);
		
		initManagers(scene,config);
        initDrawingContextAndManager(center,scene);
        
		
        logger.info("Loaded Config and Managers, now loading Galaxy");
        
        GalaxyFactory.defaultGalaxy();
        
        initPrimaryStage(primaryStage,scene);
	}
	
	private Scene initScene(Config config, Group root) {
        return new Scene(root,config.getSettings().getScreenWidth() , config.getSettings().getScreenHeight());     
	}
	
	private void initDrawingContextAndManager(Group root, Scene scene) {
		JavaFXDrawingContext jfx = new JavaFXDrawingContext(root);
        jfx.bindSizeProperties(scene);
        ManagerRegistry.getDrawingManager().initDrawingManager(jfx);
	}

	private void initPrimaryStage(Stage primaryStage, Scene scene) {
        primaryStage.setScene(scene);
        primaryStage.show();
	}

	private void initManagers(Scene scene,Config config) {	    
		ManagerRegistry.getKeyBoardManager().initScene(scene);
        ManagerRegistry.getMouseManager().initScene(scene);   
	}

}
