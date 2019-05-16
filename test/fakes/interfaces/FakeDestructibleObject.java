package fakes.interfaces;

import interfaces.logical.DestructibleObject;

public class FakeDestructibleObject extends FakeCollidingObject implements DestructibleObject {
	
	public boolean removed;
	public boolean destroyed;
	
	public FakeDestructibleObject() {
		collides=true;
		destroyed=false;
	}
	
	public void remove() {
		removed = true;
	}

	public void destruct() {
		destroyed=true;
	}
	
	public boolean isOrphan() {
		return removed;
	}

}
