package space.spacecrafts.ships;

import geom.UShape;
import interfaces.geom.Shape;
import javafx.scene.paint.Color;
import space.core.SpaceObject;
import space.spacecraft.ships.devices.WeaponFactory;

public final class ShipFactory {
	//"Martians",mars,3,50,Math.PI/100
	public static final Spaceshuttle standardArmedShuttle(String name, SpaceObject parent, int size, int distance_to_parent,double speed) {
		var b = new Spaceshuttle.Builder(name,parent);
		b.setStandardWeaponry(true).build();
		return null;
	}
	
	public static final Spaceshuttle standardLaserDrone(String name, SpaceObject parent, int size, int orbitingDistance, double speed) {
		Shape droneShape = new UShape(size*2,size*2,size/2);
		Spaceshuttle drone = new Spaceshuttle.Builder(name, parent)
				.size(size)
				.distance_to_parent(orbitingDistance)
				.speed(speed)
				.spawnSpaceTrashOnDestruct(false)
				.color(Color.BLANCHEDALMOND)
				.shape(droneShape)
				.addMountedWeapon(WeaponFactory::standardLaserCannon)
				.build();

		return drone;
	}
}
