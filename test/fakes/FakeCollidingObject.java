package fakes;

import interfaces.logical.CollidingObject;

public class FakeCollidingObject implements CollidingObject{
	
	public boolean collides;

	public FakeCollidingObject() {
		collides=true;
	}
	
	public boolean collides(CollidingObject other) {
		if(other.equals(this))
			return false;
		if(other instanceof FakeCollidingObject)
			return collides && ((FakeCollidingObject) other).collides;
		if(other instanceof FakeDestructibleObject)
			return collides && ((FakeDestructibleObject) other).collides;
		return collides;
	}

	public void updateHitbox() {}
	
}
