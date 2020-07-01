package interfaces.spacecraft;

import interfaces.geom.Point;
import interfaces.logical.UpdatingObject;
import space.core.SpaceObject;

public interface MountedWeapon extends MountedDevice,UpdatingObject {
	
	public void setTarget(SpaceObject o);
	public void setTarget(Double dir);
	public void setTarget(Point p);
	
	public boolean isReady();
	
}
