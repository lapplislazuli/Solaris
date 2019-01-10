package space.spacecrafts.navigators;

import logic.interaction.PlayerManager;
import space.spacecrafts.ships.PlayerSpaceShuttle;

public class PlayerNavigator extends ArmedShuttleNavigator{
	private PlayerSpaceShuttle shuttle; 
	
	public PlayerNavigator(String name, PlayerSpaceShuttle shuttle) {
		super(name, shuttle, true);
		this.shuttle=shuttle;
		PlayerManager.getInstance().registerPlayerNavigator(this);
	}
	
	@Override
	protected void rebuildShuttle() {
		shuttle = shuttle.rebuildAt(name+"s Ship", route.get(0));
		ship=shuttle;
	}
}
