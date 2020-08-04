package junit.fakes;


import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import interfaces.geom.Point;
import interfaces.logical.CollidingObject;
import interfaces.spacecraft.Spacecraft;
import interfaces.spacecraft.SpacecraftState;
import space.core.SpaceObject;
import space.spacecrafts.ships.Sensor;

public class FakeSpacecraft implements Spacecraft{
	
	public boolean launched=false,dead=false,collided=false,moved=false,attacked=false,rebuild=false,removed=false,updated=false,isArmed=false;
	public boolean colliding = false,isPlayer=false;
	public double speed=0;
	public boolean speedSet=false;
	public Optional<SpaceObject> nearestTarget = Optional.empty();
	public String name;
	
	public Spacecraft rebuildAt (String name, SpaceObject at) {
		rebuild=true;
		FakeSpacecraft copy = new FakeSpacecraft();
		copy.parent=at;
		this.name = name;
		return copy;
	}
	
	public void attack(Point p) {
		attacked=true;
	}

	public void attack(SpaceObject o) {
		attacked=true;
	}

	public Optional<SpaceObject> getNearestPossibleTarget() {
		return nearestTarget;
	}

	public SpacecraftState state = SpacecraftState.ORBITING;
	
	public SpaceObject parent,target;
	
	public Point moveFrom,moveTo,fakeCenter;
	
	public List<CollidingObject> itemsToDetect= new LinkedList<CollidingObject>();
	
	public void launch() {
		launched=true;
		state=SpacecraftState.FLYING;
	}

	public boolean isDead() {
		return dead;
	}

	public SpaceObject getParent() {
		return parent;
	}

	public double getSpeed() {
		return speed;
	}

	public void setTarget(SpaceObject target) {
		this.target=target;
	}

	public SpacecraftState getState() {
		return state;
	}

	public double degreeTo(SpaceObject parent) {
		return 0;
	}

	public List<CollidingObject> getDetectedItems() {
		return itemsToDetect;
	}

	public void destruct() {
		dead=true;
	}

	public boolean collides(CollidingObject other) {
		return colliding;
	}

	public void updateHitbox() {}

	public void remove() {
		removed=true;
	}

	public boolean isOrphan() {
		return dead;
	}

	public void move(Point point) {
		moved=true;
		moveTo = point;
	}

	public boolean isActivePlayer() {
		return isPlayer;
	}

	public void setSpeed(double val) {
		speedSet=true;
		speed=val;
	}

	public Point getCenter() {
		return fakeCenter;
	}

	public void setSensor(Sensor val) {}

	public void update() {
		updated=true;
	}

	public String getName() {
		return name;
	}

	@Override
	public boolean isArmed() {
		return isArmed;
	}

	
}
