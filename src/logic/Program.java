package logic;

import space.*;

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
	
	private List<SpaceObject> rootSpaceObjects;
	private ShuttleNavigator nav;
	private ShuttleNavigator nav2;
	
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
        
        initSpace();
        //Listeners for resize
        scene.widthProperty().addListener(evt -> drawAll(gc));
        scene.heightProperty().addListener(evt -> drawAll(gc));
	
        canvas.widthProperty().bind(scene.widthProperty());
        canvas.heightProperty().bind(scene.heightProperty());
        drawAll(gc);
        
        //ToDo: Kill Timer on Close
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
            	Platform.runLater ( () -> updateAll(gc) ); 
            }
        }, 0, 25);
        
        primaryStage.setTitle("Solaris");
        primaryStage.setScene(scene);
        primaryStage.show();
	}
	
	private void updateAll(GraphicsContext gc) {
		nav.update();
		nav2.update();
		drawAll(gc);
	}
	
	private void drawAll(GraphicsContext gc) {
		
		gc.setFill(new LinearGradient(0, 0, 0.2, 0.2, true, CycleMethod.REFLECT, 
                new Stop(0.0, Color.BLUE),
                new Stop(1.0, Color.DARKBLUE)));          
		gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
		
		for(SpaceObject item : rootSpaceObjects) {
			item.update(gc);
		}
	}
	
	private void initSpace() {
		DistantGalaxy milkyway = new DistantGalaxy("MilkyWay",1200,800,500);
		
		//Sun Will be centered in the first update
		Star sun = new Star("Sun",Color.ORANGE,100);
		Planet earth = new Planet("Earth", (SpaceObject)sun, Color.CYAN, 50, 150, Math.PI/200);
		Planet mars = new Planet("Mars", (SpaceObject)sun, Color.INDIANRED, 75, 250, Math.PI/100);
		Planet moon = new Planet("Moon", (SpaceObject)earth, Color.GRAY,10,20,Math.PI/80);
		
		Planet saturn = (new Planet.Builder("Saturn", sun))
							.size(100)
							.color(Color.LIGHTGOLDENRODYELLOW)
							.speed(Math.PI/1000)
							.distance(500)
							.build();
		
		AsteroidBelt andromeda = new AsteroidBelt("Andromeda",sun,320,Math.PI/2000,500);
		
		SpaceShuttle shuttle = new SpaceShuttle("Trumps SpaceTroopers",earth,15,5,0.05);
		SpaceShuttle shuttle2 = new SpaceShuttle("Alien SpaceShip",mars,15,5,0.05);
		
		nav = new ShuttleNavigator("NASA", shuttle);
		nav.addToRoute(sun);
		nav.addToRoute(saturn);
		
		nav2 = new ShuttleNavigator("AlienOverlord", shuttle2);
		nav2.addToRoute(earth);
		
		rootSpaceObjects=new LinkedList<SpaceObject>();
		rootSpaceObjects.add(milkyway);
		rootSpaceObjects.add(sun);
	}
	
}
