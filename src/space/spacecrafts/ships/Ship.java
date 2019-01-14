package space.spacecrafts.ships;

import java.util.List;

import org.pmw.tinylog.Logger;

import drawing.JavaFXDrawingInformation;
import geom.AbsolutePoint;
import geom.HShape;
import interfaces.drawing.DrawingInformation;
import interfaces.geom.Shape;
import interfaces.logical.CollidingObject;
import interfaces.spacecraft.Spacecraft;
import interfaces.spacecraft.SpacecraftState;
import javafx.scene.paint.Color;
import space.advanced.Asteroid;
import space.core.MovingSpaceObject;
import space.core.SpaceObject;
import space.effect.Explosion;

@SuppressWarnings("restriction")
public class Ship extends MovingSpaceObject implements Spacecraft{
	public SpaceObject target;
	public SpaceObject parent;
	public double orbitingDistance;
	
	protected SensorArray sensor;
	protected SpacecraftState state = SpacecraftState.ORBITING;
	
	protected int size;
	
	public Ship(String name, SpaceObject parent, int size, int orbitingDistance, double speed) {
		super(name, parent, new JavaFXDrawingInformation(Color.GHOSTWHITE), new HShape(size*2,size*3,size), orbitingDistance , speed);
		
		this.size=size;
		this.parent=parent;
		this.orbitingDistance=orbitingDistance;
		distance=(int) (orbitingDistance+distanceTo(parent));
		sensor = new SensorArray(this,50);

		shape.setLevelOfDetail(size/2);
	}
	
	public Ship(String name, SpaceObject parent,DrawingInformation dinfo,Shape s, int size, int orbitingDistance, double speed) {
		super(name, parent, dinfo, s, orbitingDistance , speed);
		
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
		state=SpacecraftState.FLYING;
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
		if(state!=SpacecraftState.ORBITING) {
			if(distance>=orbitingDistance+distanceTo(parent)) 
				distance--;
			else 
				state=SpacecraftState.ORBITING;
		}
		sensor.move(center);
		super.move(parentCenter);
	}
	
	public void destruct() {
		Logger.info("Spaceship: " + toString() + " Destroyed @" +center.toString());
		if(!isDead()) {
			state=SpacecraftState.DEAD;
			new Explosion("Explosion from" + name,center,5,1500,1.02,new JavaFXDrawingInformation(Color.MEDIUMVIOLETRED));
			new Asteroid("Trash from " + name,parent,(int)(orbitingDistance+distanceTo(parent)),speed,Asteroid.Type.TRASH);
			remove();
		}
	}
	
	@Override
	public void rotate() {
		rotation=degreeTo(parent);
	}
	
	public boolean isDead() {
		return state==SpacecraftState.DEAD;
	}
	
	public void remove() {
		parent.trabants.remove(this);
		parent=(target=null);
	}

	public double getOrbitingDistance() {return orbitingDistance;}


	public SpaceObject getParent() {return parent;}
	public void setTarget(SpaceObject target) {this.target=target;}

	public double getSpeed() {
		return speed;
	}

	public SpacecraftState getState() {
		return state;
	}

	public Ship rebuildAt(String name, SpaceObject at) {
		Ship copy = new Ship(name,at,dInfo,shape,size,(int) orbitingDistance,speed);
		return copy;
	}

	public List<CollidingObject> getDetectedItems() {
		return sensor.detectedItems;
	}
}
