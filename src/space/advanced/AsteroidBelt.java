package space.advanced;

import java.util.LinkedList;
import java.util.List;

import interfaces.CollidingObject;
import javafx.scene.canvas.GraphicsContext;
import space.core.MovingSpaceObject;
import space.core.SpaceObject;

@SuppressWarnings("restriction")
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
			a.setRelativePos(i/Math.PI*2);
			astroids.add(a);
		}
	}
	
	@Override
	public boolean collides(CollidingObject other) {
		return false;
	}
	
	@Override
	public void move(int parentX, int parentY) {
		//For recentered Items, and moving stuff
		x=parentX;
		y=parentY;

		for(Asteroid a : astroids)
			a.move(x, y);
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		//Do nothing here, just draw asteroids
		for(Asteroid a : astroids)
			a.draw(gc);
	}
}
