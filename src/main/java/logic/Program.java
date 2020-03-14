package logic;

import space.advanced.AsteroidBelt;
import space.advanced.DistantGalaxy;
import space.core.Planet;
import space.core.Satellite;
import space.core.SpaceObject;
import space.core.Star;
import space.spacecrafts.navigators.BaseNavigator;
import space.spacecrafts.navigators.ArmedShuttleNavigator;
import space.spacecrafts.ships.ArmedSpaceShuttle;
import space.spacecrafts.ships.BattleCarrier;
import space.spacecrafts.ships.Ship;
import views.GalaxyView;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import config.JSON.JSONConfig;
import config.JSON.JSONConfigFactory;
import config.interfaces.Config;
import drawing.JavaFXDrawingContext;
import drawing.JavaFXDrawingInformation;
import geom.AbsolutePoint;
import interfaces.spacecraft.AggressiveNavigator;
import interfaces.spacecraft.ArmedSpacecraft;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import logic.manager.ManagerRegistry;
import logic.manager.UpdateManager;



public class Program extends Application{

	private static Logger logger = LogManager.getLogger(Program.class);

	
	JSONConfig config;
	
	private static Program INSTANCE;


    public static void main(String[] args) {
        launch(args);
    }
    
	@Override
	public void start(Stage primaryStage) throws Exception {
		INSTANCE = this;
		
		config = JSONConfigFactory.read("config.json");
		
		var something = new GalaxyView(config,primaryStage);
	}
	

	
	// To Call the correct shutdown which needs to be static
	public static void invokeStop() {
		if(INSTANCE!=null)
			INSTANCE.stop();
	}
	
	@Override
	public void stop() {
		logger.info("Saving Config");
		JSONConfigFactory.save(config);
		logger.info("Closing Solaris");
		System.exit(0);
	}
	
}
