package views;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import config.json.JSONConfig;
import config.interfaces.Config;
import drawing.JavaFXDrawingContext;
import drawing.JavaFXDrawingInformation;
import drawing.PopupText;
import geom.AbsolutePoint;
import interfaces.spacecraft.AggressiveNavigator;
import interfaces.spacecraft.ArmedSpacecraft;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import logic.manager.ManagerRegistry;
import logic.manager.UpdateManager;
import space.advanced.AsteroidBelt;
import space.advanced.DistantGalaxy;
import space.core.Planet;
import space.core.Satellite;
import space.core.SpaceObject;
import space.core.Star;
import space.spacecrafts.navigators.ArmedShuttleNavigator;
import space.spacecrafts.navigators.BaseNavigator;
import space.spacecrafts.ships.ArmedSpaceShuttle;
import space.spacecrafts.ships.BattleCarrier;
import space.spacecrafts.ships.Ship;

public class GalaxyView {
	private static Logger logger = LogManager.getLogger(GalaxyView.class);

	
	JSONConfig config;
	
	public GalaxyView (JSONConfig config,Stage primaryStage) {
		
		Group center = new Group();

		Scene scene=initScene(config,center);
		
		initManagers(scene,config);
        initDrawingContextAndManager(center,scene);
        
		
        logger.info("Loaded Config and Managers, now loading Galaxy");
        initSpace();
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
	
	@SuppressWarnings("unused")
	private void initSpace() {
		UpdateManager updateManager = ManagerRegistry.getUpdateManager();
		
		DistantGalaxy milkyway = new DistantGalaxy("MilkyWay",250);
		
		Star sun = new Star("Sun",new JavaFXDrawingInformation(Color.ORANGE),new AbsolutePoint(600,400),30);
		Planet earth = (new Planet.Builder("Earth", sun))
				.size(13)
				.distance(150)
				.levelOfDetail(10)
				.speed(Math.PI/2000)
				.rotationSpeed(Math.PI*2/365)
				.color(Color.DARKCYAN)
				.build();
		Planet mars = (new Planet.Builder("Mars", sun))
				.size(19)
				.distance(280)
				.levelOfDetail(20)
				.speed(Math.PI/1000)
				.rotationSpeed(Math.PI*2/800)
				.color(Color.INDIANRED)
				.build();

		Planet moon = (new Planet.Builder("Moon", earth))
				.size(4)
				.distance(28)
				.levelOfDetail(7)
				.speed(Math.PI/800)
				.rotationSpeed(Math.PI*2/30)
				.color(Color.LIGHTGRAY)
				.build();
				
		Planet saturn = (new Planet.Builder("Saturn", sun))
				.size(20)
				.color(Color.LIGHTGOLDENRODYELLOW)
				.speed(Math.PI/4000)
				.rotationSpeed(Math.PI/10000)
				.distance(560)
				.build();
		
		AsteroidBelt andromeda = (new AsteroidBelt.Builder("Andromeda",sun))
				.distance(400)
				.speed(Math.PI/4000)
				.asteroids(60)
				.build();
		
		//TODO: Builder Patttern!
		ArmedSpacecraft pS = ArmedSpaceShuttle.PlayerSpaceShuttle("Ikarus",(SpaceObject)earth,2,40,(Math.PI/140));
		AggressiveNavigator playerNav = ArmedShuttleNavigator.PlayerNavigator("Nasa",pS);
		playerNav.getRoute().add(mars);
		
		Satellite astra = (new Satellite.Builder("Astra", earth))
				.size(3,3)
				.distance(20)
				.levelOfDetail(8)
				.speed(-Math.PI/400)
				.color(Color.LIGHTGRAY)
				.build();
		
		Satellite hubble = (new Satellite.Builder("Hubble-Telescope", sun))
				.size(5,5)
				.distance(70)
				.levelOfDetail(8)
				.speed(-Math.PI/800)
				.color(Color.CADETBLUE)
				.build();
		
		Planet phobos = (new Planet.Builder("Phobos", mars))
				.size(3)
				.distance(55)
				.levelOfDetail(3)
				.speed(-Math.PI/300)
				.rotationSpeed(-Math.PI/400)
				.color(Color.LIGHTGRAY)
				.build();
		
		Planet deimos = (new Planet.Builder("Deimos", mars))
				.size(2)
				.distance(35)
				.levelOfDetail(3)
				.speed(Math.PI/600)
				.rotationSpeed(Math.PI*2/800)
				.color(Color.GRAY)
				.build();
		
		ArmedSpaceShuttle martians = new ArmedSpaceShuttle("Martians",mars,3,50,Math.PI/100);
		ArmedShuttleNavigator aliens = new ArmedShuttleNavigator("Alien Invader",martians,true);
		aliens.getRoute().add(saturn);
		aliens.getRoute().add(sun);
		aliens.setAutoAttack(true);
		
		Ship chineseShip = new Ship("Chinese",sun,3,50,Math.PI/100);
		BaseNavigator chinNav = new BaseNavigator("Xin Ping", chineseShip,true);
		chinNav.getRoute().add(mars);
		
		BattleCarrier mothership = new BattleCarrier("Mothership", sun,6, 480, Math.PI/500);
		ArmedShuttleNavigator carrierNav = new ArmedShuttleNavigator("Overlord",mothership,false);
		carrierNav.getRoute().add(earth);
		carrierNav.setAutoAttack(true);
		
		updateManager.addSpaceObject(milkyway);
		updateManager.addSpaceObject(sun);
		
		//var test_popup = //new PopupText("Hey I am the sun",sun.getCenter(),1500);
		var test_popup = new PopupText.Builder("Hey, I am a popup!")
				.color(Color.ALICEBLUE)
				.size(2)
				.lifetime(2000)
				.position(new AbsolutePoint(100,100))
				.build();
		
		updateManager.update();
	}

}
