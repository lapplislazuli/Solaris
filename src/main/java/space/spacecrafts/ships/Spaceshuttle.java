package space.spacecrafts.ships;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
import space.spacecraft.ships.devices.WeaponFactory;
import space.spacecrafts.ships.missiles.Missile;

public class Spaceshuttle extends MovingSpaceObject implements ArmedSpacecraft{
	
	protected static Logger logger = LogManager.getLogger(Spaceshuttle.class);
	
	protected SpaceObject target;
	protected SpaceObject parent;
	protected double orbitingDistance;
	protected boolean isPlayer;
	protected int size;
	protected boolean leavesSpaceTrash = true;
	
	protected Sensor sensor;
	protected SpacecraftState state = SpacecraftState.ORBITING;

	protected MountedWeapon primaryWeapon,secondaryWeapon;
	protected List<MountedWeapon> weapons = new ArrayList<>();
	

	@Deprecated
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
		spawnDronesIfAnyAreGiven();
	}
	
	private void spawnDronesIfAnyAreGiven() {
		weapons.stream().filter(w -> w instanceof DroneMount).map(w -> (DroneMount)w).forEach(dm -> dm.launch_drone());
	}

	@Deprecated
	public Spaceshuttle(String name, SpaceObject parent,DrawingInformation dinfo,Shape s, int size, int orbitingDistance, double speed) {
		super(name, parent, dinfo, s, orbitingDistance , speed);
		
		this.parent=parent;
		this.orbitingDistance=orbitingDistance;
		distance=(int) (orbitingDistance+distanceTo(parent));
		sensor = new SensorArray(this,50);

		shape.setLevelOfDetail(size/2);

		setStandardWeapons();
		spawnDronesIfAnyAreGiven();
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
	
	public List<CarrierDrone> getDrones(){
		return weapons.stream()
				.filter(w -> w instanceof DroneMount)
				.map(d -> (DroneMount)d)
				.map(d -> d.getDrone())
				.filter(d -> d != null)
				.collect(Collectors.toList());
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
			if(leavesSpaceTrash) {
				new Asteroid("Trash from " + name,parent,(int)(orbitingDistance+distanceTo(parent)),speed,Asteroid.Type.TRASH);	
			}
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

	public List<CollidingObject> getDetectedItems() {
		return sensor.getDetectedItems();
	}

	public void setSensor(Sensor val) {
		sensor=val;
	}
	
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
		if(primaryWeapon!=null) {
			primaryWeapon.setTarget(p);
			primaryWeapon.activate();
		}
	}
	public void attack(SpaceObject o) {
		if(primaryWeapon!=null) {
			primaryWeapon.setTarget(o);
			primaryWeapon.activate();
		}
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
				.filter(c -> {
					if(c instanceof Spaceshuttle) {
						var casted = (Spaceshuttle) c;
						return     !this.equals(casted)                 
								&& !isMyCarrier(casted) 
								&& !isMyDrone(casted) 
								&& !isDroneWithSameCarrier(casted);	
					}
					return true; 
				})
				.findFirst();
		return possibleTarget;
	}

	public boolean isMyCarrier(Spaceshuttle other) {
		if(other==null)
			return false;
		if(!other.isCarrier())
			return false;
		if(this instanceof CarrierDrone) {
			var meCasted = (CarrierDrone) this;
			return other.getDrones().contains(meCasted);
		}
		return false;
	}
	
	public boolean isDroneWithSameCarrier(Spaceshuttle other) {
		if(other==null)
			return false;
		if(! (this instanceof CarrierDrone))
			return false;
		if(! (other instanceof CarrierDrone))
			return false;
		
		var otherCasted = (CarrierDrone) other;
		var parentCasted = (Spaceshuttle) parent;
		
		return parentCasted.getDrones().contains(otherCasted);
	}
	
	public boolean isMyDrone(Spaceshuttle other) {
		if(other==null)
			return false;
		if(!this.isCarrier())
			return false;
		if(! (other instanceof CarrierDrone))
			return false;
		var otherCasted = (CarrierDrone) other;
		return otherCasted.getParent().equals(this);
	}
	
	public Collection<MountedWeapon> getWeapons() {
		return weapons;
	}
	
	
	public static class Builder {
		private final String name;
		private SpaceObject parent;
		private Color color= Color.CORNSILK;
		private int distance = 0,size = 0, levelOfDetail=2,sensorsize=50;
		private Shape shape;
		private double speed = 0,rotationSpeed=0;
		private boolean setStandardWeaponry=false,shallBeCarrier=false,leavesSpaceTrash=true;
		
		private List<Function<Spaceshuttle,MountedWeapon>> weaponFns = new ArrayList<>();
		private List<Function<Spaceshuttle,CarrierDrone>> droneFns = new ArrayList<>();
		
		
		public Builder(String name,SpaceObject parent) throws IllegalArgumentException{
			if(name==null||name.isEmpty())
				throw new IllegalArgumentException("Name cannot be null or empty");
			if(parent==null)
				throw new IllegalArgumentException("Parent cannot be null");
			this.name=name;
			this.parent=parent;
		}
		
		public Builder color(Color val){ 
			color= val; 
			return this;
		}
		
		public Builder distance_to_parent(int val){
			if(val<0)
				throw new IllegalArgumentException("Distance cannot be smaller than 0");
			distance= val; 
			return this;
		}
		
		public Builder size(int val){
			if(val<0)
				throw new IllegalArgumentException("Size cannot be smaller than 0");
			size= val; 
			return this;
		}
		
		public Builder speed(double radiantPerUpdate){
			speed= radiantPerUpdate; 
			return this;
		}
		public Builder rotationSpeed(double radiantPerUpdate){
			rotationSpeed= radiantPerUpdate; 
			return this;
		}
		public Builder levelOfDetail(int val){ 
			if(val<1)
				throw new IllegalArgumentException("LoD cannot be smaller or equal 0");
			levelOfDetail= val; 
			return this;
		}
		
		public Builder spawnSpaceTrashOnDestruct(boolean val) {
			leavesSpaceTrash = val;
			return this;
		}
		
		public Builder addMountedWeapon(Function<Spaceshuttle,MountedWeapon> weaponFn){ 
			weaponFns.add(weaponFn); 
			return this;
		}
		
		public Builder addDroneMount(Function<Spaceshuttle,CarrierDrone> droneFn) {
			droneFns.add(droneFn);
			return this;
		}
		
		public Builder setStandardWeaponry(boolean val) {
			setStandardWeaponry=val;
			return this;
		}
		
		public Builder shallBeCarrier(boolean val) {
			shallBeCarrier=val;
			return this;
		}
		
		public Builder sensorSize(int val) {
			if(val<0)
				throw new IllegalArgumentException("Sensorsize cannot be smaller than 0");
			sensorsize=val;
			return this;
		}
		
		public Spaceshuttle build() {
			return new Spaceshuttle(this);
		}
	}
	
	private Spaceshuttle(Builder builder) {
		super(builder.name, builder.parent, new JavaFXDrawingInformation(builder.color), new HShape(builder.size*2,builder.size*3,builder.size), builder.distance , builder.speed);
		this.parent=builder.parent;
		rotationSpeed=builder.rotationSpeed;
		distance=(int) (orbitingDistance+distanceTo(parent));
		leavesSpaceTrash = builder.leavesSpaceTrash;
		
		sensor = new SensorArray(this,builder.sensorsize);
		move(parent.getCenter());
		
		if(builder.setStandardWeaponry)
			setStandardWeapons();
		
		this.size=builder.size;
		
		shape.setLevelOfDetail(builder.levelOfDetail);
		if(getDrawingInformation() instanceof JavaFXDrawingInformation)
			((JavaFXDrawingInformation)getDrawingInformation()).hasColorEffect=true;
		
		builder.droneFns
			.stream()
			.map(fun -> {
				Supplier<CarrierDrone> supFn = () -> fun.apply(this);
				return supFn;
			})
			.map(supFn -> new DroneMount(supFn))
			.forEach(mount -> this.weapons.add(mount));
		
		builder.weaponFns
			.stream()
			.map(fun -> fun.apply(this))
			.forEach(weapon -> this.weapons.add(weapon));
		
		if(!builder.droneFns.isEmpty())
			this.primaryWeapon = WeaponFactory.combineDroneMountsToDroneRack(this);
		this.secondaryWeapon = null;
		

		spawnDronesIfAnyAreGiven();
	}
}
