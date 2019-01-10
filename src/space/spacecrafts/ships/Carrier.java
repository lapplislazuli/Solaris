package space.spacecrafts.ships;

import java.util.LinkedList;
import java.util.List;

import drawing.JavaFXDrawingInformation;
import geom.LolliShape;
import geom.UShape;
import interfaces.logical.CollidingObject;
import javafx.scene.paint.Color;
import space.advanced.Asteroid;
import space.core.SpaceObject;
import space.spacecrafts.ships.missiles.Missile;

public class Carrier extends Ship{
	
	private int maxShips = 3; // How Many Ships can the carrier have active?
	private List<CarrierShip> ships; 
	private int shipCounter = 0; // Overall Ships Build
	private int shipCooldown = 0; 
	
	private int size;
	
	public Carrier(String name, SpaceObject parent, int size, int orbitingDistance, double speed) {
		super(name, parent,new JavaFXDrawingInformation(Color.CORNSILK), new LolliShape(size,size*2,size/2), size, orbitingDistance, speed);
		this.size=size;
		
		ships=new LinkedList<CarrierShip>();
		for(int i = 0; i<maxShips;i++)
			spawnShip();
		shipCooldown=0; //For Init no cooldown
	}
	
	@Override
	public boolean collides(CollidingObject other) {
		//Don't collide with Children
		if(other instanceof CarrierShip && ships.contains((CarrierShip)other))
			return false;
		// Don't collide with Childrens missiles
		if(other instanceof Missile)
			if(ships.stream().anyMatch(t -> t.getAllChildrenFlat().contains(other)))
				return false;
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
		for(Ship ship : ships) {
			ship.target=target;
			ship.launch();
		}
	}
	
	public void revokeShips() {
		for(Ship ship : ships) {
			ship.target=this;
			ship.launch();
		}
	}
	
	private void removeDeadShips() {
		ships.removeIf(s -> s.isDead());
	}
	
	private void spawnShip() {
		shipCounter++;
		CarrierShip spawned = new CarrierShip(name + "'s ship no." + shipCounter, this, size/2 ,size*3,speed*4);
		spawned.relativePos = shipSpawnAngle(shipCounter,maxShips);
		ships.add(spawned);
		
		setShipCooldown();
	}
	
	private void setShipCooldown() {
		shipCooldown = 30000/25; //UpdateRatio is 25/s so this will take 30 seconds
	}
	
	public boolean isBuilding() {
		return shipCooldown>0;
	}
	
	// Returns an angle depending on the counter i and maxShips max 
	private double shipSpawnAngle(int counter, int max) {
		counter = counter%max;
		return counter* (Math.PI*2/max);
	}
	
	public void removeFirstShipWithoutTrash() {
		ships.get(0).destruct();
		removeDeadShips();
		getAllChildrenFlat().stream().filter(t-> t instanceof Asteroid).map(s -> (Asteroid) s).forEach(a -> a.remove());
	}
	
	public void setMaxShips(int max) {
		if(max<0)
			throw new IllegalArgumentException("MaxShips cannot be smaller than 0!");
		if(maxShips > max && ships.size()> max) 
			for(int i = 0; i<maxShips-max;i++)
				ships.get(i).destruct();
		getAllChildrenFlat().stream().filter(t-> t instanceof Asteroid).map(s -> (Asteroid) s).forEach(a -> a.remove());
		removeDeadShips();	
		maxShips = max;	
	}
	
	public int getMaxShips() {return maxShips;}
	public int getCurrentShipCount() {return ships.size();}
	public boolean hasFullShips() {return ships.size()==maxShips;}
	public boolean hasNoShips() {return ships.isEmpty();}
	public List<CarrierShip> getCurrentShips(){return ships;}
	
}