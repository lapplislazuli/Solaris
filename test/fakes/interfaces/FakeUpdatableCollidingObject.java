package fakes.interfaces;

import interfaces.logical.CollidingObject;
import interfaces.logical.DestructibleObject;
import interfaces.logical.RecursiveObject;

public class FakeUpdatableCollidingObject extends FakeUpdatingObject implements DestructibleObject,RecursiveObject{

	public boolean hitBoxUpdatet;
	
	public FakeUpdatableCollidingObject() {
		super();
		hitBoxUpdatet = false;
	}
	
	public boolean collides(CollidingObject other) {return false;}

	public void updateHitbox() {
		hitBoxUpdatet=true;
	}

	public void remove() {}
	public void destruct() {}

	public boolean isOrphan() {
		return false;
	}
}