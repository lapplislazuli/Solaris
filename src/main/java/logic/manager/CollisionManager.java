package logic.manager;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import config.interfaces.Config;
import interfaces.logical.CollidingObject;
import interfaces.logical.DestructibleObject;
import interfaces.logical.UpdatingManager;
import interfaces.spacecraft.Spacecraft;

public class CollisionManager implements UpdatingManager<CollidingObject>{
	
	private Set<CollidingObject> registeredItems;
	private Set<DestructibleObject> registeredDestructibles;

	private static Logger logger = LogManager.getLogger(CollisionManager.class);
	
	private boolean running = true; //If this variable is false, there will be no collisions - but everything will still be flying around and moving etc. 
	
	public CollisionManager() {
		registeredItems = new HashSet<CollidingObject>();
		registeredDestructibles = new HashSet<DestructibleObject>();
		logger.info("Build CollisionManager");
	}

	public void update() {
		if(running) {
			refresh();
			doCollisions();
		}	
	}
	
	public void doCollisions() {
		Set<DestructibleObject> destroyed = new HashSet<DestructibleObject>();
		
		for(DestructibleObject destructible : registeredDestructibles) {
			for(CollidingObject collider : registeredItems) {
				if(collider.equals(destructible)) {
					//If the items are equal, skip their collision and check for the next collider
					continue; 
				}
				if(destroyed.contains(destructible) || destroyed.contains(collider)) {
					//If the item is already in the destroyed list, skip this item and look for next collider
					continue; 
				}
				if(collider.collides(destructible)) {
					// First check if either one of those is a (dead) spacecraft - if yes, no collision!
					if(collider instanceof Spacecraft || destructible instanceof Spacecraft ) {
						Spacecraft sc = null;
						if(destructible instanceof Spacecraft) {
							sc = (Spacecraft) destructible;
								
						} else {
							sc = (Spacecraft) collider;	
						}
						if(sc.isDead()) {
							continue;
						}
						logger.debug("SpaceshipCollision: " + collider.toString() + " collided destructible " +destructible.toString() );
					}
					
					// If there are no special cases for (dead) spaceships, destroy the destructible and remove them later.
					destructible.destruct();
					destroyed.add(destructible);
				}
			}
		}
		
		registeredDestructibles.removeAll(destroyed);
		registeredItems.removeAll(destroyed);
	}
	
	public void empty() {
		registeredItems.clear();
		registeredDestructibles.clear();
	}
	
	public void refresh() {
		empty();
		for(CollidingObject o : ManagerRegistry.getUpdateManager().getAllActiveColliders()) {
			// There are currently regression issues with dead spacecrafts colliding - do not add dead spacecrafts to colliders
			if(o instanceof Spacecraft && ((Spacecraft) o).isDead()) {
				continue;
			}
			registerItem(o);
			o.updateHitbox();
		}
	}
	
	public void toggleUpdate() {
		running =! running;
		logger.info("CollisionManager set to running:" + running);
	}
	public boolean isRunning() {
		return running;
	}
	
	public void init(Config c) {}

	public void reset() {
		registeredItems = new HashSet<CollidingObject>();
		registeredDestructibles = new HashSet<DestructibleObject>();
		running = true;
		logger.debug("Reset CollisionManager");
	}

	public Collection<CollidingObject> getRegisteredItems() {
		return registeredItems;
	}
	
	public Collection<DestructibleObject> getRegisteredDestructibles() {
		return registeredDestructibles;
	}
	
	public void registerItem(CollidingObject item) {
		// This method adds an item if its not in the list of destructibles and collidables. Nothing more. 
		// For the contains method to properly function the objects need a functioning equals and hashcode method. 
		if(!registeredItems.contains(item)) {
			registeredItems.add(item);	
		}
		if(item instanceof DestructibleObject) {
			if(!registeredDestructibles.contains((DestructibleObject)item)) {
				registeredDestructibles.add((DestructibleObject)item);			
			}
		}
	}
}
