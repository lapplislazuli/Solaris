/**
 * @Author Leonhard Applis
 * @Created 03.09.2018
 * @Package space.shuttle
 */
package space.shuttle;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import interfaces.logical.CollidingObject;
import interfaces.logical.MovingObject;
import interfaces.logical.UpdatingObject;
import logic.CollisionManager;
import space.advanced.FixStar;
import space.core.SpaceObject;

/*
 * This SensorArray is a collidable item, which Pseudocollides with everything in the radius 
 * The pseudocollided items will be reportet to parent-spaceshuttle, yet wont explode or anything
 */
public class SensorArray implements UpdatingObject,MovingObject{
	
	private int radius;
	private SpaceShuttle emitter;
	private int x,y;
	private List<CollidingObject> detectedItems;
	
	public SensorArray (SpaceShuttle emitter,int radius) {
		this.emitter=emitter;
		this.x=emitter.getX(); this.y=emitter.getY();
		this.radius=radius;
		detectedItems=new LinkedList<CollidingObject>();
	}
	
	public List<CollidingObject> getDetectedItems(){return detectedItems;}
	
	@Override
	public void update() {
		detectedItems=
				CollisionManager.getInstance().collidables.stream()
				.filter(c->!(c instanceof FixStar))
				.filter(c->isCovered(c))
				.filter(c->!(c == emitter))
				.collect(Collectors.toList());
	}

	private boolean isCovered(int otherX, int otherY) {
		return
				otherY>=y-radius && otherY<=y+radius
			&&	otherX>=x-radius && otherX<=x+radius;
	}
	private boolean isCovered(CollidingObject other) {
		if(other instanceof SpaceObject) {
			return isCovered(((SpaceObject)other).getX(),((SpaceObject)other).getY());
		}
		return false;
	}

	@Override
	public void move(int x, int y) {
		this.x=x;
		this.y=y;
	}

}
