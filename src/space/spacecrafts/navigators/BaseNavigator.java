package space.spacecrafts.navigators;

import java.util.LinkedList;
import java.util.List;

import org.pmw.tinylog.Logger;

import interfaces.spacecraft.Navigator;
import interfaces.spacecraft.Spacecraft;
import interfaces.spacecraft.SpacecraftState;
import logic.manager.UpdateManager;
import space.core.SpaceObject;

public class BaseNavigator implements Navigator{

	String name;
	private int currentPointer=0;
	public List<SpaceObject> route;
	
	private Spacecraft ship;
	
	boolean respawn; //Bool whether new Ships will be spawned
	double idlingTurns,currentIdle; //Turns spend to Idle on Planet before Relaunch in Radiant-Degree
	
	public BaseNavigator(String name, Spacecraft ship) {
		route= new LinkedList<SpaceObject>();
		route.add(ship.getParent());
		this.ship=ship;
		this.name=name;
		Logger.info("Initiated " + name + " with Shuttle " + ship.toString());
	}
	
	public void update() {
		if(ship.isDead() && respawn)
			rebuildShuttle();
		else if(ship.getState() == SpacecraftState.ORBITING) {
			currentIdle+=Math.abs(ship.getSpeed());
			if(currentIdle>=idlingTurns && isInGoodLaunchPosition(getNextWayPoint())) { //SpaceShuttle idled some time
				ship.setTarget(getNextWayPoint());
				ship.launch();
				currentIdle=0;
				incrementPointer();
			}
		}
	}
	
	public void clearRoute() {
		route= new LinkedList<SpaceObject>();
		route.add(ship.getParent());
	}
	
	public SpaceObject getNextWayPoint() {
		return route.get(currentPointer+1);
	}
	
	private void incrementPointer() {
		currentPointer++;
		if (currentPointer>=route.size()-1) 
			currentPointer=0;
	}
	
	private void rebuildShuttle() {
		ship = ship.copy();
		//ship.getParent() = route.get(0);
		//shuttle.setPlayer(isPlayer);
	}

	public void remove() {
		UpdateManager.getInstance().registeredItems.remove(this);
	}

	@Override
	public Spacecraft getShuttle() {
		return ship;
	}

	@Override
	public List<SpaceObject> getRoute() {
		return route;
	}
}
