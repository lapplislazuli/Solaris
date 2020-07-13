package space.spacecraft.ships.devices;

import java.util.List;
import java.util.stream.Collectors;

import interfaces.geom.Point;
import interfaces.spacecraft.MountedWeapon;
import interfaces.spacecraft.Spacecraft;
import space.core.SpaceObject;
import space.spacecrafts.ships.Spaceshuttle;

public final class DroneRack implements MountedWeapon{
	
	
	public DroneRack(Spaceshuttle c){
		var droneMounts = c.getWeapons().stream().filter(w->w instanceof DroneMount).map(w -> (DroneMount) w).collect(Collectors.toList());

		this.droneMounts = (List<DroneMount>) droneMounts;
		carrier = c;
	}
	
	public DroneRack(List<DroneMount> drones){
		this.droneMounts = drones;
		if(drones.size()>0)
			carrier = (Spaceshuttle) drones.get(0).getParent();
		else
			carrier = null;
	}
	
	final List<DroneMount> droneMounts;
	final Spaceshuttle carrier;
	@Override
	public void activate() {
		for(var d:droneMounts)
			d.activate();
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


	@Override
	public Spacecraft getParent() {
		// TODO Auto-generated method stub
		return null;
	}
	
}