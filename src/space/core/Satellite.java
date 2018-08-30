package space.core;

import interfaces.CollidingObject;
import interfaces.DestructibleObject;
import space.advanced.Asteroid;

public class Satellite extends MovingSpaceObject implements DestructibleObject{
	public Satellite(String name, SpaceObject parent, int size, int distance, double speed) {
		super(name, parent,null, size, distance, speed);
	}
	@Override
	public void move(int parentX, int parentY) {
		super.move(parentX, parentY);
		//ToDo: Change my Rotation
	}
	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void destruct(CollidingObject other) {
		System.out.println("Spaceship: " + name + " collided with " + other.toString() + " @" + x + "|" + y);
		/*if(parent!=null) {
			if(other instanceof SpaceObject)
				new Asteroid("Trash from " + name,(SpaceObject) other,(int)orbitingDistance+parent.getSize(),speed,Asteroid.Type.TRASH);
			remove();
		}*/
		
	}
}
