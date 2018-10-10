package space.shuttle;

import geom.AbsolutePoint;
import geom.HShape;
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
	protected double orbitingDistance;
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
		if(isAliveAndRegistered()) {
			changeHierarchy();
			changeMovementAndPositionAttributes();
		}
	}

	protected void changeMovementAndPositionAttributes() {
		distance=(int)distanceTo(parent);
		relativePos=parent.degreeTo(this);
		evaluateBestSpeed();
	}

	protected void evaluateBestSpeed() {
		if(isFasterThanMe(parent)) {
			if(!movesInSameDirection(parent))
				speed=-speed;
		}
		else {
			if(movesInSameDirection(parent))
				speed=-speed;
		}
	}

	protected void changeHierarchy() {
		target.trabants.add(this);
		orbiting=false;
		parent = target;
		target=null;
	}

	protected boolean isAliveAndRegistered() {
		return target!=null && parent.trabants.remove(this);
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
	
	public void destruct() {
		System.out.println("Spaceship: " + toString() + " Destroyed @" +center.toString());
		if(!isDead()) {
			new Explosion("Explosion from" + name,center,5,1500,1.02,Color.MEDIUMVIOLETRED);
			new Asteroid("Trash from " + name,parent,(int)(orbitingDistance+distanceTo(parent)),speed,Asteroid.Type.TRASH);
			remove();
		}
	}
	
	@Override
	public void rotate() {
		rotation=degreeTo(parent);
	}
	
	public boolean isDead() {
		return parent==null;
	}
	
	public void remove() {
		parent.trabants.remove(this);
		parent=(target=null);
	}

	public double getOrbitingDistance() {return orbitingDistance;}
}
