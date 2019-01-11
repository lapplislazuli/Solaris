package space.spacecrafts.navigators;

import java.util.Optional;

import interfaces.geom.Point;
import interfaces.spacecraft.AggressiveNavigator;
import space.core.SpaceObject;
import space.spacecrafts.ships.ArmedSpaceShuttle;

public class ArmedShuttleNavigator extends BaseNavigator implements AggressiveNavigator{
	
	private ArmedSpaceShuttle shuttle;
	
	boolean isPlayer;
	boolean doesAutoAttack = false;
	
	public ArmedShuttleNavigator(String name, ArmedSpaceShuttle shuttle, boolean respawn) {
		super(name, shuttle,respawn);
		this.shuttle=shuttle;
	}
	
	@Override
	public void update(){
		super.update();
		if(doesAutoAttack)
			autoAttack();
	}
	
	@Override
	protected void rebuildShuttle() {
		shuttle = shuttle.rebuildAt(name+"s Ship", route.get(0));
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
		if(possible.isPresent())
			attack(possible.get());
	}
	
	public boolean doesAutoAttack() {return doesAutoAttack;}
	public void setAutoAttack(boolean doesit) {doesAutoAttack=doesit;}
	public void toggleAutoAttack() {doesAutoAttack= !doesAutoAttack;}
}