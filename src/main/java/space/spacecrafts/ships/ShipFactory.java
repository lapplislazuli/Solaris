package space.spacecrafts.ships;

import space.core.SpaceObject;

public final class ShipFactory {
	//"Martians",mars,3,50,Math.PI/100
	public static final Spaceshuttle standardArmedShuttle(String name, SpaceObject parent, int size, int distance_to_parent,double speed) {
		var b = new Spaceshuttle.Builder(name,parent);
		
		return null;
	}
}
