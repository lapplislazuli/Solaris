package logic.manager;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.pmw.tinylog.Logger;

import interfaces.logical.CollidingObject;
import interfaces.logical.DestructibleObject;
import interfaces.logical.UpdatingObject;
import space.core.SpaceObject;

public class CollisionManager implements UpdatingObject{
	
	public final Set<CollidingObject> registeredItems;
	public final Set<DestructibleObject> registeredDestructibles;

	private UpdateManager parent;
	private boolean running=true;
	
	private static CollisionManager INSTANCE;
	
	private CollisionManager() {
		registeredItems = new HashSet<CollidingObject>();
		registeredDestructibles = new HashSet<DestructibleObject>();
		Logger.debug("Build CollisionManager");
	}
	
	public static CollisionManager getInstance() {
		if(INSTANCE==null)
			INSTANCE=new CollisionManager();
		return INSTANCE;
	}
	
	public void initCollisionManager(UpdateManager parent) {
		this.parent=parent;
		Logger.debug("Initialised CollisionManager");
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
	
	public void register(CollidingObject o){
		registeredItems.add(o);
		if(o instanceof DestructibleObject)
			registeredDestructibles.add((DestructibleObject)o);
	}
	
	public void refresh() {
			empty();
			for(SpaceObject o : getAllActiveSpaceObjects())
				register(o);
			registeredItems.stream().forEach(e->e.updateHitbox());
	}
	
	public Set<SpaceObject> getAllActiveSpaceObjects() {	
		return parent.registeredItems.stream()
				.filter(updatingObject -> updatingObject instanceof SpaceObject)
				.map(spaceObject -> (SpaceObject)spaceObject)
				.flatMap(spaceObject -> spaceObject.getAllChildrenFlat().stream())
				.collect(Collectors.toSet());
	}

	public void togglePause() {
		running=!running;
		Logger.debug("CollisionManager set to running:" + running);
	}
}
