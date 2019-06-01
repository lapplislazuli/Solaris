package junit.fakes.interfaces;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import interfaces.logical.RecursiveObject;
import interfaces.logical.UpdatingObject;

public class FakeUpdatingObject implements UpdatingObject,RecursiveObject {
	
	public boolean updatet;
	public boolean canBeUpdatet;
	public FakeUpdatingObject kid;
	
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

	public Collection<RecursiveObject> getAllChildren() {
		List<RecursiveObject> results = new LinkedList<RecursiveObject>();
		if(kid!=null)
			results.add(kid);
		return results;
	}
}
