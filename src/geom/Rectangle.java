package geom;

import interfaces.geom.Point;
import javafx.scene.canvas.GraphicsContext;

@SuppressWarnings("restriction")
public class Rectangle extends BaseShape{
	
	int xSize,ySize;
	
	public Rectangle(int xSize, int ySize) {
		super();
		this.xSize=xSize;
		this.ySize=ySize;
	}
	
	public Rectangle(Point center, int xSize, int ySize) {
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

	public void initOutline() {
		outLine.clear();
		
		for(int i=-levelOfDetail/2;i<=levelOfDetail/2;i++) {
			//Upper Edge
			outLine.add(new RelativePoint(center,i*(xSize/levelOfDetail),-(ySize)/2));
			//Lefthand Edge
			outLine.add(new RelativePoint(center,-(xSize)/2,i*(ySize/levelOfDetail)));
			//Bottom Edge
			outLine.add(new RelativePoint(center,i*(xSize/levelOfDetail),(ySize)/2));
			//Righthand Edge
			outLine.add(new RelativePoint(center,(xSize)/2,i*(ySize/levelOfDetail)));
		}
	}

	public double area() {
		return xSize*ySize;
	}
	
	@Override 
	public boolean equals(Object other) {
		if(! (other instanceof Rectangle))
			return false;
		Rectangle otherParsed = (Rectangle) other;
		
		if(!getCenter().equals(otherParsed.getCenter()))
			return false;
		return otherParsed.xSize==xSize && otherParsed.ySize==ySize;
	}
}
