package space;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/*
 * The SpaceShuttle orbits a Planet, then gets launched to another planet, and then orbits there
 * The whole "flying" Process will be displayed in the move
 */
public class SpaceShuttle extends MovingSpaceObject{

	private boolean orbiting; 
	private SpaceObject target;
	private SpaceObject parent;
	private double orbitingDistance;
	
	public SpaceShuttle(String name, SpaceObject parent, int size, int orbitingDistance, double speed) {
		super(name, parent, Color.GHOSTWHITE, size, 0 , speed);
		this.parent=parent;
		this.orbitingDistance=orbitingDistance;
		distance=orbitingDistance+parent.size/2;
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
	protected void draw(GraphicsContext gc) {
		gc.setFill(color);
		gc.fillRect(x-size/4, y-size/4, size/2, size/2);
	}
	
	@Override 
	protected void move(int parentX, int parentY) {
		if(!orbiting) {
			if(distance>=orbitingDistance+parent.size/2) 
				distance--;
			else 
				orbiting=true;
		}
		super.move(parentX, parentY);
	}
}
