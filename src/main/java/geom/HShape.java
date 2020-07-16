package geom;

import interfaces.geom.Shape;

public class HShape extends CombinedShape{
	
	private int width,height,barThickness;
	
	public HShape(int width, int height, int barthickness) {
		super();

		this.width=width; this.height=height;this.barThickness=barThickness;
		
		Rectangle midbar = new Rectangle(new RelativePoint(center,0,0), width, barthickness);
		Rectangle leftbar = new Rectangle(new RelativePoint(center,width/2,0), barthickness,height);
		Rectangle rightbar = new Rectangle(new RelativePoint(center,-width/2,0), barthickness, height);
		
		parts.add(midbar);
		parts.add(leftbar);
		parts.add(rightbar);
	}
	
	@Override
	public Shape copy() {
		return new HShape(width,height,barThickness);
	}
	
}
