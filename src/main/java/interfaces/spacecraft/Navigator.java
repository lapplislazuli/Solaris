package interfaces.spacecraft;

import java.util.List;

import interfaces.logical.RemovableObject;
import interfaces.logical.UpdatingObject;
import space.core.SpaceObject;

public interface Navigator extends UpdatingObject,RemovableObject{
	
	public Spacecraft getShuttle();
	public List<SpaceObject> getRoute();
	
	public void clearRoute();

	default boolean isInGoodLaunchPosition(SpaceObject target) {
		if(getShuttle()!=null && !getShuttle().isDead()) {
			double delta = Math.abs(getShuttle().degreeTo(getShuttle().getParent()) - getShuttle().degreeTo(target));
			delta = Math.abs(delta-Math.PI);
			return delta<=0.1;	
		}
		return false;
	}
	
	public SpaceObject getNextWayPoint();
	
	public void commandLaunch();
}
