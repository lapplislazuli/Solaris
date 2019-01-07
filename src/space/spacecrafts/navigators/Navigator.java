package space.spacecrafts.navigators;

import java.util.List;

import interfaces.logical.UpdatingObject;
import space.core.SpaceObject;
import space.spacecrafts.ships.SpaceShuttle;

public interface Navigator extends UpdatingObject{
	
	public SpaceShuttle getShuttle();
	public List<SpaceObject> getRoute();
	
	public void clearRoute();

	default boolean isInGoodLaunchPosition(SpaceObject target) {
		if(getShuttle()!=null && !getShuttle().isDead()) {
			double delta = Math.abs(getShuttle().degreeTo(getShuttle().parent) - getShuttle().degreeTo(target));
			delta = Math.abs(delta-Math.PI);
			return delta<=0.1;	
		}
		return false;
	}
	
	public SpaceObject getNextWayPoint();
}
