/**
 * @Author Leonhard Applis
 * @Created 31.08.2018
 * @Package space.shuttle
 */
package space.shuttle;

import geom.Point;
import geom.Rectangle;
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
		super(name, parent, Color.GHOSTWHITE, new Rectangle(size,size+1), 0 , speed);
		this.parent=parent;
		this.orbitingDistance=orbitingDistance;
		distance=(int) (orbitingDistance+distanceTo(parent));
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
	/*
	@Override
	public void drawThisItem(GraphicsContext gc) {
		gc.setFill(color);
		area.draw(gc);
	}*/
	
	@Override 
	public void move(Point parentCenter) {
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
		System.out.println("Spaceship: " + name + " collided with " + other.toString() + " @" + center.x + "|" + center.y);
		if(!isDead()) {
			new Explosion("Explosion from" + name,center.x,center.y,1500,5,1.02,Color.MEDIUMVIOLETRED);
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
