package space.core;

public class Satellite extends MovingSpaceObject{
	public Satellite(String name, SpaceObject parent, int size, int distance, double speed) {
		super(name, parent,null, size, distance, speed);
	}
	@Override
	public void move(int parentX, int parentY) {
		super.move(parentX, parentY);
		//ToDo: Change my Rotation
	}
}
