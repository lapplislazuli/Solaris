/**
 * @Author Leonhard Applis
 * @Created 31.08.2018
 * @Package space.shuttle
 */
package space.shuttle.missiles;

import interfaces.logical.RemovableObject;
import space.core.MovingSpaceObject;
import space.shuttle.SpaceShuttle;

public abstract class Missile extends MovingSpaceObject implements RemovableObject {
	
	SpaceShuttle emitter; //who shot me
	/*
	 * Rotation will be used as the direction of the missile
	 * speed will be used to move in direction, this will be an absolute value not relative to parents location
	 * Distance will be used to measure the flown distance, for remove logic
	 */

	public Missile(String name, SpaceShuttle emitter, int size,double direction, double speed) {
		super(name, emitter, null, size, 0,speed);
		this.emitter=emitter;
		rotation=direction;
	}
	
	@Override
	public void update() {
		move(0,0);
		if(distance>=150 || x<=0 || y <=0) 
			remove();
	}
	
	@Override
	public void move(int parentX, int parentY){
		int oldX=x; int oldY=y;
		x= (int) (x+Math.cos(rotation)*speed);
		y= (int) (y+Math.sin(rotation)*speed);
		distance += Math.sqrt((oldX-x)*(oldX-x)+(oldY-y)*(oldY-y));
	}
	
	@Override
	public void remove() {
		if(emitter!=null)
			emitter.trabants.remove(this);
		emitter=null;
	}
	@Override 
	public void rotate() {}
}
