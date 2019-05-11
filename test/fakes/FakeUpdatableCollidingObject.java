package fakes;

import interfaces.logical.CollidingObject;
import interfaces.logical.DestructibleObject;

public class FakeUpdatableCollidingObject extends FakeUpdatingObject implements DestructibleObject{

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

}
