package space.spacecraft.ships.devices;

import interfaces.geom.Point;
import interfaces.spacecraft.MountedWeapon;
import interfaces.spacecraft.Spacecraft;
import space.core.SpaceObject;
import space.spacecrafts.ships.BaseShip;
import space.spacecrafts.ships.missiles.Rocket;

public class RocketLauncher implements MountedWeapon {
	
	private double targetDirection; // In radiant from the emiter
	private int rockets; 
	private BaseShip parent;
	
	
	@Override
	public void activate() {
		if(rockets>0 && targetDirection != 0) {
			new Rocket("Rocket from " + parent.getName(), parent,Rocket.ROCKET_SIZE,targetDirection,Rocket.ROCKET_SPEED);
			rockets--;	
		}
		targetDirection=0;
	}

	public void setDirection(double dir) {
		
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

}
