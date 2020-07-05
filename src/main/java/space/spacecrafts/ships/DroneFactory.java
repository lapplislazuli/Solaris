package space.spacecrafts.ships;

public final class DroneFactory {
	
	public final static LaserDrone standardLaserDrone(Spaceshuttle c) {
		return new LaserDrone("testDrone", c, 2, 10, Math.PI/2);
	}
	
}
