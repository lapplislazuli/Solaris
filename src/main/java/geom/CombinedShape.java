package geom;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import interfaces.drawing.DrawingContext;
import interfaces.geom.Point;
import interfaces.geom.Shape;

public abstract class CombinedShape implements Shape{
	
	protected List<Shape> parts;
	protected Point center;
	protected int levelOfDetail = 3;
	
	public CombinedShape() {
		parts = new LinkedList<Shape>();
		center = new AbsolutePoint(0,0);
	}
	
	public CombinedShape(Point center) {
		parts = new LinkedList<Shape>();
		this.center = center;
	}
	
	public double area() {
		return parts.stream().mapToDouble(p -> p.area()).sum();
	}

	public boolean intersects(Shape other) {
		 return parts.stream().anyMatch(p -> p.intersects(other));
	}
	
	public boolean covers(Shape other) {
		return other.getOutline().stream()
			.allMatch(
				p -> parts.stream().anyMatch(k -> k.contains(p))			
			);	
	}
	
	public boolean contains(Point p) {
		 return parts.stream().anyMatch(a-> a.contains(p));
	}
	
	public void updateOrInitOutline() {
		for(Shape part : parts) {
			part.updateOrInitOutline();
		}
	}
	
	public void draw(DrawingContext dc) {
		for(Shape part : parts) {
			part.draw(dc);
		}
	}
	
	public void setCenter(Point p) {
		center = p;
		for(Shape part : parts) {
			part.setCenter(p);
		}
	}
	public Point getCenter() {return center;}
	
	public boolean sameShape(Shape other) {
		if(!(other instanceof CombinedShape)) {
			return false;
		}
		CombinedShape otherCasted=(CombinedShape)other;
		if(otherCasted == this) {
			return true;
		}
		return 
			center.samePosition(otherCasted.getCenter())
			&& area() == otherCasted.area();
	}
	
	public int getLevelOfDetail() {
		return levelOfDetail;
	}
	
	public void setLevelOfDetail(int lod) {
		levelOfDetail = lod;
		for(Shape part : parts) {
			part.setLevelOfDetail(lod);
		}
	}

	@Override
	public Collection<Point> getOutline() {
		return parts.stream().flatMap(p -> p.getOutline().stream()).collect(Collectors.toList());
	}
	
	
	
}
