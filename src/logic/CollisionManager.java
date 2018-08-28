package logic;

import java.util.LinkedList;
import java.util.List;

import interfaces.CollidingObject;
import interfaces.DestructibleObject;
import interfaces.UpdatingObject;

public class CollisionManager implements UpdatingObject {
	
	public List<CollidingObject> collidables = new LinkedList<CollidingObject>();
	public List<DestructibleObject> destructibles = new LinkedList<DestructibleObject>();
	
	public CollisionManager() {}
	
	public void update() {
		for(DestructibleObject destructible : destructibles) {
			for(CollidingObject collider: collidables) {
				if(destructible!=collider && destructible.collides(collider))
					destructible.destruct(collider);
			}
		}
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
}
