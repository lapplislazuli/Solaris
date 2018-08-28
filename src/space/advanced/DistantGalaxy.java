package space.advanced;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
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
    public void update(GraphicsContext gc) {
    	for(FixStar star : stars) {
    		star.draw(gc);
    	}
    	//No need to do anything else
    }
}
