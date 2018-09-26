package space.advanced;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import geom.Circle;
import geom.Point;
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
		super(name, new Point( 0, 0), new Circle(0)); 
		maxStars=stars;
		fillStars();
	}
    
    @Override
	public boolean collides(CollidingObject other) {return false;}
	@Override
	public boolean isCovered(int x, int y) {return false;}
	 
    @Override
    public void drawThisItem(GraphicsContext gc) {
    	//Fill Background
		gc.setFill(new LinearGradient(0, 0, 0.2, 0.2, true, CycleMethod.REFLECT, 
                new Stop(0.0, Color.BLUE),
                new Stop(1.0, Color.DARKBLUE)));          
		gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
		
    	for(FixStar star : stars)
    		star.draw(gc);
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
