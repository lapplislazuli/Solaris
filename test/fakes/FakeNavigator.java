package fakes;

import java.util.LinkedList;
import java.util.List;

import interfaces.spacecraft.Navigator;
import interfaces.spacecraft.Spacecraft;
import space.core.SpaceObject;

public class FakeNavigator implements Navigator {
	
	boolean updated=false,removed=false, routeCleared =false,launchCommandet=false;
	
	public SpaceObject fakeNextWayPoint;
	public Spacecraft shuttle;
	public List<SpaceObject> route= new LinkedList<SpaceObject>();
	
	
	public void update() {
		updated = true;
	}

	public void remove() {
		removed=true;
	}

	public boolean isOrphan() {
		return removed;
	}

	public Spacecraft getShuttle() {
		return shuttle;
	}

	public List<SpaceObject> getRoute() {
		return route;
	}

	public void clearRoute() {
		routeCleared=true;
		route.clear();
	}

	public SpaceObject getNextWayPoint() {
		if(fakeNextWayPoint!=null)
			return fakeNextWayPoint;
		else
			return route.get(1);
	}

	public void commandLaunch() {
		launchCommandet=true;
	}

}
