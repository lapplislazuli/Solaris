package interfaces.spacecraft;

import interfaces.geom.Point;
import space.core.SpaceObject;

public interface ArmedSpacecraft extends Spacecraft{
	
	public void attack(Point p); //Maybe Point? Or Spaceobject as targets?
	
	public void attack(SpaceObject o);
}