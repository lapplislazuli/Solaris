package space.shuttle;

import interfaces.RemovableObject;
import space.core.MovingSpaceObject;

public abstract class Missile extends MovingSpaceObject implements RemovableObject {
	
	SpaceShuttle emitter; //Attribute who shot me
	/*
	 * Rotation will be used as the direction of the missile
	 * speed will be used to move in direction, this will be an absolute value not relative to parents location
	 * Distance will be used to measure the flown distance, for remove logic
	 */
	
	
	/*
	 * For relative-speeded Missiles like rockets
	 */
	public Missile(String name, SpaceShuttle emitter, int size) {
		super(name, emitter, null, size, 0,emitter.getSpeed()*emitter.getDistance()*2);
		this.emitter=emitter;
		rotation=emitter.getRotation();
	}
	
	/*
	 * For Absolute speeded Missiles like lasers
	 */
	public Missile(String name, SpaceShuttle emitter, int size, double speed) {
		super(name, emitter, null, size, 0, speed);
		this.emitter=emitter;
		rotation=emitter.getRotation();
	}
	@Override
	public void update() {
		super.update();
		if(distance>=150 || x<=0 || y <=0) {
			remove();
		}
	}
	
	@Override
	public void move(int parentX, int parentY){
		int oldX=x; int oldY=y;
		x= (int) (x+Math.cos(rotation)*speed);
		y= (int) (y+Math.sin(rotation)*speed);
		distance += Math.sqrt((oldX-x)*(oldX-x)+(oldY-y)*(oldY-y));
		//System.out.println(speed);
	}
	
	@Override
	public void remove() {
		if(emitter!=null)
			emitter.removeTrabant(this);
		emitter=null;
	}
	@Override 
	public void rotate() {
		//Normal Missiles don't Rotate!
	}
}
