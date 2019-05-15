package space.spacecrafts.ships;

import java.util.Optional;

import interfaces.drawing.DrawingInformation;
import interfaces.geom.Point;
import interfaces.geom.Shape;
import interfaces.logical.CollidingObject;
import interfaces.logical.DestructibleObject;
import interfaces.spacecraft.ArmedSpacecraft;
import space.core.SpaceObject;
import space.spacecrafts.ships.missiles.Laserbeam;
import space.spacecrafts.ships.missiles.Missile;
import space.spacecrafts.ships.missiles.Rocket;

public class ArmedSpaceShuttle extends Ship implements ArmedSpacecraft{
	
	public int rocketsLeft=60, laserCoolDown=0;

	protected boolean isPlayer;
	
	public ArmedSpaceShuttle(String name, SpaceObject parent, int size, int orbitingDistance, double speed) {
		super(name, parent, size, orbitingDistance, speed);
	}
	
	public ArmedSpaceShuttle(String name, SpaceObject parent,DrawingInformation dinfo,Shape s, int size, int orbitingDistance, double speed) {
		super(name, parent,dinfo,s, size, orbitingDistance, speed);
	}
	
	@Override
	public void update() {
		laserCoolDown--;
		laserCoolDown=Math.max(0,laserCoolDown);
		super.update();
	}
	
	public void shootLaser(SpaceObject targetSpaceObject) {
		if(laserCoolDown<=0) {
			new Laserbeam("Laser from " + name, this,degreeTo(targetSpaceObject),5);
			//@UpdateRatio 25ms its every 3 Seconds:
			laserCoolDown= 3000/25;
		}
	}
	
	public void shootRocket(SpaceObject targetSpaceObject) {
		if(rocketsLeft > 0) {
			new Rocket("Rocket from " + name, this,4,degreeTo(targetSpaceObject),5);
			rocketsLeft --;
		}
	}
	public void shootLaser(Point targetPoint) {
		if(laserCoolDown<=0) {
			new Laserbeam("Laser from " + name, this,center.degreeTo(targetPoint),5);
			//@UpdateRatio 25ms its every 3 Seconds:
			laserCoolDown= 3000/25;
		}
	}
	
	public void shootRocket(Point targetPoint) {
		if(rocketsLeft> 0) {
			new Rocket("Rocket from " + name, this,3,center.degreeTo(targetPoint),10);
			rocketsLeft--;		
		}
	}

	@Override
	public boolean collides(CollidingObject other) {
		if(other instanceof Missile && trabants.contains((Missile)other))
			return false;
		return super.collides(other);
	}
	
	public void attack(Point p) {
		shootLaser(p);
	}
	public void attack(SpaceObject o) {
		shootLaser(o);
	}
	
	@Override
	public ArmedSpaceShuttle rebuildAt(String name, SpaceObject at) {
		ArmedSpaceShuttle copy = new ArmedSpaceShuttle(name,at,dInfo,shape,size,(int) orbitingDistance,speed);
		return copy;
	}

	public Optional<SpaceObject> getNearestPossibleTarget() {
		Optional<SpaceObject> possibleTarget = Optional.empty();
		if(!sensor.detectedItems.isEmpty())
			possibleTarget=sensor.detectedItems.stream()
				.filter(c->c instanceof DestructibleObject)
				.filter(c -> c instanceof SpaceObject)
				.map(c-> (SpaceObject)c)
				.findFirst();
		return possibleTarget;
	}
}
