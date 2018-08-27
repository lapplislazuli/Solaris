package logic;

import java.util.LinkedList;
import java.util.List;

import space.*;

/*
 * Pushes a Shuttle whenever its orbiting to another SpaceObject
 */

public class ShuttleNavigator implements UpdatingObject{
	
	private String name;
	private int currentPointer = 1;
	private List<SpaceObject> route;
	private SpaceShuttle shuttle;
	
	public ShuttleNavigator(String name, SpaceShuttle shuttle) {
		route= new LinkedList<SpaceObject>();
		route.add(shuttle.getParent());
		this.shuttle=shuttle;
		this.name=name;
	}
	
	public void addToRoute(SpaceObject next) {
		route.add(next);
	}
	
	public boolean removeFromRoute(SpaceObject next) {
		return route.remove(next);
	}
	
	public void update() {
		if(shuttle.isOrbiting()) {
			if (currentPointer>=route.size())
				currentPointer=1;
			else
				currentPointer++;
			shuttle.setTarget(route.get(currentPointer-1));
			shuttle.launch();
		}
	}
	
}
