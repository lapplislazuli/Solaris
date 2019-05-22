package space.spacecrafts.navigators;

import interfaces.spacecraft.ArmedSpacecraft;
import logic.manager.ManagerRegistry;

public class PlayerNavigator extends ArmedShuttleNavigator{
	private ArmedSpacecraft shuttle; 
	
	public PlayerNavigator(String name, ArmedSpacecraft shuttle) {
		super(name, shuttle, true);
		this.shuttle=shuttle;
		ManagerRegistry.getPlayerManager().registerPlayerNavigator(this);
	}
	
	@Override
	protected void rebuildShuttle() {
		shuttle = (ArmedSpacecraft) shuttle.rebuildAt(name+"s Ship", route.get(0));
		ship=shuttle;
	}
}
