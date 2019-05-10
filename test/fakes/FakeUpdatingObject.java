package fakes;

import interfaces.logical.UpdatingObject;

public class FakeUpdatingObject implements UpdatingObject {
	
	public boolean updatet;
	public boolean canBeUpdatet;
	public UpdatingObject kid;
	
	public FakeUpdatingObject() {
		updatet=false;
		canBeUpdatet=true;
	}
	
	public void update() {
		if(canBeUpdatet) {
			updatet=true;
			if(kid!=null)
				kid.update();
		}
	}
	
}
