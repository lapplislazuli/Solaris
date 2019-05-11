package logic.manager;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.pmw.tinylog.Logger;

import config.Config;
import interfaces.logical.CollidingObject;
import interfaces.logical.DestructibleObject;
import interfaces.logical.ManagerObject;
import space.core.SpaceObject;

public class CollisionManager implements ManagerObject<CollidingObject>{
	
	private Set<CollidingObject> registeredItems;
	private Set<DestructibleObject> registeredDestructibles;

	private UpdateManager parent;
	private boolean running=true;
	
	public CollisionManager() {
		registeredItems = new HashSet<CollidingObject>();
		registeredDestructibles = new HashSet<DestructibleObject>();
		Logger.debug("Build CollisionManager");
	}

	public void update() {
		if(running) {
			refresh();
			Set<DestructibleObject> destroyed=new HashSet<DestructibleObject>();
			
			for(DestructibleObject destructible : registeredDestructibles)
				for(CollidingObject collider: registeredItems)
					if(collider.collides(destructible)) {
						destructible.destruct();
						destroyed.add(destructible);
					}
			registeredDestructibles.removeAll(destroyed);
			registeredItems.removeAll(destroyed);
		}	
	}
	
	public void empty() {
		registeredItems.clear();
		registeredDestructibles.clear();
	}
	
	public void refresh() {
			empty();
			for(SpaceObject o : getAllActiveSpaceObjects())
				registerItem(o);
			registeredItems.stream().forEach(e->e.updateHitbox());
	}
	
	public Set<SpaceObject> getAllActiveSpaceObjects() {	
		return parent.getRegisteredItems().stream()
				.filter(updatingObject -> updatingObject instanceof SpaceObject)
				.map(spaceObject -> (SpaceObject)spaceObject)
				.flatMap(spaceObject -> spaceObject.getAllChildrenFlat().stream())
				.collect(Collectors.toSet());
	}

	public void togglePause() {
		running=!running;
		Logger.debug("CollisionManager set to running:" + running);
	}

	public void init(Config c) {
		parent = ManagerRegistry.getUpdateManager();
	}

	public void reset() {
		registeredItems = new HashSet<CollidingObject>();
		registeredDestructibles = new HashSet<DestructibleObject>();
		Logger.debug("Reset CollisionManager");
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
