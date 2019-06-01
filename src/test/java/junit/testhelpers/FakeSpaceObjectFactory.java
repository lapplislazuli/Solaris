package junit.testhelpers;

import geom.AbsolutePoint;
import space.advanced.FixStar;
import space.core.Planet;
import space.core.Satellite;
import space.core.SpaceObject;
import space.core.Star;

public abstract class FakeSpaceObjectFactory {
	
	public static Star fakeStar(int xpos,int ypos) {
		Star anchor = new Star("Anker", null, new AbsolutePoint(xpos,ypos),0);
		anchor.getShape().updateOrInitOutline();
		return anchor;
	}
	
	public static Star fakeStar(int xpos,int ypos,int size) {
		Star anchor = new Star("Anker", null, new AbsolutePoint(xpos,ypos),size);
		anchor.getShape().updateOrInitOutline();
		return anchor;
	}
	
	public static Star fakeStar(String name,int xpos,int ypos,int size) {
		Star anchor = new Star(name, null, new AbsolutePoint(xpos,ypos),size);
		anchor.getShape().updateOrInitOutline();
		return anchor;
	}
	
	public static Planet fakePlanet(SpaceObject parent,int distance) {
		Planet planet = (new Planet.Builder("NoCollider", parent))
				.size(0)
				.distance(distance)
				.levelOfDetail(10)
				.speed(0)
				.build();
		
		planet.updateHitbox();
		return planet;
	}
	
	public static Planet fakePlanet(SpaceObject parent,int distance,int size) {
		Planet planet = (new Planet.Builder("NoCollider", parent))
				.size(size)
				.distance(distance)
				.levelOfDetail(10)
				.speed(0)
				.build();
		
		planet.updateHitbox();
		return planet;
	}
	
	public static Planet fakePlanetWithSpeed(SpaceObject parent, int distance, double speed) {
		Planet planet = (new Planet.Builder("NoCollider", parent))
				.size(0)
				.distance(distance)
				.levelOfDetail(10)
				.speed(speed)
				.build();
		
		planet.updateHitbox();
		return planet;	
	}
	
	public static FixStar fakeFixStar(int xpos, int ypos) {
		FixStar f = new FixStar("dummy", xpos, ypos, 0);
		f.getShape().updateOrInitOutline();
		return f;
	}
	
	public static FixStar fakeFixStar(int xpos, int ypos, int size) {
		FixStar f = new FixStar("dummy", xpos, ypos, size);
		f.getShape().updateOrInitOutline();
		return f;
	}
	
	public static Satellite fakeSatelliteWithSpeed(SpaceObject parent,int distance,double speed){
		Satellite satellite= (new Satellite.Builder("dummy", parent))
				.size(0,0)
				.distance(distance)
				.speed(speed)
				.build();
		satellite.getShape().updateOrInitOutline();
		return satellite;
	}
	
}
