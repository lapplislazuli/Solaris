package space.spacecrafts.ships;

import java.util.Optional;

import interfaces.geom.Point;
import interfaces.logical.DestructibleObject;
import interfaces.spacecraft.ArmedSpacecraft;
import space.core.SpaceObject;

public class BattleCarrier extends Carrier implements ArmedSpacecraft{
	
	
	public BattleCarrier(String name, SpaceObject parent, int size, int orbitingDistance, double speed) {
		super(name, parent, size, orbitingDistance, speed);
	}
	
	@Override
	protected void spawnShip() {
		LaserDrone spawned = new LaserDrone(name + "'s ship no." + shipCounter, this, size/2 ,size*3,speed*4);
		spawned.relativePos = shipSpawnAngle(shipCounter,maxShips);
		drones.add(spawned);
		
		super.spawnShip();
	}

	public void attack(Point p) {
		// Carriers cannot attack points?
		// TODO: SensorArray for Point p with given Radious and spread attack for every found valid target!
	}

	public void attack(SpaceObject o) {
		launchShips(o);
	}
	
	@Override
	public BattleCarrier rebuildAt(String name, SpaceObject at) {
		BattleCarrier copy = new BattleCarrier(name,at,size,(int) orbitingDistance,speed);
		return copy;
	}

	@Override
	public Optional<SpaceObject> getNearestPossibleTarget() {
		Optional<SpaceObject> possibleTarget = Optional.empty();
		if(!sensor.detectedItems.isEmpty())
			possibleTarget=sensor.detectedItems.stream()
				.filter(c->c instanceof DestructibleObject)
				.filter(c -> c instanceof SpaceObject)
				.map(c-> (SpaceObject)c)
				.filter(c -> ! (c instanceof Carrier)) // Papa i shot a man
				.filter(c -> ! drones.contains(c)) 
				.findFirst();
		return possibleTarget;
	}
}
