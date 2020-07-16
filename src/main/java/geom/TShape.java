package geom;

import interfaces.geom.Shape;

public class TShape extends CombinedShape{

	private int width,length,barThickness;
	
	public TShape(int width, int length, int barThickness) {
		super();

		this.width=width; this.length=length;this.barThickness=barThickness;
		
		Rectangle stick = new Rectangle(new RelativePoint(center,barThickness/2,0), length, barThickness);
		Rectangle bar = new Rectangle(new RelativePoint(center,-barThickness/2,0), barThickness, width);
		
		parts.add(stick);
		parts.add(bar);	
	}

	@Override
	public Shape copy() {
		return new TShape(width,length,barThickness);
	}

}
