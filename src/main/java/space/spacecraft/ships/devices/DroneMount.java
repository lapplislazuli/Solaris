package space.spacecraft.ships.devices;

import java.util.function.Supplier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import interfaces.geom.Point;
import interfaces.spacecraft.MountedWeapon;
import interfaces.spacecraft.Spacecraft;
import space.core.MovingSpaceObject;
import space.core.SpaceObject;
import space.spacecrafts.ships.Spaceshuttle;

public class DroneMount implements MountedWeapon {
	/*
	 * Note: This is overall a very strange class
	 * The Drone is added to the parents trabants, and has to have automatic logic. 
	 * The trabants will be handled mostly within the parents (carriers).
	 */

	protected static Logger logger = LogManager.getLogger(DroneMount.class);
	
	
	SpaceObject target;
	Spacecraft parent;
	Spaceshuttle drone;
	
	Supplier<Spaceshuttle> factoryMethod = () -> null;
	
	private double rebuild_cooldown = 10000/25;
	public boolean drone_launched = false;
	
	@Deprecated
	public DroneMount(Spacecraft mount,Spaceshuttle drone) {
		if(mount == null)
			throw new IllegalArgumentException("Mount cannot be null - it is required in later computations");
		if(drone == null)
			throw new IllegalArgumentException("Drone cannot be null");
		this.parent=mount;
		this.drone=drone;

		moveDroneToSpawn();
	}
	
	public DroneMount(Supplier<Spaceshuttle> droneSupplier) {
		this.factoryMethod = droneSupplier;
		this.drone = droneSupplier.get();
		if(drone==null)
			throw new IllegalArgumentException("The supplierfunction returned a null value - it is invalid");
		this.parent = (Spacecraft) drone.getParent();

		moveDroneToSpawn();
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

	private void moveDroneToSpawn() {
		var droneCasted = (MovingSpaceObject) drone;
		double currentRelativeAngle = droneSpawnAngle();
		droneCasted.setRelativePos(currentRelativeAngle);
		droneCasted.move(parent.getCenter());
		logger.debug("Moved Drone for " + parent.toString() + " with Drone " + drone.toString() + " and set it to " + currentRelativeAngle + " radiant degree to parent");
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
			this.drone = factoryMethod.get();
			this.parent = (Spacecraft) drone.getParent();
			moveDroneToSpawn();
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
		//TODO: Currently, the first drone spawns at 0, second drone at 0 too, third drone spawns at 1/2 rotation, fourth drone at 1/4 rotation (this is bullshit)
		int currentAlive = ((Spaceshuttle) parent).getDrones().size(); //-1 to readjust for the currently build Drone
		int currentAliveEscaped = Math.max(currentAlive, 0); // So I don't have negative values for the first drone
		long maxDrones = ((Spaceshuttle) parent).getWeapons().stream().filter(w-> w instanceof DroneMount).count()+1;
		return currentAliveEscaped * (Math.PI*2/maxDrones);
	}
	
	public Spaceshuttle getDrone() {
		return drone;
	}
	
}
