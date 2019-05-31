package interfaces.spacecraft;

import java.util.Optional;

import interfaces.geom.Point;
import space.core.SpaceObject;

public interface ArmedSpacecraft extends Spacecraft{
	
	public void attack(Point p);
	
	public void attack(SpaceObject o);
	
	public Optional<SpaceObject> getNearestPossibleTarget();
}