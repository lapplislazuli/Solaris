package interfaces.geom;

import interfaces.DrawingObject;

public interface Shape extends DrawingObject{
	public boolean contains(Point p);
	public boolean intersects(Shape other);
	public boolean covers(Shape other);
	
	public double area();
	
	public Point getCenter();
	public void setCenter(Point p);
	
	public void updateOrInitOutline();
	public int getLevelOfDetail();
	public void setLevelOfDetail(int levelOfDetail);
	
	public boolean sameShape(Shape shape);
}
