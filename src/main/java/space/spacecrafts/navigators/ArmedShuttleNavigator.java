package space.spacecrafts.navigators;

import java.util.Optional;

import interfaces.geom.Point;
import interfaces.spacecraft.AggressiveNavigator;
import interfaces.spacecraft.Spacecraft;
import logic.manager.ManagerRegistry;
import space.core.SpaceObject;
import space.spacecrafts.ships.Spaceshuttle;

public class ArmedShuttleNavigator extends BaseNavigator implements AggressiveNavigator{
	
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
		shuttle = shuttle.rebuildAt(name+"s Ship", getRoute().get(0));
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