package space.spacecraft.ships.devices;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import interfaces.geom.Point;
import interfaces.spacecraft.CarrierDrone;
import interfaces.spacecraft.MountedWeapon;
import interfaces.spacecraft.Spacecraft;
import space.spacecrafts.ships.Spaceshuttle;
import space.core.SpaceObject;
import space.spacecrafts.ships.DroneFactory;

public final class WeaponFactory {
	
	public static final LaserCannon standardLaserCannon(Spaceshuttle shuttle) {
		return new LaserCannon(shuttle);
	}
	
	public static final RocketLauncher standardRocketLauncher(Spaceshuttle shuttle) {
		return new RocketLauncher(shuttle,60);
	}
	
	public static final DroneMount standardLaserDroneMount(Spaceshuttle shuttle) {
		Supplier<CarrierDrone> laserDroneFn = () -> DroneFactory.standardLaserDrone(shuttle);
		return new DroneMount(laserDroneFn);
	}
	
	public static MountedWeapon combineDroneMountsToDroneRack(Spaceshuttle carrier) {
			
			final class DroneRack implements MountedWeapon{
				
				DroneRack(List<DroneMount> drones){
					this.droneMounts = drones;
				}
				
				final List<DroneMount> droneMounts;
				@Override
				public void activate() {
					for(var d:droneMounts)
						d.activate();
				}
	
				@Override
				public Spacecraft getParent() {
					return carrier;
				}
	
				@Override
				public void update() {
					// The droneMounts are updated separately with all the other potential weapons
				}
	
				@Override
				public void setTarget(SpaceObject o) {
					for(var d:droneMounts)
						d.setTarget(o);
				}
	
				@Override
				public void setTarget(Double dir) {
					for(var d:droneMounts)
						d.setTarget(dir);
				}
	
				@Override
				public void setTarget(Point p) {
					for(var d:droneMounts)
						d.setTarget(p);
				}
	
				@Override
				public boolean isReady() {
					return true; // The rack is always ready, but not necessarily all of the droneMounts
				}
				
			}
			var droneMounts = carrier.getWeapons().stream().filter(w->w instanceof DroneMount).map(w -> (DroneMount) w).collect(Collectors.toList());
			return new DroneRack(droneMounts);
		}
		
}
