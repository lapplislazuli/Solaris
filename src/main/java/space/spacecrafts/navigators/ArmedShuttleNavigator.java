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
	private Spacecraft shuttle;
	
	protected boolean doesAutoAttack = false;
	
	public ArmedShuttleNavigator(String name, Spacecraft shuttle, boolean respawn) {
		super(name, shuttle,respawn);
		this.shuttle=shuttle;
	}
	

	public static ArmedShuttleNavigator PlayerNavigator(String name, Spacecraft shuttle) {
		ArmedShuttleNavigator nav = new ArmedShuttleNavigator(name, shuttle, true);
		ManagerRegistry.getPlayerManager().registerPlayerNavigator(nav);
		return nav;
	}
	
	@Override
	public void update(){
		super.update();
		if(!shuttle.isDead() && doesAutoAttack)
			autoAttack();
	}
	
	@Override
	public void rebuildShuttle() {
		logger.debug(name +" is rebuilding its ship " + shuttle.toString());
		shuttle = shuttle.rebuildAt(this.name+"s Ship", getRoute().get(0));
		ship=shuttle;
	}

	public void attack(Point p) {
		shuttle.attack(p);
	}

	public void attack(SpaceObject o) {
		shuttle.attack(o);
	}

	public void autoAttack() {
		Optional<SpaceObject> possible = shuttle.getNearestPossibleTarget();
		if(possible!=null && possible.isPresent())
			attack(possible.get());
	}
	
	public boolean doesAutoAttack() {return doesAutoAttack;}
	public void setAutoAttack(boolean doesit) {doesAutoAttack=doesit;}
	public void toggleAutoAttack() {doesAutoAttack= !doesAutoAttack;}

	public boolean isPlayer() {
		return equals(ManagerRegistry.getPlayerManager().getPlayerNavigator());
	}
}