package space.spacecrafts.navigators;

import java.util.LinkedList;

import interfaces.spacecraft.SpacecraftState;
import space.core.SpaceObject;
import space.spacecrafts.ships.ArmedSpaceShuttle;

public class ShuttleNavigator extends BaseNavigator{
	
	private int currentPointer=0;
	private ArmedSpaceShuttle shuttle;
	
	boolean isPlayer;
	
	public ShuttleNavigator(String name, ArmedSpaceShuttle shuttle, boolean respawn) {
		super(name, shuttle,respawn);
		this.shuttle=shuttle;
	}
	
	public void update() {
		if(shuttle.isDead() && respawn)
			rebuildShuttle();
		else if(shuttle.getState()==SpacecraftState.ORBITING) {
			currentIdle+=Math.abs(shuttle.speed);
			if(currentIdle>=idlingTurns && isInGoodLaunchPosition(getNextWayPoint())) { //SpaceShuttle idled some time
				shuttle.target=getNextWayPoint();
				shuttle.launch();
				currentIdle=0;
				incrementPointer();
			}
		}
	}
	
	public void clearRoute() {
		route= new LinkedList<SpaceObject>();
		route.add(shuttle.parent);
	}
	
	public SpaceObject getNextWayPoint() {
		return route.get(currentPointer+1);
	}
	
	private void incrementPointer() {
		currentPointer++;
		if (currentPointer>=route.size()-1) 
			currentPointer=0;
	}
	
	@Override
	protected void rebuildShuttle() {
		shuttle = shuttle.rebuildAt(name+"s Ship", route.get(0));
		ship=shuttle;
		//shuttle.setPlayer(isPlayer);
	}

}