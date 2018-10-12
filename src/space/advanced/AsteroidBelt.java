package space.advanced;

import geom.Circle;
import drawing.EmptyJFXDrawingInformation;
import geom.AbsolutePoint;
import interfaces.logical.CollidingObject;
import space.core.MovingSpaceObject;
import space.core.SpaceObject;

public class AsteroidBelt extends MovingSpaceObject{
	
	private AsteroidBelt(Builder builder) {
		super(builder.name,builder.parent, new EmptyJFXDrawingInformation(), new Circle(0),0,0);
		center=builder.parent.center;
		
		for(int i = 1; i<=builder.asteroids;i++) {
			Asteroid a = new Asteroid(name+"#"+i,this,builder.distance,builder.speed);
			a.relativePos=(i/Math.PI*2);
			trabants.add(a);
		}
	}

	@Override
	public boolean collides(CollidingObject other) {
		return false;
	}
	
	@Override
	public void move(AbsolutePoint parentCenter) {
		center=parentCenter;

		for(MovingSpaceObject asteroid : trabants)
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
			if(distance<0)
				throw new IllegalArgumentException("Distance cannot be negative");
			distance= val; 
			return this;
		}
		public Builder asteroids(int val){
			if(asteroids<0)
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
