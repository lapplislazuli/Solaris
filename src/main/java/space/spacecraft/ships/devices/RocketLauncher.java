package space.spacecraft.ships.devices;

import interfaces.geom.Point;
import interfaces.spacecraft.MountedWeapon;
import interfaces.spacecraft.Spacecraft;
import space.core.SpaceObject;
import space.spacecrafts.ships.Spaceshuttle;
import space.spacecrafts.ships.missiles.Rocket;

public class RocketLauncher implements MountedWeapon {
	
	private double targetDirection; // In radiant from the emiter
	private int rockets; 
	private Spaceshuttle parent;
	
	public RocketLauncher(Spaceshuttle mount,int magazineSize) {
		if(mount == null) {
			throw new IllegalArgumentException("Mount cannot be null - it is required in later computations");
		}
		if(magazineSize < 0) {
			throw new IllegalArgumentException("MagazineSize cannot be lower than 0");
		}
		this.parent = mount;
		rockets = magazineSize;
	}
	
	@Override
	public void activate() {
		if(isReady()) {
			new Rocket("Rocket from " + parent.getName(), parent,Rocket.ROCKET_SIZE,targetDirection,Rocket.ROCKET_SPEED);
			rockets--;				
			// Alter parameters to not be ready next time without newly set target
			targetDirection = 0;
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
		return rockets > 0 && targetDirection != 0;
	}
	
	public int getRemainingRockets() {
		return rockets;
	}

	@Override
	public void update() {
		//Empty, Rockets are managed somewhere else
	}
}
