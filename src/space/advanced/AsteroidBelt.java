/**
 * @Author Leonhard Applis
 * @Created 31.08.2018
 * @Package space.advanced
 */
package space.advanced;

import geom.Circle;
import geom.Point;
import interfaces.logical.CollidingObject;
import space.core.MovingSpaceObject;
import space.core.SpaceObject;

@SuppressWarnings("restriction")
public class AsteroidBelt extends MovingSpaceObject{
	public AsteroidBelt(String name, SpaceObject parent, int distance, double speed,int asteroids) {	
		super(name, parent, null, new Circle(0), 0, 0);		
		center=parent.center;

		for(int i = 1; i<=asteroids;i++) {
			Asteroid a = new Asteroid(name+"#"+i,this,distance,speed);
			a.relativePos=(i/Math.PI*2);
			trabants.add(a);
		}
	}
	
	@Override
	public boolean collides(CollidingObject other) {
		return false;
	}
	
	@Override
	public void move(Point parentCenter) {
		center=parentCenter;

		for(MovingSpaceObject asteroid : trabants)
			asteroid.move(center);
	}
	
}
