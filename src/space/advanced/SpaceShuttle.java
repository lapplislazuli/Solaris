package space.advanced;

import interfaces.CollidingObject;
import interfaces.DestructibleObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import space.core.MovingSpaceObject;
import space.core.Satellite;
import space.core.SpaceObject;

/*
 * The SpaceShuttle orbits a Planet, then gets launched to another planet, and then orbits there
 * The whole "flying" Process will be displayed in the move
 */
public class SpaceShuttle extends MovingSpaceObject implements DestructibleObject{

	private boolean orbiting; 
	private SpaceObject target;
	private SpaceObject parent;
	private double orbitingDistance;
	
	public SpaceShuttle(String name, SpaceObject parent, int size, int orbitingDistance, double speed) {
		super(name, parent, Color.GHOSTWHITE, size, 0 , speed);
		this.parent=parent;
		this.orbitingDistance=orbitingDistance;
		distance=orbitingDistance+parent.getSize()/2;
	}
	
	
	public void setTarget(SpaceObject target) {
		this.target=target;
	}
	
	public void launch() {
		if(target!=null && parent.removeTrabant(this)) {
			target.addTrabant(this);
			orbiting=false;
			parent = target;
			target=null;
			
			//Distance+Degree relative to new Parent
			relativePos=degreeTo(parent);
			distance=(int)distanceTo(parent);
		}
		//Else?	
	}
	
	public SpaceObject getParent() { return parent;}
	public boolean isOrbiting() {return orbiting;}
	
	@SuppressWarnings("restriction")
	@Override
	public void draw(GraphicsContext gc) {
		gc.setFill(color);
		gc.fillRect(x-size/4, y-size/4, size/2, size/2);
	}
	
	@Override 
	public void move(int parentX, int parentY) {
		if(!orbiting) {
			if(distance>=orbitingDistance+parent.getSize()/2) 
				distance--;
			else 
				orbiting=true;
		}
		super.move(parentX, parentY);
	}

	public void destruct(CollidingObject other) {
		// TODO Auto-generated method stub
		System.out.println("Spaceship: " + name + " collided with " + other.toString() + " @" + x + "|" + y);
		//new Satellite(("SpaceTrash from " + name),parent,size,(int)orbitingDistance+parent.getSize()/2,speed);
		new Asteroid("Trash from " + name,parent,(int)orbitingDistance+parent.getSize()/2,speed,Asteroid.Type.TRASH);
		parent.removeTrabant(this);
		parent=null;
		target=null;
	}
}
