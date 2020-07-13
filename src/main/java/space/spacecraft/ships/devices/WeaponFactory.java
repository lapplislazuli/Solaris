package space.spacecraft.ships.devices;

import java.util.function.Supplier;
import java.util.stream.Collectors;

import interfaces.spacecraft.MountedWeapon;
import space.spacecrafts.ships.Spaceshuttle;
import space.spacecrafts.ships.DroneFactory;

public final class WeaponFactory {
	
	public static final LaserCannon standardLaserCannon(Spaceshuttle shuttle) {
		return new LaserCannon(shuttle);
	}
	
	public static final RocketLauncher standardRocketLauncher(Spaceshuttle shuttle) {
		return new RocketLauncher(shuttle,60);
	}
	
	public static final DroneMount standardLaserDroneMount(Spaceshuttle shuttle) {
		Supplier<Spaceshuttle> laserDroneFn = () -> DroneFactory.standardLaserDrone(shuttle);
		return new DroneMount(laserDroneFn);
	}
	
	public static MountedWeapon combineDroneMountsToDroneRack(Spaceshuttle carrier) {
			var droneMounts = carrier.getWeapons().stream().filter(w->w instanceof DroneMount).map(w -> (DroneMount) w).collect(Collectors.toList());
			return new DroneRack(droneMounts);
	}
		
}
