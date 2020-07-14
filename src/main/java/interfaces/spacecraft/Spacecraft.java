package interfaces.spacecraft;

import java.util.List;
import java.util.Optional;

import interfaces.geom.Point;
import interfaces.logical.CollidingObject;
import interfaces.logical.DestructibleObject;
import interfaces.logical.MovingObject;
import interfaces.logical.MovingUpdatingObject;
import space.core.SpaceObject;
import space.spacecrafts.ships.Sensor;

public interface Spacecraft extends DestructibleObject,MovingObject,MovingUpdatingObject{

	public void launch();
	
	public boolean isDead();

	public SpaceObject getParent();
	
	public double getSpeed();
	
	public void setTarget(SpaceObject target);
	
	public SpacecraftState getState();

	public Spacecraft rebuildAt(String name, SpaceObject at);
	
	public double degreeTo(SpaceObject parent); //TODO: Move this somewhere better!
	
	public List<CollidingObject> getDetectedItems();
	
	public boolean isPlayer();

	public Point getCenter();
	
	public void setSensor(Sensor val);
	
	public void attack(Point p);
	
	public void attack(SpaceObject o);
	
	public Optional<SpaceObject> getNearestPossibleTarget();
}

