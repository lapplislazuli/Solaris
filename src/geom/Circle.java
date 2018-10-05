package geom;

import interfaces.geom.Point;
import javafx.scene.canvas.GraphicsContext;

@SuppressWarnings("restriction")
public class Circle extends BaseShape{
	public double radious;
	
	public Circle(int radious) {
		super();
		this.radious=radious;
	}
	
	public Circle(Point center, int radious) {
		super(center);
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
		outLine.removeIf(p->true);
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
	
}
