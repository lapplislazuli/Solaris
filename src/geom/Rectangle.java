/**
 * @Author Leonhard Applis
 * @Created 20.09.2018
 * @Package geom
 */
package geom;

import javafx.scene.canvas.GraphicsContext;

@SuppressWarnings("restriction")
public class Rectangle extends BaseArea{
	
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
				( p.x <= center.x+xSize/2	&&  p.x >=center.x-xSize/2) &&
				( p.y <= center.y+ySize/2  	&&  p.y >=center.y-ySize/2);
	}

	@Override
	public void draw(GraphicsContext gc) {
		gc.fillRect(center.x-xSize/2, center.y-ySize/2, xSize, ySize);
	}

	@Override
	public void initOutline() {
		outLine.removeIf(p->true);
		for(int i=-levelOfDetail/2;i<levelOfDetail/2;i++) {
			Point outLinePoint=center.clone();
			outLinePoint.move(i*xSize/levelOfDetail,i*ySize/levelOfDetail);
			outLine.add(outLinePoint);
		}
	}
	
}
