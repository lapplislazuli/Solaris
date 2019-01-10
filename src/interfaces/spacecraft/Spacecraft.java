package interfaces.spacecraft;

import interfaces.logical.DestructibleObject;
import interfaces.logical.MovingObject;
import space.core.SpaceObject;

public interface Spacecraft extends DestructibleObject,MovingObject{

	public void launch();
	
	public boolean isDead();
	
	public Spacecraft copy(); //For respawn purposes

	public SpaceObject getParent();
	
	public double getSpeed();
	
	public void setTarget(SpaceObject target);
	
	public SpacecraftState getState();

	public double degreeTo(SpaceObject parent); //TODO: Move this somewhere better!
}

