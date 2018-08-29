package logic;

import java.util.LinkedList;
import java.util.List;

import interfaces.UpdatingObject;
import javafx.scene.paint.Color;
import space.advanced.SpaceShuttle;
import space.core.MovingSpaceObject;
import space.core.Planet;
import space.core.SpaceObject;
import space.core.Planet.Builder;

/*
 * Pushes a Shuttle whenever its orbiting to another SpaceObject
 */

public class ShuttleNavigator implements UpdatingObject{
	
	private String name;
	private int currentPointer = 1;
	private List<SpaceObject> route;
	private SpaceShuttle shuttle;
	
	private boolean respawn; //Bool whether new Ships will be spawned
	private int idlingTime; //Time Spent to Idle on Planet before Relaunch in MS -- NOT USED
	private double idlingTurns; //Turns spend to Idle on Planet before Relaunch in Radiant-Degree
	private double currentIdlingTime; //Both for Time and Turns
	
	public ShuttleNavigator(String name, SpaceShuttle shuttle) {
		route= new LinkedList<SpaceObject>();
		route.add(shuttle.getParent());
		this.shuttle=shuttle;
		this.name=name;
	}
	
	public void addToRoute(SpaceObject next) {
		route.add(next);
	}
	
	public boolean removeFromRoute(SpaceObject next) {
		return route.remove(next);
	}
	
	public void update() {
		//Respawn
		if(shuttle.isDead()&&respawn) {
			rebuildShuttle();
		}
		
		if(shuttle.isOrbiting()) {
			if(currentIdlingTime>=idlingTurns) {
				
				if (currentPointer>=route.size())
					currentPointer=1;
				else
					currentPointer++;
				shuttle.setTarget(route.get(currentPointer-1));
				shuttle.launch();
				currentIdlingTime=0;
			}
			else {
				currentIdlingTime+=shuttle.getSpeed();
			}
		}
	}
	
	private void rebuildShuttle() {
		shuttle = new SpaceShuttle(shuttle.getName(),route.get(0),shuttle.getSize(),shuttle.getOrbitingDistance(),shuttle.getSpeed());
	}

	public static class Builder {
		private final String name;
		private String shuttleName;
		private Color color= Color.BLACK;
		private int orbitingDistance = 0;
		private int size = 0;
		private double speed = 0;
		private List<SpaceObject> route;
		

		private boolean respawn=false;
		private int idlingTime=0;
		private double idlingTurns=0;
		
		
		public Builder(String name) throws IllegalArgumentException{
			if(name==null||name.isEmpty())
				throw new IllegalArgumentException("Name cannot be null or empty");
			this.name=name;
			route = new LinkedList<SpaceObject>();
		}
		
		public Builder shuttleColor(Color val)
		{ color= val; return this;}
		
		public Builder start(SpaceObject o) {
			route.add(0, o);
			return this;
		}
		public Builder next(SpaceObject o) {
			route.add(o);
			return this;
		}
		public Builder shuttleName(String val) {
			shuttleName=val;
			return this;
		}
		public Builder shuttleOrbitingDistance(int val)
		{ orbitingDistance= val; return this;}
		
		public Builder shuttlesize(int val)
		{ size= val; return this;}
		
		public Builder shuttleSpeed(double val)
		{ speed= val; return this;}
		
		public Builder idlingTime(int val) {
			idlingTime=val;
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
		public ShuttleNavigator build() {
			return new ShuttleNavigator(this);
		}
	}
	
	private ShuttleNavigator(Builder builder) {
		shuttle = new SpaceShuttle(builder.shuttleName,builder.route.get(1),builder.size,builder.orbitingDistance,builder.speed);
		route=builder.route;
		name=builder.name;
		idlingTime=builder.idlingTime;
		idlingTurns=builder.idlingTurns;
		respawn=builder.respawn;
	}
}
