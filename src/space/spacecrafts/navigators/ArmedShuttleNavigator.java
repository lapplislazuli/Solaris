package space.spacecrafts.navigators;

import java.util.Optional;

import interfaces.geom.Point;
import interfaces.spacecraft.AggressiveNavigator;
import interfaces.spacecraft.ArmedSpacecraft;
import space.core.SpaceObject;
import space.spacecrafts.ships.Carrier;

public class ArmedShuttleNavigator extends BaseNavigator implements AggressiveNavigator{
	
	private ArmedSpacecraft shuttle;
	
	boolean isPlayer;
	boolean doesAutoAttack = false;
	
	public ArmedShuttleNavigator(String name, ArmedSpacecraft shuttle, boolean respawn) {
		super(name, shuttle,respawn);
		this.shuttle=shuttle;
	}
	
	@Override
	public void update(){
		super.update();
		//DEBUG ONLY
		//if(shuttle instanceof Carrier)
		//	System.out.println(shuttle.toString()+" is " + shuttle.getState().toString());
		if(!shuttle.isDead() && doesAutoAttack)
			autoAttack();
	}
	
	@Override
	protected void rebuildShuttle() {
		shuttle = (ArmedSpacecraft) shuttle.rebuildAt(name+"s Ship", route.get(0));
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
}