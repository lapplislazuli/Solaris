package logic;

import space.*;
import space.advanced.AsteroidBelt;
import space.advanced.DistantGalaxy;
import space.advanced.SpaceShuttle;
import space.core.Planet;
import space.core.SpaceObject;
import space.core.Star;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.*;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
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
        updateManager=new UpdateManager(gc);
        initSpace();
        //Listeners for resize
        //scene.widthProperty().addListener(evt -> drawAll(gc));
        //scene.heightProperty().addListener(evt -> drawAll(gc));
	
        canvas.widthProperty().bind(scene.widthProperty());
        canvas.heightProperty().bind(scene.heightProperty());
        
        //ToDo: Kill Timer on Close
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
            	Platform.runLater ( () -> updateManager.update() ); 
            }
        }, 0, 25);
        
        primaryStage.setTitle("Solaris");
        primaryStage.setScene(scene);
        primaryStage.show();
	}
	
	private void initSpace() {
		DistantGalaxy milkyway = new DistantGalaxy("MilkyWay",1200,800,500);
		
		//Sun Will be centered in the first update
		Star sun = new Star("Sun",Color.ORANGE,60);
		Planet earth = new Planet("Earth", sun, Color.CYAN, 25, 150, Math.PI/200);
		Planet mars = new Planet("Mars", sun, Color.INDIANRED, 35, 250, Math.PI/100);
		Planet moon = new Planet("Moon", earth, Color.GRAY,10,10,Math.PI/80);
		
		Planet saturn = (new Planet.Builder("Saturn", sun))
							.size(45)
							.color(Color.LIGHTGOLDENRODYELLOW)
							.speed(Math.PI/1000)
							.distance(500)
							.build();
		
		AsteroidBelt andromeda = new AsteroidBelt("Andromeda",sun,350,Math.PI/2000,200);
		
		SpaceShuttle shuttle = new SpaceShuttle("Trumps SpaceTroopers",earth,5,10,0.05);
		SpaceShuttle shuttle2 = new SpaceShuttle("Alien SpaceShip",mars,4,15,0.05);
		
		ShuttleNavigator nav1 = new ShuttleNavigator("NASA", shuttle);
		nav1.addToRoute(sun);
		
		ShuttleNavigator  nav2 = new ShuttleNavigator("AlienOverlord", shuttle2);
		nav2.addToRoute(earth);
		
		updateManager.addSpaceObject(milkyway);
		updateManager.addSpaceObject(sun);
		
		updateManager.toUpdate.add(nav2);
		updateManager.toUpdate.add(nav1);
	}
	
}
