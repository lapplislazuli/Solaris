package geom;

import javafx.scene.canvas.GraphicsContext;

@SuppressWarnings("restriction")
public class Circle extends BaseArea{
	public int radious;
	public int levelOfDetail=4;
	
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
		gc.fillOval(center.x-radious/2, center.y-radious/2, radious, radious);
	}

	@Override
	public void initOutline() {
		outLine.removeIf(p->true);
		for(int i=0;i<levelOfDetail;i++) {
			Point outLinePoint=center.clone();
			outLinePoint.move((int)(Math.cos(i*Math.PI*2/levelOfDetail)*radious), -(int)(Math.sin(i*Math.PI*2/levelOfDetail)*radious));
			outLine.add(outLinePoint);
		}
	}
	
}
