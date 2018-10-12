package logic;

import space.advanced.AsteroidBelt;
import space.advanced.DistantGalaxy;
import space.core.Planet;
import space.core.Satellite;
import space.core.Star;
import space.shuttle.ShuttleNavigator;
import drawing.JavaFXDrawingContext;
import drawing.JavaFXDrawingInformation;
import geom.AbsolutePoint;
import javafx.application.*;
import javafx.stage.Stage;
import logic.manager.DrawingManager;
import logic.manager.KeyBoardManager;
import logic.manager.MouseManager;
import logic.manager.UpdateManager;
import javafx.scene.*;

import javafx.scene.paint.Color;
@SuppressWarnings("restriction")
public class Program extends Application{
	
	private UpdateManager updateManager;
	
	public static void main(String[] args) {
		System.out.println("Starting Solaris");
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {

		Group root = new Group();
		
		JavaFXDrawingContext jfx = new JavaFXDrawingContext(root);
		
        UpdateManager.getInstance().initUpdateManager(25);
        updateManager = UpdateManager.getInstance();
        
        DrawingManager.getInstance().initDrawingManager(jfx);
        
        initSpace();
        
        Scene scene = new Scene(root,1200 , 600);
        
        jfx.bindSizeProperties(scene);
        
        MouseManager.getInstance().init(scene);
        KeyBoardManager.getInstance().init(scene);
        
        primaryStage.setTitle("Solaris");
        primaryStage.setScene(scene);
        primaryStage.show();
	}
	
	@Override
	public void stop() {
		System.out.println("Closing Solaris");
		System.exit(0);
	}
	
	@SuppressWarnings("unused")
	private void initSpace() {
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
				.distance(250)
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
				.distance(480)
				.build();
		
		AsteroidBelt andromeda = (new AsteroidBelt.Builder("Andromeda",sun))
				.distance(350)
				.speed(Math.PI/4000)
				.asteroids(100)
				.build();
		
		ShuttleNavigator nasa = new ShuttleNavigator.Builder("NASA")
				.shuttleName("Ikarus")
				.idlingTurns(5*Math.PI/2)
				.doesRespawn(true)
				.isPlayer(true)
				.shuttlesize(2)
				.shuttleOrbitingDistance(40)
				.shuttleSpeed(Math.PI/140)
				.start(earth)
				.next(mars)
				.build();
		
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
		
		Planet phobos = (new Planet.Builder("Phobos", saturn))
				.size(4)
				.distance(70)
				.levelOfDetail(4)
				.speed(-Math.PI/300)
				.rotationSpeed(-Math.PI/400)
				.color(Color.LIGHTGRAY)
				.build();
		
		Planet deimos = (new Planet.Builder("Deimos", saturn))
				.size(3)
				.distance(50)
				.levelOfDetail(4)
				.speed(Math.PI/600)
				.rotationSpeed(Math.PI*2/800)
				.color(Color.GRAY)
				.build();
		
		ShuttleNavigator aliens = new ShuttleNavigator.Builder("Alien Overlord")
				.shuttleName("Martians")
				.idlingTurns(Math.PI)
				.doesRespawn(true)
				.isPlayer(false)
				.shuttlesize(3)
				.shuttleOrbitingDistance(50)
				.shuttleSpeed(Math.PI/100)
				.start(mars)
				.next(saturn)
				.next(sun)
				.build();
		
		ShuttleNavigator chinesePeople = new ShuttleNavigator.Builder("Xin Ping")
				.shuttleName("Chinese")
				.idlingTurns(Math.PI)
				.doesRespawn(true)
				.isPlayer(false)
				.shuttlesize(3)
				.shuttleOrbitingDistance(55)
				.shuttleSpeed(Math.PI/90)
				.start(sun)
				.next(mars)
				.build();
		
		updateManager.addSpaceObject(milkyway);
		updateManager.addSpaceObject(sun);
		updateManager.registeredItems.add(nasa);
		updateManager.registeredItems.add(aliens);
		updateManager.registeredItems.add(chinesePeople);
	}
	
}
