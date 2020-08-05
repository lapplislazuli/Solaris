package space.spacecrafts.navigators;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

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
	protected int currentPointer = 0;
	protected List<SpaceObject> route;
	
	protected Spacecraft ship;
	
	protected boolean doesAutoAttack = false;
	protected boolean respawn; //Bool whether new Ships will be spawned
	protected boolean isRebuilding; // Little flag to check if the respawntimer is running 
	protected double idlingTurns,currentIdle; //Turns spend to Idle on Planet before Relaunch in Radiant-Degree
	protected double MAX_RESPAWNTIME = 3000.0; //With a common update cycle of 25ms, this will take 120 turns
	protected double currentRespawnTime = 3000.0;

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
					if(getShuttle().isActivePlayer()) {
						logger.debug("Navigator respawning PlayerShuttle");
					}
					rebuildShuttle();
					isRebuilding = false;
				}
				//Timer is still running - reduce the number with every update cycle.
				currentRespawnTime --;
			}	
		} else if(getShuttle().getState() == SpacecraftState.ORBITING) {
			currentIdle += Math.abs(getShuttle().getSpeed());
			if(currentIdle >= idlingTurns && isInGoodLaunchPosition(getNextWayPoint())) { //SpaceShuttle idled some time
				commandLaunch();
			}
		}
		// The last case which is not checked here is the flying shuttle. The reason is, that the flying shuttle does not need input from navigator.
		
		if(!ship.isDead() && isArmed() && doesAutoAttack) {
			autoAttack();
		}
	}
	
	public void clearRoute() {
		route = new LinkedList<SpaceObject>();
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
		if (currentPointer >= route.size()-1) {
			currentPointer = 0;		
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
		currentIdle = 0;
		incrementPointer();
	}
	
	public void attack(Point p) {
		ship.attack(p);
	}

	public void attack(SpaceObject o) {
		ship.attack(o);
	}

	public void autoAttack() {
		Optional<SpaceObject> possible = ship.getNearestPossibleTarget();
		if(possible != null && possible.isPresent()) {
			attack(possible.get());
		}
	}
	
	public boolean doesAutoAttack() {return doesAutoAttack;}
	public void setAutoAttack(boolean doesit) {doesAutoAttack = doesit;}
	public void toggleAutoAttack() {doesAutoAttack = !doesAutoAttack;}

	public boolean isActivePlayerNavigator() {
		return equals(ManagerRegistry.getPlayerManager().getPlayerNavigator());
	}

	public boolean isArmed() {
		return ship.isArmed();
	}
	
	public static class Builder {
		
		private final Spacecraft shuttle;
		private final String name;
		private boolean doesRespawn = false;
		private boolean doesAutoAttack = false;
		private boolean isPlayer = false;
		private List<SpaceObject> route = new ArrayList<SpaceObject>();
		private double respawntime = 3000.0;
		
		public Builder (String name, Spacecraft shuttle) {
			if(name == null || name.isEmpty() || name.isBlank()) {
				throw new IllegalArgumentException("Name cannot be null or empty");
			}
			if(shuttle == null) {
				throw new IllegalArgumentException("Ship cannot be null");
			}
			if(shuttle.getParent() == null) {
				throw new IllegalArgumentException("Ship apparently has no parent! Failed to build Route.");
			}
			this.shuttle = shuttle;
			this.name = name;
			
			route.add(shuttle.getParent());
		}
		
		public Builder doesRespawn(boolean val) {
			this.doesRespawn = val;
			return this;
		}
		
		public Builder doesAutoAttack(boolean val) {
			this.doesAutoAttack = val;
			return this;
		}
		
		public Builder isPlayer(boolean val) {
			this.isPlayer = val;
			return this;
		}
		
		public Builder respawntime (double val) {
			if(val <= 0) {
				throw new IllegalArgumentException("RespawnTime cannot be 0 or smaller");
			}
			this.respawntime = val;
			return this;
		}
		
		public Builder nextWayPoint(SpaceObject val) {
			if(val == null) {
				throw new IllegalArgumentException("Spaceobject was null - cannot add null to the Navigators Route");
			}
			//Shortcut to not add the same item twice to the same Route at the tsame position
			if(route.contains(val)) {
				int currentMax = route.size();
				if(route.indexOf(val) == currentMax-1) {
					return this;
				}
			}
			
			route.add(val);
			return this;
		}
		
		public BaseNavigator build() {
			return new BaseNavigator(this);
		}
	}
	
	public BaseNavigator(Builder builder) {
		this.route = builder.route;
		this.ship = builder.shuttle;
		this.name = builder.name;
		this.respawn = builder.doesRespawn;
		this.doesAutoAttack = builder.doesAutoAttack;
		this.MAX_RESPAWNTIME = builder.respawntime;
		
		logger.info("Initiated " + name + " with Shuttle " + ship.toString());
		
		ManagerRegistry.getUpdateManager().registerItem(this);
		
		if(builder.isPlayer) {
			ManagerRegistry.getPlayerManager().registerPlayerNavigator(this);
		}
	}
}
