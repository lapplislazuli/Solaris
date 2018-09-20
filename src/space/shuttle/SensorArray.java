/**
 * @Author Leonhard Applis
 * @Created 03.09.2018
 * @Package space.shuttle
 */
package space.shuttle;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import geom.Point;
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
	
	int radius;
	Point center;
	SpaceShuttle emitter;
	List<CollidingObject> detectedItems=new LinkedList<CollidingObject>();
	
	public SensorArray (SpaceShuttle emitter,int radius) {
		this.emitter=emitter;
		center=emitter.center;
		this.radius=radius;
	}
	
	@Override
	public void update() {
		detectedItems=
				CollisionManager.getInstance().collidables.stream()
				.filter(c-> c instanceof SpaceObject)
				.filter(c->!(c instanceof FixStar))
				.map(c->(SpaceObject)c)
				.filter(other -> isCovered(other.center.x, other.center.y))
				.filter(c->!(c == emitter))
				.collect(Collectors.toList());
	}
	@Override
	public void move(Point parentCenter) {
		//Should work without anything?
		//center=parentCenter;
	}
	
	private boolean isCovered(int otherX, int otherY) {
		return
				otherY>=center.y-radius && otherY<=center.y+radius
			&&	otherX>=center.x-radius && otherX<=center.x+radius;
	}

}
