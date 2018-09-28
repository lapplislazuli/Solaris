package geom;

import java.util.LinkedList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;

@SuppressWarnings("restriction")
public abstract class CombinedShape implements Shape{
	
	protected List<Shape> parts;
	protected Point center;
	protected int levelOfDetail=3;
	
	public CombinedShape() {
		parts=new LinkedList<Shape>();
	}
	
	public double area() {
		double sum=0;
		for(Shape part:parts)
			sum+=part.area();
		return sum;
	}

	public boolean intersects(Shape other) {
		return parts.stream().anyMatch(r->intersects(other));
	}

	public boolean isCovered(int x, int y) {
			return parts.stream().anyMatch(r->isCovered(x,y));
	}
	
	public boolean covers(Shape other) {
		return parts.stream().anyMatch(p->p.covers(other));
	}
	
	public boolean contains(Point p) {
		return parts.stream().anyMatch(r->contains(p));
	}
	
	public void initOutline() {
		for(Shape part : parts)
			part.initOutline();
	}
	
	public void draw(GraphicsContext gc) {
		for(Shape part : parts)
			part.draw(gc);
	}
	
	public void setCenter(Point p) {
		center=p;
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
