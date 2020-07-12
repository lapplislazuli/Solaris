package space.spacecrafts.ships;

import drawing.JavaFXDrawingInformation;
import geom.UShape;
import interfaces.geom.Shape;
import javafx.scene.paint.Color;
import space.core.SpaceObject;

public final class ShipFactory {
	//"Martians",mars,3,50,Math.PI/100
	public static final Spaceshuttle standardArmedShuttle(String name, SpaceObject parent, int size, int distance_to_parent,double speed) {
		var b = new Spaceshuttle.Builder(name,parent);
		
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
				.build();
		
//		var drone = new Spaceshuttle(name, parent,new JavaFXDrawingInformation(Color.BLANCHEDALMOND), new UShape(size*2,size*2,size/2), size, orbitingDistance, speed);

		return drone;
	}
}
