package fakes;

import interfaces.logical.CollidingObject;

public class FakeCollidingObject implements CollidingObject{
	
	public boolean collides;

	public FakeCollidingObject() {
		collides=true;
	}
	
	public boolean collides(CollidingObject other) {
		return collides;
	}

	public void updateHitbox() {}
	
	
	
}
