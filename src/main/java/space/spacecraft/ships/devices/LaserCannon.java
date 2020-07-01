package space.spacecraft.ships.devices;

import interfaces.geom.Point;
import interfaces.logical.UpdatingObject;
import interfaces.spacecraft.MountedWeapon;
import interfaces.spacecraft.Spacecraft;
import space.core.SpaceObject;
import space.spacecrafts.ships.BaseShip;
import space.spacecrafts.ships.missiles.Laserbeam;

public class LaserCannon implements MountedWeapon, UpdatingObject {

	
	private double targetDirection; // In radiant from the emitter
	private double cooldown;
	private final double max_cooldown;
	private BaseShip parent;
	
	public LaserCannon(BaseShip mount) {
		this.parent=mount;
		this.max_cooldown=Laserbeam.COMMON_MAX_COOLDOWN;
	}
	
	public LaserCannon(BaseShip mount,double max_cooldown) {
		this.parent=mount;
		this.max_cooldown=max_cooldown;
	}
	
	@Override
	public void activate() {
		if(isReady()) {
			new Laserbeam("Rocket from " + parent.getName(), parent,targetDirection,Laserbeam.COMMON_LASER_SPEED);
			cooldown = max_cooldown;			
			// Alter parameters to not be ready next time without newly set target
			targetDirection=0;
		}
	}
	
	@Override
	public Spacecraft getParent() {
		return parent;
	}

	@Override
	public void setTarget(SpaceObject o) {
		this.targetDirection = parent.getCenter().degreeTo(o.getCenter());
	}

	@Override
	public void setTarget(Double dir) {
		this.targetDirection = dir;
	}

	@Override
	public void setTarget(Point p) {
		this.targetDirection = parent.getCenter().degreeTo(p);
	}

	@Override
	public boolean isReady() {
		return cooldown <= 0 && targetDirection != 0;
	}

	@Override
	public void update() {
		cooldown --;
	}
}
