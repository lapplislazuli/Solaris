package space.spacecrafts.navigators;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import interfaces.geom.Point;
import interfaces.spacecraft.AggressiveNavigator;
import interfaces.spacecraft.Spacecraft;
import logic.manager.ManagerRegistry;
import space.core.SpaceObject;

public class ArmedShuttleNavigator extends BaseNavigator implements AggressiveNavigator{
	
	private static Logger logger = LogManager.getLogger(ArmedShuttleNavigator.class);
	
	protected boolean doesAutoAttack = false;
	
	public ArmedShuttleNavigator(String name, Spacecraft ship, boolean respawn) {
		super(name, ship,respawn);
	}
	
	@Override
	public void update(){
		super.update();
		if(!ship.isDead() && doesAutoAttack) {
			autoAttack();
		}
	}

	public void attack(Point p) {
		ship.attack(p);
	}

	public void attack(SpaceObject o) {
		ship.attack(o);
	}

	public void autoAttack() {
		Optional<SpaceObject> possible = ship.getNearestPossibleTarget();
		if(possible != null && possible.isPresent()) {
			attack(possible.get());
		}
	}
	
	public boolean doesAutoAttack() {return doesAutoAttack;}
	public void setAutoAttack(boolean doesit) {doesAutoAttack = doesit;}
	public void toggleAutoAttack() {doesAutoAttack = !doesAutoAttack;}

	public boolean isActivePlayerNavigator() {
		return equals(ManagerRegistry.getPlayerManager().getPlayerNavigator());
	}
}