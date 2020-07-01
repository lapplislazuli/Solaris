package interfaces.spacecraft;

import interfaces.geom.Point;
import space.core.SpaceObject;

public interface MountedWeapon extends MountedDevice {
	
	public void setTarget(SpaceObject o);
	public void setTarget(Double dir);
	public void setTarget(Point p);
	
}
