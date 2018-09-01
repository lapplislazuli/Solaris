/**
 * @Author Leonhard Applis
 * @Created 31.08.2018
 * @Package space.advanced
 */
package space.advanced;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import interfaces.logical.CollidingObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import space.core.MovingSpaceObject;
import space.core.SpaceObject;

@SuppressWarnings("restriction")
public class DistantGalaxy extends SpaceObject{
	
	List<FixStar> stars;
	Random r;
	
    public DistantGalaxy(String name, int width, int height, int stars) {
		super(name, 0, 0, 0);
		r=new Random();
		this.stars = new LinkedList<FixStar>();
		for(int i = 1;i<stars;i++) {
			this.stars.add(new FixStar(null,r.nextDouble(),r.nextDouble(),r.nextInt(50)*1000));
		}
	}
    
    @Override
	public boolean collides(CollidingObject other) {
		return false;
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
    
    @Override
    public void update() {
    	checkStars();
		super.update();
	};
	
	@Override
	public boolean isCovered(int x, int y) {
		return false;
	}
	
    public void checkStars() {
    	List<FixStar> deadStars = new ArrayList<>();
    	//Check if stars are dead
    	for(FixStar star : stars) {
    		if (star.dead == true) {
    			deadStars.add(star);
    		}
    	}
    	//Replace dead stars
    	for(FixStar star : deadStars) {
    		stars.remove(star);
			stars.add(new FixStar(null,r.nextDouble(),r.nextDouble(),r.nextInt(50)*1000));
    	}
    }
}
