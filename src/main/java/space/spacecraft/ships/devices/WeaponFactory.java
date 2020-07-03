package space.spacecraft.ships.devices;

import java.util.function.Supplier;

import interfaces.spacecraft.CarrierDrone;
import space.spacecrafts.ships.ArmedSpaceShuttle;
import space.spacecrafts.ships.DroneFactory;

public final class WeaponFactory {
	
	public static final LaserCannon standardLaserCannon(ArmedSpaceShuttle shuttle) {
		return new LaserCannon(shuttle);
	}
	
	public static final RocketLauncher standardRocketLauncher(ArmedSpaceShuttle shuttle) {
		return new RocketLauncher(shuttle,60);
	}
	
	public static final DroneMount standardLaserDroneMount(ArmedSpaceShuttle shuttle) {
		Supplier<CarrierDrone> laserDroneFn = () -> DroneFactory.standardLaserDrone(shuttle);
		return new DroneMount(laserDroneFn);
	}
}
