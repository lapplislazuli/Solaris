package geom;

import java.util.LinkedList;
import java.util.List;

public abstract class BaseShape implements Shape{
	public Point center;
	public List<Point> outLine= new LinkedList<Point>();
	protected int levelOfDetail=10;
	
	protected BaseShape(Point center) {
		this.center=center;
		outLine.add(center);
	}
	
	protected BaseShape() {
		center= new Point(0,0);
		outLine.add(center);
	}
	
	public boolean intersects(Shape other) {
		if(other != this && other instanceof BaseShape) {
			return other.covers(this)
					|| ((BaseShape)other).outLine.stream().anyMatch(p->contains(p));
		}
		return false;
	}

	public boolean isCovered(int x, int y) {
		return contains(new Point(x,y));
	}
	
	public boolean covers(Shape other) {
		if(other instanceof BaseShape)
			return ((BaseShape)other).outLine.stream().allMatch(p->contains(p));
		return false;
	}
	
	public abstract void initOutline();
	
	public void setCenter(Point p) {
		center=p;
	}
	
	public int getLevelOfDetail() {
		return levelOfDetail;
	}
	
	public void setLevelOfDetail(int lod) {
		levelOfDetail=lod;
	}
}
