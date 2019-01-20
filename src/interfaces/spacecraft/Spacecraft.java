package interfaces.spacecraft;

import java.util.List;

import interfaces.logical.CollidingObject;
import interfaces.logical.DestructibleObject;
import interfaces.logical.MovingObject;
import space.core.SpaceObject;

public interface Spacecraft extends DestructibleObject,MovingObject{

	public void launch();
	
	public boolean isDead();

	public SpaceObject getParent();
	
	public double getSpeed();
	
	public void setTarget(SpaceObject target);
	
	public SpacecraftState getState();

	public Spacecraft rebuildAt(String name, SpaceObject at);
	
	public double degreeTo(SpaceObject parent); //TODO: Move this somewhere better!
	
	public List<CollidingObject> getDetectedItems();
}
