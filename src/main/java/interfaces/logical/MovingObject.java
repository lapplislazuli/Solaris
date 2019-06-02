package interfaces.logical;

import interfaces.geom.Point;

public interface MovingObject {
	
	public void move(Point point);
	
	public double getSpeed();
	
	public void setSpeed(double val);
}
