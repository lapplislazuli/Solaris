/**
 * @Author Leonhard Applis
 * @Created 20.09.2018
 * @Package geom
 */
package geom;

import java.util.LinkedList;
import java.util.List;

public abstract class BaseArea implements Area{
	public Point center;
	public List<Point> outLine= new LinkedList<Point>();
	public int levelOfDetail=10;
	
	protected BaseArea(Point center) {
		this.center=center;
		outLine.add(center);
	}
	
	protected BaseArea() {
		center= new Point(0,0);
		outLine.add(center);
	}
	
	public boolean intersects(Area other) {
		if(other != this && other instanceof BaseArea) {
			return other.covers(this)
					|| ((BaseArea)other).outLine.stream().anyMatch(p->contains(p));
		}
		return false;
	}

	public boolean isCovered(int x, int y) {
		return contains(new Point(x,y));
	}
	
	public boolean covers(Area other) {
		if(other instanceof BaseArea)
			return ((BaseArea)other).outLine.stream().allMatch(p->contains(p));
		return false;
	}
	
	public abstract void initOutline();
}
