package junit.fakes;


import java.util.LinkedList;
import java.util.List;

import interfaces.geom.Point;
import interfaces.spacecraft.Navigator;
import interfaces.spacecraft.Spacecraft;
import space.core.SpaceObject;

public class FakeNavigator implements Navigator {
	
	boolean updated=false,removed=false, routeCleared =false,launchCommandet=false, isArmed=false;
	
	public SpaceObject fakeNextWayPoint;
	public Spacecraft shuttle;
	public List<SpaceObject> route= new LinkedList<SpaceObject>();
	

	boolean attacked=false,autoattacked=false;
	boolean doesAutoAttack = false;
	boolean isPlayer = false;
	
	public void attack(Point p) {
		attacked=true;
	}

	public void attack(SpaceObject o) {
		attacked=true;
	}

	public void autoAttack() {
		if(doesAutoAttack)
			autoattacked=true;
	}

	public boolean isActivePlayerNavigator() {
		return isPlayer;
	}
	
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

	public boolean isArmed() {
		return isArmed;
	}

}
