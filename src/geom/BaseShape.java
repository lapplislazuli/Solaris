package geom;

import java.util.LinkedList;
import java.util.List;

import interfaces.geom.Point;
import interfaces.geom.Shape;

public abstract class BaseShape implements Shape{
	public Point center;
	public List<Point> outLine= new LinkedList<Point>();
	protected int levelOfDetail=10;
	
	protected BaseShape(Point center) {
		this.center=center;
		outLine.add(center);
	}
	
	protected BaseShape() {
		center= new AbsolutePoint(0,0);
		outLine.add(center);
	}
	
	public boolean intersects(Shape other) {
		if(other != this && other instanceof Shape) {
			return outLine.stream().anyMatch(p->other.contains(p)) || covers(other);
		}
		return false;
	}

	public boolean isCovered(int x, int y) {
		return contains(new AbsolutePoint(x,y));
	}
	
	public boolean covers(Shape other) {
		if(other instanceof BaseShape)
			return ((BaseShape)other).outLine.stream().allMatch(p->contains(p));
		return false;
	}
	
	public void updateOrInitOutline() {
		if(outLine.size()<levelOfDetail)
			initOutline();
	};
	
	public abstract void initOutline();
	
	public void setCenter(Point p) {
		if(center instanceof RelativePoint)
			((RelativePoint)center).anker=p;
		else
			center=p;
	}
	public Point getCenter() { return center;}
	
	public int getLevelOfDetail() {
		return levelOfDetail;
	}
	
	public void setLevelOfDetail(int lod) {
		levelOfDetail=lod;
	}
	
	public boolean sameShape(Shape other) {
		if(!(other instanceof BaseShape))
			return false;
		return 
			center.samePosition(other.getCenter())
			&& area()==other.area();
	}
}
