package geom;

import javafx.scene.canvas.GraphicsContext;

@SuppressWarnings("restriction")
public class Rectangle extends BaseShape{
	
	int xSize,ySize;
	
	public Rectangle(int xSize, int ySize) {
		super();
		this.xSize=xSize;
		this.ySize=ySize;
	}
	
	public Rectangle(AbsolutePoint center, int xSize, int ySize) {
		super(center);
		this.xSize=xSize;
		this.ySize=ySize;
	}

	@Override
	public boolean contains(Point p) {
		return 
				( p.getX() <= center.getX()+xSize/2	&&  p.getX() >=center.getX()-xSize/2) &&
				( p.getY() <= center.getY()+ySize/2 &&  p.getY() >=center.getY()-ySize/2);
	}

	@Override
	public void draw(GraphicsContext gc) {
		gc.fillRect(center.getX()-xSize/2, center.getY()-ySize/2, xSize, ySize);
	}

	@Override
	public void initOutline() {
		outLine.removeIf(p->true);
		for(int i=-levelOfDetail/2;i<levelOfDetail/2;i++) {
			//TODO: Make relative Points here
			AbsolutePoint outLinePoint=(AbsolutePoint)center.clone();
			outLinePoint.move(i*xSize/levelOfDetail,i*ySize/levelOfDetail);
			outLine.add(outLinePoint);
		}
	}

	public double area() {
		return xSize*ySize;
	}
}
