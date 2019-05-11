package space.spacecrafts.ships;

import java.util.LinkedList;
import java.util.List;

import drawing.JavaFXDrawingInformation;
import geom.LolliShape;
import interfaces.logical.CollidingObject;
import interfaces.spacecraft.ArmedSpacecraft;
import interfaces.spacecraft.CarrierDrone;
import javafx.scene.paint.Color;
import space.advanced.Asteroid;
import space.core.SpaceObject;
import space.spacecrafts.ships.missiles.Missile;

@SuppressWarnings("restriction")
public abstract class Carrier extends Ship{
	
	protected int maxShips = 3; // How Many Ships can the carrier have active?
	protected List<CarrierDrone> drones; 
	protected int shipCounter = 0; // Overall Ships Build, for numbering and statistics
	protected int shipCooldown = 0; 
	
	protected int size;
	
	public Carrier(String name, SpaceObject parent, int size, int orbitingDistance, double speed) {
		super(name, parent,new JavaFXDrawingInformation(Color.CORNSILK), new LolliShape(size,size*2,size/2), size, orbitingDistance, speed);
		
		this.size=size;
		
		drones=new LinkedList<CarrierDrone>();
		for(int i = 0; i<maxShips;i++) {
			spawnShip();
			drones.get(i).move(center);
		}
		shipCooldown=0; //For Init no cooldown
	}
	
	@Override
	public boolean collides(CollidingObject other) {
		if(super.collides(other)) {
			if(other instanceof CarrierDrone && drones.contains((CarrierDrone)other)) //Don't collide with Children
				return false;
			if(other instanceof Missile) {	// Don't collide with Childrens missiles
				if (drones.stream()
						.filter(d -> d instanceof ArmedSpacecraft)
						.filter(d -> d instanceof Ship)
						.map(d -> (Ship)d )
						.anyMatch(d-> d.getAllChildren().contains(other)))
					return false;
			}
			return true;
		}
		return false;
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
		//REMOVE TRASH -- TRASH IS ASTEROID
		getAllChildren().stream().filter(t-> t instanceof Asteroid).map(s -> (Asteroid) s).forEach(a -> a.remove());
	}
	
	public void setMaxShips(int max) {
		if(max<0)
			throw new IllegalArgumentException("MaxShips cannot be smaller than 0!");
		if(maxShips > max && drones.size()> max) 
			for(int i = 0; i<maxShips-max;i++)
				drones.get(i).destruct();
		//REMOVE TRASH -- TRASH IS ASTEROID
		getAllChildren().stream().filter(t-> t instanceof Asteroid).map(s -> (Asteroid) s).forEach(a -> a.remove());
		removeDeadShips();	
		maxShips = max;	
	}
	
	public int getMaxShips() {return maxShips;}
	public int getCurrentShipCount() {return drones.size();}
	public boolean hasFullShips() {return drones.size()==maxShips;}
	public boolean hasNoShips() {return drones.isEmpty();}
	public List<CarrierDrone> getCurrentShips(){return drones;}
	
}