package logic;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import interfaces.logical.CollidingObject;
import interfaces.logical.DestructibleObject;
import interfaces.logical.UpdatingObject;
import space.core.SpaceObject;

@SuppressWarnings("restriction")
public class CollisionManager implements UpdatingObject{
	
	public final Set<CollidingObject> collidables;
	public final Set<DestructibleObject> destructibles;

	private UpdateManager parent;
	private boolean running=true;
	
	private static CollisionManager INSTANCE;
	
	private CollisionManager() {
		collidables = new HashSet<CollidingObject>();
		destructibles = new HashSet<DestructibleObject>();
	}
	
	public static CollisionManager getInstance() {
		if(INSTANCE==null)
			INSTANCE=new CollisionManager();
		return INSTANCE;
	}
	
	public void initCollisionManager(int updateIntervall, UpdateManager parent) {
		this.parent=parent;
	}
	
	public void update() {
		if(running) {
			refresh();
			collidables.stream().forEach(e->e.updateHitbox());
			
			Set<DestructibleObject> destroyed=new HashSet<DestructibleObject>();
			for(DestructibleObject destructible : destructibles) {
				for(CollidingObject collider: collidables) {
					//if(destructible.collides(collider)&&collider.collides(destructible)) {
					if(collider.collides(destructible)) {
						destructible.destruct(collider);
						destroyed.add(destructible);
					}	
				}
			}
			destructibles.removeAll(destroyed);
			collidables.removeAll(destroyed);
		}	
	}
	
	public void empty() {
		collidables.clear();
		destructibles.clear();
	}
	
	public void add(CollidingObject o){
		collidables.add(o);
		if(o instanceof DestructibleObject)
			destructibles.add((DestructibleObject)o);
	}
	
	public void refresh() {
		if(running) {
			empty();
			for(SpaceObject o : getAllActiveSpaceObjects())
				add(o);
		}	
	}
	
	public Set<SpaceObject> getAllActiveSpaceObjects() {	
		return parent.toUpdate.stream()
				.filter(updatingObject -> updatingObject instanceof SpaceObject)
				.map(spaceObject -> (SpaceObject)spaceObject)
				.flatMap(spaceObject -> spaceObject.getAllChildrenFlat().stream())
				.collect(Collectors.toSet());
	}

	public void pause() {
		running=false;
	}

	public void start() {
		running=true;
	}
}
