/**
 * @Author Leonhard Applis et Al.
 * @Created 31.08.2018
 * @Package logic
 */

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
		
		Star sun = new Star("Sun",Color.ORANGE,60);
		Planet earth = new Planet("Earth", sun, Color.CYAN, 25, 150, Math.PI/2000);
		Planet mars = new Planet("Mars", sun, Color.INDIANRED, 35, 250, Math.PI/1000);
		Planet moon = new Planet("Moon", earth, Color.LIGHTGRAY,10,30,Math.PI/800);
		
		Planet saturn = (new Planet.Builder("Saturn", sun))
							.size(45)
							.color(Color.LIGHTGOLDENRODYELLOW)
							.speed(Math.PI/4000)
							.distance(480)
							.build();
		
		//AsteroidBelt andromeda = new AsteroidBelt("Andromeda",sun,350,Math.PI/4000,50);
		
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
		
		Satellite Astra = new Satellite("Astra",earth,5,20, -Math.PI/400);
		
		Planet phobos = new Planet("Phobos", saturn, Color.LIGHTGRAY, 8, 70, -Math.PI/600);
		Planet deimos = new Planet("Deimos", saturn, Color.GRAY, 7, 50, Math.PI/500);

		updateManager.addSpaceObject(milkyway);
		updateManager.addSpaceObject(sun);
		updateManager.toUpdate.add(nasa);
	}
	
}
