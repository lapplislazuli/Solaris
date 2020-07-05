package space.spacecrafts.ships;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import drawing.JavaFXDrawingInformation;
import geom.LolliShape;
import interfaces.geom.Point;
import interfaces.spacecraft.CarrierDrone;
import interfaces.spacecraft.MountedWeapon;
import javafx.scene.paint.Color;
import space.core.SpaceObject;
import space.spacecraft.ships.devices.DroneMount;
import space.spacecraft.ships.devices.WeaponFactory;

public class Carrier extends Spaceshuttle{
	
	private Carrier(String name, SpaceObject parent, int size, int orbitingDistance, double speed,List<MountedWeapon> weapons) {
		super(name, parent,new JavaFXDrawingInformation(Color.CORNSILK), new LolliShape(size,size*2,size/2), size, orbitingDistance, speed);
		this.size=size;
		this.weapons = weapons;
	}
		

	public void attack(Point p) {
		// Carriers cannot attack points?
		// TODO: SensorArray for Point p with given Radious and spread attack for every found valid target!
	}
	
	@Override
	public boolean isCarrier() {
		return true;
	}
	
	public void attack(SpaceObject o) {
		primaryWeapon.setTarget(o);
		primaryWeapon.activate();
	}
	
	public Carrier rebuildAt(String name, SpaceObject at) {
		Carrier copy = new Carrier(name,at,size,(int) orbitingDistance,speed,weapons);
		return copy;
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
		
		this.primaryWeapon = WeaponFactory.combineDroneMountsToDroneRack(this);
		this.secondaryWeapon = null;
	}
	
	

}