package space.spacecrafts.ships.missiles;

import geom.BaseShape;
import geom.AbsolutePoint;
import interfaces.drawing.DrawingInformation;
import interfaces.geom.Point;
import interfaces.logical.CollidingObject;
import interfaces.logical.RemovableObject;
import space.core.MovingSpaceObject;
import space.spacecrafts.ships.Ship;

public abstract class Missile extends MovingSpaceObject implements RemovableObject {
	
	Ship emitter; //who shot me
	/*
	 * Rotation will be used as the direction of the missile
	 * speed will be used to move in direction, this will be an absolute value not relative to parents location
	 * Distance will be used to measure the flown distance, for remove logic
	 */

	public Missile(String name, Ship emitter, BaseShape area,DrawingInformation dInfo, double direction, double speed) {
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
	public void move(Point oldPosition){
		AbsolutePoint oldCenter=new AbsolutePoint(center.getX(),center.getY());
		center.setX((int) (center.getX()+Math.cos(rotation)*speed));
		center.setY((int) (center.getY()+Math.sin(rotation)*speed));
		distance += oldCenter.distanceTo(center);
	}
	
	@Override
	protected void updateDrawingInformation() {}
	
	@Override
	public void remove() {
		if(emitter!=null)
			emitter.getTrabants().remove(this);
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
	
	public boolean isOrphan() {
		return emitter==null;
	}
}
