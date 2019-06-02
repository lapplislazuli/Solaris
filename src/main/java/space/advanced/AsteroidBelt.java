package space.advanced;

import geom.Circle;
import drawing.EmptyJFXDrawingInformation;
import interfaces.geom.Point;
import interfaces.logical.CollidingObject;
import interfaces.logical.MovingUpdatingObject;
import space.core.MovingSpaceObject;
import space.core.SpaceObject;

public class AsteroidBelt extends MovingSpaceObject{
	
	private AsteroidBelt(Builder builder) {
		super(builder.name,builder.parent, new EmptyJFXDrawingInformation(), new Circle(0),0,0);
		center=builder.parent.getCenter();
		
		for(int i = 1; i<builder.asteroids;i++) {
			Asteroid a = new Asteroid(name+"#"+i,this,builder.distance,builder.speed);
			a.setRelativePos(i/Math.PI*2);
		}
		for(MovingUpdatingObject t:trabants)
			if(t instanceof Asteroid)
				((Asteroid)t).move(center);
		
		distance=builder.distance;
		speed=builder.speed;
	}

	@Override
	public boolean collides(CollidingObject other) {
		return false;
	}
	
	@Override
	public void move(Point parentCenter) {
		center=parentCenter;

		for(MovingUpdatingObject asteroid : trabants)
			asteroid.move(center);
	}
	
	public static class Builder{
		private final String name;
		private final SpaceObject parent;
		private double speed=0;
		private int distance=0;
		private int asteroids=0;
		
		public Builder(String name,SpaceObject parent) throws IllegalArgumentException{
			if(name==null||name.isEmpty())
				throw new IllegalArgumentException("Name cannot be null or empty");
			if(parent==null)
				throw new IllegalArgumentException("Parent cannot be null");
			this.name=name;
			this.parent=parent;
		}
		
		public Builder distance(int val){ 
			if(val<0)
				throw new IllegalArgumentException("Distance cannot be negative");
			distance= val; 
			return this;
		}
		public Builder asteroids(int val){
			if(val<0)
				throw new IllegalArgumentException("Asteroid-Count cannot be negative");
			asteroids= val; 
			return this;
		}
		
		public Builder speed(double val){
			speed= val; 
			return this;
		}
		
		public AsteroidBelt build() {
			return new AsteroidBelt(this);
		}
	}
	
}
