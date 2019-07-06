package geom;

import java.util.LinkedList;
import java.util.List;

import interfaces.drawing.DrawingContext;
import interfaces.geom.Point;
import interfaces.geom.Shape;

public abstract class CombinedShape implements Shape{
	
	protected List<Shape> parts;
	protected Point center;
	protected int levelOfDetail=3;
	
	public CombinedShape() {
		parts=new LinkedList<Shape>();
		center=new AbsolutePoint(0,0);
	}
	
	public CombinedShape(Point center) {
		parts=new LinkedList<Shape>();
		this.center=center;
	}
	
	public double area() {
		double sum=0;
		for(Shape part:parts)
			sum+=part.area();
		return sum;
	}

	public boolean intersects(Shape other) {
		for(Shape part : parts)
			if(part.intersects(other))
				return true;
		return false;
	}
	
	public boolean covers(Shape other) {
		for(Shape part:parts)
			if(part.covers(other))
				return true;
		return false;
	}
	
	public boolean contains(Point p) {
		for(Shape part:parts)
			if(part.contains(p))
				return true;
		return false;
	}
	
	public void updateOrInitOutline() {
		for(Shape part : parts)
			part.updateOrInitOutline();
	}
	
	public void draw(DrawingContext dc) {
		for(Shape part : parts)
			part.draw(dc);
	}
	
	public void setCenter(Point p) {
		center=p;
		for(Shape part : parts)
			part.setCenter(p);
	}
	public Point getCenter() {return center;}
	
	public boolean sameShape(Shape other) {
		if(!(other instanceof CombinedShape))
			return false;
		CombinedShape otherCasted=(CombinedShape)other;
		if(otherCasted==this)
			return true;
		return 
			center.samePosition(otherCasted.getCenter())
			&& area()==otherCasted.area();
	}
	
	public int getLevelOfDetail() {
		return levelOfDetail;
	}
	
	public void setLevelOfDetail(int lod) {
		levelOfDetail=lod;
		for(Shape part : parts)
			part.setLevelOfDetail(lod);
	}
}
