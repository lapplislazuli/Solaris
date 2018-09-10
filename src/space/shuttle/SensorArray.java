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
	
	int radius,x,y;
	SpaceShuttle emitter;
	List<CollidingObject> detectedItems=new LinkedList<CollidingObject>();
	
	public SensorArray (SpaceShuttle emitter,int radius) {
		this.emitter=emitter;
		x=emitter.x; 
		y=emitter.y;
		this.radius=radius;
	}
	
	@Override
	public void update() {
		detectedItems=
				CollisionManager.getInstance().collidables.stream()
				.filter(c-> c instanceof SpaceObject)
				.filter(c->!(c instanceof FixStar))
				.map(c->(SpaceObject)c)
				.filter(other -> isCovered(other.x, other.y))
				.filter(c->!(c == emitter))
				.collect(Collectors.toList());
	}
	@Override
	public void move(int x, int y) {
		this.x=x;
		this.y=y;
	}
	
	private boolean isCovered(int otherX, int otherY) {
		return
				otherY>=y-radius && otherY<=y+radius
			&&	otherX>=x-radius && otherX<=x+radius;
	}

}
