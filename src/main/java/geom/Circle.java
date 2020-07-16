package geom;

import java.util.Collection;

import interfaces.geom.Point;
import interfaces.geom.Shape;
import javafx.scene.canvas.GraphicsContext;

public class Circle extends BaseShape{
	public double radious;
	
	public Circle(int radious) {
		super();
		this.radious=radious;
	}
	
	public Circle(Point center, int radious) {
		super(center);
		if(radious<0)
			throw new IllegalArgumentException("radious cannot smaller than 0!");
		this.radious=radious;
	}

	@Override
	public boolean contains(Point p) {
		return (center.distanceTo(p) <= radious);
	}

	@Override
	public void draw(GraphicsContext gc) {
		gc.fillOval(center.getX()-radious, center.getY()-radious, radious*2, radious*2);
	}

	public void initOutline() {
		//Reset!
		outLine.removeIf(p->true);
		//New Points
		for(int i=0;i<levelOfDetail;i++) {
			RelativePoint outLinePoint= 
					new RelativePoint(center,
							(int)(Math.cos(i*Math.PI*2/levelOfDetail)*radious),
							(int)(Math.sin(i*Math.PI*2/levelOfDetail)*radious));
			outLine.add(outLinePoint);
		}
	}

	public double area() {
		return Math.PI*Math.pow(radious, 2);
	}
	
	@Override 
	public boolean equals(Object other) {
		if(! (other instanceof Circle))
			return false;
		Circle otherParsed = (Circle) other;
		
		if(!getCenter().equals(otherParsed.getCenter()))
			return false;
		return otherParsed.radious==radious;
	}

	@Override
	public Collection<Point> getOutline() {
		return outLine;
	}

	@Override
	public Shape copy() {
		return new Circle(center.clone(),(int) radious);
	}
}
