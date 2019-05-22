package space.spacecrafts.ships;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import interfaces.geom.Point;
import interfaces.logical.CollidingObject;
import interfaces.logical.MovingObject;
import interfaces.logical.UpdatingObject;
import interfaces.spacecraft.Spacecraft;
import logic.manager.ManagerRegistry;
import space.advanced.FixStar;
import space.core.SpaceObject;

/*
 * This SensorArray is a collidable item, which Pseudocollides with everything in the radius 
 * The pseudocollided items will be reportet to parent-spaceshuttle, yet wont explode or anything
 */
public class SensorArray implements UpdatingObject,MovingObject{
	
	int radius;
	Point center;
	Spacecraft emitter;
	private List<CollidingObject> detectedItems=new LinkedList<CollidingObject>();
	
	public SensorArray (Spacecraft emitter,int radius) {
		this.emitter=emitter;
		center=emitter.getCenter();
		this.radius=radius;
	}
	
	@Override
	public void update() {
		detectedItems=
				ManagerRegistry.getCollisionManager().getRegisteredItems()
				.stream()
				.filter(c-> c instanceof SpaceObject)
				.filter(c->!(c instanceof FixStar))
				.map(c->(SpaceObject)c)
				.filter(other -> isCovered(other.getCenter().getX(), other.getCenter().getY()))
				.filter(c->!(c == emitter))
				.collect(Collectors.toList());
	}
	@Override
	public void move(Point parentCenter) {}
	
	private boolean isCovered(int otherX, int otherY) {
		return
				otherY>=center.getY()-radius && otherY<=center.getY()+radius
			&&	otherX>=center.getX()-radius && otherX<=center.getX()+radius;
	}

	public double getSpeed() {
		return emitter.getSpeed();
	}

	public void setSpeed(double val) {
		// I should not be able to set my speed
		// I only have the Speed of my Emitter
	}

	public List<CollidingObject> getDetectedItems() {
		return detectedItems;
	}

}
