package space.spacecrafts.ships;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import drawing.JavaFXDrawingInformation;
import geom.LolliShape;
import interfaces.geom.Point;
import interfaces.logical.CollidingObject;
import interfaces.logical.DestructibleObject;
import interfaces.spacecraft.CarrierDrone;
import interfaces.spacecraft.MountedWeapon;
import interfaces.spacecraft.Spacecraft;
import javafx.scene.paint.Color;
import space.core.SpaceObject;
import space.spacecraft.ships.devices.DroneMount;
import space.spacecrafts.ships.missiles.Missile;

public class Carrier extends Spaceshuttle{
	
	private Carrier(String name, SpaceObject parent, int size, int orbitingDistance, double speed,List<MountedWeapon> weapons) {
		super(name, parent,new JavaFXDrawingInformation(Color.CORNSILK), new LolliShape(size,size*2,size/2), size, orbitingDistance, speed);
		this.size=size;
		this.weapons = weapons;
	}
	
	@Override
	public boolean collides(CollidingObject other) {
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

	public void attack(Point p) {
		// Carriers cannot attack points?
		// TODO: SensorArray for Point p with given Radious and spread attack for every found valid target!
	}

	public void attack(SpaceObject o) {
		primaryWeapon.setTarget(o);
		primaryWeapon.activate();
	}
	
	public Carrier rebuildAt(String name, SpaceObject at) {
		Carrier copy = new Carrier(name,at,size,(int) orbitingDistance,speed,weapons);
		return copy;
	}
	
	@Override
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
	
	public List<CarrierDrone> getDrones(){
		return weapons.stream()
				.filter(w -> w instanceof DroneMount)
				.map(d -> (DroneMount)d)
				.map(d -> d.getDrone())
				.filter(d -> d != null)
				.collect(Collectors.toList());
	}
	
	public static class Builder {
		private final String name;
		private SpaceObject parent;
		private Color color= Color.CORNSILK;
		private int distance = 0,size = 0, levelOfDetail=2;
		private double speed = 0,rotationSpeed=0;
		
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
		
		public Builder addMountedWeapon(Function<Spaceshuttle,MountedWeapon> weaponFn){ 
			weaponFns.add(weaponFn); 
			return this;
		}
		
		public Builder addDroneMount(Function<Spaceshuttle,CarrierDrone> droneFn) {
			droneFns.add(droneFn);
			return this;
		}
		
		public Carrier build() {
			return new Carrier(this);
		}
	}
	
	private Carrier(Builder builder) {
		super(builder.name,builder.parent,new JavaFXDrawingInformation(builder.color), new LolliShape(builder.size,builder.size*2,builder.size/2),builder.size,builder.distance,builder.speed);
		this.size=builder.size;
		
		shape.setLevelOfDetail(builder.levelOfDetail);
		rotationSpeed=builder.rotationSpeed;
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
		
		this.primaryWeapon = combineDroneMountsToDroneRack(this);
		this.secondaryWeapon = null;
	}
	
	private static MountedWeapon combineDroneMountsToDroneRack(Carrier carrier) {
		
		final class DroneRack implements MountedWeapon{
			DroneRack(List<DroneMount> drones){
				this.droneMounts = drones;
			}
			
			final List<DroneMount> droneMounts;
			@Override
			public void activate() {
				for(var d:droneMounts)
					d.activate();
			}

			@Override
			public Spacecraft getParent() {
				return carrier;
			}

			@Override
			public void update() {
				// The droneMounts are updated separately with all the other potential weapons
			}

			@Override
			public void setTarget(SpaceObject o) {
				for(var d:droneMounts)
					d.setTarget(o);
			}

			@Override
			public void setTarget(Double dir) {
				for(var d:droneMounts)
					d.setTarget(dir);
			}

			@Override
			public void setTarget(Point p) {
				for(var d:droneMounts)
					d.setTarget(p);
			}

			@Override
			public boolean isReady() {
				return true; // The rack is always ready, but not necessarily all of the droneMounts
			}
			
		}
		var droneMounts = carrier.weapons.stream().filter(w->w instanceof DroneMount).map(w -> (DroneMount) w).collect(Collectors.toList());
		return new DroneRack(droneMounts);
	}
	

}