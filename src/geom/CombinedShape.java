package geom;

import java.util.LinkedList;
import java.util.List;

import interfaces.geom.Point;
import interfaces.geom.Shape;
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
		for(Shape part:parts)
			if(part.isCovered(x,y))
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
		//System.out.println("Init Combined Outline");
		for(Shape part : parts)
			part.updateOrInitOutline();
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
