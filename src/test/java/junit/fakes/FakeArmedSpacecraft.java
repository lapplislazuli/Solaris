package junit.fakes;


import java.util.Optional;

import interfaces.geom.Point;
import interfaces.spacecraft.ArmedSpacecraft;
import space.core.SpaceObject;

public class FakeArmedSpacecraft extends FakeSpacecraft implements ArmedSpacecraft{
	
	public Optional<SpaceObject> nearestTarget = Optional.empty();
	
	public ArmedSpacecraft rebuildAt (String name, SpaceObject at) {
		rebuild=true;
		FakeArmedSpacecraft copy = new FakeArmedSpacecraft();
		copy.parent=at;
		return copy;
	}
	
	public void attack(Point p) {
		attacked=true;
	}

	public void attack(SpaceObject o) {
		attacked=true;
	}

	public Optional<SpaceObject> getNearestPossibleTarget() {
		return nearestTarget;
	}

}
