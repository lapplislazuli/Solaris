package interfaces.spacecraft;

import interfaces.geom.Point;
import interfaces.logical.UpdatingObject;
import space.core.SpaceObject;
import space.spacecrafts.ships.Spaceshuttle;

public interface MountedWeapon extends MountedDevice,UpdatingObject {
	
	public void setTarget(SpaceObject o);
	public void setTarget(Double dir);
	public void setTarget(Point p);
	
	public boolean isReady();
	//Added to move the weapons from spaceshuttle to its copy
	public void setParent(Spaceshuttle copy);
	
}
