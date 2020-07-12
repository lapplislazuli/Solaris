package space.spacecrafts.ships;

public final class DroneFactory {
	
	public final static Spaceshuttle standardLaserDrone(Spaceshuttle c) {
		return ShipFactory.standardLaserDrone("LaserDrone of "+c.getName(), c, 2, 20, Math.PI/2);
	}
	
}
