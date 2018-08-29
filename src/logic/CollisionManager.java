package logic;

import java.util.LinkedList;
import java.util.List;

import interfaces.CollidingObject;
import interfaces.DestructibleObject;
import interfaces.UpdatingObject;
import space.core.SpaceObject;

public class CollisionManager implements UpdatingObject {
	
	public List<CollidingObject> collidables = new LinkedList<CollidingObject>();
	public List<DestructibleObject> destructibles = new LinkedList<DestructibleObject>();
	
	public CollisionManager() {}
	
	public void update() {
		List<DestructibleObject> destroyed=new LinkedList<DestructibleObject>();
		for(DestructibleObject destructible : destructibles) {
			for(CollidingObject collider: collidables) {
				if(destructible!=collider && destructible.collides(collider)) {
					destructible.destruct(collider);
					destroyed.add(destructible);
				}	
			}
		}
		destructibles.removeAll(destroyed);
		collidables.removeAll(destroyed);
	}
	
	public void emptyCollisions() {
		collidables = new LinkedList<CollidingObject>();
		destructibles = new LinkedList<DestructibleObject>();
	}
	
	public void addCollidable(CollidingObject o){
		collidables.add(o);
		if(o instanceof DestructibleObject)
			destructibles.add((DestructibleObject)o);
	}
	
	public void refresh(List<SpaceObject> objects) {
		collidables = new LinkedList<CollidingObject>();
		destructibles = new LinkedList<DestructibleObject>();
		
		for(SpaceObject o : objects)
			addCollidable(o);
	}
}
