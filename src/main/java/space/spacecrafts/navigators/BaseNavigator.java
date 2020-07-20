package space.spacecrafts.navigators;

import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import interfaces.geom.Point;
import interfaces.spacecraft.Navigator;
import interfaces.spacecraft.Spacecraft;
import interfaces.spacecraft.SpacecraftState;
import logic.manager.ManagerRegistry;
import space.core.SpaceObject;

public class BaseNavigator implements Navigator{
	
	private static Logger logger = LogManager.getLogger(BaseNavigator.class);
	
	protected String name;
	protected int currentPointer=0;
	protected List<SpaceObject> route;
	
	protected Spacecraft ship;
	
	protected boolean respawn; //Bool whether new Ships will be spawned
	protected double idlingTurns,currentIdle; //Turns spend to Idle on Planet before Relaunch in Radiant-Degree
	
	public BaseNavigator(String name, Spacecraft ship, boolean respawn) {
		if(name==null||name.isEmpty())
			throw new IllegalArgumentException("Name cannot be null or empty");
		if(ship==null)
			throw new IllegalArgumentException("Ship cannot be null");
		
		route= new LinkedList<SpaceObject>();
		route.add(ship.getParent());
		this.ship=ship;
		this.name=name;
		this.respawn=respawn;
		logger.info("Initiated " + name + " with Shuttle " + ship.toString());
		
		ManagerRegistry.getUpdateManager().registerItem(this);
	}
	
	public void update() {
		if(getShuttle().isDead() && respawn)
			rebuildShuttle();
		else if(getShuttle().getState() == SpacecraftState.ORBITING) {
			currentIdle+=Math.abs(getShuttle().getSpeed());
			if(currentIdle>=idlingTurns && isInGoodLaunchPosition(getNextWayPoint())) { //SpaceShuttle idled some time
				commandLaunch();
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
	
	public void rebuildShuttle() {
		logger.debug(name +" is rebuilding its ship " + ship.toString() + " around " + route.get(0).toString());
		ship = ship.rebuildAt(ship.getName(), route.get(0));
		ship.updateHitbox();
	}
	
	public void remove() {
		ManagerRegistry.getUpdateManager().getRegisteredItems().remove(this);
	}

	public Spacecraft getShuttle() {
		return ship;
	}

	public List<SpaceObject> getRoute() {
		return route;
	}

	public boolean isOrphan() {
		return !ManagerRegistry.getUpdateManager().getRegisteredItems().contains(this);
	}

	public void commandLaunch() {
		getShuttle().setTarget(getNextWayPoint());
		getShuttle().launch();
		currentIdle=0;
		incrementPointer();
	}
}
