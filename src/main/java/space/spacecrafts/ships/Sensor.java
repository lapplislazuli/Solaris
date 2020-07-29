package space.spacecrafts.ships;

import java.util.List;

import interfaces.geom.Point;
import interfaces.logical.CollidingObject;

public interface Sensor {

	void update();

	void move(Point parentCenter);

	double getSpeed();
	double getSize();

	void setSpeed(double val);
	void setSize(double val);

	List<CollidingObject> getDetectedItems();

}