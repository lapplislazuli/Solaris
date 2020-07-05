package space.spacecrafts.ships;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import drawing.JavaFXDrawingInformation;
import geom.HShape;
import interfaces.drawing.DrawingInformation;
import interfaces.geom.Point;
import interfaces.geom.Shape;
import interfaces.logical.CollidingObject;
import interfaces.logical.DestructibleObject;
import interfaces.logical.UpdatingObject;
import interfaces.spacecraft.ArmedSpacecraft;
import interfaces.spacecraft.CarrierDrone;
import interfaces.spacecraft.MountedWeapon;
import interfaces.spacecraft.SpacecraftState;
import javafx.scene.paint.Color;
import logic.manager.ManagerRegistry;
import space.advanced.Asteroid;
import space.core.MovingSpaceObject;
import space.core.SpaceObject;
import space.effect.Explosion;
import space.spacecraft.ships.devices.DroneMount;
import space.spacecraft.ships.devices.LaserCannon;
import space.spacecraft.ships.devices.RocketLauncher;
import space.spacecrafts.ships.missiles.Missile;

public class Spaceshuttle extends MovingSpaceObject implements ArmedSpacecraft{
	protected SpaceObject target;
	protected SpaceObject parent;
	protected double orbitingDistance;
	
	protected Sensor sensor;
	protected SpacecraftState state = SpacecraftState.ORBITING;
	
	protected int size;
	
	public Spaceshuttle(String name, SpaceObject parent, int size, int orbitingDistance, double speed) {
		super(name, parent, new JavaFXDrawingInformation(Color.GHOSTWHITE), new HShape(size*2,size*3,size), orbitingDistance , speed);
		
		this.size=size;
		this.parent=parent;
		this.orbitingDistance=orbitingDistance;
		distance=(int) (orbitingDistance+distanceTo(parent));
		sensor = new SensorArray(this,50);

		shape.setLevelOfDetail(size/2);
		move(parent.getCenter());

		setStandardWeapons();
	}
	
	public Spaceshuttle(String name, SpaceObject parent,DrawingInformation dinfo,Shape s, int size, int orbitingDistance, double speed) {
		super(name, parent, dinfo, s, orbitingDistance , speed);
		
		this.parent=parent;
		this.orbitingDistance=orbitingDistance;
		distance=(int) (orbitingDistance+distanceTo(parent));
		sensor = new SensorArray(this,50);

		shape.setLevelOfDetail(size/2);

		setStandardWeapons();
	}
	
	public void launch() {
		if(isAliveAndRegistered()) {
			changeHierarchy();
			changeMovementAndPositionAttributes();
		}
	}

	protected void changeMovementAndPositionAttributes() {
		distance=(int)distanceTo(parent);
		relativePos=parent.degreeTo(this);
		evaluateBestSpeed();
	}

	protected void evaluateBestSpeed() {
		if(isFasterThanMe(parent)) {
			if(!movesInSameDirection(parent))
				speed=-speed;
		}
		else {
			if(movesInSameDirection(parent))
				speed=-speed;
		}
	}

	protected void changeHierarchy() {
		target.getTrabants().add(this);
		state=SpacecraftState.FLYING;
		parent = target;
		target=null;
	}

	protected boolean isAliveAndRegistered() {
		return target!=null && parent != null && parent.getTrabants().remove(this);
	}
	
	
	@Override 
	public void move(Point parentCenter) {
		if(state!=SpacecraftState.ORBITING) {
			if(distance>=orbitingDistance+distanceTo(parent)) 
				distance--;
			else 
				state=SpacecraftState.ORBITING;
		}
		sensor.move(center);
		super.move(parentCenter);
	}
	
	public void destruct() {
		logger.info("Spaceship: " + toString() + " Destroyed @" +center.toString());
		if(!isDead()) {
			if(isPlayer()) {
				ManagerRegistry.getPlayerManager().increasePlayerDeaths();
				logger.info("PlayerDeath #"+ManagerRegistry.getPlayerManager().getPlayerDeaths());
			}
			state=SpacecraftState.DEAD;
			new Explosion("Explosion from" + name,center,5,1500,1.02,new JavaFXDrawingInformation(Color.MEDIUMVIOLETRED));
			new Asteroid("Trash from " + name,parent,(int)(orbitingDistance+distanceTo(parent)),speed,Asteroid.Type.TRASH);
			remove();
		}
	}
	
	@Override
	public void rotate() {rotation=degreeTo(parent);}
	
	public boolean isDead() {return state==SpacecraftState.DEAD;}
	
	public void remove() {
		parent.getTrabants().remove(this);
		parent=null;
		target=null;
	}

	public double getOrbitingDistance() {return orbitingDistance;}
	public SpaceObject getParent() {return parent;}
	public void setTarget(SpaceObject target) {this.target=target;}
	public SpaceObject getTarget() {return target;}
	public boolean isOrphan() {return parent==null;}
	public SpacecraftState getState() {return state;}
/*
	public Ship rebuildAt(String name, SpaceObject at) {
		Ship copy = new Ship(name,at,dInfo,shape,size,(int) orbitingDistance,speed);
		return copy;
	}
*/
	public List<CollidingObject> getDetectedItems() {
		return sensor.getDetectedItems();
	}

	public void setSensor(Sensor val) {
		sensor=val;
	}
	protected boolean isPlayer;
	
	protected MountedWeapon primaryWeapon,secondaryWeapon;
	protected List<MountedWeapon> weapons = new ArrayList<>();
	
	private void setStandardWeapons() {

		primaryWeapon = new LaserCannon(this);
		secondaryWeapon = new RocketLauncher(this,60);
		
		weapons.add(primaryWeapon);
		weapons.add(secondaryWeapon);
	}

	public static Spaceshuttle PlayerSpaceShuttle(String name, SpaceObject parent, int size, int orbitingDistance, double speed) {
		Spaceshuttle player =new Spaceshuttle(name, parent, size, orbitingDistance, speed);
		ManagerRegistry.getPlayerManager().registerPlayerShuttle(player);
		return player;
	}
	
	public static Spaceshuttle PlayerSpaceShuttle(String name, SpaceObject parent,DrawingInformation dinfo,Shape s, int size, int orbitingDistance, double speed) {
		Spaceshuttle player =new Spaceshuttle(name, parent,dinfo,s, size, orbitingDistance, speed);
		ManagerRegistry.getPlayerManager().registerPlayerShuttle(player);
		return player;
	}
	
	@Override
	public void update() {
		super.update();
		weapons.stream().filter(o -> o instanceof UpdatingObject).map(o -> (UpdatingObject)o).forEach(o -> o.update());
		sensor.update();
	}
	
	public void shootLaser(SpaceObject targetSpaceObject) {
		primaryWeapon.setTarget(targetSpaceObject);
		primaryWeapon.activate();
	}
	
	public void shootRocket(SpaceObject targetSpaceObject) {
		secondaryWeapon.setTarget(targetSpaceObject);
		secondaryWeapon.activate();
	}
	public void shootLaser(Point targetPoint) {
		primaryWeapon.setTarget(targetPoint);
		primaryWeapon.activate();
	}
	
	public void shootRocket(Point targetPoint) {
		secondaryWeapon.setTarget(targetPoint);
		secondaryWeapon.activate();
	}

	@Override
	public boolean collides(CollidingObject other) {
		if(other instanceof Missile && trabants.contains((Missile)other))
			return false;
		if(super.collides(other)) {
			if(other instanceof CarrierDrone && trabants.contains((CarrierDrone)other)) {//Don't collide with Children
				var otherCasted = (CarrierDrone) other;
				return otherCasted.getParent().equals(this);
			}
			if(other instanceof Missile) {	// Don't collide with Childrens missiles
				if (trabants.stream()
						.filter(d -> d instanceof CarrierDrone)
						.map(d -> (CarrierDrone)d)
						.filter(d -> d.getParent().equals(this))
						.map(d -> (Spaceshuttle)d )
						.anyMatch(d-> d.getAllChildren().contains(other)))
					return false;
			}
			return true;
		}
		return false;
	}
	
	public boolean isCarrier(){
		return weapons.stream().anyMatch(w-> w instanceof DroneMount);
	}
	
	public void attack(Point p) {
		primaryWeapon.setTarget(p);
		primaryWeapon.activate();
	}
	public void attack(SpaceObject o) {
		primaryWeapon.setTarget(o);
		primaryWeapon.activate();
	}
	
	@Override
	public Spaceshuttle rebuildAt(String name, SpaceObject at) {
		if(isPlayer()) {
			Spaceshuttle playercopy = PlayerSpaceShuttle(name,at,dInfo,shape,size,(int) orbitingDistance,speed);
			return playercopy;
		}
		Spaceshuttle nonplayerCopy = new Spaceshuttle(name,at,dInfo,shape,size,(int) orbitingDistance,speed);
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
				.filter(c -> ! (c instanceof Carrier)) // Papa i shot a man
				.filter(c -> {
					return ! weapons.stream()
							.filter(w -> w instanceof DroneMount)
							.map(d -> (DroneMount)d)
							.map(d -> d.getDrone())
							.anyMatch(u -> u.equals(c));
					}
				) 
				.findFirst();
		return possibleTarget;
	}

	public Collection<MountedWeapon> getWeapons() {
		return weapons;
	}
}
