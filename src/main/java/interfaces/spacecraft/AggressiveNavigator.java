package interfaces.spacecraft;

import interfaces.geom.Point;
import space.core.SpaceObject;

public interface AggressiveNavigator extends Navigator{

	public void attack(Point p); //Maybe Point? Or Spaceobject as targets?
	
	public void attack(SpaceObject o);
	
	public void autoAttack();
	
	public boolean isActivePlayerNavigator();
}
