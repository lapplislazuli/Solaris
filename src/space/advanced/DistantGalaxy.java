package space.advanced;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import space.core.SpaceObject;

public class DistantGalaxy extends SpaceObject{
	
	List<FixStar> stars;
	
    public DistantGalaxy(String name, int width, int height, int stars) {
		super(name, 0, 0, 0);
		this.stars = new LinkedList<FixStar>();
		
		Random r= new Random();
		for(int i = 1;i<stars;i++) {
			FixStar relativeFixStar  = new FixStar(name+"#"+i,r.nextDouble(), r.nextDouble());
			this.stars.add(relativeFixStar);
		}
	}
    
    @Override
    public void draw(GraphicsContext gc) {
    	//Fill Background
		gc.setFill(new LinearGradient(0, 0, 0.2, 0.2, true, CycleMethod.REFLECT, 
                new Stop(0.0, Color.BLUE),
                new Stop(1.0, Color.DARKBLUE)));          
		gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
		
    	//Draw Stars
    	for(FixStar star : stars) {
    		star.draw(gc);
    	}
    }
    
    
}
