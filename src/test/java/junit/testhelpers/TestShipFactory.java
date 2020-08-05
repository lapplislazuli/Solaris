package junit.testhelpers;

import space.core.SpaceObject;
import space.spacecrafts.ships.Spaceshuttle;

public abstract class TestShipFactory {
	
	public static Spaceshuttle standardArmedShuttle(SpaceObject root) {
		return new Spaceshuttle.Builder("Army",root)
				.orbitingDistance(50)
				.setStandardWeaponry(true)
				.speed(0)
				.size(1)
				.build();
	}	
	
	public static Spaceshuttle standardArmedShuttle(SpaceObject root,int distance,double speed) {
		return new Spaceshuttle.Builder("Army",root)
				.orbitingDistance(distance)
				.setStandardWeaponry(true)
				.speed(speed)
				.size(1)
				.build();
	}
	
	public static Spaceshuttle standardEmitter(SpaceObject root) {
		return new Spaceshuttle.Builder("TestEmitter",root)
				.orbitingDistance(5)
				.speed(5)
				.size(5)
				.build();
	}
}
