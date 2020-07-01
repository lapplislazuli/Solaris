package space.spacecraft.ships.devices;

import interfaces.geom.Point;
import interfaces.spacecraft.CarrierDrone;
import interfaces.spacecraft.MountedWeapon;
import interfaces.spacecraft.Spacecraft;
import space.core.MovingSpaceObject;
import space.core.SpaceObject;
import space.spacecrafts.ships.LaserDrone;

public class DroneMount implements MountedWeapon {
	/*
	 * Note: This is overall a very strange class
	 * The Drone is added to the parents trabants, and has to have automatic logic. 
	 * The trabants will be handled mostly within the parents (carriers).
	 */
	
	
	SpaceObject target;
	Spacecraft parent;
	CarrierDrone drone;
	
	private double rebuild_cooldown = LaserDrone.STANDARD_REBUILD_TIME;
	public boolean drone_launched = false;
	
	public DroneMount(Spacecraft mount,CarrierDrone drone) {
		if(mount == null)
			throw new IllegalArgumentException("Mount cannot be null - it is required in later computations");
		if(drone == null)
			throw new IllegalArgumentException("Drone cannot be null");
		this.parent=mount;
		this.drone=drone;
	}
	
	@Override
	public void activate() {
		//Activate toggles drone attacks (if targets are set accordingly)
		if(!drone_launched)
			launch_drone();
		else
			revoke_drone();
	}

	@Override
	public Spacecraft getParent() {
		return parent;
	}

	@Override
	public void setTarget(SpaceObject o) {
		if(o == null)
			throw new IllegalArgumentException("Target cannot be null");
		this.target=o;
	}

	@Override
	public void setTarget(Double dir) {
		//TODO: get closest point?
	}

	@Override
	public void setTarget(Point p) {
		//TODO: get closest point?
	}

	@Override
	public boolean isReady() {
		return !drone.isDead();
	}

	@Override
	public void update() {
		if(drone.isDead() && rebuild_cooldown <=0) {
			drone = (CarrierDrone) drone.rebuildAt(drone.toString(), (SpaceObject) parent);
			var droneCasted = (MovingSpaceObject) drone;
			droneCasted.setRelativePos(droneSpawnAngle());
			setDroneCooldown();
			drone_launched = false;
		}
		if(drone.isDead() && rebuild_cooldown > 0) {
			rebuild_cooldown --;
			drone_launched = false;
		}
	}
	
	public void launch_drone(){
		if(isReady() && !drone_launched && target != null) {
			drone.setTarget(target);
			drone.launch();
			drone_launched = true;
			target = null;
		}
	}
	
	public void revoke_drone() {
		if(isReady() && drone_launched) {
			drone.setTarget((SpaceObject)parent);
			drone.launch();	
			drone_launched = false;
		}
	}
	
	protected void setDroneCooldown() {
		rebuild_cooldown = 30000/25; //UpdateRatio is 25/s so this will take 30 seconds
	}
	
	private double droneSpawnAngle() {
	//TODO: Rework this? its not smart
		int currentAlive = ((SpaceObject) parent).getTrabants().size();
		int max = currentAlive*2;
		return currentAlive* (Math.PI*2/max);
	}
	
}
