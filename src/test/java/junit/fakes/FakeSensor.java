package junit.fakes;


import java.util.LinkedList;
import java.util.List;

import interfaces.geom.Point;
import interfaces.logical.CollidingObject;
import space.spacecrafts.ships.Sensor;

public class FakeSensor implements Sensor {
	
	public List<CollidingObject> detectedItems = new LinkedList<CollidingObject>();

	public void update() {}

	public void move(Point parentCenter) {}
	
	public double getSpeed() {return 0;}
	
	public void setSpeed(double val) {}

	public List<CollidingObject> getDetectedItems() {
		return detectedItems;
	}

}
