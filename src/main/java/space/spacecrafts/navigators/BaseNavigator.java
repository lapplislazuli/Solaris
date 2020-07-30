package space.spacecrafts.navigators;

import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
	protected boolean isRebuilding; // Little flag to check if the respawntimer is running 
	protected double idlingTurns,currentIdle; //Turns spend to Idle on Planet before Relaunch in Radiant-Degree
	protected double MAX_RESPAWNTIME = 3000.0; //With a common update cycle of 25ms, this will take 120 turns
	protected double currentRespawnTime=3000.0;
	
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
	
	public BaseNavigator(String name, Spacecraft ship, boolean respawn,double respawntimer) {
		if(name==null||name.isEmpty())
			throw new IllegalArgumentException("Name cannot be null or empty");
		if(ship==null)
			throw new IllegalArgumentException("Ship cannot be null");
		
		route= new LinkedList<SpaceObject>();
		route.add(ship.getParent());
		this.ship=ship;
		this.name=name;
		this.respawn=respawn;
		this.MAX_RESPAWNTIME = respawntimer;
		logger.info("Initiated " + name + " with Shuttle " + ship.toString());
		
		ManagerRegistry.getUpdateManager().registerItem(this);
	}
	
	public void update() {
		if(getShuttle().isDead() && respawn) {
			/*
			 * In case that the shuttle is dead but should be respawned, it first checks whether the rebuilding process is runnign
			 * If not, start rebuilding process and set the current Respawntime
			 * The shuttle will be build of the respawntime reaches 0 
			 */
			if(!isRebuilding) {
				isRebuilding = true;
				currentRespawnTime = MAX_RESPAWNTIME;
			} else {
				// Timer is done - rebuild the shuttle and set rebuilding to false. 
				if(currentRespawnTime <= 0) {
					if(getShuttle().isPlayer()) {
						logger.debug("Navigator respawning PlayerShuttle");
					}
					rebuildShuttle();
					isRebuilding=false;
				}
				//Timer is still running - reduce the number with every update cycle.
				currentRespawnTime --;
			}	
		} else if(getShuttle().getState() == SpacecraftState.ORBITING) {
			currentIdle+=Math.abs(getShuttle().getSpeed());
			if(currentIdle>=idlingTurns && isInGoodLaunchPosition(getNextWayPoint())) { //SpaceShuttle idled some time
				commandLaunch();
			}
		}
		// The last case which is not checked here is the flying shuttle. The reason is, that the flying shuttle does not need input from navigator.
	}
	
	public void clearRoute() {
		route= new LinkedList<SpaceObject>();
		route.add(getShuttle().getParent());
	}
	
	public SpaceObject getNextWayPoint() {
		if (currentPointer < route.size()-1) {
			return route.get(currentPointer+1);	
		} else {
			return route.get(0);	
		}
	}
	
	private void incrementPointer() {
		currentPointer++;
		if (currentPointer>=route.size()-1) {
			currentPointer=0;		
		}
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
