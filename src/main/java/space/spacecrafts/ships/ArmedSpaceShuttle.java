package space.spacecrafts.ships;

import java.util.Optional;

import interfaces.drawing.DrawingInformation;
import interfaces.geom.Point;
import interfaces.geom.Shape;
import interfaces.logical.CollidingObject;
import interfaces.logical.DestructibleObject;
import interfaces.spacecraft.ArmedSpacecraft;
import interfaces.spacecraft.MountedWeapon;
import logic.manager.ManagerRegistry;
import space.core.SpaceObject;
import space.spacecraft.ships.devices.LaserCannon;
import space.spacecraft.ships.devices.RocketLauncher;
import space.spacecrafts.ships.missiles.Missile;

public class ArmedSpaceShuttle extends BaseShip implements ArmedSpacecraft{

	protected boolean isPlayer;
	
	private LaserCannon laserCannon;
	private MountedWeapon rocketLauncher;
	
	public ArmedSpaceShuttle(String name, SpaceObject parent, int size, int orbitingDistance, double speed) {
		super(name, parent, size, orbitingDistance, speed);
		
		laserCannon = new LaserCannon(this);
		rocketLauncher = new RocketLauncher(this,60);
	}
	
	public ArmedSpaceShuttle(String name, SpaceObject parent,DrawingInformation dinfo,Shape s, int size, int orbitingDistance, double speed) {
		super(name, parent,dinfo,s, size, orbitingDistance, speed);
		
		laserCannon = new LaserCannon(this);
		rocketLauncher = new RocketLauncher(this,60);
	}
	
	public static ArmedSpaceShuttle PlayerSpaceShuttle(String name, SpaceObject parent, int size, int orbitingDistance, double speed) {
		ArmedSpaceShuttle player =new ArmedSpaceShuttle(name, parent, size, orbitingDistance, speed);
		ManagerRegistry.getPlayerManager().registerPlayerShuttle(player);
		return player;
	}
	
	public static ArmedSpaceShuttle PlayerSpaceShuttle(String name, SpaceObject parent,DrawingInformation dinfo,Shape s, int size, int orbitingDistance, double speed) {
		ArmedSpaceShuttle player =new ArmedSpaceShuttle(name, parent,dinfo,s, size, orbitingDistance, speed);
		ManagerRegistry.getPlayerManager().registerPlayerShuttle(player);
		return player;
	}
	
	@Override
	public void update() {
		laserCannon.update();
		super.update();
	}
	
	public void shootLaser(SpaceObject targetSpaceObject) {
		laserCannon.setTarget(targetSpaceObject);
		laserCannon.activate();
	}
	
	public void shootRocket(SpaceObject targetSpaceObject) {
		rocketLauncher.setTarget(targetSpaceObject);
		rocketLauncher.activate();
	}
	public void shootLaser(Point targetPoint) {
		laserCannon.setTarget(targetPoint);
		laserCannon.activate();
	}
	
	public void shootRocket(Point targetPoint) {
		rocketLauncher.setTarget(targetPoint);
		rocketLauncher.activate();
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
		if(isPlayer()) {
			ArmedSpaceShuttle playercopy = PlayerSpaceShuttle(name,at,dInfo,shape,size,(int) orbitingDistance,speed);
			return playercopy;
		}
		ArmedSpaceShuttle nonplayerCopy = new ArmedSpaceShuttle(name,at,dInfo,shape,size,(int) orbitingDistance,speed);
		return nonplayerCopy;
	}
	
	@Override
	public boolean isPlayer() {
		// This way round, otherwise if PlayerManager is empty/new it dies 
		return equals(ManagerRegistry.getPlayerManager().getPlayerShuttle());
	}

	public Optional<SpaceObject> getNearestPossibleTarget() {
		Optional<SpaceObject> possibleTarget = Optional.empty();
		if(!sensor.getDetectedItems().isEmpty())
			possibleTarget=sensor.getDetectedItems().stream()
				.filter(c->c instanceof DestructibleObject)
				.filter(c -> c instanceof SpaceObject)
				.map(c-> (SpaceObject)c)
				.findFirst();
		return possibleTarget;
	}
}
