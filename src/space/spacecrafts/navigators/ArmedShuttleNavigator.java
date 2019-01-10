package space.spacecrafts.navigators;

import java.util.LinkedList;

import interfaces.spacecraft.SpacecraftState;
import space.core.SpaceObject;
import space.spacecrafts.ships.ArmedSpaceShuttle;

public class ArmedShuttleNavigator extends BaseNavigator{
	
	private int currentPointer=0;
	private ArmedSpaceShuttle shuttle;
	
	boolean isPlayer;
	
	public ArmedShuttleNavigator(String name, ArmedSpaceShuttle shuttle, boolean respawn) {
		super(name, shuttle,respawn);
		this.shuttle=shuttle;
	}

	@Override
	protected void rebuildShuttle() {
		shuttle = shuttle.rebuildAt(name+"s Ship", route.get(0));
		ship=shuttle;
	}

}