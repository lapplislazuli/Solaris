package interfaces.spacecraft;

import java.util.List;

import interfaces.geom.Point;
import interfaces.logical.RemovableObject;
import interfaces.logical.UpdatingObject;
import space.core.SpaceObject;

public interface Navigator extends UpdatingObject,RemovableObject{
	
	public Spacecraft getShuttle();
	public List<SpaceObject> getRoute();
	
	public void clearRoute();

	default boolean isInGoodLaunchPosition(SpaceObject target) {
		/*
		 * This method checks if a ship is in a good launch position.
		 * The primary check done is that the shuttle is *alive* (required for the interface to function as a default method)
		 * And then that the shuttle does not try to fly through the current parent object.
		 * 
		 * This is a bug that happened quite often and could be avoided that way. 
		 */
		if(getShuttle()!=null && !getShuttle().isDead()) {
			double delta = Math.abs(getShuttle().degreeTo(getShuttle().getParent()) - getShuttle().degreeTo(target));
			delta = Math.abs(delta-Math.PI);
			return delta<=0.1;	
		}
		return false;
	}
	
	public SpaceObject getNextWayPoint();
	
	public void commandLaunch();
	
	public void attack(Point p); //Maybe Point? Or Spaceobject as targets?
	
	public void attack(SpaceObject o);
	
	public void autoAttack();
	
	public boolean isActivePlayerNavigator();
	
	public boolean isArmed();
}
