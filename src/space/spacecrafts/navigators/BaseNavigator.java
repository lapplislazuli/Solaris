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
	
	protected Spacecraft ship;
	
	boolean respawn; //Bool whether new Ships will be spawned
	double idlingTurns,currentIdle; //Turns spend to Idle on Planet before Relaunch in Radiant-Degree
	
	public BaseNavigator(String name, Spacecraft ship, boolean respawn) {
		route= new LinkedList<SpaceObject>();
		route.add(ship.getParent());
		this.ship=ship;
		this.name=name;
		this.respawn=respawn;
		Logger.info("Initiated " + name + " with Shuttle " + ship.toString());
	}
	
	public void update() {
		if(getShuttle().isDead() && respawn)
			rebuildShuttle();
		else if(getShuttle().getState() == SpacecraftState.ORBITING) {
			currentIdle+=Math.abs(getShuttle().getSpeed());
			if(currentIdle>=idlingTurns && isInGoodLaunchPosition(getNextWayPoint())) { //SpaceShuttle idled some time
				getShuttle().setTarget(getNextWayPoint());
				getShuttle().launch();
				currentIdle=0;
				incrementPointer();
			}
		}
	}
	
	public void clearRoute() {
		route= new LinkedList<SpaceObject>();
		route.add(getShuttle().getParent());
	}
	
	public SpaceObject getNextWayPoint() {
		if (currentPointer<route.size()-1)
			return route.get(currentPointer+1);
		else
			return route.get(0);
	}
	
	private void incrementPointer() {
		currentPointer++;
		if (currentPointer>=route.size()-1) 
			currentPointer=0;
	}
	
	protected void rebuildShuttle() {
		ship = ship.rebuildAt(name+"s Ship", route.get(0));
	}

	public void remove() {
		UpdateManager.getInstance().getRegisteredItems().remove(this);
	}

	public Spacecraft getShuttle() {
		return ship;
	}

	public List<SpaceObject> getRoute() {
		return route;
	}
}
