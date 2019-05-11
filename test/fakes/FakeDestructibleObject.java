package fakes;

import interfaces.logical.DestructibleObject;

public class FakeDestructibleObject extends FakeCollidingObject implements DestructibleObject {
	
	public boolean removed;
	public boolean destroyed;
	
	public FakeDestructibleObject() {
		collides=true;
		removed=false;
		destroyed=false;
	}
	
	public void remove() {
		removed=true;
	}

	public void destruct() {
		destroyed=true;
	}

}
