package space.shuttle;

import geom.AbsolutePoint;
import geom.HShape;
import interfaces.logical.CollidingObject;
import interfaces.logical.DestructibleObject;
import javafx.scene.paint.Color;
import space.advanced.Asteroid;
import space.core.MovingSpaceObject;
import space.core.SpaceObject;
import space.effect.Explosion;

@SuppressWarnings("restriction")
public class SpaceShuttle extends MovingSpaceObject implements DestructibleObject{
	public boolean orbiting = true; 
	public SpaceObject target;
	public SpaceObject parent;
	double orbitingDistance;
	protected SensorArray sensor;
	
	public SpaceShuttle(String name, SpaceObject parent, int size, int orbitingDistance, double speed) {
		super(name, parent, Color.GHOSTWHITE, new HShape(5,10,3), 0 , speed);
		
		this.parent=parent;
		this.orbitingDistance=orbitingDistance;
		distance=(int) (orbitingDistance+distanceTo(parent));
		sensor = new SensorArray(this,50);

		shape.setLevelOfDetail(size/2);
	}
	
	public void launch() {
		if(target!=null && parent.trabants.remove(this)) {
			System.out.println("Launch "+ toString() + " from "+ parent.toString()+ " at " + target.toString());
			target.trabants.add(this);
			orbiting=false;
			parent = target;
			target=null;
			
			System.out.println("Set relative Pos from " + relativePos + " to " + degreeTo(parent));
			
			relativePos=degreeTo(parent);
			distance=(int)distanceTo(parent);
		}
	}
	
	@Override
	public void update() {
		super.update();
		sensor.update();
	}
	
	@Override 
	public void move(AbsolutePoint parentCenter) {
		if(!orbiting) {
			if(distance>=orbitingDistance+distanceTo(parent)) 
				distance--;
			else 
				orbiting=true;
		}
		sensor.move(center);
		super.move(parentCenter);
	}
	
	public void destruct(CollidingObject other) {
		System.out.println("Spaceship: " + toString() + " collided with " + other.toString() + " @" +center.toString());
		if(!isDead()) {
			new Explosion("Explosion from" + name,center.getX(),center.getY(),1500,5,1.02,Color.MEDIUMVIOLETRED);
			if(other instanceof SpaceObject)
				new Asteroid("Trash from " + name,(SpaceObject) other,(int)(orbitingDistance+distanceTo(parent)),speed,Asteroid.Type.TRASH);
			remove();
		}
	}

	public boolean isDead() {
		return parent==null;
	}
	
	public void remove() {
		parent.trabants.remove(this);
		parent=(target=null);
	}
}
