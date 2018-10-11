package space.advanced;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import drawing.EmptyJFXDrawingInformation;
import drawing.JavaFXDrawingContext;
import geom.Circle;
import geom.AbsolutePoint;
import interfaces.drawing.DrawingContext;
import interfaces.logical.CollidingObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import space.core.SpaceObject;

@SuppressWarnings("restriction")
public class DistantGalaxy extends SpaceObject{
	
	List<FixStar> stars = new LinkedList<FixStar>();
	int maxStars;
	Random r =new Random();;
	
    public DistantGalaxy(String name,int stars) {
		super(name, new AbsolutePoint( 0, 0), new Circle(0),new EmptyJFXDrawingInformation(null)); 
		maxStars=stars;
		fillStars();
	}
    
    @Override
	public boolean collides(CollidingObject other) {return false;}
	 
    @Override
    public void drawShape(DrawingContext dc) {
    	drawBackGround(dc);
    	
    	dc.resetContext();
		
    	for(FixStar star : stars)
    		star.draw(dc);
    }
    
    public void drawBackGround(DrawingContext dc) {
    	if(dc instanceof JavaFXDrawingContext) {
			GraphicsContext gc = ((JavaFXDrawingContext)dc).getGraphicsContext();
	    	gc.setFill(new LinearGradient(0, 0, 0.2, 0.2, true, CycleMethod.REFLECT, 
	                new Stop(0.0, Color.BLUE),
	                new Stop(1.0, Color.DARKBLUE)));          
			gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
		}
    	else
    		throw new UnsupportedOperationException("Unsupported DrawingContext!");
    }
    
    @Override
    public void update() {
    	stars.removeIf(star->star.dead);
    	fillStars();
		super.update();
	};
	
    public void fillStars() {
    	while(stars.size()<maxStars)
    		stars.add(new FixStar(null,r.nextDouble(),r.nextDouble(),r.nextInt(50)*1000));
    }
}
