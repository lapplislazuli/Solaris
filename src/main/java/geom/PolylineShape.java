package geom;

import java.util.List;

import interfaces.geom.Point;
import interfaces.geom.Shape;

public abstract class PolylineShape extends BaseShape {

	
	protected PolylineShape(Point center,List<Point> outline) {
		this.outLine=outline;
		this.center=center;
	}
	
	@Override
	public boolean contains(Point p) {
		return getBoundingBox().contains(p);
	}

	@Override
	public double area() {
		return getBoundingBox().area();
	}
	
	@Override
	public boolean covers(Shape other) {
		return getBoundingBox().covers(other);
	}
	
	@Override
	public void initOutline() {
		// Maybe: We need to infer additional points of the shape in regard of the level of detail
		// this would have to be done in the sub-classes
	}
	
	/*
	 * The bounding box is a simple approximation of the shape
	 * It is not completely correct, but a fair estimate for stuff like covering, area and containing points.
	 * its a better estimate for small items
	 * (Proportionally, the bounding box is always the same wrong. 
	 * But when it comes to clicking, its harder to notice any difference)
	 */
	public Rectangle getBoundingBox(){
		int max_x=0, max_y=0,min_x=100000,min_y=100000;
		for (Point p : outLine) {
			max_x = Math.max(max_x,p.getX());
			min_x = Math.min(min_x,p.getX());
			max_y = Math.max(max_y,p.getY());
			min_y = Math.min(min_y,p.getY());
		}
		
		var y_size = max_y-min_y / 2;
		var x_size = max_x-min_x / 2;
		var x_center = min_x+x_size;
		var y_center = min_y+y_size;
		
		var bbox = new Rectangle(new AbsolutePoint(x_center,y_center),x_size,y_size);
		bbox.setLevelOfDetail(this.levelOfDetail);
		bbox.initOutline();
		
		return bbox;
	}
}
