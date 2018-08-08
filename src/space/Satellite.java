package space;

public class Satellite extends MovingSpaceObject{
	public Satellite(String name, SpaceObject parent, int size, int distance, double speed) {
		super(name, parent,null, size, distance, speed);
	}
	@Override
	protected void move(int parentX, int parentY) {
		super.move(parentX, parentY);
		//ToDo: Change my Rotation
	}
}
