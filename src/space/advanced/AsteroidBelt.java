package space.advanced;

import java.util.LinkedList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import space.core.MovingSpaceObject;
import space.core.SpaceObject;

public class AsteroidBelt extends MovingSpaceObject{
	
	private List<Asteroid> astroids;
	
	public AsteroidBelt(String name, SpaceObject parent, int distance, double speed,int asteroids) {
		
		super(name, parent, null, 0, 0, 0);
		
		x=parent.getX();
		y=parent.getY();
		
		astroids=new LinkedList<Asteroid>();
		
		for(int i = 1; i<=asteroids;i++) {
			Asteroid a = new Asteroid(name+"#"+i,this,distance,speed);
			//move them on different points
			a.relativePos=i/Math.PI*2;
			astroids.add(a);
		}
	}
	
	@Override
	protected void draw(GraphicsContext gc) {
		//Do nothing here, just draw children with update
	}
}
