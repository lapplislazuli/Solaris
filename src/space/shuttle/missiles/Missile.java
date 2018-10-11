package space.shuttle.missiles;

import geom.BaseShape;
import geom.AbsolutePoint;
import interfaces.drawing.DrawingInformation;
import interfaces.logical.CollidingObject;
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

	public Missile(String name, SpaceShuttle emitter, BaseShape area,DrawingInformation dInfo, double direction, double speed) {
		super(name, emitter, dInfo, area, 0,speed);
		this.emitter=emitter;
		rotation=direction;
	}
	
	@Override
	public void update() {
		move(center);
		if(distance>=250 || center.getX()<=0 || center.getY() <=0) 
			remove();
	}
	
	@Override
	public void move(AbsolutePoint oldPosition){
		AbsolutePoint oldCenter=center.clone();
		center.setX((int) (center.getX()+Math.cos(rotation)*speed));
		center.setY((int) (center.getY()+Math.sin(rotation)*speed));
		distance += oldCenter.distanceTo(center);
	}
	
	@Override
	public void remove() {
		if(emitter!=null)
			emitter.trabants.remove(this);
		emitter=null;
	}
	@Override 
	public void rotate() {}
	
	@Override
	public boolean collides(CollidingObject other) {
		if(other == emitter)
			return false;
		return super.collides(other);
	}
}
