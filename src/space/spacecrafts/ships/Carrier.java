package space.spacecrafts.ships;

import java.util.LinkedList;
import java.util.List;

import drawing.JavaFXDrawingInformation;
import geom.LolliShape;
import geom.UShape;
import interfaces.logical.CollidingObject;
import interfaces.spacecraft.CarrierDrone;
import javafx.scene.paint.Color;
import space.advanced.Asteroid;
import space.core.SpaceObject;
import space.spacecrafts.ships.missiles.Missile;

public abstract class Carrier extends Ship{
	
	protected int maxShips = 3; // How Many Ships can the carrier have active?
	protected List<CarrierDrone> drones; 
	protected int shipCounter = 0; // Overall Ships Build
	protected int shipCooldown = 0; 
	
	protected int size;
	
	public Carrier(String name, SpaceObject parent, int size, int orbitingDistance, double speed) {
		super(name, parent,new JavaFXDrawingInformation(Color.CORNSILK), new LolliShape(size,size*2,size/2), size, orbitingDistance, speed);
		this.size=size;
		
		drones=new LinkedList<CarrierDrone>();
		for(int i = 0; i<maxShips;i++)
			spawnShip();
		shipCooldown=0; //For Init no cooldown
	}
	
	@Override
	public boolean collides(CollidingObject other) {
		//Don't collide with Children
		if(other instanceof LaserDrone && drones.contains((LaserDrone)other))
			return false;
		// Don't collide with Childrens missiles TODO Reimplement this
		/*if(other instanceof Missile)
			//if(drones.stream().anyMatch(t -> t.getAllChildrenFlat().contains(other)))
				return false;
				*/
		return super.collides(other);
	}
	
	@Override
	public void update() {
		super.update();
		removeDeadShips();
		
		if(!hasFullShips()&&shipCooldown<=0)
			spawnShip();
		shipCooldown --;
	}
	
	public void launchShips(SpaceObject target) {
		for(CarrierDrone ship : drones) {
			ship.setTarget(target);
			ship.launch();
		}
	}
	
	public void revokeShips() {
		for(CarrierDrone ship : drones) {
			ship.setTarget(this);
			ship.launch();
		}
	}
	
	protected void removeDeadShips() {
		drones.removeIf(s -> s.isDead());
	}
	
	protected void spawnShip() {
		//LaserDrone spawned = new LaserDrone(name + "'s ship no." + shipCounter, this, size/2 ,size*3,speed*4);
		//spawned.relativePos = shipSpawnAngle(shipCounter,maxShips);
		//drones.add(spawned);
		shipCounter++;
		setShipCooldown();
	}
	
	protected void setShipCooldown() {
		shipCooldown = 30000/25; //UpdateRatio is 25/s so this will take 30 seconds
	}
	
	public boolean isBuilding() {
		return shipCooldown>0;
	}
	
	// Returns an angle depending on the counter i and maxShips max 
	protected double shipSpawnAngle(int counter, int max) {
		counter = counter%max;
		return counter* (Math.PI*2/max);
	}
	
	public void removeFirstShipWithoutTrash() {
		drones.get(0).destruct();
		removeDeadShips();
		getAllChildrenFlat().stream().filter(t-> t instanceof Asteroid).map(s -> (Asteroid) s).forEach(a -> a.remove());
	}
	
	public void setMaxShips(int max) {
		if(max<0)
			throw new IllegalArgumentException("MaxShips cannot be smaller than 0!");
		if(maxShips > max && drones.size()> max) 
			for(int i = 0; i<maxShips-max;i++)
				drones.get(i).destruct();
		getAllChildrenFlat().stream().filter(t-> t instanceof Asteroid).map(s -> (Asteroid) s).forEach(a -> a.remove());
		removeDeadShips();	
		maxShips = max;	
	}
	
	public int getMaxShips() {return maxShips;}
	public int getCurrentShipCount() {return drones.size();}
	public boolean hasFullShips() {return drones.size()==maxShips;}
	public boolean hasNoShips() {return drones.isEmpty();}
	public List<CarrierDrone> getCurrentShips(){return drones;}
	
}