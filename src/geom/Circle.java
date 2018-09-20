/**
 * @Author Leonhard Applis
 * @Created 20.09.2018
 * @Package geom
 */
package geom;

import javafx.scene.canvas.GraphicsContext;

@SuppressWarnings("restriction")
public class Circle extends BaseArea{

	int radious;
	
	protected Circle(Point center, int radious) {
		super(center);
		this.radious=radious;
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean contains(Point p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean intersects(Area other) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean covers(Area other) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void draw(GraphicsContext gc) {
		gc.fillOval(center.x-radious/2, center.y-radious/2, radious, radious);
	}

	@Override
	public boolean isCovered(int x, int y) {
		return contains(new Point(x,y));
	}
	
}
