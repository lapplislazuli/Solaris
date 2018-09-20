/**
 * @Author Leonhard Applis
 * @Created 31.08.2018
 * @Package space.shuttle
 */
package space.shuttle;

import java.util.LinkedList;
import java.util.List;

import interfaces.logical.UpdatingObject;
import space.core.SpaceObject;

/*
 * Pushes a Shuttle whenever its orbiting to another SpaceObject
 */

public class ShuttleNavigator implements UpdatingObject{
	
	String name;
	private int currentPointer = 1;
	public List<SpaceObject> route;
	private ArmedSpaceShuttle shuttle;
	
	boolean respawn; //Bool whether new Ships will be spawned
	double idlingTurns,currentIdle; //Turns spend to Idle on Planet before Relaunch in Radiant-Degree
	
	public ShuttleNavigator(String name, ArmedSpaceShuttle shuttle) {
		route= new LinkedList<SpaceObject>();
		route.add(shuttle.parent);
		this.shuttle=shuttle;
		this.name=name;
	}
	
	public void update() {
		if(shuttle.isDead()&&respawn)
			rebuildShuttle();
		else if(shuttle.orbiting) {
			if((currentIdle+=shuttle.speed)>=idlingTurns) { //SpaceShuttle idled some time
				if (currentPointer++>=route.size()) 
					currentPointer=1;
				shuttle.target=route.get(currentPointer-1);
				shuttle.launch();
				currentIdle=0;
			}
		}
	}
	
	private void rebuildShuttle() {
		shuttle = new ArmedSpaceShuttle(shuttle.name,route.get(0),4,(int) shuttle.orbitingDistance,shuttle.speed);
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
		
		
		public Builder(String name) throws IllegalArgumentException{
			if(name==null||name.isEmpty())
				throw new IllegalArgumentException("Name cannot be null or empty");
			this.name=name;
			route = new LinkedList<SpaceObject>();
		}
		
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
		shuttle = new ArmedSpaceShuttle(builder.shuttleName,builder.route.get(1),builder.size,builder.orbitingDistance,builder.speed);
		route=builder.route;
		name=builder.name;
		currentIdle=0;
		idlingTurns=builder.idlingTurns;
		respawn=builder.respawn;
	}
}
