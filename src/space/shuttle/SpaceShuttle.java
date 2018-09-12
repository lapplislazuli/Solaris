/**
 * @Author Leonhard Applis
 * @Created 31.08.2018
 * @Package space.shuttle
 */
package space.shuttle;

import interfaces.logical.CollidingObject;
import interfaces.logical.DestructibleObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import space.advanced.Asteroid;
import space.core.MovingSpaceObject;
import space.core.SpaceObject;
import space.effect.Explosion;

/*
 * The SpaceShuttle orbits a Planet, then gets launched to another planet, and then orbits there
 * The whole "flying" Process will be displayed in the move
 */
@SuppressWarnings("restriction")
public class SpaceShuttle extends MovingSpaceObject implements DestructibleObject{
	boolean orbiting = true; 
	SpaceObject target, parent;
	double orbitingDistance;
	protected SensorArray sensor;
	
	public SpaceShuttle(String name, SpaceObject parent, int size, int orbitingDistance, double speed) {
		super(name, parent, Color.GHOSTWHITE, size, 0 , speed);
		this.parent=parent;
		this.orbitingDistance=orbitingDistance;
		distance=orbitingDistance+parent.size/2;
		sensor = new SensorArray(this,50);
	}
	
	public void launch() {
		if(target!=null && parent.trabants.remove(this)) {
			target.trabants.add(this);
			orbiting=false;
			parent = target;
			target=null;
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
	public void draw(GraphicsContext gc) {
		gc.setFill(color);
		gc.fillRect(x-size/2, y-size/2, size, size);
		super.draw(gc);
	}
	
	@Override 
	public void move(int parentX, int parentY) {
		if(!orbiting) {
			if(distance>=orbitingDistance+parent.size/2) 
				distance--;
			else 
				orbiting=true;
		}
		sensor.move(x, y);
		super.move(parentX, parentY);
	}

	
	public void destruct(CollidingObject other) {
		System.out.println("Spaceship: " + name + " collided with " + other.toString() + " @" + x + "|" + y);
		if(!isDead()) {
			new Explosion("Explosion from" + name,x,y,1500,size*2,1.02,Color.MEDIUMVIOLETRED);
			if(other instanceof SpaceObject)
				new Asteroid("Trash from " + name,(SpaceObject) other,(int)orbitingDistance+parent.size,speed,Asteroid.Type.TRASH);
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
