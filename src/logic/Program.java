package logic;

import space.advanced.AsteroidBelt;
import space.advanced.DistantGalaxy;
import space.core.Planet;
import space.core.Satellite;
import space.core.Star;
import space.shuttle.ShuttleNavigator;

import javafx.application.*;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.canvas.*;
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
        Canvas canvas = new Canvas();
        root.getChildren().add(canvas);
       
        Scene scene = new Scene(root,1200 , 600);
        
        GraphicsContext gc = canvas.getGraphicsContext2D();

        UpdateManager.getInstance().initUpdateManager(25, gc);
        updateManager = UpdateManager.getInstance();
        
        initSpace();
        
        canvas.widthProperty().bind(scene.widthProperty());
        canvas.heightProperty().bind(scene.heightProperty());

        MouseManager.getInstance().init(scene);
        KeyBoardManager.getInstance().init(scene);
        
        primaryStage.setTitle("Solaris");
        primaryStage.setScene(scene);
        primaryStage.show();
	}
	
	private void initSpace() {
		DistantGalaxy milkyway = new DistantGalaxy("MilkyWay",1200,800,250);
		
		Star sun = new Star("Sun",Color.ORANGE,30);
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
					.size(5)
					.distance(30)
					.levelOfDetail(8)
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
		
		AsteroidBelt andromeda = new AsteroidBelt("Andromeda",sun,350,Math.PI/4000,150);
		
		ShuttleNavigator nasa = new ShuttleNavigator.Builder("NASA")
				.shuttleName("Ikarus")
				.idlingTurns(Math.PI*2)
				.doesRespawn(true)
				.shuttlesize(4)
				.shuttleOrbitingDistance(40)
				.shuttleSpeed(Math.PI/70)
				.start(earth)
				.next(mars)
				.build();
		
		ShuttleNavigator AlienOverlord = new ShuttleNavigator.Builder("AlienOverlord")
				.shuttleName("Aliens")
				.doesRespawn(true)
				.shuttlesize(6)
				.shuttleOrbitingDistance(30)
				.shuttleSpeed(Math.PI/90)
				.start(mars)
				.next(earth)
				.next(sun)
				.next(earth)
				.build();
		
		Satellite astra = (new Satellite.Builder("Astra", earth))
				.size(3,3)
				.distance(20)
				.levelOfDetail(8)
				.speed(-Math.PI/400)
				.color(Color.LIGHTGRAY)
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

		updateManager.addSpaceObject(milkyway);
		updateManager.addSpaceObject(sun);
		updateManager.toUpdate.add(nasa);
	}
	
}
