package junit.fakes;


import interfaces.geom.Point;
import interfaces.logical.MovingUpdatingObject;

public class FakeMovingSpaceObject implements MovingUpdatingObject {

	public boolean moving;
	public boolean moved;
	public boolean updating;
	public boolean updated;
	
	public Point aim;
	public Point current;
	
	public FakeMovingSpaceObject() {
		moving=true;
		moved=false;
		updating=true;
		updated=false;
	}
	
	public void move(Point point) {
		if(moving) {
			moved=true;
			if(aim!=null)
				current=aim;
		}
	}

	public void update() {
		if(updating)
			updated=true;
	}
	public double getSpeed() {
		return 0;
	}

	public void setSpeed(double val) {}

}
