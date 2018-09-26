package geom;

import interfaces.DrawingObject;

public interface Area extends DrawingObject{
	public boolean contains(Point p);
	public boolean intersects(Area other);
	public boolean covers(Area other);
}
