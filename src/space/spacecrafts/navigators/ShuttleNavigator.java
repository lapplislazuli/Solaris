package space.spacecrafts.navigators;

import java.util.LinkedList;
import java.util.List;

import org.pmw.tinylog.Logger;

import logic.interaction.PlayerManager;
import space.core.SpaceObject;
import space.spacecrafts.ships.ArmedSpaceShuttle;
import space.spacecrafts.ships.SpaceShuttle;

public class ShuttleNavigator implements Navigator{
	
	String name;
	private int currentPointer=0,shuttleSize;
	public List<SpaceObject> route;
	private ArmedSpaceShuttle shuttle;
	
	boolean isPlayer;
	boolean respawn; //Bool whether new Ships will be spawned
	double idlingTurns,currentIdle; //Turns spend to Idle on Planet before Relaunch in Radiant-Degree
	
	public ShuttleNavigator(String name, ArmedSpaceShuttle shuttle) {
		route= new LinkedList<SpaceObject>();
		route.add(shuttle.parent);
		this.shuttle=shuttle;
		this.name=name;
		Logger.info("Initiated " + name + " with Shuttle " + shuttle.name);
	}
	
	public void update() {
		if(shuttle.isDead() && respawn)
			rebuildShuttle();
		else if(shuttle.orbiting) {
			currentIdle+=Math.abs(shuttle.speed);
			if(currentIdle>=idlingTurns && isInGoodLaunchPosition(getNextWayPoint())) { //SpaceShuttle idled some time
				shuttle.target=getNextWayPoint();
				shuttle.launch();
				currentIdle=0;
				incrementPointer();
			}
		}
	}
	
	public void clearRoute() {
		route= new LinkedList<SpaceObject>();
		route.add(shuttle.parent);
	}
	
	public SpaceObject getNextWayPoint() {
		return route.get(currentPointer+1);
	}
	
	private void incrementPointer() {
		currentPointer++;
		if (currentPointer>=route.size()-1) 
			currentPointer=0;
	}
	
	private void rebuildShuttle() {
		shuttle = new ArmedSpaceShuttle(shuttle.name,route.get(0),shuttleSize,(int) shuttle.orbitingDistance,shuttle.speed);
		shuttle.setPlayer(isPlayer);
	}

	public static class Builder {
		private final String name;
		private String shuttleName;
		private int orbitingDistance = 0;
		private int size = 0;
		private double speed = 0;
		private List<SpaceObject> route;
		private boolean respawn=false;
		private double idlingTurns=0;
		
		private boolean isPlayer=false;
		
		public Builder(String name) throws IllegalArgumentException{
			if(name==null||name.isEmpty())
				throw new IllegalArgumentException("Name cannot be null or empty");
			this.name=name;
			route = new LinkedList<SpaceObject>();
		}
		
		public Builder start(SpaceObject o) {
			if(o==null)
				throw new IllegalArgumentException("Cannot add Null-SpaceObject to Route!");
			route.add(0, o);
			return this;
		}
		public Builder next(SpaceObject o) {
			if(o==null)
				throw new IllegalArgumentException("Cannot add Null-SpaceObject to Route!");
			route.add(o);
			return this;
		}
		public Builder shuttleName(String val) {
			if(val==null||val.isEmpty())
				throw new IllegalArgumentException("Name of the Shuttle cannot be null or empty");
			
			shuttleName=val;
			return this;
		}
		public Builder shuttleOrbitingDistance(int val){ 
			if(val<0)
				throw new IllegalArgumentException("Orbiting Distance cannot be null!");	
			orbitingDistance= val;
			return this;
		}
		
		public Builder shuttlesize(int val){ 
			if(val<0)
				throw new IllegalArgumentException("Size cannot be null!");
			
			size= val; 
			return this;}
		
		public Builder shuttleSpeed(double val){
			speed= val; 
			return this;
		}
		
		public Builder idlingTurns(double val) {
			idlingTurns=val; 
			return this;
		}
		public Builder doesRespawn(boolean val) {
			respawn=val;
			return this;
		}
		public Builder isPlayer(boolean val) {
			isPlayer=val;
			return this;
		}
		public ShuttleNavigator build() {
			return new ShuttleNavigator(this);
		}
	}
	
	private ShuttleNavigator(Builder builder) {
		shuttle = new ArmedSpaceShuttle(builder.shuttleName,builder.route.get(1),builder.size,builder.orbitingDistance,builder.speed);
		route=builder.route;
		name=builder.name;
		currentIdle=0;
		shuttleSize=builder.size;
		idlingTurns=builder.idlingTurns;
		respawn=builder.respawn;
		isPlayer=builder.isPlayer;
		shuttle.setPlayer(builder.isPlayer);
		if(isPlayer)
			PlayerManager.getInstance().registerPlayerNavigator(this);
	}

	@Override
	public SpaceShuttle getShuttle() {
		return shuttle;
	}

	@Override
	public List<SpaceObject> getRoute() {
		return route;
	}
}