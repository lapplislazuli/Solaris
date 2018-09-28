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
		center=new AbsolutePoint(0,0);
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

	public boolean isCovered(int x, int y) {
		return parts.size()>0 ?	parts.stream().anyMatch(r->isCovered(x,y)) : false;
	}
	
	public boolean covers(Shape other) {
		return parts.size()>0 ? parts.stream().anyMatch(p->p.covers(other)) : false;
	}
	
	public boolean contains(Point p) {
		return parts.size()>0 ? parts.stream().anyMatch(r->contains(p)) : false;
	}
	
	public void initOutline() {
		//System.out.println("Init Combined Outline");
		for(Shape part : parts)
			part.initOutline();
	}
	
	public void draw(GraphicsContext gc) {
		for(Shape part : parts)
			part.draw(gc);
	}
	
	public void setCenter(Point p) {
		center=p;
		for(Shape part : parts)
			part.setCenter(p);
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
