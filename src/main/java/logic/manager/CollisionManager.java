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
	
	private boolean running=true;
	
	public CollisionManager() {
		registeredItems = new HashSet<CollidingObject>();
		registeredDestructibles = new HashSet<DestructibleObject>();
		logger.debug("Build CollisionManager");
	}

	public void update() {
		if(running) {
			refresh();
			doCollisions();
		}	
	}
	
	public void doCollisions() {
		Set<DestructibleObject> destroyed=new HashSet<DestructibleObject>();
		
		for(DestructibleObject destructible : registeredDestructibles)
			for(CollidingObject collider: registeredItems) {
				if(collider.equals(destructible)) {
					//If the items are equal, skip their collision and check for the next collider
					continue; 
				}
				if(collider.collides(destructible)) {
					if(collider instanceof Spacecraft || destructible instanceof Spacecraft) {
						logger.debug("SpaceshipCollision: " + collider.toString() + " collided destructible " +destructible.toString() );
					}
					destructible.destruct();
					destroyed.add(destructible);
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
			for(CollidingObject o : ManagerRegistry.getUpdateManager().getAllActiveColliders())
				registerItem(o);
			registeredItems.stream().forEach(e->e.updateHitbox());
	}
	
	public void toggleUpdate() {
		running=!running;
		logger.debug("CollisionManager set to running:" + running);
	}
	public boolean isRunning() {
		return running;
	}
	
	
	public void init(Config c) {}

	public void reset() {
		registeredItems = new HashSet<CollidingObject>();
		registeredDestructibles = new HashSet<DestructibleObject>();
		running=true;
		logger.debug("Reset CollisionManager");
	}

	public Collection<CollidingObject> getRegisteredItems() {
		return registeredItems;
	}
	
	public Collection<DestructibleObject> getRegisteredDestructibles() {
		return registeredDestructibles;
	}
	
	public void registerItem(CollidingObject item) {
		if(!registeredItems.contains(item))
			registeredItems.add(item);
		if(item instanceof DestructibleObject)
			if(!registeredDestructibles.contains((DestructibleObject)item))
				registeredDestructibles.add((DestructibleObject)item);
	}
}
